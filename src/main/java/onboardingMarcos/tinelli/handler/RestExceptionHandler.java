package onboardingMarcos.tinelli.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.exceptions.BadRequestExceptionDetails;
import onboardingMarcos.tinelli.exceptions.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(
      BadRequestException bre) {
    return new ResponseEntity<>(
        BadRequestExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad Request Exception, Check the Documentation")
            .detail(bre.getMessage())
            .developerMessage(bre.getClass().getName())
            .build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

    String fields = fieldErrors.stream().map(FieldError::getField)
        .collect(Collectors.joining(", "));
    String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(", "));

    return new ResponseEntity<>(
        ValidationExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad Request Exception, Invalid Fields")
            .detail("Check the field(s) error")
            .developerMessage(exception.getClass().getName())
            .fields(fields)
            .fieldsMessage(fieldsMessage)
            .build(), HttpStatus.BAD_REQUEST);
  }
}