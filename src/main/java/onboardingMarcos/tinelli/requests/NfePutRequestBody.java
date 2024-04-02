package onboardingMarcos.tinelli.requests;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class NfePutRequestBody {

  private UUID id;
  private Long number;
  private LocalDate date;
  private Double value;

  public NfePutRequestBody(UUID id, Long number, LocalDate date, Double value) {
    this.id = id;
    this.number = number;
    this.date = date;
    this.value = value;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
    NfePutRequestBody that = (NfePutRequestBody) o;
    return Objects.equals(id, that.id) && Objects.equals(number, that.number)
        && Objects.equals(date, that.date) && Objects.equals(value, that.value);
  }
}
