package br.com.dextra.curso.bank.interceptors;

import java.io.Serializable;
import java.math.BigDecimal;

public class InterceptorMessage implements Serializable {

	private static final long serialVersionUID = -847725481899139381L;
	
	private Long accountId;
	
	private BigDecimal moviment;

	public InterceptorMessage() {
	}
	
	public InterceptorMessage(Long accountId, BigDecimal moviment) {
		this.accountId = accountId;
		this.moviment = moviment;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getMoviment() {
		return moviment;
	}

	public void setMoviment(BigDecimal moviment) {
		this.moviment = moviment;
	}

}
