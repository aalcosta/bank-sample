package br.com.dextra.curso.bank.exceptions;

public class NoAvailableCashException extends Exception {

	private static final long serialVersionUID = -1057970556925830111L;

	public NoAvailableCashException() {
		super();
	}

	public NoAvailableCashException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAvailableCashException(String message) {
		super(message);
	}

	public NoAvailableCashException(Throwable cause) {
		super(cause);
	}

}
