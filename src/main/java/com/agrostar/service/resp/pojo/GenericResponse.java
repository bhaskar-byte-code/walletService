package com.agrostar.service.resp.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse {

	private boolean success;
	private List<String> errorMsgs;
	private int currentBalance;
	private List<PassbookEntry> passbook;
	private String customerId;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<String> getErrorMsgs() {
		return errorMsgs;
	}
	public void setErrorMsgs(List<String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}
	public int getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
	}
	public List<PassbookEntry> getPassbook() {
		return passbook;
	}
	public void setPassbook(List<PassbookEntry> passbook) {
		this.passbook = passbook;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
