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

var datesFI = { 
	months : ["Tammikuu","Helmikuu","Maaliskuu","Huhtikuu","Toukokuu","Kes\u00E4kuu",
			"Hein\u00E4kuu","Elokuu","Syyskuu","Lokakuu","Marraskuu","Joulukuu"], 
	shortMonths : ["Tammi","Helmi","Maalis","Huhti","Touko","Kes\u00E4",
			"Hein\u00E4","Elo","Syys","Loka","Marras","Joulu"],
	weekdays : ["Sunnuntai","Maanantai","Tiistai","Keskiviikko","Torstai","Perjantai","Lauantai"],
	shortWeekdays : ["Su","Ma","Ti","Ke","To","Pe","La"],
	firstDayOfWeek : 1
};

function setUp() {
}

function testConstruction() {
	new Wicket.Calendar("dob", "cal", "EEEE, MMMM d yyyy", datesFI);
}

function testStartTodayInMiddle() {
	var cal = new Wicket.Calendar("dob", "cal", "EEEE, MMMM d yyyy");
	assertEquals("Start day at start of week", 0, cal.getStartTodayInMiddle().getDay());
}

function tearDown() {
}
