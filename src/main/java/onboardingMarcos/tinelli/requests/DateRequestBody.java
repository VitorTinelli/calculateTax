package onboardingMarcos.tinelli.requests;

import java.time.LocalDate;

public class DateRequestBody {

  private LocalDate date;

  public DateRequestBody(LocalDate date) {
    this.date = date;
  }

  public DateRequestBody() {
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}
