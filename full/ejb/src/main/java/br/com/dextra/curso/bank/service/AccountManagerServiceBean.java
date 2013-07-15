package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.dextra.curso.bank.domain.Account;
import br.com.dextra.curso.bank.domain.Moviment;
import br.com.dextra.curso.bank.domain.Moviment.MovimentType;
import br.com.dextra.curso.bank.util.JMSSenderUtil;

@Stateless
public class AccountManagerServiceBean implements AccountManagerServiceLocal,
		AccountManagerServiceRemote {

	/**
	 * Logger
	 */
	private static final Log LOGGER = LogFactory
			.getLog(AccountManagerServiceBean.class);

	@Resource
	private SessionContext ctx;

	@PersistenceContext
	private EntityManager em;

	public void applyTax(Long accountId, String taxName, BigDecimal value) {
		Account account = em.find(Account.class, accountId);
		account.setCash(account.getCash().subtract(value));

		LOGGER.info(" ##################### ");
		LOGGER.info(" Aplicando Cobranca de " + taxName);
		this.createMoviment(account, "Cobranca de " + taxName,
				MovimentType.DEBIT, value);
	}

	public Account createAccount(Account account) {		
		em.persist(account);

		LOGGER.info(" ##################### ");
		LOGGER.info("  1 - Conta numero '" + account.getAccountNumber()
				+ "' (id " + account.getId() + ") criada.");

		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.MONTH, 1);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LOGGER.info("  2 - Agendando cobrança de administraçao para "
				+ sdf.format(c.getTime()));
		ctx.getTimerService().createTimer(c.getTime(), account.getId());

		return account;
	}

	public Account findAccountByOwner(String owner) {
		Query eql = em.createQuery("from AccountBean where owner = :owner");
		eql.setParameter("owner", owner);
		return (Account) eql.getSingleResult();
	}

	@Timeout
	public void applyAccountMonthlyTax(Timer timer) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LOGGER.info(" ##################### ");
		LOGGER.info("  1 - Timer para cobrança da taxa de admin disparado... ");

		Long accountId = (Long) timer.getInfo();
		BigDecimal adminTax = new BigDecimal("21.00");

		LOGGER.info("  2 - Debitando valor da conta ... ");
		Account account = em.find(Account.class, accountId);
		account.setCash(account.getCash().subtract(adminTax));

		LOGGER.info("  3 - Gerando movimento de cobrança... ");
		this.createMoviment(account, "Administraçao da conta",
				MovimentType.DEBIT, adminTax);
		timer.cancel();

		// Agenda o proximo aniversario da conta
		Calendar c = GregorianCalendar.getInstance();
		c.add(Calendar.MONTH, 1);
		ctx.getTimerService().createTimer(c.getTime(), account.getId());
		LOGGER.info("  4 - Agendando novo timer para cobrança em "
				+ sdf.format(c.getTime()));
	}

	private void createMoviment(Account account, String desc,
			MovimentType type, BigDecimal value) {
		Moviment mv = new Moviment();
		mv.setTarget(account);
		mv.setValue(value);
		mv.setDate(new Date());
		mv.setDesc(desc);
		mv.setType(type);

		em.persist(mv);

		JMSSenderUtil.sendJMSMessage(ctx, "queue/B", mv);
	}
	
}
