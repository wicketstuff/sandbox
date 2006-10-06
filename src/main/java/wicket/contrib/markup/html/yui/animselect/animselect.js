var anim${boxId}_${javaScriptId};
var easing${boxId}_${javaScriptId} = ${easing};
var duration${boxId}_${javaScriptId} = ${duration};
var maxSelection${boxId}_${javaScriptId} = ${maxSelection};
var visible${boxId}_${javaScriptId} = {opacity: {to: 1}};
var invisible${boxId}_${javaScriptId} = {opacity: {to: 0}};
var noOfBoxes${boxId}_${javaScriptId} = ${noOfBoxes}; 
var boxes${boxId}_${javaScriptId} = ['DefaultImg${boxId}_${javaScriptId}', 'DefaultImgOver${boxId}_${javaScriptId}', 'SelectedImg${boxId}_${javaScriptId}', 'SelectedImgOver${boxId}_${javaScriptId}'];
var count${boxId}_${javaScriptId} = 0;
var value${boxId}_${javaScriptId} = ${value};
var message${boxId}_${javaScriptId} = '${message}';

function fnMouseOver${boxId}_${javaScriptId}(){
	if(count${boxId}_${javaScriptId}%2 == 0){
		fnShow${boxId}_${javaScriptId}('DefaultImgOver${boxId}_${javaScriptId}');
	}
	else{
		fnShow${boxId}_${javaScriptId}('SelectedImg${boxId}_${javaScriptId}');
	}
}

function fnMouseOut${boxId}_${javaScriptId}(){
	if(count${boxId}_${javaScriptId}%2 == 0){
		fnShow${boxId}_${javaScriptId}('DefaultImg${boxId}_${javaScriptId}');
	}
	else{
		fnShow${boxId}_${javaScriptId}('SelectedImgOver${boxId}_${javaScriptId}');
	}
}

function fnClick${boxId}_${javaScriptId}(){
	maxCount=0;
	
	//single selection
	if(maxSelection${boxId}_${javaScriptId} == 1 ){ 
		for(m=0; m< noOfBoxes${boxId}_${javaScriptId}; m++){ 
			if(eval("boxes"+m+"_${javaScriptId}[0]") == this.id){	
				eval("fnShow"+m+"_${javaScriptId}('SelectedImg"+m+"_${javaScriptId}')");
				eval("count"+m+"_${javaScriptId}++");
				//document.getElementById("selectedValue${boxId}_${javaScriptId}").value= value${boxId}_${javaScriptId};
			}
			else{
				eval("fnShow"+m+"_${javaScriptId}('DefaultImg"+m+"_${javaScriptId}')");
				eval("count"+m+"_${javaScriptId}=0");
			}
		}
	}
	
	//multiple selection
	else{
		for(i=0; i< noOfBoxes${boxId}_${javaScriptId}; i++){
			if((eval("count"+i+"_${javaScriptId}"))%2 == 1){
				maxCount++;	
			}
		}
		if(maxCount>= maxSelection${boxId}_${javaScriptId} && count${boxId}_${javaScriptId}%2==0){
			alert(message${boxId}_${javaScriptId});
		}
		else{
			if(count${boxId}_${javaScriptId}%2 == 0) {
				fnShow${boxId}_${javaScriptId}('SelectedImg${boxId}_${javaScriptId}');
				count${boxId}_${javaScriptId}++;
				fn_addSelection${boxId}_${javaScriptId}(value${boxId}_${javaScriptId});
			}
			else {	
				fnShow${boxId}_${javaScriptId}('DefaultImgOver${boxId}_${javaScriptId}');
				count${boxId}_${javaScriptId}++;
				fn_removeSelection${boxId}_${javaScriptId}(value${boxId}_${javaScriptId});
			}
		}
	}
}

function fn_addSelection${boxId}_${javaScriptId}(selectedValue){
	newValue="";
	currentValue= document.getElementById("selectedValue${boxId}_${javaScriptId}").value;
	if(currentValue == ""){
		newValue=selectedValue;
	}
	else{
		newValue=currentValue+","+selectedValue;
	}
	document.getElementById("selectedValue${boxId}_${javaScriptId}").value = newValue;
}

function fn_removeSelection${boxId}_${javaScriptId}(unselectedValue){
	newValue="";
	currentValue= ","+document.getElementById("selectedValue${boxId}_${javaScriptId}").value;
	newValue= currentValue.replace(","+unselectedValue, "");
	document.getElementById("selectedValue${boxId}_${javaScriptId}").value = newValue.substring(1, newValue.length);
}

function fnShow${boxId}_${javaScriptId}(elementId){

	for(i=0; i<boxes${boxId}_${javaScriptId}.length; i++){
	
		if(elementId == boxes${boxId}_${javaScriptId}[i]){
			anim = new YAHOO.util.Anim(boxes${boxId}_${javaScriptId}[i], visible${boxId}_${javaScriptId} , duration${boxId}_${javaScriptId} , easing${boxId}_${javaScriptId});
			anim.animate();	
		}
		else{
			anim = new YAHOO.util.Anim(boxes${boxId}_${javaScriptId}[i], invisible${boxId}_${javaScriptId}, duration${boxId}_${javaScriptId} , easing${boxId}_${javaScriptId});
			anim.animate();
		}
	}	
}

YAHOO.util.Event.addListener(boxes${boxId}_${javaScriptId}, "mouseover", fnMouseOver${boxId}_${javaScriptId});
YAHOO.util.Event.addListener(boxes${boxId}_${javaScriptId}, "mouseout", fnMouseOut${boxId}_${javaScriptId});
YAHOO.util.Event.addListener(boxes${boxId}_${javaScriptId}, "click", fnClick${boxId}_${javaScriptId});