package org.jfree.data.test;

import static org.junit.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

import org.jfree.data.time.Quarter;
import org.jfree.data.time.Year;
import org.junit.Test;

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

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuarterCtor3_invalidYear() {
        arrange(4, 1800);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuarterCtor3_invalidQuarter() {
        arrange(5, 2000);
    }

    @Test
    public void testQuarterCtor4() {
        quarter = new Quarter(3, new Year(2023));

        assertEquals(2023, quarter.getYear().getYear());
        assertEquals(3, quarter.getQuarter());
    }

    @Test
    public void testCompareTo_greaterYear_greaterQuarter() {
        Quarter q = new Quarter(2, 2000);
        arrange(3, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result > 0);
    }
    @Test
    public void testCompareTo_greaterYear_sameQuarter() {
        Quarter q = new Quarter(4, 2000);
        arrange(4, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result > 0);
    }
    @Test
    public void testCompareTo_greaterYear_smallerQuarter() {
        Quarter q = new Quarter(4, 2000);
        arrange(1, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result > 0);
    }
    @Test
    public void testCompareTo_smallerYear_greaterQuarter() {
        Quarter q = new Quarter(2, 2024);
        arrange(3, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result < 0);
    }

    @Test
    public void testCompareTo_smallerYear_sameQuarter() {
        Quarter q = new Quarter(4, 2024);
        arrange(4, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result < 0);
    }

    @Test
    public void testCompareTo_smallerYear_smallerQuarter() {
        Quarter q = new Quarter(4, 2024);
        arrange(1, 2023);

        int result = quarter.compareTo(q);

        assertTrue(result < 0);
    }

    @Test
    public void testCompareTo_sameYear_greaterQuarter() {
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
    public void testCompareTo_sameYear_smallerQuarter() {
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
    public void testEquals_differentObject_returnsFalse() {
        Quarter q = new Quarter(4, 2022);
        arrange(1, 2023);

        boolean result = quarter.equals(q);

        assertFalse(result);
    }

    //Therefore, the first millisecond of the first quarter of 2023 based on the start year 1900 is 3,883,215,776,000.
    @Test
    public void testGetFirstMillisecond() {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("Africa/Cairo");
        calendar.setTimeZone(timeZone);

        arrange();

        long result = quarter.getFirstMillisecond(calendar);

        assertEquals(3883215776000L ,result);
    }


    @Test
    public void testGetLastMillisecond() {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("Africa/Cairo");
        calendar.setTimeZone(timeZone);
        System.out.println(calendar);
        arrange();

        long result = quarter.getLastMillisecond(calendar);

        assertEquals(3883215775999L ,result);
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
    public void testParseString() { //insufficient documentation: format of string?
        String string = "2012Q4";

        Quarter q = Quarter.parseQuarter(string);

        assertEquals(4, q.getQuarter());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testParseString_invalidQuarter() { //insufficient documentation: format of string?
        String string = "2012Q5";

        Quarter q = Quarter.parseQuarter(string);
    }

    //long	getSerialIndex()
    //RegularTimePeriod	previous()
    // RegularTimePeriod next()
}