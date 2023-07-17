package com.everphase.hogar.customer;

import com.everphase.hogar.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Customer {
	private static final Logger log = LoggerFactory.getLogger(Customer.class);

	@Id
	@GeneratedValue
	private Long id;

	@ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "transactionIds", joinColumns = @JoinColumn(name = "customer_id"))
	@Column(name = "transactionId", nullable = false)
	private List<Long> transactionIds = new ArrayList<Long>();

	@ElementCollection(targetClass = Transaction.class, fetch = FetchType.LAZY)
	@CollectionTable(name = "transactions", joinColumns = @JoinColumn(name = "customer_id"))
	@Column(name = "transaction", nullable = true)
	private List<Transaction> transactions = new ArrayList<Transaction>();

	private String userId;
	private Integer rewardsPoints;

	public Customer() {
		if (userId == null)
			userId = "@sanity check: userId null";
		log.info("Customer() initialized");
	}

	public Customer(String userId) {
		this.userId = userId;
		log.info("Customer(" + userId + ") called");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Long> getTransactionIds() {
		return transactionIds;
	}

	public void setTransactionIds(List<Long> transactionIds) {
		this.transactionIds = transactionIds;
	}

	public Integer getRewardsPoints() {
		return this.rewardsPoints;
	}

	public void setRewardsPoints(Integer rewardsPoints) {
		this.rewardsPoints = rewardsPoints;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Customer))
			return false;
		Customer customer = (Customer) o;
		return Objects.equals(this.id, customer.id) && Objects.equals(this.userId, customer.userId)
				&& Objects.equals(this.transactionIds, customer.transactionIds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.userId, this.transactionIds);
	}

	@Override
	public String toString() {
		return "Customer{ id=" + this.id + ", userId='" + this.userId + "',transactionIds=" + this.transactionIds + "}";
	}
}