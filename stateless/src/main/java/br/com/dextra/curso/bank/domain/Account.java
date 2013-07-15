package br.com.dextra.curso.bank.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {

	private static final long serialVersionUID = 8017110528282874977L;

	private Long id;

	private String accountNumber;

	private String owner;

	private BigDecimal cash;

	public Account() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal value) {
		this.cash = value;
	}

}
