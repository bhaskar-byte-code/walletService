package com.agrostar.dao.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Transaction {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Wallet wallet;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;
	
	@NotBlank
	@Column(name="trxType")
	private String trxType;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="isCancelled")
	private boolean isCancelled;
	
	@Column(name="linkedTrx")
	private long linkedTrx;
	
	@Column(name="createdAt", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createdAt;
	

	public Transaction() {}
	public Transaction(Wallet wallet, Customer customer, String trxType, int amount) {
		super();
		this.wallet = wallet;
		this.customer = customer;
		this.trxType = trxType;
		this.amount = amount;
	}
	

	public Transaction(Wallet wallet, Customer customer, String trxType, int amount, long linkedTrx) {
		super();
		this.wallet = wallet;
		this.customer = customer;
		this.trxType = trxType;
		this.amount = amount;
		this.linkedTrx = linkedTrx;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public long getLinkedTrx() {
		return linkedTrx;
	}

	public void setLinkedTrx(long linkedTrx) {
		this.linkedTrx = linkedTrx;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	

}
