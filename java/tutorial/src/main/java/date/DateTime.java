package date;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateTime {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate payday = today.with(TemporalAdjusters.lastDayOfMonth()).minusDays(2);

        System.out.println(today);
        System.out.println(payday);

        LocalDate dateOfBirth = LocalDate.of(2012, Month.MAY, 14);
        LocalDate firstBirthday = dateOfBirth.plusYears(1);

        System.out.println(dateOfBirth);
        System.out.println(firstBirthday);
        System.out.printf("%s%n", DayOfWeek.MONDAY.plus(3));

        DayOfWeek dow = DayOfWeek.MONDAY;
        Locale locale = Locale.getDefault();
        System.out.println(dow.getDisplayName(TextStyle.FULL, locale));
        System.out.println(dow.getDisplayName(TextStyle.NARROW, locale));
        System.out.println(dow.getDisplayName(TextStyle.SHORT, locale));

        System.out.printf("%d%n", Month.FEBRUARY.maxLength());
        Month month = Month.AUGUST;
        System.out.println(month.getDisplayName(TextStyle.FULL, locale));
        System.out.println(month.getDisplayName(TextStyle.NARROW, locale));
        System.out.println(month.getDisplayName(TextStyle.SHORT, locale));

        LocalDate date = LocalDate.of(2000, Month.NOVEMBER, 20);
        LocalDate nextWed = date.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        System.out.println(date);
        System.out.println(nextWed);

        YearMonth date1 = YearMonth.now();
        System.out.printf("%s: %d%n", date1, date1.lengthOfMonth());

        YearMonth date2 = YearMonth.of(2010, Month.FEBRUARY);
        System.out.printf("%s: %d%n", date2, date2.lengthOfMonth());

        YearMonth date3 = YearMonth.of(2012, Month.FEBRUARY);
        System.out.printf("%s: %d%n", date3, date3.lengthOfMonth());

        MonthDay date4 = MonthDay.of(Month.FEBRUARY, 29);
        boolean validLeapYear = date4.isValidYear(2010);
        System.out.println(validLeapYear);

        validLeapYear = Year.of(2012).isLeap();
        System.out.println(validLeapYear);


        System.out.printf("now: %s%n", LocalDateTime.now());

        System.out.printf("Apr 15, 1994 @ 11:30am: %s%n",
                LocalDateTime.of(1994, Month.APRIL, 15, 11, 30));

        System.out.printf("now (from Instant): %s%n",
                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

        System.out.printf("6 months from now: %s%n",
                LocalDateTime.now().plusMonths(6));

        System.out.printf("6 months ago: %s%n",
                LocalDateTime.now().minusMonths(6));

        Set<String> allZones = ZoneId.getAvailableZoneIds();
        System.out.println(allZones);
        LocalDateTime dt = LocalDateTime.now();
        List<String> zoneList = new ArrayList<>(allZones);
        Collections.sort(zoneList);
        for (String s : zoneList) {
            ZoneId zone = ZoneId.of(s);
            ZonedDateTime zdt = dt.atZone(zone);
            ZoneOffset offset = zdt.getOffset();
            int secondsOfHour = offset.getTotalSeconds() % (60 * 60);
            String out = String.format("%35s %10s%n", zone, offset);

            // Write only time zones that do not have a whole hour offset
            // to standard out.
            if (secondsOfHour != 0) {
                System.out.printf(out);
            }
        }
    }
}
