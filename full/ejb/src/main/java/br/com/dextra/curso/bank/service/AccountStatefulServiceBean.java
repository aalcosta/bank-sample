package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.dextra.curso.bank.domain.Account;
import br.com.dextra.curso.bank.domain.Moviment;
import br.com.dextra.curso.bank.domain.Moviment.MovimentType;
import br.com.dextra.curso.bank.exceptions.AccountNotFoundException;
import br.com.dextra.curso.bank.exceptions.NoAvailableCashException;
import br.com.dextra.curso.bank.interceptors.AccountTaxStatefulInterceptor;
import br.com.dextra.curso.bank.util.JMSSenderUtil;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RolesAllowed(value = "bankAccountOwner")
public class AccountStatefulServiceBean implements AccountStatefulService {

	@Resource
	private SessionContext ctx;

	@PersistenceContext(unitName = "teste")
	private EntityManager em;

	private Account account;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void setAccount(String number) throws AccountNotFoundException {
		Query eql = em.createQuery("from Account where accountNumber = :x");
		eql.setParameter("x", number);
		this.account = (Account) eql.getSingleResult();
	}

	public Account getAccount() {
		return this.account;
	}

	public BigDecimal getCash() {
		return account.getCash();
	}

	public void deposit(BigDecimal value) throws NoAvailableCashException {
		if (account.getCash().compareTo(value) < 0) {
			throw new NoAvailableCashException();
		}
		account.setCash(account.getCash().subtract(value));

		this.createMoviment(MovimentType.CREDIT, value);
	}

	@Interceptors(AccountTaxStatefulInterceptor.class)
	public void withdraw(BigDecimal value) {
		account.setCash(account.getCash().add(value));

		this.createMoviment(MovimentType.DEBIT, value);
	}

	private void createMoviment(MovimentType type, BigDecimal value) {
		Moviment mv = new Moviment();
		mv.setTarget(account);
		mv.setValue(value);
		mv.setDate(new Date());
		mv.setType(type);

		em.persist(mv);

		JMSSenderUtil.sendJMSMessage(ctx, "queue/B", mv);
	}

}
