package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;

import javax.ejb.Remote;

import br.com.dextra.curso.bank.exceptions.AccountNotFoundException;
import br.com.dextra.curso.bank.exceptions.NoAvailableCashException;

@Remote
public interface AccountStatelessServiceRemote extends java.rmi.Remote {

	public BigDecimal deposit(String accountNumber, BigDecimal value)
			throws AccountNotFoundException;

	public BigDecimal withdraw(String accountNumber, BigDecimal value)
			throws NoAvailableCashException, AccountNotFoundException;

}
