package com.everphase.hogar.customer;

import com.everphase.hogar.transaction.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CustomerController {
	
	  private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
		
	  private final CustomerRepository customerRepo;
	  private final TransactionRepository transactionRepo;
	
	  CustomerController(CustomerRepository customerRepo, TransactionRepository transactionRepo) {
	    this.customerRepo = customerRepo;
	    this.transactionRepo = transactionRepo;
	  }
	
	  // Aggregate root
	  @GetMapping("/customers")
	  List<Customer> allCustomers() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

		//spit out date info for reference
		try {
			String dateInStart = "01-01-2023 00:00:0 AM"; 
			Date dJan01 = formatter.parse(dateInStart);
			  
			log.info("dJan01 date = "+dJan01);
			log.info("dJan01 date epochMs = "+dJan01.getTime());
	
			String dateInEnd = "01-08-2023 00:00:00 AM"; 
			Date d = formatter.parse(dateInEnd);
	
			log.info("Aug 1 = "+d);
			log.info("Aug 1 epochMs = "+d.getTime());
			
		}catch (Exception e){
			log.info(e.getMessage());
		}
		
		return customerRepo.findAll();
	  }

	  //add new customers. not to be used for updating an existing customer
	  @PostMapping("/customers")
	  Customer addCustomer(@RequestBody Customer customerIn) {
		  Customer newCustomer;
		  if (customerIn != null) {
			  String testUserId = customerIn.getUserId();
			  
			  //check for null and duplicate userId
			  if (testUserId == null || testUserId.equals("")) {
				  throw( new InvalidCustomerUserIdException(testUserId));
			  }else if (customerRepo.existsByUserId(testUserId)) {
				  throw( new CustomerUserIdExistsException(testUserId));
			  }
			  
			  newCustomer = new Customer(testUserId);
			  List<Transaction> ts = new ArrayList<Transaction>(customerIn.getTransactions());
			  processIncomingTransactions(ts, newCustomer);  
				
		  }else {
			  newCustomer = new Customer();
		  }
		  
		  return customerRepo.save(newCustomer);
	  }
	  
	  //get a specific customer by id
	  @GetMapping("/customers/{id}")
	  Customer getCustomerById(@PathVariable Long id) {
	    
	    return customerRepo.findById(id)
	      .orElseThrow(() -> new CustomerNotFoundException(id));
	  }
	  
	//get a specific customer by userId
	  @GetMapping("/customers/{id}/userId")
	  String getCustomerUserId(@PathVariable Long id) {
	    
		  Customer c = customerRepo.findById(id)
				  .orElseThrow(() -> new CustomerNotFoundException(id));
		  
		  return c.getUserId();
	  }
	  
	  //return all transactions for a given Customer by range.  @RequestParms are not required, so request may or may not include a date range
	  @GetMapping("/customers/{id}/transactions/{epochSecsRangeBegin}/{epochSecsRangeEnd}")
	  List<Transaction> getTransactionsByCustomerId(@PathVariable Long id, @PathVariable(required = false) Long epochSecsRangeBegin, @PathVariable(required = false) Long epochSecsRangeEnd) {
	    
	    Customer c = (Customer)customerRepo.findById(id)
	      .orElseThrow(() -> new CustomerNotFoundException(id));
	    
	    List<Long> tIds = c.getTransactionIds();
	    return filterTransactionsByDateRange(tIds, epochSecsRangeBegin, epochSecsRangeEnd);
	  }
	  
	  //return all transactions by range. @RequestParms are not required, so request may or may not include a date range
	  @GetMapping("/customers/transactions/{epochSecsRangeBegin}/{epochSecsRangeEnd}")
	  List<Transaction> getTransactions(@PathVariable(required = false) Long epochSecsRangeBegin, @PathVariable(required = false) Long epochSecsRangeEnd) {
	    
		List<Customer> acs = allCustomers();
		List<Transaction> ts = new ArrayList<Transaction>();
	    
		for (Customer c : acs) {
		    List<Long> tIds = c.getTransactionIds();
		    ts.addAll(filterTransactionsByDateRange(tIds, epochSecsRangeBegin, epochSecsRangeEnd));	
		}
	    return ts;
	  }
	  
	  //return all rewards by range and timezoneid. @PathVariable are required
	  @GetMapping("/customers/monthlyRewardsMap/{epochSecsRangeBegin}/{epochSecsRangeEnd}/{tzIdPrefix}/{tzIdSuffix}")
	  Map<Long, Map<String, List<Integer>>> getMonthlyRewardsMap(@PathVariable Long epochSecsRangeBegin, @PathVariable Long epochSecsRangeEnd, @PathVariable String tzIdPrefix, @PathVariable String tzIdSuffix) {
		Map<Long, Map<String, List<Integer>>> allRwdsMapByTimeInterval = new HashMap<>();
		List<Customer> acs = allCustomers();

		for (Customer c : acs) {
			Map<String, List<Integer>> cmRwdsMapByTimeInterval = new HashMap<String, List<Integer>>();
		    allRwdsMapByTimeInterval.put(c.getId(), cmRwdsMapByTimeInterval);
		    
			List<Long> tIds = c.getTransactionIds();
		    List<Transaction> ts = (ArrayList<Transaction>)filterTransactionsByDateRange(tIds, epochSecsRangeBegin, epochSecsRangeEnd);	
		    
		    for (Transaction t : ts) {
		    	Date date = new Date(t.getEpochSecs() * 1000);
		    	log.info("date = "+date);
		    	
		    	ZoneId zId = ZoneId.of(tzIdPrefix+"/"+tzIdSuffix);
		    	log.info("tzIdPrefix = "+tzIdPrefix);
		    	log.info("tzIdSuffix = "+tzIdSuffix);
		    	log.info("zId = "+zId);
		    	
		    	LocalDate localDate = date.toInstant().atZone(zId).toLocalDate();
		    	log.info("localDate = "+localDate);
		    	
		    	Month m = Month.of(localDate.getMonthValue());
		    	Year y = Year.of(localDate.getYear());
		    	String myStr = m.toString() + "-" + y.toString();
		    	
		    	List<Integer> rwdsList = cmRwdsMapByTimeInterval.containsKey(myStr) ? cmRwdsMapByTimeInterval.get(myStr) : new ArrayList<Integer>();
		    	rwdsList.add(t.getRewardsPoints());
		    	cmRwdsMapByTimeInterval.put(myStr, rwdsList);
		    }
		}
	    return allRwdsMapByTimeInterval;
	  }
	  
	  //return total rewards by customer id and by month. @PathVariable are required
	  @GetMapping("/customers/monthlyRewardsTotals/{epochSecsRangeBegin}/{epochSecsRangeEnd}/{tzIdPrefix}/{tzIdSuffix}")
	  Map<Long, Map<String, Integer>> getMonthlyRewardsTotals(@PathVariable Long epochSecsRangeBegin, @PathVariable Long epochSecsRangeEnd, @PathVariable String tzIdPrefix, @PathVariable String tzIdSuffix) {
		Map<Long, Map<String, Integer>> rRwdsTotalByTimeInterval = new HashMap<Long, Map<String, Integer>>();
				  
		Map<Long, Map<String, List<Integer>>> allRwdsMapByTimeInterval = getMonthlyRewardsMap(epochSecsRangeBegin, epochSecsRangeEnd, tzIdPrefix, tzIdSuffix);
		List<Customer> acs = allCustomers();

		for (Map.Entry<Long, Map<String, List<Integer>>> entry : allRwdsMapByTimeInterval.entrySet()) {

			Map<String, Integer> mSI = new HashMap<String, Integer>();
		    for (Map.Entry<String, List<Integer>> monthlyRewardsMap : (entry.getValue()).entrySet()) {
		    	log.info("monthlyRewardsMap.getKey() " + monthlyRewardsMap.getKey() + " & monthlyRewardsMap.getValue() " + monthlyRewardsMap.getValue());
		    	
		    	Integer rwdsTot = 0;
		    	for (Integer i : (List<Integer>)monthlyRewardsMap.getValue()) {
		    		rwdsTot += i;
		    	}
		    	mSI.put((String)monthlyRewardsMap.getKey(), rwdsTot);
		    }
		    
		    rRwdsTotalByTimeInterval.put(entry.getKey(), mSI);
		}
	    return rRwdsTotalByTimeInterval;
	  }
	  
	  //API for merging transactions
	  @PutMapping("/customers/{id}")
	  Customer updateCustomer(@RequestBody Customer customerIn, @PathVariable Long id) {
		
	    log.info("inside updateCustomer 0");
	    Optional<Customer> oc = customerRepo.findById(id);
	    Customer c = oc.orElseThrow(() -> new CustomerNotFoundException(id));

	    //check userIds
	    String testUserId = customerIn.getUserId();
	    String userId = c.getUserId();
	    
	    log.info("inside updateCustomer testUserId=" + testUserId);
	    log.info("inside updateCustomer userId=" + userId);
	    
	    
	    if (testUserId == null || !testUserId.equals(userId)) {
	    	throw new InvalidCustomerUserIdException(testUserId);
	    }

		processIncomingTransactions(new ArrayList<Transaction>(customerIn.getTransactions()), c);  
		return customerRepo.save(c);
	  }
	
	  @DeleteMapping("/customers/{id}")
	  void deleteCustomer(@PathVariable Long id) {
		Customer c = (Customer)customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException(id));
		  
	    customerRepo.deleteById(id);
	  }

	  //helper function used by multiple APIs to return transaction ArrayList by date range
	  List<Transaction> filterTransactionsByDateRange(List<Long> tIds, Long epochSecsRangeBegin, Long epochSecsRangeEnd){
		List<Transaction> ts = new ArrayList<Transaction>();
		boolean filterByDateRange = epochSecsRangeBegin != null && epochSecsRangeEnd != null;
		
		for (Long tId : tIds) {
			Optional<Transaction> ot = transactionRepo.findById(tId);
			Transaction t = (Transaction)ot.get();
			if (!filterByDateRange) {
				ts.add(t);
			}else if (t.getEpochSecs() >= epochSecsRangeBegin && t.getEpochSecs() < epochSecsRangeEnd){
				ts.add(t);
			}
		}	    
		return ts;
	  }
	  
	  //helper function used by multiple APIs to return transaction ArrayList by date range
	  void processIncomingTransactions(List<Transaction> ts, Customer c) {
		  if (ts != null && ts.size() > 0){
			  
			  List<Long> tIds = new ArrayList<Long>(c.getTransactionIds());
			  Integer rwdsTot = c.getRewardsPoints() == null ? 0 : c.getRewardsPoints();
			  
			  List<Transaction> tsOut = new ArrayList<Transaction>();
			  
			  /** use processing time for a cursory check on elements of incoming List
			   *  this is a new Customer - possibilities are no elements, a few or many.
			   *  given common use cases it seems unlikely there will so many that cause
			   *  an inefficiency here.
			   */
		      Iterator<Transaction> iter = ts.iterator();
		      while (iter.hasNext()) {
		    	  
		    	  //no need to instantiate new transactions
		    	  //use incoming data as skeleton for transactions to be persisted
		    	  Transaction t = (Transaction)iter.next();
		    	  
		    	  log.info("iter el : " + t);
		    	  
		    	  BigDecimal b = t.getAmount();
		    	  Long d = t.getEpochSecs();

		    	  if (d != null && d >= 0L && 
		    			  b != null && 
		    			  b.compareTo(BigDecimal.ZERO) >= 0) {
		    		  //set userId and rewards points
		    		  t.setUserId(c.getUserId());
		    		  Integer rwds = t.calcRewardsPoints(b);
		    		  t.setRewardsPoints(rwds);
		    		  rwdsTot += rwds;
		    		  
		    		  tsOut.add(t);
		    		  
		    	  }else {
		    		  throw( new InvalidTransactionException());
		    	  }
		      }

			  transactionRepo.saveAll(tsOut);
			  
			  //an efficient approach : persist an array of transaction ids 
			  for (Transaction t : tsOut) {
				  tIds.add(t.getId());
			  }
			  c.setTransactionIds(tIds);
			  c.setRewardsPoints(rwdsTot);
		  }
	  }
}