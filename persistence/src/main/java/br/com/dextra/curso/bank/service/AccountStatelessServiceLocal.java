package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.dextra.curso.bank.domain.Moviment;
import br.com.dextra.curso.bank.exceptions.AccountNotFoundException;
import br.com.dextra.curso.bank.exceptions.NoAvailableCashException;

@Local
public interface AccountStatelessServiceLocal {

	public BigDecimal deposit(String accountNumber, BigDecimal value)
			throws AccountNotFoundException;

	public BigDecimal withdraw(String accountNumber, BigDecimal value)
			throws AccountNotFoundException, NoAvailableCashException;

	public List<Moviment> findMoviments(String accountNumber,
			Date beginDate, Date endDate);

}
