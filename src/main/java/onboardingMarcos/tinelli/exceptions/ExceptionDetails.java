package onboardingMarcos.tinelli.exceptions;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.experimental.SuperBuilder;


@SuperBuilder
public class ExceptionDetails {

  private String title;
  private int status;
  private String detail;
  private LocalDateTime timestamp;
  private String developerMessage;

  public ExceptionDetails(String title, int status, String detail, LocalDateTime timestamp,
      String developerMessage) {
    this.title = title;
    this.status = status;
    this.detail = detail;
    this.timestamp = timestamp;
    this.developerMessage = developerMessage;
  }

  public ExceptionDetails() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getDeveloperMessage() {
    return developerMessage;
  }

  public void setDeveloperMessage(String developerMessage) {
    this.developerMessage = developerMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExceptionDetails that = (ExceptionDetails) o;
    return status == that.status && Objects.equals(title, that.title)
        && Objects.equals(detail, that.detail) && Objects.equals(timestamp,
        that.timestamp) && Objects.equals(developerMessage, that.developerMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, status, detail, timestamp, developerMessage);
  }

}
