package onboardingMarcos.tinelli.requests;

import java.util.Objects;

public class TaxesPostRequestBody {

  private String name;
  private double aliquot;

  public TaxesPostRequestBody(String name, double aliquot) {
    this.name = name;
    this.aliquot = aliquot;
  }

  public TaxesPostRequestBody() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getAliquot() {
    return aliquot;
  }

  public void setAliquot(Double aliquot) {
    this.aliquot = aliquot;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaxesPostRequestBody that = (TaxesPostRequestBody) o;
    return Double.compare(aliquot, that.aliquot) == 0 && Objects.equals(name,
        that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, aliquot);
  }

  @Override
  public String toString() {
    return "TaxesPostRequestBody{" +
        "name='" + name + '\'' +
        ", aliquot=" + aliquot +
        '}';
  }
}
