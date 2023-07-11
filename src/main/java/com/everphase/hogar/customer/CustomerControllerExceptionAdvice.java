package com.everphase.hogar.customer;

import com.everphase.hogar.transaction.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerControllerExceptionAdvice {

  @ResponseBody
  @ExceptionHandler(CustomerUserIdExistsException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String customerUserIdExistsHandler(CustomerUserIdExistsException ex) {
    return ex.getMessage();
  }
  
  @ResponseBody
  @ExceptionHandler(InvalidCustomerUserIdException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String customerUserIdExistsHandler(InvalidCustomerUserIdException ex) {
    return ex.getMessage();
  }
  
  @ResponseBody
  @ExceptionHandler(CustomerNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String customerNotFoundHandler(CustomerNotFoundException ex) {
    return ex.getMessage();
  }
  
  @ResponseBody
  @ExceptionHandler(InvalidTransactionException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String customerNotFoundHandler(InvalidTransactionException ex) {
    return ex.getMessage();
  }
}
