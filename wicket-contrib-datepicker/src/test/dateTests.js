/* ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * @author Karri-Pekka Laakso (kplaakso)
 */


function setUp() {
}

function testDaysInMonth() {
	// Basic year
	assertEquals("Jan", 31, Date.getDaysInMonth(2006, 0));
	assertEquals("Feb", 28, Date.getDaysInMonth(2006, 1));
	assertEquals("Mar", 31, Date.getDaysInMonth(2006, 2));
	assertEquals("Apr", 30, Date.getDaysInMonth(2006, 3));
	assertEquals("May", 31, Date.getDaysInMonth(2006, 4));
	assertEquals("Jun", 30, Date.getDaysInMonth(2006, 5));
	assertEquals("Jul", 31, Date.getDaysInMonth(2006, 6));
	assertEquals("Aug", 31, Date.getDaysInMonth(2006, 7));
	assertEquals("Sep", 30, Date.getDaysInMonth(2006, 8));
	assertEquals("Oct", 31, Date.getDaysInMonth(2006, 9));
	assertEquals("Nov", 30, Date.getDaysInMonth(2006, 10));
	assertEquals("Dec", 31, Date.getDaysInMonth(2006, 11));

	// Leap years
	assertEquals("Feb 2005", 28, Date.getDaysInMonth(2005, 1));
	assertEquals("Feb 2004", 29, Date.getDaysInMonth(2004, 1));
	assertEquals("Feb 2000", 29, Date.getDaysInMonth(2000, 1));
	assertEquals("Feb 2100", 28, Date.getDaysInMonth(2100, 1));
	assertEquals("Feb 2400", 29, Date.getDaysInMonth(2400, 1));
}

function testDayOfYear() {
	// Basic years
	assertEquals("Jan 31", 31, Date.getDayInYear(2006, 0, 31));
	assertEquals("Feb 28", 59, Date.getDayInYear(2006, 1, 28));
	assertEquals("Mar 1", 60, Date.getDayInYear(2006, 2, 1));
	
	// Leap years
	assertEquals("Dec 31 2006", 365, Date.getDayInYear(2006, 11, 31));
	assertEquals("Dec 31 2004 (leap)", 366, Date.getDayInYear(2004, 11, 31));
}

function testAddDays() {
    // Basic adding
	checkWholeYear(new Date(2006, 0, 1));
	checkWholeYear(new Date(2006, 0, 1, 23, 30, 00));
	checkWholeYear(new Date(2006, 0, 1, 00, 30, 00));
	checkWholeYear(new Date(2006, 0, 1, 12, 00, 00));
	checkWholeYear(new Date(2006, 0, 1, 11, 00, 00));
	checkWholeYear(new Date(2006, 0, 1, 12, 30, 00));
}

function checkWholeYear(day) {
	var reference = new Date(day.getTime());
	assertEquals("day <> reference", 0, day.compareTo(reference));

	var dayOfYear = day.getDayInYear();
	for (var i=0; i<364; i++) {
	    day.addDays(1);
		assertEquals("Hours mismatched for "+day, reference.getHours(), day.getHours());
		assertEquals("Mins mismatched for "+day, reference.getMinutes(), day.getMinutes());
		assertEquals("Secs mismatched for "+day, reference.getSeconds(), day.getSeconds());
	    assertEquals("Adding mismatch", dayOfYear+1, day.getDayInYear())
	    dayOfYear++;
	}
}

function testAddYears() {
	assertEquals("Apr 30 2006 + 1 year", new Date(2007, 03, 30).toString(), new Date(2006, 03, 30).addYears(1).toString());
	assertEquals("Apr 30 2006 - 1 year", new Date(2005, 03, 30).toString(), new Date(2006, 03, 30).addYears(-1).toString());
	
	assertEquals("Feb 29 2004 + 1 year", new Date(2005, 02, 1).toString(), new Date(2004, 01, 29).addYears(1).toString());
	assertEquals("Mar 1 2005 - 1 year", new Date(2004, 02, 1).toString(), new Date(2005, 02, 1).addYears(-1).toString());
	assertEquals("Feb 28 2005 - 1 year", new Date(2004, 01, 28).toString(), new Date(2005, 01, 28).addYears(-1).toString());
}

function testCompareTo() {
	
}

function testCompareToDateOnly() {
	
}

function testIsToday() {
	
}

function testWeekInYear() {
	// Basic stuff, US
	assertEquals("Sat Jun 17 2006 (US)", 24, new Date(2006, 05, 17).getWeekInYear('US'));	
	assertEquals("Sun Jun 18 2006 (US)", 25, new Date(2006, 05, 18).getWeekInYear('US'));	
	assertEquals("Mon Jun 19 2006 (US)", 25, new Date(2006, 05, 19).getWeekInYear('US'));	

	assertEquals("Sat Dec 27 2003 (US)", 52, new Date(2003, 11, 27).getWeekInYear('US'));	
	assertEquals("Sun Dec 28 2003 (US)", 53, new Date(2003, 11, 28).getWeekInYear('US'));	
	assertEquals("Mon Dec 29 2003 (US)", 53, new Date(2003, 11, 29).getWeekInYear('US'));	

	// Year endings and beginnings (US)
	var usWeekNumbers = [ 
		{year:2003, weeks:[53,53,53,53,1,1,1,2,2]},
		{year:2004, weeks:[53,53,53,53,1,2,2,2,2]},
		{year:2005, weeks:[53,53,53,53,1,1,1,1,1]},
		{year:2006, weeks:[52,52,52,53,1,1,1,1,1]},
		{year:2007, weeks:[52,52,53,53,1,1,1,1,1]},
		{year:2008, weeks:[53,53,53,53,1,1,1,2,2]},
		{year:2009, weeks:[53,53,53,53,1,1,2,2,2]},
		{year:2010, weeks:[53,53,53,53,1,2,2,2,2]},
		{year:2011, weeks:[53,53,53,53,1,1,1,1,1]},
		{year:2012, weeks:[52,52,53,53,1,1,1,1,1]},
		{year:2013, weeks:[52,53,53,53,1,1,1,1,2]},
		{year:2014, weeks:[53,53,53,53,1,1,1,2,2]},
	];
	checkWeekInYear(usWeekNumbers, 'US');

	// Basic stuff, ISO
	assertEquals("Sat Jun 17th 2006 (ISO)", 24, new Date(2006, 05, 17).getWeekInYear('ISO'));	
	assertEquals("Sun Jun 18th 2006 (ISO)", 24, new Date(2006, 05, 18).getWeekInYear('ISO'));	
	assertEquals("Mon Jun 19th 2006 (ISO)", 25, new Date(2006, 05, 19).getWeekInYear('ISO'));	

	// Year endings and beginnings (ISO)
	var isoWeekNumbers = [ 
		{year:2003, weeks:[52,1,1,1,1,1,1,1,2]},
		{year:2004, weeks:[53,53,53,53,53,53,1,1,1]},
		{year:2005, weeks:[52,52,52,52,52,1,1,1,1]},
		{year:2006, weeks:[52,52,52,52,1,1,1,1,1]},
		{year:2007, weeks:[52,52,52,1,1,1,1,1,1]},
		{year:2008, weeks:[52,1,1,1,1,1,1,1,2]},
		{year:2009, weeks:[53,53,53,53,53,53,53,1,1]},
		{year:2010, weeks:[52,52,52,52,52,52,1,1,1]},
		{year:2011, weeks:[52,52,52,52,52,1,1,1,1]},
		{year:2012, weeks:[52,52,52,1,1,1,1,1,1]},
		{year:2013, weeks:[52,52,1,1,1,1,1,1,1]},
		{year:2014, weeks:[52,1,1,1,1,1,1,1,2]},
	];
	checkWeekInYear(isoWeekNumbers, 'ISO');
}


function checkWeekInYear(years, weekNumberingSystem) {
	for (var i=0; i<years.length; i++) {
		var date = new Date(years[i].year, 11, 28);

		for (var j=0; j<9; j++) {
			var dayString = date.toString() + ' ('+ weekNumberingSystem +')';
			assertEquals(dayString, years[i].weeks[j], date.getWeekInYear(weekNumberingSystem));
			date.addDays(1);		
		}
	}
} 

function testFirstDateOfWeek() {
	assertEquals("Jun 11th 2006", new Date(2006, 5, 11).toString(), new Date(2006, 05, 14).setToFirstDateOfWeek(0).toString());
	assertEquals("Jun 12th 2006", new Date(2006, 5, 12).toString(), new Date(2006, 05, 14).setToFirstDateOfWeek(1).toString());
	assertEquals("Jun 12th 2006", new Date(2006, 5, 12).toString(), new Date(2006, 05, 18).setToFirstDateOfWeek(1).toString());
}

function testMonthChangedOnLastWeek() {
	var SUNDAY=0, MONDAY=1;
	assertFalse("Jun 3rd 2006 (US)", new Date(2006, 5, 3).hasMonthChangedOnPreviousWeek(SUNDAY));
	assertTrue("Jun 4th 2006 (US)", new Date(2006, 5, 4).hasMonthChangedOnPreviousWeek(SUNDAY));
	assertFalse("Jun 4th 2006 (ISO)", new Date(2006, 5, 4).hasMonthChangedOnPreviousWeek(MONDAY));
}


function tearDown() {
}
