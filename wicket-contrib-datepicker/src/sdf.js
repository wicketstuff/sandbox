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
 * @author Igor Vaynberg (ivaynberg)
 */

if (Wicket == undefined) {
	var Wicket = {};
}

Wicket.SimpleDateFormat = function() {
	this.initialize.apply(this, arguments);
}

Wicket.SimpleDateFormat.pad = function(str, len) {
	var tmp = "" + str;
	while (tmp.length < len) {
		tmp = '0' + tmp;
	}
	return tmp;
}


Wicket.SimpleDateFormat.prototype = {
	initialize : function(expr, locale) {
		if (expr == undefined) {
			throw ("argument expr is required");
		}
		if (locale == undefined) {
			throw ("argument locale is required");
		}

		if (expr.length < 1) {
			throw ("argument expr cannot be an empty string");
		}

		this.formatters = {
			"y":this.formatYear,
			"M":this.formatMonth,
			"d":this.formatDayOfMonth,
			"W":this.formatWeekInYear,
			"D":this.formatDayOfYear,
			"F":this.formatDayOfWeek,
			"E":this.formatWeekday
		}

		this.tokens = this.tokenize(expr);
		this.locale = locale;
	},
	
	tokenize : function(expr) {
		var tokens = [];
		var token = 0;

		var i = 0;
		while (i < expr.length) {
			var c = expr[i];
			
			if ((c >= 'A'&& c <= 'Z') || (c >= 'a'&& c <= 'z')) {
				var legal = (this.formatters[c] != undefined);
    			
				if (!legal) {
    			throw("expression [["+expr+"]] contains an illegal character [["+c+"]] at position [["+i+"]]");
				} else {
					var start = i;
					for (i;i<expr.length;i++) {
						if (expr[i]!=c) break;
					}
					
					tokens[token] = [c,i-start];
					token++;
				}	
			}
			else if (c == '\'') {
				var quote = "";
				do {
					quote += expr[i];
					i++;
				} while (i < expr.length && expr[i] != '\'')
				
				if (i == expr.length) {
					throw("expression [["+expr+"]] contains an unclosed quote (')");
				}
				
				quote += "'";
				i++;
				
				if (quote.length == 2) {
					quote = "'";
				}
				tokens[token] = [quote];
				token++;
			}
			else {
				tokens[token] = [c];
				token++;
				i++;
			}
		}
		
		return tokens;
	},

	format : function(date) {
		var str = "";
		for (var i = 0;i<this.tokens.length;i++) {
		    var token = this.tokens[i];
		    if (token.length == 1) {
		        str+=token[0];
		    } else {
		        var c = token[0];
		        var rank = token[1];
		        var formatter = this.formatters[c];
		        str+=formatter(date, rank, this.locale);
		    }
		}
		return str;
	},

	formatYear : function(date, rank, locale) {
		var year = date.getFullYear();
		if (rank<=2) {
			return Wicket.SimpleDateFormat.pad(""+year-Math.floor(year/100)*100, 2);
		} else {
			return Wicket.SimpleDateFormat.pad(year, rank);
		}
	},
	
	formatMonth : function(date, rank, locale) {	
		var month = date.getMonth();
		
		if (rank<=2) {
		  return Wicket.SimpleDateFormat.pad(month, 2);
		} else if (rank == 3) {
	    return locale.getShortMonth(month);
		} else {
	    return locale.getMonth(month);
		}
	},
            
	formatDayOfMonth : function(date, rank, locale) {
		var day = date.getDate();
		return Wicket.SimpleDateFormat.pad(day, rank);
	},
            
	formatWeekInYear : function(date, rank, locale) {
		return Wicket.SimpleDateFormat.pad(date.getWeekInYear(locale.getWeekNumbering()), rank);
	},

	formatDayOfYear : function(date, rank, locale) {
		return Wicket.SimpleDateFormat.pad(date.getDayInYear(), rank);
	},

	formatDayOfWeek : function(date, rank, locale) {
		return Wicket.SimpleDateFormat.pad(date.getDay(), rank);
	},

	formatWeekday : function(date, rank, locale) {
		if (rank<=3) {
			return locale.getShortWeekday(date.getDay());
		} else {
			return locale.getWeekday(date.getDay());
		}
	}

}
