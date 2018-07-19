package com.agrostar.service.resp.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletType {
	private int walletTypeId;
	private String walletType;
	private int minBalAllowed;
	private String createdAt;
	
	public WalletType() {}
	public WalletType(int walletTypeId, String walletType, int minBalAllowed2, String createdAt) {
		this.walletTypeId = walletTypeId;
		this.walletType = walletType;
		this.minBalAllowed = minBalAllowed2;
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
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
