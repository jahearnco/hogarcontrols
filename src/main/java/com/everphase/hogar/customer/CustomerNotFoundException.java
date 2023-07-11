package com.everphase.hogar.customer;

class CustomerNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 3L;
  
  public CustomerNotFoundException(Long id) {
    super("Could not find customer " + id);
  }
}