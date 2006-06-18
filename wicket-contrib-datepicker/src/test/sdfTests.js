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

function testPad() {
	
}

function testConstruction() {
	
}

function testTokenize() {
	
}

function testFormat() {
	var sdf = new Wicket.SimpleDateFormat("[y][MMM][d][W][w][D][F][EEE]", new Wicket.DateLocale());
	assertEquals("Jun 18th 2006", "[06][Jun][18][25][3][169][0][Sun]",  sdf.format(new Date(2006, 5, 18)));
}

function testFormatYear() {
	
}

function testFormatMonth() {
	
}

function testFormatDayOfMonth() {
	
}

function testFormatWeekInYear() {
	
}

function testFormatWeekInMonth() {
	
}

function testFormatDayOfYear() {
	
}

function testFormatDayOfWeek() {
	
}

function testFormatWeekday() {
	
}

function tearDown() {

}
