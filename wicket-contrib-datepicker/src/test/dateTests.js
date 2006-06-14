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

function testConstruction() {
	new Wicket.Calendar("dob", "cal", "EEEE, MMMM d yyyy");
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
	assertEquals("Jan 31", 31, Date.getDayOfYear(2006, 0, 31));
	assertEquals("Feb 28", 59, Date.getDayOfYear(2006, 1, 28));
	assertEquals("Mar 1", 60, Date.getDayOfYear(2006, 2, 1));
	
	// Leap years
	assertEquals("Dec 31 2006", 365, Date.getDayOfYear(2006, 11, 31));
	assertEquals("Dec 31 2004 (leap)", 366, Date.getDayOfYear(2004, 11, 31));
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

	var dayOfYear = day.getDayOfYear();
	for (var i=0; i<364; i++) {
	    day.addDays(1);
		assertEquals("Hours mismatched for "+day, reference.getHours(), day.getHours());
		assertEquals("Mins mismatched for "+day, reference.getMinutes(), day.getMinutes());
		assertEquals("Secs mismatched for "+day, reference.getSeconds(), day.getSeconds());
	    assertEquals("Adding mismatch", dayOfYear+1, day.getDayOfYear())
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

function testFirstDateOfWeek() {
	assertEquals("Jun 11th 2006", new Date(2006, 5, 11).toString(), new Date(2006, 05, 14).getFirstDateOfWeek(0).toString());
	assertEquals("Jun 12th 2006", new Date(2006, 5, 12).toString(), new Date(2006, 05, 14).getFirstDateOfWeek(1).toString());
	assertEquals("Jun 12th 2006", new Date(2006, 5, 12).toString(), new Date(2006, 05, 18).getFirstDateOfWeek(1).toString());
}

function tearDown() {
}
