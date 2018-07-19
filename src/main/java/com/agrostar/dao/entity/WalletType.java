package com.agrostar.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "wallet_type")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class WalletType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int walletTypeId;
	
	@NotBlank
	@Column(name="wt_name", unique=true)
	private String walletType;
	
	@ColumnDefault("0")
	@Column(name="min_bal_allowed")
	private int minBalAllowed;
	
	@ColumnDefault("100000")
	@Column(name="max_bal_allowed")
	private int maxBalAllowed;
	
	@ColumnDefault("100000")
	@Column(name="max_trx_limit_Amt")
	private int maxTrxLimitAmount;
	
	@ColumnDefault("100")
	@Column(name="max_trx_limit_count")
	private int maxTrxLimitCount;
	
	@ColumnDefault("30")
	@Column(name="max_trx_limit_duration_in_days")
	private int maxTrxLimitDurationInDays;
	
	@ColumnDefault("0")
	@Column(name="min_trx_amt")
	private int minTrxAmount;
	
	@ColumnDefault("1000")
	@Column(name="max_trx_amt")
	private int maxTrxAmountt;
	
	@Column(name="createdAt", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date createdAt;
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public int getWalletTypeId() {
		return walletTypeId;
	}
	public void setWalletTypeId(int walletTypeId) {
		this.walletTypeId = walletTypeId;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
	public int getMinBalAllowed() {
		return minBalAllowed;
	}
	public void setMinBalAllowed(int minBalAllowed) {
		this.minBalAllowed = minBalAllowed;
	}
	public int getMaxBalAllowed() {
		return maxBalAllowed;
	}
	public void setMaxBalAllowed(int maxBalAllowed) {
		this.maxBalAllowed = maxBalAllowed;
	}
	public int getMinTrxAmount() {
		return minTrxAmount;
	}
	public void setMinTrxAmount(int minTrxAmount) {
		this.minTrxAmount = minTrxAmount;
	}
	public int getMaxTrxAmountt() {
		return maxTrxAmountt;
	}
	public void setMaxTrxAmountt(int maxTrxAmountt) {
		this.maxTrxAmountt = maxTrxAmountt;
	}
	public int getMaxTrxLimitAmount() {
		return maxTrxLimitAmount;
	}
	public void setMaxTrxLimitAmount(int maxTrxLimitAmount) {
		this.maxTrxLimitAmount = maxTrxLimitAmount;
	}
	public int getMaxTrxLimitCount() {
		return maxTrxLimitCount;
	}
	public void setMaxTrxLimitCount(int maxTrxLimitCount) {
		this.maxTrxLimitCount = maxTrxLimitCount;
	}
	public int getMaxTrxLimitDurationInDays() {
		return maxTrxLimitDurationInDays;
	}
	public void setMaxTrxLimitDurationInDays(int maxTrxLimitDurationInDays) {
		this.maxTrxLimitDurationInDays = maxTrxLimitDurationInDays;
	}
		
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxBalAllowed;
		result = prime * result + maxTrxAmountt;
		result = prime * result + maxTrxLimitAmount;
		result = prime * result + maxTrxLimitCount;
		result = prime * result + maxTrxLimitDurationInDays;
		result = prime * result + minBalAllowed;
		result = prime * result + minTrxAmount;
		result = prime * result + ((walletType == null) ? 0 : walletType.hashCode());
		result = prime * result + walletTypeId;
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
		WalletType other = (WalletType) obj;
		if (maxBalAllowed != other.maxBalAllowed)
			return false;
		if (maxTrxAmountt != other.maxTrxAmountt)
			return false;
		if (maxTrxLimitAmount != other.maxTrxLimitAmount)
			return false;
		if (maxTrxLimitCount != other.maxTrxLimitCount)
			return false;
		if (maxTrxLimitDurationInDays != other.maxTrxLimitDurationInDays)
			return false;
		if (minBalAllowed != other.minBalAllowed)
			return false;
		if (minTrxAmount != other.minTrxAmount)
			return false;
		if (walletType == null) {
			if (other.walletType != null)
				return false;
		} else if (!walletType.equals(other.walletType))
			return false;
		if (walletTypeId != other.walletTypeId)
			return false;
		return true;
	}

}
