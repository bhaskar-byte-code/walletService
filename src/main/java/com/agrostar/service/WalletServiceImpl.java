package com.agrostar.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.agrostar.dao.WalletDaoImpl;
import com.agrostar.dao.entity.Customer;
import com.agrostar.dao.entity.Transaction;
import com.agrostar.dao.entity.TransactionType;
import com.agrostar.dao.entity.Wallet;
import com.agrostar.dao.entity.WalletType;
import com.agrostar.service.req.pojo.CreateWalletReq;
import com.agrostar.service.resp.pojo.GenericResponse;
import com.agrostar.service.resp.pojo.PassbookEntry;

/**
 * @author bhaskerghosh
 *
 */

@Service
public class WalletServiceImpl implements WalletService {
	
	@Autowired
	private WalletDaoImpl walletDaoImpl;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	@Value("${passbook.txn.count}")
	private int passbookTxnCount;
	
	EmailValidator emailValidator = new EmailValidator();
	
	
	@Transactional
	public GenericResponse createWallet(CreateWalletReq createWalletReq) {
		GenericResponse resp = new GenericResponse();
		try  {
			List<String> errMsgs = validateCreateWalletReq(createWalletReq);
			if (CollectionUtils.isEmpty(errMsgs)) {
				Customer customer = walletDaoImpl.getCustomerByUniqFields(createWalletReq.getEmail(), createWalletReq.getPhone(), createWalletReq.getPan());
				if (customer == null) {
					customer = createNewCustomer(createWalletReq);
				}
				
				if (customer.getWallet() != null) {
					//  wallet exists
					resp.setErrorMsgs(Collections.singletonList("Wallet already exists"));
				} else {
					WalletType walletType = walletDaoImpl.getWalletType(createWalletReq.getWalletTypeId());
					if (walletType != null) {
						customer.setWallet(getNewWallet(walletType));
						walletDaoImpl.persist(customer);
						resp.setSuccess(true);
						resp.setCustomerId(customer.getCustomerId() != 0 ? Integer.toString(customer.getCustomerId()) : null);
					} else {
						// invalid wallet type
						resp.setErrorMsgs(Collections.singletonList("Invalid Wallet type"));
					}			
				}
			} else {
				resp.setErrorMsgs(errMsgs);
			}
		} catch (Exception e) {
			resp.setErrorMsgs(Collections.singletonList("Something went wrong"));
		}
		return resp;
	}



	private Wallet getNewWallet(WalletType walletType) {
		Wallet wallet = new Wallet();
		wallet.setWalletType(walletType);
		return wallet;
	}



	private Customer createNewCustomer(CreateWalletReq createWalletReq) {
		Customer cust = new Customer();
		cust.setfName(createWalletReq.getFirstName());
		cust.setlName(createWalletReq.getLastName());
		cust.setEmail(createWalletReq.getEmail());
		cust.setPhone(createWalletReq.getPhone());
		cust.setPan(createWalletReq.getPan());
		cust.setCity(createWalletReq.getCity());
		return cust;		
	}



	@Override
	public GenericResponse getCurrentBalance(int customerId) {
		GenericResponse resp = new GenericResponse();
		try {
			Customer customer = walletDaoImpl.getCustomerById(customerId);
			if (customer != null && customer.getWallet() !=null) {
				resp.setCurrentBalance(customer.getWallet().getCurrentBal());
				resp.setSuccess(true);
			} else {
				resp.setErrorMsgs(Collections.singletonList("Invalid customer"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMsgs(Collections.singletonList("Something went wrong"));
		}
		return resp;
	}

	@Override
	public GenericResponse getPassBook(int customerId) {
		GenericResponse resp = new GenericResponse();
		try {
			Customer customer = walletDaoImpl.getCustomerById(customerId);
			if (customer != null && customer.getWallet() !=null) {
				List<Transaction> trxList = walletDaoImpl.getTrxHistory(customer, passbookTxnCount);
				if (!CollectionUtils.isEmpty(trxList)) {
					List<PassbookEntry> passbook = new ArrayList<>();
					for (Transaction trx : trxList) {
						passbook.add(new PassbookEntry(trx.getId(), trx.getTrxType(), trx.getAmount(), formatter.format(trx.getCreatedAt()), trx.isCancelled(), trx.getLinkedTrx()));
					}
					resp.setPassbook(passbook);
				}
				resp.setSuccess(true);
				resp.setCurrentBalance(customer.getWallet().getCurrentBal());
			} else {
				resp.setErrorMsgs(Collections.singletonList("Invalid customer"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMsgs(Collections.singletonList("Something went wrong"));
		}
		return resp;
	}

	@Override
	@Transactional
	public GenericResponse cancel(long txnId, String txnType) {
		GenericResponse resp = new GenericResponse();
		TransactionType cancel_txn = TransactionType.CANCEL;
		try {
			Transaction old_txn = walletDaoImpl.getTransactionById(txnId);
			if (old_txn != null) {
				if (old_txn.getTrxType().equalsIgnoreCase(txnType) && !old_txn.isCancelled() && !old_txn.getTrxType().equalsIgnoreCase(cancel_txn.getStringValue())) {
					int txnAmount = old_txn.getAmount() * cancel_txn.getOpValue(old_txn.getTrxType());
					int newBal = old_txn.getWallet().getCurrentBal() + txnAmount;
					Transaction new_txn = new Transaction(old_txn.getWallet(), old_txn.getCustomer(), cancel_txn.getStringValue(), old_txn.getAmount(), old_txn.getId());
					new_txn.getWallet().setCurrentBal(newBal);
					walletDaoImpl.persist(new_txn);
					old_txn.setCancelled(true);
					walletDaoImpl.persist(old_txn);
					resp.setSuccess(true);
					resp.setCurrentBalance(newBal);
				} else {
					resp.setErrorMsgs(Collections.singletonList("Transaction already cancelled or invalid details"));
				}
			} else {
				resp.setErrorMsgs(Collections.singletonList("Invalid transaction"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMsgs(Collections.singletonList("Something went wrong"));
		}
		return resp;
	
	}

	@Override
	@Transactional
	public GenericResponse createWalletType(String wtName, int minBalAllowed) {
		GenericResponse resp = new GenericResponse();
		try {
			WalletType walletType = new WalletType();
			walletType.setMinBalAllowed(minBalAllowed);
			walletType.setWalletType(wtName);
			walletDaoImpl.persist(walletType);
			resp.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}



	@Override
	@Transactional
	public GenericResponse transact(int amount, int customerId, String trxType) {
		GenericResponse resp = new GenericResponse();
		try {
			List<String> validationErrMsgs = validateTransactionReq(amount, customerId, trxType);
			
			if (CollectionUtils.isEmpty(validationErrMsgs)) {
				TransactionType txtype = TransactionType.getTrxType(trxType);
				int trxAmount = amount * txtype.getOpValue();
				Customer customer = walletDaoImpl.getCustomerById(customerId);
				Wallet wallet = customer.getWallet();
				
				List<String> errorMsg = isValidTransaction(trxAmount,wallet);
				
				if (CollectionUtils.isEmpty(errorMsg)) {
					int newBal = wallet.getCurrentBal() + trxAmount;
					Transaction trx = new Transaction(wallet, customer, txtype.getStringValue(), amount);
					wallet.setCurrentBal(newBal);
					walletDaoImpl.persist(trx);
					resp.setSuccess(true);
					resp.setCurrentBalance(newBal);
				} else {
					resp.setErrorMsgs(errorMsg);
				}
			} else {
				resp.setErrorMsgs(validationErrMsgs);
			}
		} catch (IllegalArgumentException ex) {
			resp.setErrorMsgs(Collections.singletonList("Invalid transaction type"));
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMsgs(Collections.singletonList("Something went wrong"));
		}
		return resp;
	}



	private List<String> validateTransactionReq(int amount, int customerId, String trxType) {
		List<String> error = new ArrayList<>();
		if (amount < 1) {
			error.add("Invalid amonut");
		}
		if (customerId < 1) {
			error.add("Invalid CustomerId");
		}
		if (StringUtils.isEmpty(trxType)) {
			error.add("Invalid transactionType");
		}
		return error;
	}



	private List<String> isValidTransaction(int trxAmount, Wallet wallet) {
		WalletType wType = wallet.getWalletType();
		List<String> error = new ArrayList<>();
		if (wType.getMinBalAllowed() > (wallet.getCurrentBal() + trxAmount)) {
			error.add("Min wallet balance can not be less than " + wType.getMinBalAllowed());
		}
		return error;
	}
	
	private List<String> validateCreateWalletReq(CreateWalletReq createWalletReq) {
		List<String> error = new ArrayList<>();
		if (StringUtils.isEmpty(createWalletReq.getFirstName())) {
			error.add("First name is mandatory parameter");
		}
		if (StringUtils.isEmpty(createWalletReq.getLastName())) {
			error.add("Last name is mandatory parameter");
		}
		if (StringUtils.isEmpty(createWalletReq.getEmail())) {
			error.add("Email is mandatory parameter");
		} else if (!emailValidator.isValid(createWalletReq.getEmail(), null)) {
			error.add("Invalid Email");
		}
		if (StringUtils.isEmpty(createWalletReq.getPhone())) {
			error.add("Phone number is mandatory parameter");
		} else if (!StringUtils.isNumeric(createWalletReq.getPhone()) || createWalletReq.getPhone().length()!=10) {
			error.add("Invalid Phone number");
		}
		if (StringUtils.isEmpty(createWalletReq.getPan())) {
			error.add("PAN is mandatory parameter");
		}
		return error;
	}



	@Override
	@Transactional
	public List<com.agrostar.service.resp.pojo.WalletType> getwalletTypes() {
		List<com.agrostar.service.resp.pojo.WalletType> walletTypeList = new ArrayList<>();
		List<WalletType> wtList = walletDaoImpl.getWalletTypeList();
		if (!CollectionUtils.isEmpty(wtList)) {
			for (WalletType wt : wtList) {
				walletTypeList.add(new com.agrostar.service.resp.pojo.WalletType(wt.getWalletTypeId(), wt.getWalletType(), wt.getMinBalAllowed(), formatter.format(wt.getCreatedAt())));
			}
		}
		return walletTypeList;
	}
	
}
