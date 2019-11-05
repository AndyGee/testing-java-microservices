package testing.microservices.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "Content has not changed")  // 304
public class ContentNotChangedException extends RuntimeException {
  private static final long serialVersionUID = 1;
}
