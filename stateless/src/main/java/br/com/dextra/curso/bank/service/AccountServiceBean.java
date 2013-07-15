package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.dextra.curso.bank.domain.Account;
import br.com.dextra.curso.bank.util.BankUtil;

@Stateless
public class AccountServiceBean implements AccountServiceLocal {

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog(AccountServiceBean.class);

    public Account createAccount(String owner) {

        Date now = new Date();
        
        Account ret = new Account();
        ret.setId(now.getTime());
        ret.setAccountNumber(new SimpleDateFormat("yyyyMM-dd").format(now));
        ret.setOwner(owner);
        ret.setCash(new BigDecimal(0));
        BankUtil.bd.put(ret.getAccountNumber(), ret);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(now);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.MONTH, 1);

        LOGGER.info(" ##################### ");
        LOGGER.info("  1 - Criando conta para o titular: '" + owner + "'");
        LOGGER.info("  2 - Agendando cobrança de mensalidade: '" + sdf.format(c.getTime()));

        return ret;
    }

}
