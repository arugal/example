package com.github.arugal.jdk.example;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author: zhangwei
 * @date: 2019-07-02/13:30
 */
public class LocalDateTimeTest {


    @Test
    public void test() {
        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println("now time:" + nowTime);

        LocalDate date = nowTime.toLocalDate();
        System.out.println("date:" + date);

        Month month = nowTime.getMonth();
        int day = nowTime.getDayOfMonth();
        int sencods = nowTime.getSecond();
        System.out.println("月:" + month.name() + ",日:" + day + ",秒:" + sencods);

        LocalDate date1 = LocalDate.of(2017, Month.AUGUST, 12);
        System.out.println("date1:" + date1);

        LocalTime date2 = LocalTime.of(22, 15);
        System.out.println("date2:" + date2);

        LocalTime date3 = LocalTime.parse("20:15:30");
        System.out.println("date3:" + date3);
    }

    @Test
    public void testZone() {
        ZonedDateTime date1 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
        System.out.println("date1:" + date1);

        ZoneId id = ZoneId.systemDefault();
        System.out.println("zoneId:" + id);
    }

}
