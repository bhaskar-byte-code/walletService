package com.agrostar.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.agrostar.dao.entity.Customer;
import com.agrostar.dao.entity.Transaction;
import com.agrostar.dao.entity.WalletType;

/**
 * @author bhaskerghosh
 *
 */
@Repository
public class WalletDaoImpl {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	public void persist(Object obj) {
		entityManager.persist(obj);
		entityManager.flush();
	}
	
	public Customer getCustomerById(int custId) {
		return entityManager.find(Customer.class, custId);
	}
	
	public Customer getCustomerByUniqFields(String emailId, String phn, String panId) {
		
		List<Customer> userList = entityManager
				.createQuery("SELECT cust FROM Customer cust WHERE cust.email= :emailId or cust.phone= :phn or cust.pan= :panId")
				.setParameter("emailId", emailId).setParameter("phn", phn).setParameter("panId", panId).getResultList();
		
		if (CollectionUtils.isEmpty(userList)) {
			return null;
		} else {
			return userList.get(0);
		}
	}
	
	public Transaction getTransactionById(long trxId) {
		return entityManager.find(Transaction.class, trxId);
	}
	
	public List<Transaction> getTrxHistory(Customer customer, int trxCount) {
		String hql = "FROM Transaction as trx WHERE trx.customer = ? order by trx.createdAt desc";
		List<Transaction> trxList = entityManager.createQuery(hql).setParameter(1, customer).setMaxResults(trxCount).getResultList();
		return trxList;
	}

	public WalletType getWalletType(int walletType) {
		return entityManager.find(WalletType.class, walletType);
	}

	public List<WalletType> getWalletTypeList() {
		return entityManager.createQuery("SELECT wt FROM WalletType wt").getResultList();
	}
}
