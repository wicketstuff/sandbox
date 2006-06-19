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

function testTokensToFormat() {
	var format = "[y][MMM][d][W][D][F][EEE]";
	var sdf = new Wicket.SimpleDateFormat(format, new Wicket.DateLocale());
	assertEquals("Token format", format, sdf.tokensToFormat());	
	assertNotEquals("Token format", ' '+format, sdf.tokensToFormat());	
}

function testFormat() {
	var sdf = new Wicket.SimpleDateFormat("[y][MMM][d][W][D][F][EEE]", new Wicket.DateLocale());
	assertEquals("Jun 18th 2006", "[06][Jun][18][25][169][0][Sun]",  sdf.format(new Date(2006, 5, 18)));
}

function testParse() {
	var formats = [
		{ format:"[y][MMM][d]", value:"[1349][3][31]", legal:true, date: new Date(1349,2,31) },
		{ format:"[y][MMM][d][W][D][F][EEE]", value:"", legal:false },
		{ format:"dd.MM.yyyy", value:"11.5.2002", legal:true, date: new Date(2002,4,11) },
		{ format:"yyyy.MM.dd", value:"789.12.9", legal:true,  date: new Date(789,11,9) },
		{ format:"yyyy.MM.dd", value:"789,12,9", legal:false, date: new Date(789,11,9) },
		{ format:"yyyy.MM.dd", value:"789.1o.9", legal:false, date: new Date(789,11,9) },
		{ format:"yyyy.MM.dd", value:"789.o1.9", legal:false, date: new Date(789,11,9) }
	];
	for (var i=0; i<formats.length; i++) {
		checkParserFormats(formats[i]);
	} 
}

function checkParserFormats(format) {
	var sdf = new Wicket.SimpleDateFormat(format.format, new Wicket.DateLocale());
	var gotException = false;
	var exceptionText = '';
	var day = null;
	try {
		day = sdf.parse(format.value);
	} catch (parseException) {
		gotException = true;
		exceptionText = parseException;
	}

	if (format.legal) {
		assertFalse("Did throw parseException with format "+format.format+": "+exceptionText, gotException);
		assertEquals("Date parsing failed", format.date.toString(), day.toString());
	} else {
		assertTrue("Did not throw parseException with format "+format.format, gotException);
	}
}

function testParseString() {
	var sdf = new Wicket.SimpleDateFormat("dd.MM.yyyy", new Wicket.DateLocale());
	var day = "16.06.2006";
	assertEquals("First dot of "+day, 3, sdf.parseString(day, 2, "."));
	
	var gotException = false;
	try {
		sdf.parseString(day, 1, ".");
	} catch (parseException) {
		gotException = true;
	}
	assertTrue("Did not notice 6 instead of dot in "+day, gotException);

	sdf = new Wicket.SimpleDateFormat("dd---MM-@-yyyy", new Wicket.DateLocale());
	day = "16---06-@-2006";
	assertEquals("First string of "+day, 5, sdf.parseString(day, 2, "---"));
	assertEquals("Second string of "+day, 10, sdf.parseString(day, 7, "-@-"));

	sdf = new Wicket.SimpleDateFormat("dd':na 'MM':ta vuonna 'yyyy", new Wicket.DateLocale());
	day = "16:na 06:ta vuonna 2006";
	assertEquals("First string of "+day, 6, sdf.parseString(day, 2, ":na "));
	assertEquals("Second string of "+day, 19, sdf.parseString(day, 8, ":ta vuonna "));
}


function testFormatYear() {
	
}

function testParseYear() {
	
}

function testFormatMonth() {
	
}

function testParseMonth() {
	
}

function testFormatDayOfMonth() {
	
}

function testParseDayOfMonth() {
	
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
