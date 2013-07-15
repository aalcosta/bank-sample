package br.com.dextra.curso.bank.service;

import javax.ejb.Local;

import br.com.dextra.curso.bank.domain.Account;

@Local
public interface AccountServiceLocal {

	public Account createAccount (String owner);
	
}
