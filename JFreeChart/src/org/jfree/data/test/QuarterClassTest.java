package org.jfree.data.test;

import static org.junit.Assert.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

import org.jfree.data.time.Month;
import org.jfree.data.time.Quarter;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Year;
import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class QuarterClassTest {
    Quarter quarter;

    private void arrange(Integer quart, Integer year) {
        quarter = new Quarter(quart, year);
    }

    private void arrange() {
        quarter = new Quarter();
    }

    //    (January to March) 1,
//    (April to June) 2,
//    (july to sep) 3
//    (October to December) 4.
    @Test
    public void testQuarterDefaultCtor() {
        arrange();

        assertEquals(2023, quarter.getYear().getYear());
        assertEquals(2, quarter.getQuarter());
    }

    @Test
    public void testQuarterCtor1() {
        Date date = new Date(2023, Calendar.JANUARY, 1);
        quarter = new Quarter(date);

        assertEquals(2023, quarter.getYear().getYear());
        assertEquals(1, quarter.getQuarter());
    }

    @Test
    public void testQuarterCtor2() {
        Date date = new Date(2023, Calendar.JANUARY, 1);
        quarter = new Quarter(date, TimeZone.getTimeZone("Africa/Cairo"));

        assertEquals(2023, quarter.getYear().getYear());
        assertEquals(1, quarter.getQuarter());
    }

    @Test
    public void testQuarterCtor3() {
        arrange(4, 2023);

        assertEquals(2023, quarter.getYear().getYear());
        assertEquals(4, quarter.getQuarter());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)  //insufficient doc
    public void testQuarterCtor3_invalidYear() {
        arrange(4, 1800);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)  //insufficient doc
    public void testQuarterCtor3_invalidYear2() {
        arrange(4, 10000);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuarterCtor3_invalidQuarter() {
        arrange(5, 2023);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuarterCtor3_invalidQuarter2() {
        arrange(0, 2023);
    }

    @Test
    public void testQuarterCtor4() {
        quarter = new Quarter(3, new Year(2023));

        assertEquals(2023, quarter.getYear().getYear());
        assertEquals(3, quarter.getQuarter());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuarterCtor4_invalidYear() {
        quarter = new Quarter(3, new Year(1800));
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuarterCtor4_invalidQuarter() {
        quarter = new Quarter(5, new Year(2023));
    }

    @Test
    public void testCompareTo_smallerYear_smallerQuarter() {
        Quarter q = new Quarter(2, 2000);
        arrange(3, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result > 0);
    }

    @Test
    public void testCompareTo_smallerYear_sameQuarter() {
        Quarter q = new Quarter(4, 2000);
        arrange(4, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result > 0);
    }

    @Test
    public void testCompareTo_smallerYear_greaterQuarter() {
        Quarter q = new Quarter(4, 2000);
        arrange(1, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result > 0);
    }

    @Test
    public void testCompareTo_greaterYear_smallerQuarter() {
        Quarter q = new Quarter(2, 2024);
        arrange(3, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result < 0);
    }

    @Test
    public void testCompareTo_greaterYear_sameQuarter() {
        Quarter q = new Quarter(4, 2024);
        arrange(4, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result < 0);
    }

    @Test
    public void testCompareTo_greaterYear_greaterQuarter() {
        Quarter q = new Quarter(4, 2024);
        arrange(1, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result < 0);
    }

    @Test
    public void testCompareTo_sameYear_smallerQuarter() {
        Quarter q = new Quarter(2, 2023);
        arrange(3, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result > 0);
    }

    @Test
    public void testCompareTo_sameYear_sameQuarter() {
        Quarter q = new Quarter(4, 2023);
        arrange(4, 2023);

        int result = quarter.compareTo(q);

        assertEquals(0, result);
    }

    @Test
    public void testCompareTo_sameYear_greaterQuarter() {
        Quarter q = new Quarter(4, 2023);
        arrange(1, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result < 0);
    }

    @Test
    public void testEquals_sameObject_returnsTrue() {
        Quarter q = new Quarter(1, 2023);
        arrange(1, 2023);

        boolean result = quarter.equals(q);

        assertTrue(result);
    }

    @Test
    public void testEquals_sameYear_differentQuarter_returnsFalse() {
        Quarter q = new Quarter(4, 2023);
        arrange(1, 2023);

        boolean result = quarter.equals(q);

        assertFalse(result);
    }


    @Test
    public void testEquals_differentYear_sameQuarter_returnsFalse() {
        Quarter q = new Quarter(4, 2022);
        arrange(4, 2023);

        boolean result = quarter.equals(q);

        assertFalse(result);
    }


    @Test
    public void testGetQuarter() {
        arrange(4, 2022);

        int result = quarter.getQuarter();

        assertEquals(result, 4);
    }

    @Test
    public void testGetYear() {
        arrange(4, 2022);

        Year result = quarter.getYear();

        assertEquals(result.getYear(), 2022);
    }

    @Test
    public void testParseString() {
        String string = "2012,Q4";

        Quarter q = Quarter.parseQuarter(string);

        assertEquals(4, q.getQuarter());
        assertEquals(2012, q.getYear().getYear());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testParseString_invalidQuarter() {
        String string = "2012,Q5";

        Quarter q = Quarter.parseQuarter(string);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testParseString_invalidQuarter2() {
        String string = "2012,Q0";

        Quarter q = Quarter.parseQuarter(string);
    }

    @Test
    public void testNext_validArgs() {
        arrange(1, 2023);

        RegularTimePeriod t = quarter.next();

        assertEquals(quarter.getSerialIndex() + 1, t.getSerialIndex());
    }

    @Test
    public void testNext_validArgs2() {
        arrange(1, 1900);

        RegularTimePeriod t = quarter.next();

        assertEquals(quarter.getSerialIndex() + 1, t.getSerialIndex());
    }

    @Test
    public void testNext_invalidArgs() {
        arrange(4, 9999);

        RegularTimePeriod t = quarter.next();

        assertNull(t);
    }


    @Test
    public void testPrevious_validArgs() {
        arrange(2, 2023);

        RegularTimePeriod t = quarter.previous();

        assertEquals(quarter.getSerialIndex() - 1, t.getSerialIndex());
    }

    @Test
    public void testPrevious_validArgs2() {
        arrange(4, 9999);

        RegularTimePeriod t = quarter.previous();

        assertEquals(quarter.getSerialIndex() - 1, t.getSerialIndex());
    }

    @Test
    public void testPrevious_invalidArgs() {
        arrange(1, 1900);

        RegularTimePeriod t = quarter.previous();

        assertNull(t);
    }


    @Test
    public void testGetSerialIndex() { //insufficient documentation
        arrange(2, 2023);
        long expectedResult = ((2023 - 1900) * 4) + 2;

        assertEquals(expectedResult, quarter.getSerialIndex());
    }

    @Test
    public void testGetSerialIndex2() { //insufficient documentation
        arrange(1, 1900);
        long expectedResult = 1;

        assertEquals(expectedResult, quarter.getSerialIndex());
    }


    @Test
    public void testGetFirstMillisecond() { //insufficient documentation
        arrange(2, 2023);
        LocalDate firstDayOfQuarter = LocalDate.of(2023, 4, 1);
        LocalDateTime startOfQuarter = LocalDateTime.of(firstDayOfQuarter, LocalTime.MIDNIGHT);
        LocalDateTime startOf1900 = LocalDateTime.of(1900, 1, 1, 0, 0, 0);

        long expectedResult = ChronoUnit.MILLIS.between(startOf1900, startOfQuarter);

        assertEquals(expectedResult, quarter.getFirstMillisecond());
    }

    @Test
    public void testGetFirstMillisecond2() { //insufficient documentation: how to calculate and leap seconds added or not
        arrange(1, 1900);
        LocalDate firstDayOfQuarter = LocalDate.of(1900, 1, 1);
        LocalDateTime startOfQuarter = LocalDateTime.of(firstDayOfQuarter, LocalTime.MIDNIGHT);
        LocalDateTime startOf1900 = LocalDateTime.of(1900, 1, 1, 0, 0, 0);

        long expectedResult = ChronoUnit.MILLIS.between(startOf1900, startOfQuarter);

        assertEquals(expectedResult, quarter.getFirstMillisecond());
    }

    //number of ms in a quarter  7,884,000,000
    @Test
    public void testGetLastMillisecond() { //insufficient documentation
        arrange(2, 2023);
        LocalDate lastDayOfQuarter = LocalDate.of(2023, 6, 1).with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime endOfQuarter = LocalDateTime.of(lastDayOfQuarter, LocalTime.MAX);
        LocalDateTime endOf1900 = LocalDateTime.of(1900, 1, 1, 23, 59, 59, 999999999);

        long expectedResult = ChronoUnit.MILLIS.between(endOf1900, endOfQuarter);

        assertEquals(expectedResult, quarter.getLastMillisecond());
    }

}