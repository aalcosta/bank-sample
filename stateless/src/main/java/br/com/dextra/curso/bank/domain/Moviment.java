package br.com.dextra.curso.bank.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Moviment implements Serializable {

	private static final long serialVersionUID = -6333242701084481631L;

	/**
	 * Tipo de movimento
	 */
	public static enum MovimentType {
		CREDIT, DEBIT
	}

	private Long id;

	private String desc;

	private Date date;

	private MovimentType type;

	private BigDecimal value;

	private Account target;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MovimentType getType() {
		return type;
	}

	public void setType(MovimentType type) {
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Account getTarget() {
		return target;
	}

	public void setTarget(Account target) {
		this.target = target;
	}

}
