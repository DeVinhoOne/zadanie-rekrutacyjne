import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Oferta> oferty = obliczMaksymalneParametryKredytu(24, BigDecimal.valueOf(15000), BigDecimal.valueOf(6000), BigDecimal.valueOf(1000), BigDecimal.valueOf(500));
        if (oferty.isEmpty()) System.out.println("Brak zdolności kredytowej");
        else System.out.println(oferty);
    }

    public static List<Oferta> obliczMaksymalneParametryKredytu(int okresZatrudnienia,
                                                                BigDecimal miesiecznyDochod,
                                                                BigDecimal miesieczneKosztyUtrzymania,
                                                                BigDecimal mscSumaZobowiazanKredytowych,
                                                                BigDecimal sumaSaldKredytowRatalnych) {
        int maksOkresKredytowania = Math.min(okresZatrudnienia, Config.OGRANICZNIE_GORNE_OKRESU_KREDYTOWANIA);
        if (maksOkresKredytowania < Config.MIN_OKRES_KREDYTOWANIA) return Collections.emptyList();
        BigDecimal maksMiesiecznaRata = obliczMaksMiesiecznaRate(miesiecznyDochod, miesieczneKosztyUtrzymania, maksOkresKredytowania, mscSumaZobowiazanKredytowych);
        BigDecimal maksKwotaKredytu = obliczMaksKwoteKredytu(sumaSaldKredytowRatalnych, maksOkresKredytowania, maksMiesiecznaRata);
        if (maksKwotaKredytu.compareTo(Config.MIN_KWOTA_KREDYTU) < 0) return Collections.emptyList();

        return List.of(new Oferta(maksOkresKredytowania, maksMiesiecznaRata, maksKwotaKredytu));
    }

    private static BigDecimal obliczMaksMiesiecznaRate(BigDecimal miesiecznyDochod,
                                                       BigDecimal miesieczneKosztyUtrzymania,
                                                       int maksOkresKredytowania,
                                                       BigDecimal mscSumaZobowiazanKredytowych) {
        BigDecimal a1 = miesiecznyDochod.subtract(miesieczneKosztyUtrzymania).subtract(mscSumaZobowiazanKredytowych);
        Config.Wskaznik wskaznik = Config.pobierzWskaznik(maksOkresKredytowania);
        BigDecimal a2 = miesiecznyDochod.multiply(BigDecimal.valueOf(wskaznik.getDti())).subtract(mscSumaZobowiazanKredytowych);
        return min(a1, a2);
    }

    private static BigDecimal obliczMaksKwoteKredytu(BigDecimal sumaSaldKredytowRatalnych, int maksOkresKredytowania, BigDecimal maksMiesiecznaRata) {
        BigDecimal b1 = Config.MAKS_ZAANGAZOWANIE.subtract(sumaSaldKredytowRatalnych);
        BigDecimal b2 = Config. MAKS_KWOTA_KREDYTU;
        Config.Wskaznik wskaznik = Config.pobierzWskaznik(maksOkresKredytowania);
        BigDecimal mi = BigDecimal.valueOf(wskaznik.getOprocentowanie() / 12);
        BigDecimal licznik = maksMiesiecznaRata.subtract(mi.add(BigDecimal.ONE)).subtract(BigDecimal.valueOf(maksOkresKredytowania));
        BigDecimal b3 = licznik.divide(mi, RoundingMode.HALF_UP);
        return min(min(b1, b2), b3);
    }

    private static BigDecimal min(BigDecimal a1, BigDecimal a2) {
        return a1.compareTo(a2) < 0 ? a1 : a2;
    }
}