package com.everphase.hogar;

import java.math.BigDecimal;

import com.everphase.hogar.transaction.Transaction;
import com.everphase.hogar.transaction.TransactionRepository;
import com.everphase.hogar.customer.Customer;
import com.everphase.hogar.customer.CustomerRepository;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadRepos {

  private static final Logger log = LoggerFactory.getLogger(LoadRepos.class);

  @Bean
  CommandLineRunner initDatabase(CustomerRepository customerRepo, TransactionRepository transactionRepo) {
	  
	List<Transaction> ts0 = new ArrayList<Transaction>();
	Transaction t0 = new Transaction();
	ts0.add(t0);
	  
	Customer c0 = new Customer();
	c0.setUserId("customer0");
	c0.setTransactions(ts0);
	
    return args -> {
    	log.info("Initializing Customer Repo");
    	log.info("Initializing transaction created " + transactionRepo.saveAll(ts0));
    	log.info("Initializing customer created " + customerRepo.save(c0));
    };
  }
  
}