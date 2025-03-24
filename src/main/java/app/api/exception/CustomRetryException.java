package app.api.exception;

public class CustomRetryException extends RuntimeException {
  public CustomRetryException(String message) {
    super(message);
  }
}