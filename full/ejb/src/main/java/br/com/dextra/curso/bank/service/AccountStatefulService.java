package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;

import javax.ejb.Remote;

import br.com.dextra.curso.bank.exceptions.AccountNotFoundException;
import br.com.dextra.curso.bank.exceptions.NoAvailableCashException;

@Remote
public interface AccountStatefulService {

	public void deposit(BigDecimal value) throws NoAvailableCashException;
	
	public BigDecimal getCash();

	public void withdraw(BigDecimal value);
	
	public void setAccount(String number) throws AccountNotFoundException;
	
}
