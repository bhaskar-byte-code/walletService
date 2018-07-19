package com.agrostar.service.resp.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PassbookEntry {
	private long id;
	private String trxType;
	private int amount;
	private String date;
	private String status;
	private String cancelledTxnId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCancelledTxnId() {
		return cancelledTxnId;
	}
	public void setCancelledTxnId(String cancelledTxnId) {
		this.cancelledTxnId = cancelledTxnId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public PassbookEntry() {}
	public PassbookEntry(long id, String trxType, int amount, String date, boolean isCancelled, long cancelledlTxn) {
		super();
		this.id = id;
		this.trxType = trxType;
		this.amount = amount;
		this.date = date;
		if(isCancelled) {
			status = "CANCELLED";
		} else {
			status = "ACTIVE";
		}
		
		if (cancelledlTxn != 0) {
			cancelledTxnId = Long.toString(cancelledlTxn);
		}
	}
}
