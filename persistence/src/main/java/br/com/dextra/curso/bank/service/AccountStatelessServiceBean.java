package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.dextra.curso.bank.domain.Account;
import br.com.dextra.curso.bank.domain.Moviment;
import br.com.dextra.curso.bank.domain.Moviment.MovimentType;
import br.com.dextra.curso.bank.exceptions.AccountNotFoundException;
import br.com.dextra.curso.bank.exceptions.NoAvailableCashException;

@Stateless
public class AccountStatelessServiceBean implements AccountStatelessServiceLocal {

    @PersistenceContext(unitName = "teste")
    private EntityManager em;

    public BigDecimal deposit(String accountNumber, BigDecimal value) throws AccountNotFoundException {
        Account account = this.findAccountByNumber(accountNumber);
        account.setCash(account.getCash().add(value));

        account = em.merge(account);
        em.persist(account);

        this.createMoviment(account, "Deposito", MovimentType.CREDIT, value);

        return account.getCash();
    }

    public BigDecimal withdraw(String accountNumber, BigDecimal value) throws NoAvailableCashException, AccountNotFoundException {
        Account account = this.findAccountByNumber(accountNumber);

        if (account.getCash().compareTo(value) < 0) {
            throw new NoAvailableCashException();
        }

        account.setCash(account.getCash().subtract(value));

        account = em.merge(account);
        em.persist(account);

        this.createMoviment(account, "Saque", MovimentType.DEBIT, value);

        return account.getCash();
    }

    @SuppressWarnings("unchecked")
    public List<Moviment> findMoviments(String accountNumber, Date beginDate, Date endDate) {

        Query eqlQuery = em.createQuery("from Moviment");

        return eqlQuery.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Account findAccountByNumber(String accountNumber) throws AccountNotFoundException {
        Account result = null;

        Query eql = em.createQuery("from Account where accountNumber = :x");
        eql.setParameter("x", accountNumber);

        try {
            result = (Account) eql.getSingleResult();
        } catch (NoResultException e) {
            throw new AccountNotFoundException(e);
        }

        return result;
    }

    private void createMoviment(Account account, String desc, MovimentType type, BigDecimal value) {
        Moviment mv = new Moviment();
        mv.setTarget(account);
        mv.setValue(value);
        mv.setDate(new Date());
        mv.setDesc(desc);
        mv.setType(type);

        em.persist(mv);
    }

}
