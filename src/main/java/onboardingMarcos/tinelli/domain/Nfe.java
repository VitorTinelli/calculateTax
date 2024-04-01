package onboardingMarcos.tinelli.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Nfe {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(unique = true, nullable = false)
  private UUID id;
  private Long number;
  private LocalDate date;
  private Double value;
}
