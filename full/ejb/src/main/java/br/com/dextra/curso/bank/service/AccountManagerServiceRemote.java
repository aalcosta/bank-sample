package br.com.dextra.curso.bank.service;

import javax.ejb.Remote;

import br.com.dextra.curso.bank.domain.Account;

@Remote
public interface AccountManagerServiceRemote {

	public Account createAccount (Account account);
	
	public Account findAccountByOwner(String name);
}
