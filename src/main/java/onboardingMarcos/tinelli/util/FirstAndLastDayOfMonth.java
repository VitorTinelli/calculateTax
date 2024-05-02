package onboardingMarcos.tinelli.util;

import java.time.LocalDate;

public class FirstAndLastDayOfMonth {

  public static LocalDate firstDay(LocalDate date) {
    LocalDate startDate = date;
    do {
      startDate = startDate.minusDays(1);
    } while (date.getMonth() == startDate.getMonth());
    return startDate = startDate.plusDays(1);
  }

  public static LocalDate lastDay(LocalDate date) {
    LocalDate endDate = date;
    do {
      endDate = endDate.plusDays(1);
    } while (date.getMonth() == endDate.getMonth());
    return endDate = endDate.minusDays(1);
  }

}
