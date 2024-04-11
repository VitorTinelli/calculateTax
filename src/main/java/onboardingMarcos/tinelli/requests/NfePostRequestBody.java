package onboardingMarcos.tinelli.requests;

import java.time.LocalDate;

public class NfePostRequestBody {

  private Long number;
  private LocalDate date;
  private Double value;

  public NfePostRequestBody(Long number, LocalDate date, Double value) {
    this.number = number;
    this.date = date;
    this.value = value;
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

}
