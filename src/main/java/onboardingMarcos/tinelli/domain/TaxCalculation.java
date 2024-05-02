package onboardingMarcos.tinelli.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class TaxCalculation {

  @Id
  private UUID id;
  private Double totalValue;
  private Double totalTaxedValue;
  private LocalDate calculationDate;
  @ManyToOne
  private Taxes tax;

  public TaxCalculation(UUID id, Double totalValue, Double totalTaxedValue,
      LocalDate calculationDate,
      Taxes tax) {
    this.id = id;
    this.totalValue = totalValue;
    this.totalTaxedValue = totalTaxedValue;
    this.calculationDate = calculationDate;
    this.tax = tax;
  }

  public TaxCalculation() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Double getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(Double totalValue) {
    this.totalValue = totalValue;
  }

  public Double getTotalTaxedValue() {
    return totalTaxedValue;
  }

  public void setTotalTaxedValue(Double totalTaxedValue) {
    this.totalTaxedValue = totalTaxedValue;
  }

  public LocalDate getCalculationDate() {
    return calculationDate;
  }

  public void setCalculationDate(LocalDate calculationDate) {
    this.calculationDate = calculationDate;
  }

  public Taxes getTax() {
    return tax;
  }

  public void setTax(Taxes tax) {
    this.tax = tax;
  }
}
