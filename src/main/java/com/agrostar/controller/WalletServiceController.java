package com.agrostar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agrostar.service.WalletService;
import com.agrostar.service.req.pojo.CreateWalletReq;
import com.agrostar.service.req.pojo.WalletTrxReq;
import com.agrostar.service.resp.pojo.GenericResponse;
import com.agrostar.service.resp.pojo.WalletType;

@RequestMapping(value="/wallet")
@RestController
public class WalletServiceController {
	
	@Autowired
	WalletService walletService;
	
	@RequestMapping(value="/createwallet", method = RequestMethod.POST)
	public GenericResponse createNewWallet(@RequestBody CreateWalletReq createWalletReq) {
		return walletService.createWallet(createWalletReq);
	}
	
	@RequestMapping(value="/createwalletType", method = RequestMethod.GET)
	public GenericResponse createNewWalletType(@RequestParam("wtName")String wtName, @RequestParam("minBalAllowed")int minBalAllowed) {
		return walletService.createWalletType(wtName,minBalAllowed);
	}
	
	@RequestMapping(value="/getwalletTypes", method = RequestMethod.GET)
	public List<WalletType> getwalletTypes() {
		return walletService.getwalletTypes();
	}
	
	@RequestMapping(value="/transact", method = RequestMethod.POST)
	public GenericResponse doTransaction(@RequestBody WalletTrxReq WalletTrxReq) {
		return walletService.transact(WalletTrxReq.getAmount(), WalletTrxReq.getCustomerId(), WalletTrxReq.getTrxType());
	}
	
	@RequestMapping(value="/passbook/{customerId}", method = RequestMethod.GET)
	public GenericResponse getPassbook(@PathVariable("customerId") Integer custId) {
		return walletService.getPassBook(custId);
	}
	
	@RequestMapping(value="/balance/{customerId}", method = RequestMethod.GET)
	public GenericResponse getBalance(@PathVariable("customerId") int custId) {
		return walletService.getCurrentBalance(custId);
	}
	
	@RequestMapping(value="/cancel/{txnId}/{txnType}", method = RequestMethod.GET)
	public GenericResponse cancel(@PathVariable("txnId")long txnId, @PathVariable("txnType")String txnType) {
		return walletService.cancel(txnId, txnType);
	}
}
