package onboardingMarcos.tinelli.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class NfeTax {

  @Id
  private UUID id;

  @OneToOne
  private Nfe nfe;

  @OneToOne
  private Taxes taxes;

  private Double valueWithTax;

  private Double difference;

  private String month;

  private Long year;

  public NfeTax(UUID id, Nfe nfe, Taxes taxes, double valueWithTax, double difference, String month,
      long year) {
    this.id = id;
    this.nfe = nfe;
    this.taxes = taxes;
    this.valueWithTax = valueWithTax;
    this.difference = difference;
    this.month = month;
    this.year = year;
  }

  public NfeTax() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Nfe getNfe() {
    return nfe;
  }

  public void setNfe(Nfe nfe) {
    this.nfe = nfe;
  }

  public Taxes getTaxes() {
    return taxes;
  }

  public void setTaxes(Taxes taxes) {
    this.taxes = taxes;
  }

  public double getValueWithTax() {
    return valueWithTax;
  }

  public void setValueWithTax(double valueWithTax) {
    this.valueWithTax = valueWithTax;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public double getYear() {
    return year;
  }

  public void setYear(long year) {
    this.year = year;
  }

  public double getDifference() {
    return difference;
  }

  public void setDifference(double difference) {
    this.difference = difference;
  }
}
