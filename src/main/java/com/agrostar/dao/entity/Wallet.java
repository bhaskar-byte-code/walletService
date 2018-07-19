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

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author bhaskerghosh
 *
 */

@Entity
@Table(name = "wallet")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Wallet {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int id;
    
    @Column(name="current_bal")
	@ColumnDefault("0")
	private int currentBal;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private WalletType walletType;
	
	@Column(name="created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createdAt;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCurrentBal() {
		return currentBal;
	}

	public void setCurrentBal(int currentBal) {
		this.currentBal = currentBal;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	
	public String getWalletId() {
		return Integer.toString(this.getId());
	}
	
	public int getCurrrentBalance() {
		return this.getCurrentBal();
	}
	

	
}
