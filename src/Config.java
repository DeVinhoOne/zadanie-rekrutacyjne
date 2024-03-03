import java.math.BigDecimal;
import java.util.List;

public final class Config {
    public static final int OGRANICZNIE_GORNE_OKRESU_KREDYTOWANIA = 100;
    public static final BigDecimal MIN_KWOTA_KREDYTU = BigDecimal.valueOf(5_000);
    public static final BigDecimal MAKS_KWOTA_KREDYTU = BigDecimal.valueOf(150_000);
    public static final BigDecimal MAKS_ZAANGAZOWANIE = BigDecimal.valueOf(200_000);
    public static final int MIN_OKRES_KREDYTOWANIA = 6;

    private Config() {}

    public static Wskaznik pobierzWskaznik(int okresKredytowaniaMsc) {
        if (okresKredytowaniaMsc < 13)
            return new Wskaznik(0.6, 0.02);
        else if (okresKredytowaniaMsc < 37)
            return new Wskaznik(0.6, 0.03);
        else if (okresKredytowaniaMsc < 61)
            return new Wskaznik(0.5, 0.03);
        else if (okresKredytowaniaMsc < 101)
            return new Wskaznik(0.55, 0.03);

        throw new IllegalArgumentException(String.format("Podany okres jest większy niż maksymalny okres kredytowania (%d)", OGRANICZNIE_GORNE_OKRESU_KREDYTOWANIA));
    }

    public static class Wskaznik {
        private double dti;
        private double oprocentowanie;

        public Wskaznik(double dti, double oprocentowanie) {
            this.dti = dti;
            this.oprocentowanie = oprocentowanie;
        }

        public double getDti() {
            return dti;
        }

        public double getOprocentowanie() {
            return oprocentowanie;
        }
    }
}
