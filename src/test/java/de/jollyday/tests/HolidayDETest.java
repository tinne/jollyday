/**
 * Copyright 2010 Sven Diedrichsen 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an 
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either 
 * express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package de.jollyday.tests;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.tests.base.AbstractCountryTestBase;
import de.jollyday.util.CalendarUtil;

public class HolidayDETest extends AbstractCountryTestBase {

	private static final int YEAR = 2010;
	private static final String ISO_CODE = "de";

	private CalendarUtil calendarUtil = new CalendarUtil();

	@Test
	public void testManagerDEStructure() throws Exception {
		validateCalendarData(ISO_CODE, YEAR);
	}

	@Test
	public void testManagerDEinterval() {
		try {
			HolidayManager instance = HolidayManager.getInstance(HolidayCalendar.GERMANY);
			Set<Holiday> holidays = instance.getHolidays(calendarUtil.create(2010, 10, 1), calendarUtil
					.create(2011, 1, 31));
			List<LocalDate> expected = Arrays.asList(calendarUtil.create(2010, 12, 24),
					calendarUtil.create(2010, 12, 25), calendarUtil.create(2010, 12, 26),
					calendarUtil.create(2010, 12, 31), calendarUtil.create(2010, 10, 3),
					calendarUtil.create(2011, 1, 1));
			Assert.assertEquals("Wrong number of holidays", expected.size(), holidays.size());
			for (LocalDate d : expected) {
				Assert.assertTrue("Expected date " + d + " missing.", calendarUtil.contains(holidays, d));
			}
		} catch (Exception e) {
			Assert.fail("Unexpected error occurred: " + e.getClass().getName() + " - " + e.getMessage());
		}
	}

	@Test
	public void testManagerSameInstance() {
		Locale defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.GERMANY);
		try {
			HolidayManager defaultManager = HolidayManager.getInstance();
			HolidayManager germanManager = HolidayManager.getInstance(HolidayCalendar.GERMANY);
			Assert.assertEquals("Unexpected manager found", defaultManager, germanManager);
		} catch (Exception e) {
			Assert.fail("Unexpected error occurred: " + e.getClass().getName() + " - " + e.getMessage());
		} finally {
			Locale.setDefault(defaultLocale);
		}
	}

	@Test
	public void testManagerDifferentInstance() {
		Locale defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.US);
		try {
			HolidayManager defaultManager = HolidayManager.getInstance();
			HolidayManager germanManager = HolidayManager.getInstance(HolidayCalendar.GERMANY);
			Assert.assertNotSame("Unexpected manager found", defaultManager, germanManager);
		} catch (Exception e) {
			Assert.fail("Unexpected error occurred: " + e.getClass().getName() + " - " + e.getMessage());
		} finally {
			Locale.setDefault(defaultLocale);
		}
	}

}
