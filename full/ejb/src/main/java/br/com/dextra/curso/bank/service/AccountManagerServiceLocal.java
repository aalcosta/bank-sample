package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;

import javax.ejb.Local;

import br.com.dextra.curso.bank.domain.Account;

@Local
public interface AccountManagerServiceLocal {

	public Account createAccount (Account account);
	
	public void applyTax(Long accountId, String taxName, BigDecimal value);
	
}
