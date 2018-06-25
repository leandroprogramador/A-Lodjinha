package leandro.com.aludjinha.Helpers;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class FormatHelper {

    public static String formatMoney(Double decimal){
        try {
            BigDecimal valor = new BigDecimal(decimal);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            return nf.format(valor);
        } catch (Exception ex){
            return "0,00";
        }
    }
}
