package br.com.dextra.curso.bank.exceptions;

public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = -6985876744692777300L;

	public AccountNotFoundException() {
		super();
	}

	public AccountNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotFoundException(String message) {
		super(message);
	}

	public AccountNotFoundException(Throwable cause) {
		super(cause);
	}

}
