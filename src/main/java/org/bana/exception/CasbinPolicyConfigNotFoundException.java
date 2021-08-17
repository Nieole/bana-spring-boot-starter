package org.bana.exception;

public class CasbinPolicyConfigNotFoundException extends RuntimeException {

  public CasbinPolicyConfigNotFoundException() {
  }

  public CasbinPolicyConfigNotFoundException(String message) {
    super(message);
  }

  public CasbinPolicyConfigNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public CasbinPolicyConfigNotFoundException(Throwable cause) {
    super(cause);
  }

  public CasbinPolicyConfigNotFoundException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}