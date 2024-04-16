package vitor.tinelli.exceptions;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExceptionDetails {

  private String title;
  private int status;
  private String detail;
  private LocalDateTime timestamp;
  private String developerMessage;

}
