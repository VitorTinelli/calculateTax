package onboardingMarcos.tinelli.requests;

import java.util.Objects;

public class TaxesPutRequestBody {

  private Long id;
  private String name;
  private Double aliquot;

  public TaxesPutRequestBody(Long id, String name, Double aliquot) {
    this.id = id;
    this.name = name;
    this.aliquot = aliquot;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getAliquot() {
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
    TaxesPutRequestBody that = (TaxesPutRequestBody) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name)
        && Objects.equals(aliquot, that.aliquot);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, aliquot);
  }

  @Override
  public String toString() {
    return "TaxesPutRequestBody{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", aliquot=" + aliquot +
        '}';
  }
}
