package br.com.dextra.curso.bank.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MOVIMENT")
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DESCRIPTION")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "MOVIMENT_TYPE")
	public MovimentType getType() {
		return type;
	}

	public void setType(MovimentType type) {
		this.type = type;
	}

	@Column(name = "Value")
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACCOUNT_FK", nullable = false)
	public Account getTarget() {
		return target;
	}

	public void setTarget(Account target) {
		this.target = target;
	}

}
