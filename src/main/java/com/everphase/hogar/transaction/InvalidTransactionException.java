package com.everphase.hogar.transaction;

public class InvalidTransactionException extends RuntimeException {

  private static final long serialVersionUID = 4L;
  
  public InvalidTransactionException() {
    super("Transaction has invalid properties");
  }
}