package com.everphase.hogar.customer;

class InvalidCustomerUserIdException extends RuntimeException {

  private static final long serialVersionUID = 2L;
  
  public InvalidCustomerUserIdException(String userId) {
    super("UserId = \"" + userId + "\" is invalid");
  }
}