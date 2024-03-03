import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Oferta {
    private int maksOkresKredytowania;
    private BigDecimal maksMiesiecznaRata;
    private BigDecimal maksKwotaKredytu;

    public Oferta(int maksOkresKredytowania, BigDecimal maksMiesiecznaRata, BigDecimal maksKwotaKredytu) {
        this.maksOkresKredytowania = maksOkresKredytowania;
        this.maksMiesiecznaRata = maksMiesiecznaRata.setScale(2, HALF_UP);
        this.maksKwotaKredytu = maksKwotaKredytu.setScale(2, HALF_UP);
    }

    public int getMaksOkresKredytowania() {
        return maksOkresKredytowania;
    }

    public BigDecimal getMaksMiesiecznaRata() {
        return maksMiesiecznaRata;
    }

    public BigDecimal getMaksKwotaKredytu() {
        return maksKwotaKredytu;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "maksOkresKredytowania=" + maksOkresKredytowania +
                ", maksMiesiecznaRata=" + maksMiesiecznaRata +
                ", maksKwotaKredytu=" + maksKwotaKredytu +
                '}';
    }
}
