package org.bana.exception;

public class CasbinModelConfigNotFoundException extends RuntimeException {

  public CasbinModelConfigNotFoundException() {
  }

  public CasbinModelConfigNotFoundException(String message) {
    super(message);
  }

  public CasbinModelConfigNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public CasbinModelConfigNotFoundException(Throwable cause) {
    super(cause);
  }

  public CasbinModelConfigNotFoundException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}