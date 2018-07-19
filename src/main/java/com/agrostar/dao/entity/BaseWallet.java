package com.agrostar.dao.entity;

/**
 * @author bhaskerghosh
 *
 */
public interface BaseWallet {
	
	public String getWalletId();
	public int getCurrrentBalance();
	public WalletType getWalletType();

}
