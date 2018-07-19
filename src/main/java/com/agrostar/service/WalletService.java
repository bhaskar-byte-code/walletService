package com.agrostar.service;

import java.util.List;

import com.agrostar.service.req.pojo.CreateWalletReq;
import com.agrostar.service.resp.pojo.GenericResponse;
import com.agrostar.service.resp.pojo.WalletType;

/**
 * @author bhaskerghosh
 *
 */
public interface WalletService {
	public GenericResponse createWallet(CreateWalletReq createWalletReq);
	public GenericResponse createWalletType(String wtName, int minBalAllowed);
	public GenericResponse getCurrentBalance(int customerId);
	public GenericResponse cancel(long txnId, String trxType);
	public GenericResponse getPassBook(int customerId);
	public GenericResponse transact(int amount, int customerId, String trxType);
	public List<WalletType> getwalletTypes();
}
