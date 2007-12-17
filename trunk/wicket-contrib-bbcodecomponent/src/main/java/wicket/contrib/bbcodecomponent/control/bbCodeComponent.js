/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

// Vars for identification of active textField

// instantiate to nothing
var currentTextField="";

function bbCodeSetActiveTextField(id){
	currentTextField=id;
}
function bbCodeInsertBBTag(tagStart, tagEnd)
{
	if(currentTextField.length!=0)
	{
		var textField=document.getElementById(currentTextField);
		var textValue=textField.value;
		var tag="";
		// try to figure out if we are starting a tag or ending it, this is not flawless
		// and will not repair missing tags
		if(textValue.lastIndexOf(tagStart)>textValue.lastIndexOf(tagEnd))
		{
			tag=tagEnd;
		}
		else
		{
			tag=tagStart;
		}
		insertAtCaret(textField,tag);
	}
}



// From http://parentnode.org/javascript/working-with-the-cursor-position/
function insertAtCaret(obj, text) {
		if(document.selection) {
			obj.focus();
			var orig = obj.value.replace(/\r\n/g, "\n");
			var range = document.selection.createRange();

			if(range.parentElement() != obj) {
				return false;
			}

			range.text = text;
			
			var actual = tmp = obj.value.replace(/\r\n/g, "\n");

			for(var diff = 0; diff < orig.length; diff++) {
				if(orig.charAt(diff) != actual.charAt(diff)) break;
			}

			for(var index = 0, start = 0; 
				tmp.match(text) 
					&& (tmp = tmp.replace(text, "")) 
					&& index <= diff; 
				index = start + text.length
			) {
				start = actual.indexOf(text, index);
			}
		} else if(obj.selectionStart) {
			var start = obj.selectionStart;
			var end   = obj.selectionEnd;

			obj.value = obj.value.substr(0, start) 
				+ text 
				+ obj.value.substr(end, obj.value.length);
		}
		
		if(start != null) {
			setCaretTo(obj, start + text.length);
		} else {
			obj.value += text;
		}
	}
		function setCaretTo(obj, pos) {
		if(obj.createTextRange) {
			var range = obj.createTextRange();
			range.move('character', pos);
			range.select();
		} else if(obj.selectionStart) {
			obj.focus();
			obj.setSelectionRange(pos, pos);
		}
	}
