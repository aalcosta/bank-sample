package br.com.dextra.curso.bank.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import br.com.dextra.curso.bank.domain.Account;

public class BankUtil {

    public static Map<String, Account> bd = new HashMap<String, Account>();
    
    public static String formatCurrency(BigDecimal cash) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance();
        df.setMinimumIntegerDigits(1);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(cash);
    }

}
