package onboardingMarcos.tinelli.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Taxes {

  @Id
  private UUID id;

  private String name;

  private double aliquot;

  public Taxes(UUID id, String name, double aliquot) {
    this.id = id;
    this.name = name;
    this.aliquot = aliquot;
  }

  public Taxes() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public void setAliquot(double aliquot) {
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
    Taxes taxes = (Taxes) o;
    return id == taxes.id && Double.compare(aliquot, taxes.aliquot) == 0
        && Objects.equals(name, taxes.name);
  }
}
