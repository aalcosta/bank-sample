package br.com.dextra.curso.client;

import java.math.BigDecimal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.dextra.curso.bank.exceptions.AccountNotFoundException;
import br.com.dextra.curso.bank.exceptions.NoAvailableCashException;
import br.com.dextra.curso.bank.service.AccountStatelessServiceRemote;

public class AccountStatelessClient {

	public static void main(String... args) throws NamingException {
		Context jndiContext = new InitialContext();

		AccountStatelessServiceRemote service = (AccountStatelessServiceRemote) jndiContext
				.lookup("bank/AccountStatelessServiceBean/remote");

		try {
			service.deposit("12345", new BigDecimal("100.00"));
		} catch (AccountNotFoundException e) {
			System.out.println("Conta numero '12345' inexistente");
		}

		try {
			System.out.println("Saldo da conta apos o deposito: "
					+ service.deposit("99999", new BigDecimal("100.00")));
		} catch (AccountNotFoundException e) {
			System.out.println("Erro inesperado");
		}

		try {
			System.out.println("Saldo da conta apos o saque: "
					+ service.withdraw("99999", new BigDecimal("200.00")));
		} catch (AccountNotFoundException e) {
			System.out.println("Erro inesperado");
		} catch (NoAvailableCashException e) {
			System.out.println("Saldo insuficiente para o saque");
		}

		try {
			System.out.println("Saldo da conta apos o saque: "
					+ service.withdraw("99999", new BigDecimal("50.00")));
		} catch (AccountNotFoundException e) {
			System.out.println("Erro inesperado");
		} catch (NoAvailableCashException e) {
			System.out.println("Erro inesperado");
		}
	}
}
