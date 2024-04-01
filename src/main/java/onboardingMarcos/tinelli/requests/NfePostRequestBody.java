package onboardingMarcos.tinelli.requests;

import java.time.LocalDate;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfePostRequestBody that = (NfePostRequestBody) o;
    return Objects.equals(number, that.number) && Objects.equals(date, that.date)
        && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, date, value);
  }

  @Override
  public String toString() {
    return "NfePostRequestBody{" +
        "number=" + number +
        ", date=" + date +
        ", value=" + value +
        '}';
  }
}
