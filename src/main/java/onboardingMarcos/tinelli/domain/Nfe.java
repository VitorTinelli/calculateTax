package onboardingMarcos.tinelli.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Nfe {

  @Id
  private UUID id;
  private Long number;
  private LocalDate date;
  private Double value;

  public Nfe(UUID id, Long number, LocalDate date, Double value) {
    this.id = id;
    this.number = number;
    this.date = date;
    this.value = value;
  }

  public Nfe() {
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

}

