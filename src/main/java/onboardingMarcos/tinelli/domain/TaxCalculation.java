package onboardingMarcos.tinelli.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class TaxCalculation {

  @Id
  private UUID id;
  private BigDecimal nfeValue;
  private BigDecimal taxedValue;
  private LocalDate calculationDate;
  @ManyToOne
  private Taxes tax;

  public TaxCalculation() {
  }

  public TaxCalculation(UUID id, BigDecimal nfeValue, BigDecimal taxedValue,
      LocalDate calculationDate, Taxes tax) {
    this.id = id;
    this.nfeValue = nfeValue;
    this.taxedValue = taxedValue;
    this.calculationDate = calculationDate;
    this.tax = tax;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BigDecimal getNfeValue() {
    return nfeValue;
  }

  public void setNfeValue(BigDecimal nfeValue) {
    this.nfeValue = nfeValue;
  }

  public BigDecimal getTaxedValue() {
    return taxedValue;
  }

  public void setTaxedValue(BigDecimal taxedValue) {
    this.taxedValue = taxedValue;
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
