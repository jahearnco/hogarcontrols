package com.everphase.hogar.customer;

import com.everphase.hogar.transaction.Transaction;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDTO {
	  private static final Logger log = LoggerFactory.getLogger(Customer.class);
	  
	  private Long id;
	  private List<Transaction> transactions;
	  private String userId;
	
	  public CustomerDTO(Long id, String userId, List<Transaction> transactions) {
		this.id = id;
		this.userId = userId;
	    log.info("CustomerDTO(" + userId + ") called");
	  }

	  public Long getId() {
	    return id;
	  }
	
	  public void setUserId(Long id) {
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
	
	  @Override
	  public boolean equals(Object o) {
	
	    if (this == o)
	      return true;
	    if (!(o instanceof CustomerDTO))
	      return false;
	    CustomerDTO customer = (CustomerDTO) o;
	    return Objects.equals(this.userId, customer.userId) && Objects.equals(this.transactions, customer.transactions);
	  }
	
	  @Override
	  public int hashCode() {
	    return Objects.hash(this.userId, this.transactions);
	  }
	  
	  @Override
	  public String toString() {
	    return "CustomerDTO{ userId='" + this.userId + "',transactions=" + this.transactions + "}";
	  }
}