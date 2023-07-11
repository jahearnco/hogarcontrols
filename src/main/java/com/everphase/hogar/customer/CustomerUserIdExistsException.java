package com.everphase.hogar.customer;

class CustomerUserIdExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  public CustomerUserIdExistsException(String userId) {
    super("UserId exists " + userId);
  }
}