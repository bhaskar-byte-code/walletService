package com.agrostar.dao.entity;


/**
 * @author bhaskerghosh
 *
 */
public enum TransactionType {

	CREDIT("CREDIT",1), DEDIT("DEBIT",-1),CANCEL("CANCEL", -1);

	private String trxTypeName;
	private int opValue;
	
	public int getOpValue() {
		return opValue;
	}
	
	public int getOpValue(String trxName) {
		if (this.equals(TransactionType.CANCEL)) {
			return opValue * (getTrxType(trxName).getOpValue());
		}
		return opValue;
	}
	
	public String getStringValue() {
		return trxTypeName;
	}

	TransactionType(String trxType, int opValue) {
		this.trxTypeName = trxType;
		this.opValue = opValue;
	}
	
	public static TransactionType getTrxType(String trxType) {
		for (TransactionType trx : values()) {
			if(trx.getStringValue().equalsIgnoreCase(trxType)){
				return trx;
			}
		}
		throw new IllegalArgumentException("Illegal Argument"+ trxType);
	}
}
