package br.com.dextra.curso.client;

import java.math.BigDecimal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.dextra.curso.bank.domain.Account;
import br.com.dextra.curso.bank.service.AccountManagerServiceRemote;

public class AccountManagerClient {

	public static void main(String... args) throws NamingException {
		Context jndiContext = new InitialContext();
		AccountManagerServiceRemote service = (AccountManagerServiceRemote) jndiContext
				.lookup("bank/AccountManagerServiceBean/remote");

		Account account = new Account();
		account.setAccountNumber("11111");
		account.setOwner("Usuario");
                account.setCash(new BigDecimal(0));
		service.createAccount(account);
	}
}
