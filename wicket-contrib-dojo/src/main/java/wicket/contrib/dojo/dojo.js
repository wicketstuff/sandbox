/* Copyright (c) 2004-2005 The Dojo Foundation, Licensed under the Academic Free License version 2.1 or above */var dj_global=this;
function dj_undef(_1,_2){
if(!_2){
_2=dj_global;
}
return (typeof _2[_1]=="undefined");
}
if(dj_undef("djConfig")){
var djConfig={};
}
var dojo;
if(dj_undef("dojo")){
dojo={};
}
dojo.version={major:0,minor:1,patch:0,flag:"+",revision:Number("$Rev: 1651 $".match(/[0-9]+/)[0]),toString:function(){
with(dojo.version){
return major+"."+minor+"."+patch+flag+" ("+revision+")";
}
}};
dojo.evalObjPath=function(_3,_4){
if(typeof _3!="string"){
return dj_global;
}
if(_3.indexOf(".")==-1){
return dj_global[_3];
}
var _5=_3.split(/\./);
var _6=dj_global;
for(var i=0;i<_5.length;++i){
if(!_4){
_6=_6[_5[i]];
if((typeof _6=="undefined")||(!_6)){
return _6;
}
}else{
if(dj_undef(_5[i],_6)){
_6[_5[i]]={};
}
_6=_6[_5[i]];
}
}
return _6;
};
dojo.errorToString=function(_8){
return ((!dj_undef("message",_8))?_8.message:(dj_undef("description",_8)?_8:_8.description));
};
dojo.raise=function(_9,_10){
if(_10){
_9=_9+": "+dojo.errorToString(_10);
}
var he=dojo.hostenv;
if(dj_undef("hostenv",dojo)&&dj_undef("println",dojo)){
dojo.hostenv.println("FATAL: "+_9);
}
throw Error(_9);
};
dj_throw=dj_rethrow=function(m,e){
dojo.deprecated("dj_throw and dj_rethrow deprecated, use dojo.raise instead");
dojo.raise(m,e);
};
dojo.debug=function(){
if(!djConfig.isDebug){
return;
}
var _14=arguments;
if(dj_undef("println",dojo.hostenv)){
dojo.raise("dojo.debug not available (yet?)");
}
var _15=dj_global["jum"]&&!dj_global["jum"].isBrowser;
var s=[(_15?"":"DEBUG: ")];
for(var i=0;i<_14.length;++i){
if(!false&&_14[i] instanceof Error){
var msg="["+_14[i].name+": "+dojo.errorToString(_14[i])+(_14[i].fileName?", file: "+_14[i].fileName:"")+(_14[i].lineNumber?", line: "+_14[i].lineNumber:"")+"]";
}else{
var msg=_14[i];
}
s.push(msg);
}
if(_15){
jum.debug(s.join(" "));
}else{
dojo.hostenv.println(s.join(" "));
}
};
var dj_debug=dojo.debug;
function dj_eval(s){
return dj_global.eval?dj_global.eval(s):eval(s);
}
dj_unimplemented=dojo.unimplemented=function(_18,_19){
var _20="'"+_18+"' not implemented";
if((typeof _19!="undefined")&&(_19)){
_20+=" "+_19;
}
dojo.raise(_20);
};
dj_deprecated=dojo.deprecated=function(_21,_22){
var _23="DEPRECATED: "+_21;
if((typeof _22!="undefined")&&(_22)){
_23+=" "+_22;
}
dojo.debug(_23);
};
dojo.inherits=function(_24,_25){
if(typeof _25!="function"){
dojo.raise("superclass: "+_25+" borken");
}
_24.prototype=new _25();
_24.prototype.constructor=_24;
_24.superclass=_25.prototype;
_24["super"]=_25.prototype;
};
dj_inherits=function(_26,_27){
dojo.deprecated("dj_inherits deprecated, use dojo.inherits instead");
dojo.inherits(_26,_27);
};
dojo.render=(function(){
function vscaffold(_28,_29){
var tmp={capable:false,support:{builtin:false,plugin:false},prefixes:_28};
for(var x in _29){
tmp[x]=false;
}
return tmp;
}
return {name:"",ver:dojo.version,os:{win:false,linux:false,osx:false},html:vscaffold(["html"],["ie","opera","khtml","safari","moz"]),svg:vscaffold(["svg"],["corel","adobe","batik"]),swf:vscaffold(["Swf","Flash","Mm"],["mm"]),swt:vscaffold(["Swt"],["ibm"])};
})();
dojo.hostenv=(function(){
var _32={isDebug:false,baseScriptUri:"",baseRelativePath:"",libraryScriptUri:"",iePreventClobber:false,ieClobberMinimal:true,preventBackButtonFix:true,searchIds:[],parseWidgets:true};
if(typeof djConfig=="undefined"){
djConfig=_32;
}else{
for(var _33 in _32){
if(typeof djConfig[_33]=="undefined"){
djConfig[_33]=_32[_33];
}
}
}
var djc=djConfig;
function _def(obj,_36,def){
return (dj_undef(_36,obj)?def:obj[_36]);
}
return {name_:"(unset)",version_:"(unset)",pkgFileName:"__package__",loading_modules_:{},loaded_modules_:{},addedToLoadingCount:[],removedFromLoadingCount:[],inFlightCount:0,modulePrefixes_:{dojo:{name:"dojo",value:"src"}},setModulePrefix:function(_38,_39){
this.modulePrefixes_[_38]={name:_38,value:_39};
},getModulePrefix:function(_40){
var mp=this.modulePrefixes_;
if((mp[_40])&&(mp[_40]["name"])){
return mp[_40].value;
}
return _40;
},getTextStack:[],loadUriStack:[],loadedUris:[],post_load_:false,modulesLoadedListeners:[],getName:function(){
return this.name_;
},getVersion:function(){
return this.version_;
},getText:function(uri){
dojo.unimplemented("getText","uri="+uri);
},getLibraryScriptUri:function(){
dojo.unimplemented("getLibraryScriptUri","");
}};
})();
dojo.hostenv.getBaseScriptUri=function(){
if(djConfig.baseScriptUri.length){
return djConfig.baseScriptUri;
}
var uri=new String(djConfig.libraryScriptUri||djConfig.baseRelativePath);
if(!uri){
dojo.raise("Nothing returned by getLibraryScriptUri(): "+uri);
}
var _43=uri.lastIndexOf("/");
djConfig.baseScriptUri=djConfig.baseRelativePath;
return djConfig.baseScriptUri;
};
dojo.hostenv.setBaseScriptUri=function(uri){
djConfig.baseScriptUri=uri;
};
dojo.hostenv.loadPath=function(_44,_45,cb){
if((_44.charAt(0)=="/")||(_44.match(/^\w+:/))){
dojo.raise("relpath '"+_44+"'; must be relative");
}
var uri=this.getBaseScriptUri()+_44;
try{
return ((!_45)?this.loadUri(uri,cb):this.loadUriAndCheck(uri,_45,cb));
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.hostenv.loadUri=function(uri,cb){
if(dojo.hostenv.loadedUris[uri]){
return;
}
var _47=this.getText(uri,null,true);
if(_47==null){
return 0;
}
var _48=dj_eval(_47);
return 1;
};
dojo.hostenv.getDepsForEval=function(_49){
if(!_49){
_49="";
}
var _50=[];
var tmp;
var _51=[/dojo.hostenv.loadModule\(.*?\)/mg,/dojo.hostenv.require\(.*?\)/mg,/dojo.require\(.*?\)/mg,/dojo.requireIf\(.*?\)/mg,/dojo.hostenv.conditionalLoadModule\([\w\W]*?\)/mg];
for(var i=0;i<_51.length;i++){
tmp=_49.match(_51[i]);
if(tmp){
for(var x=0;x<tmp.length;x++){
_50.push(tmp[x]);
}
}
}
return _50;
};
dojo.hostenv.loadUriAndCheck=function(uri,_52,cb){
var ok=true;
try{
ok=this.loadUri(uri,cb);
}
catch(e){
dojo.debug("failed loading ",uri," with error: ",e);
}
return ((ok)&&(this.findModule(_52,false)))?true:false;
};
dojo.loaded=function(){
};
dojo.hostenv.loaded=function(){
this.post_load_=true;
var mll=this.modulesLoadedListeners;
for(var x=0;x<mll.length;x++){
mll[x]();
}
dojo.loaded();
};
dojo.addOnLoad=function(obj,_55){
if(arguments.length==1){
dojo.hostenv.modulesLoadedListeners.push(obj);
}else{
if(arguments.length>1){
dojo.hostenv.modulesLoadedListeners.push(function(){
obj[_55]();
});
}
}
};
dojo.hostenv.modulesLoaded=function(){
if(this.post_load_){
return;
}
if((this.loadUriStack.length==0)&&(this.getTextStack.length==0)){
if(this.inFlightCount>0){
dojo.debug("files still in flight!");
return;
}
if(typeof setTimeout=="object"){
setTimeout("dojo.hostenv.loaded();",0);
}else{
dojo.hostenv.loaded();
}
}
};
dojo.hostenv.moduleLoaded=function(_56){
var _57=dojo.evalObjPath((_56.split(".").slice(0,-1)).join("."));
this.loaded_modules_[(new String(_56)).toLowerCase()]=_57;
};
dojo.hostenv.loadModule=function(_58,_59,_60){
var _61=this.findModule(_58,false);
if(_61){
return _61;
}
if(dj_undef(_58,this.loading_modules_)){
this.addedToLoadingCount.push(_58);
}
this.loading_modules_[_58]=1;
var _62=_58.replace(/\./g,"/")+".js";
var _63=_58.split(".");
var _64=_58.split(".");
for(var i=_63.length-1;i>0;i--){
var _65=_63.slice(0,i).join(".");
var _66=this.getModulePrefix(_65);
if(_66!=_65){
_63.splice(0,i,_66);
break;
}
}
var _67=_63[_63.length-1];
if(_67=="*"){
_58=(_64.slice(0,-1)).join(".");
while(_63.length){
_63.pop();
_63.push(this.pkgFileName);
_62=_63.join("/")+".js";
if(_62.charAt(0)=="/"){
_62=_62.slice(1);
}
ok=this.loadPath(_62,((!_60)?_58:null));
if(ok){
break;
}
_63.pop();
}
}else{
_62=_63.join("/")+".js";
_58=_64.join(".");
var ok=this.loadPath(_62,((!_60)?_58:null));
if((!ok)&&(!_59)){
_63.pop();
while(_63.length){
_62=_63.join("/")+".js";
ok=this.loadPath(_62,((!_60)?_58:null));
if(ok){
break;
}
_63.pop();
_62=_63.join("/")+"/"+this.pkgFileName+".js";
if(_62.charAt(0)=="/"){
_62=_62.slice(1);
}
ok=this.loadPath(_62,((!_60)?_58:null));
if(ok){
break;
}
}
}
if((!ok)&&(!_60)){
dojo.raise("Could not load '"+_58+"'; last tried '"+_62+"'");
}
}
if(!_60){
_61=this.findModule(_58,false);
if(!_61){
dojo.raise("symbol '"+_58+"' is not defined after loading '"+_62+"'");
}
}
return _61;
};
dojo.hostenv.startPackage=function(_68){
var _69=_68.split(/\./);
if(_69[_69.length-1]=="*"){
_69.pop();
}
return dojo.evalObjPath(_69.join("."),true);
};
dojo.hostenv.findModule=function(_70,_71){
if(this.loaded_modules_[(new String(_70)).toLowerCase()]){
return this.loaded_modules_[_70];
}
var _72=dojo.evalObjPath(_70);
if((typeof _72!=="undefined")&&(_72)){
return _72;
}
if(_71){
dojo.raise("no loaded module named '"+_70+"'");
}
return null;
};
if(typeof window=="undefined"){
dojo.raise("no window object");
}
(function(){
if(((djConfig["baseScriptUri"]=="")||(djConfig["baseRelativePath"]==""))&&(document&&document.getElementsByTagName)){
var _73=document.getElementsByTagName("script");
var _74=/(__package__|dojo)\.js$/i;
for(var i=0;i<_73.length;i++){
var src=_73[i].getAttribute("src");
if(_74.test(src)){
var _76=src.replace(_74,"");
if(djConfig["baseScriptUri"]==""){
djConfig["baseScriptUri"]=_76;
}
if(djConfig["baseRelativePath"]==""){
djConfig["baseRelativePath"]=_76;
}
break;
}
}
}
var dr=dojo.render;
var drh=dojo.render.html;
var dua=drh.UA=navigator.userAgent;
var dav=drh.AV=navigator.appVersion;
var t=true;
var f=false;
drh.capable=t;
drh.support.builtin=t;
dr.ver=parseFloat(drh.AV);
dr.os.mac=dav.indexOf("Macintosh")>=0;
dr.os.win=dav.indexOf("Windows")>=0;
drh.opera=dua.indexOf("Opera")>=0;
drh.khtml=(dav.indexOf("Konqueror")>=0)||(dav.indexOf("Safari")>=0);
drh.safari=dav.indexOf("Safari")>=0;
drh.mozilla=drh.moz=(dua.indexOf("Gecko")>=0)&&(!drh.khtml);
drh.ie=(document.all)&&(!drh.opera);
drh.ie50=drh.ie&&dav.indexOf("MSIE 5.0")>=0;
drh.ie55=drh.ie&&dav.indexOf("MSIE 5.5")>=0;
drh.ie60=drh.ie&&dav.indexOf("MSIE 6.0")>=0;
dr.svg.capable=f;
dr.svg.support.plugin=f;
dr.svg.support.builtin=f;
dr.svg.adobe=f;
if(document.createElementNS&&drh.moz&&parseFloat(dua.substring(dua.lastIndexOf("/")+1,dua.length))>1){
dr.svg.capable=t;
dr.svg.support.builtin=t;
dr.svg.support.plugin=f;
dr.svg.adobe=f;
}else{
if(navigator.mimeTypes&&navigator.mimeTypes.length>0){
var _83=navigator.mimeTypes["image/svg+xml"]||navigator.mimeTypes["image/svg"]||navigator.mimeTypes["image/svg-xml"];
if(_83){
dr.svg.capable=t;
dr.svg.support.plugin=t;
dr.svg.adobe=_83&&_83.enabledPlugin&&_83.enabledPlugin.description&&(_83.enabledPlugin.description.indexOf("Adobe")>-1);
}
}else{
if(drh.ie&&dr.os.win){
var _83=f;
try{
var _84=new ActiveXObject("Adobe.SVGCtl");
_83=t;
}
catch(e){
}
if(_83){
dr.svg.capable=t;
dr.svg.support.plugin=t;
dr.svg.adobe=t;
}
}else{
dr.svg.capable=f;
dr.svg.support.plugin=f;
dr.svg.adobe=f;
}
}
}
})();
dojo.hostenv.startPackage("dojo.hostenv");
dojo.hostenv.name_="browser";
dojo.hostenv.searchIds=[];
var DJ_XMLHTTP_PROGIDS=["Msxml2.XMLHTTP","Microsoft.XMLHTTP","Msxml2.XMLHTTP.4.0"];
dojo.hostenv.getXmlhttpObject=function(){
var _85=null;
var _86=null;
try{
_85=new XMLHttpRequest();
}
catch(e){
}
if(!_85){
for(var i=0;i<3;++i){
var _87=DJ_XMLHTTP_PROGIDS[i];
try{
_85=new ActiveXObject(_87);
}
catch(e){
_86=e;
}
if(_85){
DJ_XMLHTTP_PROGIDS=[_87];
break;
}
}
}
if(!_85){
return dojo.raise("XMLHTTP not available",_86);
}
return _85;
};
dojo.hostenv.getText=function(uri,_88,_89){
var _90=this.getXmlhttpObject();
if(_88){
_90.onreadystatechange=function(){
if((4==_90.readyState)&&(_90["status"])){
if(_90.status==200){
dojo.debug("LOADED URI: "+uri);
_88(_90.responseText);
}
}
};
}
_90.open("GET",uri,_88?true:false);
_90.send(null);
if(_88){
return null;
}
return _90.responseText;
};
function dj_last_script_src(){
var _91=window.document.getElementsByTagName("script");
if(_91.length<1){
dojo.raise("No script elements in window.document, so can't figure out my script src");
}
var _92=_91[_91.length-1];
var src=_92.src;
if(!src){
dojo.raise("Last script element (out of "+_91.length+") has no src");
}
return src;
}
if(!dojo.hostenv["library_script_uri_"]){
dojo.hostenv.library_script_uri_=dj_last_script_src();
}
dojo.hostenv.defaultDebugContainerId="dojoDebug";
dojo.hostenv.println=function(_93){
try{
var _94=document.getElementById(djConfig.debugContainerId?djConfig.debugContainerId:dojo.hostenv.defaultDebugContainerId);
if(!_94){
_94=document.body;
}
var div=document.createElement("div");
div.appendChild(document.createTextNode(_93));
_94.appendChild(div);
}
catch(e){
try{
document.write("<div>"+_93+"</div>");
}
catch(e2){
window.status=_93;
}
}
};
function dj_addNodeEvtHdlr(_96,_97,fp,_99){
var _100=_96["on"+_97]||function(){
};
_96["on"+_97]=function(){
fp.apply(_96,arguments);
_100.apply(_96,arguments);
};
return true;
}
dj_addNodeEvtHdlr(window,"load",function(){
if(dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
dojo.hostenv.modulesLoaded();
});
dojo.hostenv.makeWidgets=function(){
if((djConfig.parseWidgets)||(djConfig.searchIds.length>0)){
if(dojo.evalObjPath("dojo.widget.Parse")){
try{
var _101=new dojo.xml.Parse();
var sids=djConfig.searchIds;
if(sids.length>0){
for(var x=0;x<sids.length;x++){
if(!document.getElementById(sids[x])){
continue;
}
var frag=_101.parseElement(document.getElementById(sids[x]),null,true);
dojo.widget.getParser().createComponents(frag);
}
}else{
if(djConfig.parseWidgets){
var frag=_101.parseElement(document.body,null,true);
dojo.widget.getParser().createComponents(frag);
}
}
}
catch(e){
dojo.debug("auto-build-widgets error:",e);
}
}
}
};
dojo.hostenv.modulesLoadedListeners.push(function(){
if(!dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
});
try{
if(!window["djConfig"]||!window.djConfig["preventBackButtonFix"]){
document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+(dojo.hostenv.getBaseScriptUri()+"iframe_history.html")+"'></iframe>");
}
if(dojo.render.html.ie){
document.write("<style>v:*{ behavior:url(#default#VML); }</style>");
document.write("<xml:namespace ns=\"urn:schemas-microsoft-com:vml\" prefix=\"v\"/>");
}
}
catch(e){
}
dojo.hostenv.writeIncludes=function(){
};
dojo.hostenv.conditionalLoadModule=function(_104){
var _105=_104["common"]||[];
var _106=(_104[dojo.hostenv.name_])?_105.concat(_104[dojo.hostenv.name_]||[]):_105.concat(_104["default"]||[]);
for(var x=0;x<_106.length;x++){
var curr=_106[x];
if(curr.constructor==Array){
dojo.hostenv.loadModule.apply(dojo.hostenv,curr);
}else{
dojo.hostenv.loadModule(curr);
}
}
};
dojo.hostenv.require=dojo.hostenv.loadModule;
dojo.require=function(){
dojo.hostenv.loadModule.apply(dojo.hostenv,arguments);
};
dojo.requireIf=function(){
if((arguments[0]=="common")||(dojo.render[arguments[0]].capable)){
var args=[];
for(var i=1;i<arguments.length;i++){
args.push(arguments[i]);
}
dojo.require.apply(dojo,args);
}
};
dojo.conditionalRequire=dojo.requireIf;
dojo.kwCompoundRequire=function(){
dojo.hostenv.conditionalLoadModule.apply(dojo.hostenv,arguments);
};
dojo.hostenv.provide=dojo.hostenv.startPackage;
dojo.provide=function(){
dojo.hostenv.startPackage.apply(dojo.hostenv,arguments);
};
dojo.provide("dojo.lang");
dojo.provide("dojo.lang.Lang");
dojo.lang.mixin=function(obj,_109){
var tobj={};
for(var x in _109){
if(typeof tobj[x]=="undefined"){
obj[x]=_109[x];
}
}
return obj;
};
dojo.lang.extend=function(ctor,_112){
this.mixin(ctor.prototype,_112);
};
dojo.lang.extendPrototype=function(obj,_113){
this.extend(obj.constructor,_113);
};
dojo.lang.setTimeout=function(func,_115){
var _116=window,argsStart=2;
if(typeof _115=="function"){
_116=func;
func=_115;
_115=arguments[2];
argsStart++;
}
var args=[];
for(var i=argsStart;i<arguments.length;i++){
args.push(arguments[i]);
}
return setTimeout(function(){
func.apply(_116,args);
},_115);
};
dojo.lang.isObject=function(wh){
return typeof wh=="object"||dojo.lang.isArray(wh)||dojo.lang.isFunction(wh);
};
dojo.lang.isArray=function(wh){
return (wh instanceof Array||typeof wh=="array");
};
dojo.lang.isFunction=function(wh){
return (wh instanceof Function||typeof wh=="function");
};
dojo.lang.isString=function(wh){
return (wh instanceof String||typeof wh=="string");
};
dojo.lang.isNumber=function(wh){
return (wh instanceof Number||typeof wh=="number");
};
dojo.lang.isBoolean=function(wh){
return (wh instanceof Boolean||typeof wh=="boolean");
};
dojo.lang.isUndefined=function(wh){
return ((wh==undefined)&&(typeof wh=="undefined"));
};
dojo.lang.isAlien=function(wh){
return !dojo.lang.isFunction()&&/\{\s*\[native code\]\s*\}/.test(String(wh));
};
dojo.lang.find=function(arr,val,_120){
if(_120){
for(var i=0;i<arr.length;++i){
if(arr[i]===val){
return i;
}
}
}else{
for(var i=0;i<arr.length;++i){
if(arr[i]==val){
return i;
}
}
}
return -1;
};
dojo.lang.inArray=function(arr,val){
if((!arr||arr.constructor!=Array)&&(val&&val.constructor==Array)){
var a=arr;
arr=val;
val=a;
}
return dojo.lang.find(arr,val)>-1;
};
dojo.lang.getNameInObj=function(ns,item){
if(!ns){
ns=dj_global;
}
for(var x in ns){
if(ns[x]===item){
return new String(x);
}
}
return null;
};
dojo.lang.has=function(obj,name){
return (typeof obj[name]!=="undefined");
};
dojo.lang.isEmpty=function(obj){
var tmp={};
var _125=0;
for(var x in obj){
if(obj[x]&&(!tmp[x])){
_125++;
break;
}
}
return (_125==0);
};
dojo.lang.forEach=function(arr,_126,_127){
var il=arr.length;
for(var i=0;i<((_127)?il:arr.length);i++){
if(_126(arr[i])=="break"){
break;
}
}
};
dojo.lang.map=function(arr,obj,_129){
if((typeof obj=="function")&&(!_129)){
_129=obj;
obj=dj_global;
}
for(var i=0;i<arr.length;++i){
_129.call(obj,arr[i]);
}
};
dojo.lang.tryThese=function(){
for(var x=0;x<arguments.length;x++){
try{
if(typeof arguments[x]=="function"){
var ret=(arguments[x]());
if(ret){
return ret;
}
}
}
catch(e){
dojo.debug(e);
}
}
};
dojo.lang.delayThese=function(farr,cb,_132,_133){
if(!farr.length){
if(typeof _133=="function"){
_133();
}
return;
}
if((typeof _132=="undefined")&&(typeof cb=="number")){
_132=cb;
cb=function(){
};
}else{
if(!cb){
cb=function(){
};
}
}
setTimeout(function(){
(farr.shift())();
cb();
dojo.lang.delayThese(farr,cb,_132,_133);
},_132);
};
dojo.lang.shallowCopy=function(obj){
var ret={},key;
for(key in obj){
if(dojo.lang.isUndefined(ret[key])){
ret[key]=obj[key];
}
}
return ret;
};
dojo.provide("dojo.string");
dojo.require("dojo.lang");
dojo.string.trim=function(_134){
if(arguments.length==0){
_134=this;
}
if(typeof _134!="string"){
return _134;
}
if(!_134.length){
return _134;
}
return _134.replace(/^\s*/,"").replace(/\s*$/,"");
};
dojo.string.paramString=function(str,_136,_137){
if(typeof str!="string"){
_136=str;
_137=_136;
str=this;
}
for(var name in _136){
var re=new RegExp("\\%\\{"+name+"\\}","g");
str=str.replace(re,_136[name]);
}
if(_137){
str=str.replace(/%\{([^\}\s]+)\}/g,"");
}
return str;
};
dojo.string.capitalize=function(str){
if(typeof str!="string"||str==null){
return "";
}
if(arguments.length==0){
str=this;
}
var _139=str.split(" ");
var _140="";
var len=_139.length;
for(var i=0;i<len;i++){
var word=_139[i];
word=word.charAt(0).toUpperCase()+word.substring(1,word.length);
_140+=word;
if(i<len-1){
_140+=" ";
}
}
return new String(_140);
};
dojo.string.isBlank=function(str){
if(!dojo.lang.isString(str)){
return true;
}
return (dojo.string.trim(str).length==0);
};
dojo.string.encodeAscii=function(str){
if(!dojo.lang.isString(str)){
return str;
}
var ret="";
var _143=escape(str);
var _144,re=/%u([0-9A-F]{4})/i;
while((_144=_143.match(re))){
var num=Number("0x"+_144[1]);
var _146=escape("&#"+num+";");
ret+=_143.substring(0,_144.index)+_146;
_143=_143.substring(_144.index+_144[0].length);
}
ret+=_143.replace(/\+/g,"%2B");
return ret;
};
dojo.provide("dojo.io.IO");
dojo.require("dojo.string");
dojo.io.transports=[];
dojo.io.hdlrFuncNames=["load","error"];
dojo.io.Request=function(url,_148,_149,_150){
if((arguments.length==1)&&(arguments[0].constructor==Object)){
this.fromKwArgs(arguments[0]);
}else{
this.url=url;
if(arguments.length>=2){
this.mimetype=_148;
}
if(arguments.length>=3){
this.transport=_149;
}
if(arguments.length>=4){
this.changeUrl=_150;
}
}
};
dojo.lang.extend(dojo.io.Request,{url:"",mimetype:"text/plain",method:"GET",content:undefined,transport:undefined,changeUrl:undefined,formNode:undefined,sync:false,bindSuccess:false,useCache:false,load:function(type,data,evt){
},error:function(type,_154){
},fromKwArgs:function(_155){
if(_155["url"]){
_155.url=_155.url.toString();
}
if(!_155["method"]&&_155["formNode"]&&_155["formNode"].method){
_155.method=_155["formNode"].method;
}
if(!_155["handle"]&&_155["handler"]){
_155.handle=_155.handler;
}
if(!_155["load"]&&_155["loaded"]){
_155.load=_155.loaded;
}
if(!_155["changeUrl"]&&_155["changeURL"]){
_155.changeUrl=_155.changeURL;
}
if(!_155["encoding"]){
if(!dojo.lang.isUndefined(djConfig["bindEncoding"])){
_155.encoding=djConfig.bindEncoding;
}else{
_155.encoding="";
}
}
var _156=dojo.lang.isFunction;
for(var x=0;x<dojo.io.hdlrFuncNames.length;x++){
var fn=dojo.io.hdlrFuncNames[x];
if(_156(_155[fn])){
continue;
}
if(_156(_155["handle"])){
_155[fn]=_155.handle;
}
}
dojo.lang.mixin(this,_155);
}});
dojo.io.Error=function(msg,type,num){
this.message=msg;
this.type=type||"unknown";
this.number=num||0;
};
dojo.io.transports.addTransport=function(name){
this.push(name);
this[name]=dojo.io[name];
};
dojo.io.bind=function(_158){
if(!(_158 instanceof dojo.io.Request)){
try{
_158=new dojo.io.Request(_158);
}
catch(e){
dojo.debug(e);
}
}
var _159="";
if(_158["transport"]){
_159=_158["transport"];
if(!this[_159]){
return _158;
}
}else{
for(var x=0;x<dojo.io.transports.length;x++){
var tmp=dojo.io.transports[x];
if((this[tmp])&&(this[tmp].canHandle(_158))){
_159=tmp;
}
}
if(_159==""){
return _158;
}
}
this[_159].bind(_158);
_158.bindSuccess=true;
return _158;
};
dojo.io.argsFromMap=function(map,_161){
var _162=new Object();
var _163="";
var enc=/utf/i.test(_161||"")?encodeURIComponent:dojo.string.encodeAscii;
for(var x in map){
if(!_162[x]){
_163+=enc(x)+"="+enc(map[x])+"&";
}
}
return _163;
};
dojo.provide("dojo.graphics.color");
dojo.require("dojo.lang");
dojo.graphics.color.Color=function(r,g,b,a){
if(dojo.lang.isArray(r)){
this.r=r[0];
this.g=r[1];
this.b=r[2];
this.a=r[3]||1;
}else{
if(dojo.lang.isString(r)){
var rgb=dojo.graphics.color.extractRGB(r);
this.r=rgb[0];
this.g=rgb[1];
this.b=rgb[2];
this.a=b||1;
}else{
this.r=r;
this.g=g;
this.b=b;
}
}
};
dojo.graphics.color.Color.prototype.toRgb=function(){
return [this.r,this.g,this.b];
};
dojo.graphics.color.Color.prototype.toHex=function(){
return dojo.graphics.color.rgb2hex(this.toRgb());
};
dojo.graphics.color.Color.prototype.toCss=function(){
return "rgb("+this.toRgb().join()+")";
};
dojo.graphics.color.Color.prototype.toString=function(){
return this.toHex();
};
dojo.graphics.color.named={white:[255,255,255],black:[0,0,0],red:[255,0,0],green:[0,255,0],blue:[0,0,255],navy:[0,0,128],gray:[128,128,128],silver:[192,192,192]};
dojo.graphics.color.blend=function(a,b,_169){
if(typeof a=="string"){
return dojo.graphics.color.blendHex(a,b,_169);
}
if(!_169){
_169=0;
}else{
if(_169>1){
_169=1;
}else{
if(_169<-1){
_169=-1;
}
}
}
var c=new Array(3);
for(var i=0;i<3;i++){
var half=Math.abs(a[i]-b[i])/2;
c[i]=Math.floor(Math.min(a[i],b[i])+half+(half*_169));
}
return c;
};
dojo.graphics.color.blendHex=function(a,b,_172){
return dojo.graphics.color.rgb2hex(dojo.graphics.color.blend(dojo.graphics.color.hex2rgb(a),dojo.graphics.color.hex2rgb(b),_172));
};
dojo.graphics.color.extractRGB=function(_173){
var hex="0123456789abcdef";
_173=_173.toLowerCase();
if(_173.indexOf("rgb")==0){
dojo.debug("e1");
var _175=_173.match(/rgba*\((\d+), *(\d+), *(\d+)/i);
var ret=_175.splice(1,3);
return ret;
}else{
var _176=dojo.graphics.color.hex2rgb(_173);
if(_176){
return _176;
}else{
return dojo.graphics.color.named[_173]||[255,255,255];
}
}
};
dojo.graphics.color.hex2rgb=function(hex){
var _177="0123456789ABCDEF";
var rgb=new Array(3);
if(hex.indexOf("#")==0){
hex=hex.substring(1);
}
hex=hex.toUpperCase();
if(hex.replace(new RegExp("["+_177+"]","g"),"")!=""){
return null;
}
if(hex.length==3){
rgb[0]=hex.charAt(0)+hex.charAt(0);
rgb[1]=hex.charAt(1)+hex.charAt(1);
rgb[2]=hex.charAt(2)+hex.charAt(2);
}else{
rgb[0]=hex.substring(0,2);
rgb[1]=hex.substring(2,4);
rgb[2]=hex.substring(4);
}
for(var i=0;i<rgb.length;i++){
rgb[i]=_177.indexOf(rgb[i].charAt(0))*16+_177.indexOf(rgb[i].charAt(1));
}
return rgb;
};
dojo.graphics.color.rgb2hex=function(r,g,b){
if(dojo.lang.isArray(r)){
g=r[1]||"00";
b=r[2]||"00";
r=r[0]||"00";
}
return ["#",r.toString(16),g.toString(16),b.toString(16)].join("");
};
dojo.provide("dojo.dom");
dojo.dom.ELEMENT_NODE=1;
dojo.dom.ATTRIBUTE_NODE=2;
dojo.dom.TEXT_NODE=3;
dojo.dom.CDATA_SECTION_NODE=4;
dojo.dom.ENTITY_REFERENCE_NODE=5;
dojo.dom.ENTITY_NODE=6;
dojo.dom.PROCESSING_INSTRUCTION_NODE=7;
dojo.dom.COMMENT_NODE=8;
dojo.dom.DOCUMENT_NODE=9;
dojo.dom.DOCUMENT_TYPE_NODE=10;
dojo.dom.DOCUMENT_FRAGMENT_NODE=11;
dojo.dom.NOTATION_NODE=12;
dojo.dom.dojoml="http://www.dojotoolkit.org/2004/dojoml";
dojo.dom.xmlns={svg:"http://www.w3.org/2000/svg",smil:"http://www.w3.org/2001/SMIL20/",mml:"http://www.w3.org/1998/Math/MathML",cml:"http://www.xml-cml.org",xlink:"http://www.w3.org/1999/xlink",xhtml:"http://www.w3.org/1999/xhtml",xul:"http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul",xbl:"http://www.mozilla.org/xbl",fo:"http://www.w3.org/1999/XSL/Format",xsl:"http://www.w3.org/1999/XSL/Transform",xslt:"http://www.w3.org/1999/XSL/Transform",xi:"http://www.w3.org/2001/XInclude",xforms:"http://www.w3.org/2002/01/xforms",saxon:"http://icl.com/saxon",xalan:"http://xml.apache.org/xslt",xsd:"http://www.w3.org/2001/XMLSchema",dt:"http://www.w3.org/2001/XMLSchema-datatypes",xsi:"http://www.w3.org/2001/XMLSchema-instance",rdf:"http://www.w3.org/1999/02/22-rdf-syntax-ns#",rdfs:"http://www.w3.org/2000/01/rdf-schema#",dc:"http://purl.org/dc/elements/1.1/",dcq:"http://purl.org/dc/qualifiers/1.0","soap-env":"http://schemas.xmlsoap.org/soap/envelope/",wsdl:"http://schemas.xmlsoap.org/wsdl/",AdobeExtensions:"http://ns.adobe.com/AdobeSVGViewerExtensions/3.0/"};
dojo.dom.getTagName=function(node){
var _179=node.tagName;
if(_179.substr(0,5).toLowerCase()!="dojo:"){
if(_179.substr(0,4).toLowerCase()=="dojo"){
return "dojo:"+_179.substring(4).toLowerCase();
}
var djt=node.getAttribute("dojoType")||node.getAttribute("dojotype");
if(djt){
return "dojo:"+djt.toLowerCase();
}
if((node.getAttributeNS)&&(node.getAttributeNS(this.dojoml,"type"))){
return "dojo:"+node.getAttributeNS(this.dojoml,"type").toLowerCase();
}
try{
djt=node.getAttribute("dojo:type");
}
catch(e){
}
if(djt){
return "dojo:"+djt.toLowerCase();
}
if((!dj_global["djConfig"])||(!djConfig["ignoreClassNames"])){
var _181=node.className||node.getAttribute("class");
if((_181)&&(_181.indexOf("dojo-")!=-1)){
var _182=_181.split(" ");
for(var x=0;x<_182.length;x++){
if((_182[x].length>5)&&(_182[x].indexOf("dojo-")>=0)){
return "dojo:"+_182[x].substr(5);
}
}
}
}
}
return _179.toLowerCase();
};
dojo.dom.getUniqueId=function(){
do{
var id="dj_unique_"+(++arguments.callee._idIncrement);
}while(document.getElementById(id));
return id;
};
dojo.dom.getUniqueId._idIncrement=0;
dojo.dom.getFirstChildElement=function(_184){
var node=_184.firstChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.nextSibling;
}
return node;
};
dojo.dom.getLastChildElement=function(_185){
var node=_185.lastChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.previousSibling;
}
return node;
};
dojo.dom.getNextSiblingElement=function(node){
if(!node){
return null;
}
do{
node=node.nextSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
return node;
};
dojo.dom.getPreviousSiblingElement=function(node){
if(!node){
return null;
}
do{
node=node.previousSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
return node;
};
dojo.dom.moveChildren=function(_186,_187,trim){
var _189=0;
if(trim){
while(_186.hasChildNodes()&&_186.firstChild.nodeType==dojo.dom.TEXT_NODE){
_186.removeChild(_186.firstChild);
}
while(_186.hasChildNodes()&&_186.lastChild.nodeType==dojo.dom.TEXT_NODE){
_186.removeChild(_186.lastChild);
}
}
while(_186.hasChildNodes()){
_187.appendChild(_186.firstChild);
_189++;
}
return _189;
};
dojo.dom.copyChildren=function(_190,_191,trim){
var _192=_190.cloneNode(true);
return this.moveChildren(_192,_191,trim);
};
dojo.dom.removeChildren=function(node){
var _193=node.childNodes.length;
while(node.hasChildNodes()){
node.removeChild(node.firstChild);
}
return _193;
};
dojo.dom.replaceChildren=function(node,_194){
dojo.dom.removeChildren(node);
node.appendChild(_194);
};
dojo.dom.removeNode=function(node){
if(node&&node.parentNode){
node.parentNode.removeChild(node);
}
};
dojo.dom.getAncestors=function(node){
var _195=[];
while(node){
_195.push(node);
node=node.parentNode;
}
return _195;
};
dojo.dom.isDescendantOf=function(node,_196,_197){
if(_197&&node){
node=node.parentNode;
}
while(node){
if(node==_196){
return true;
}
node=node.parentNode;
}
return false;
};
dojo.dom.innerXML=function(node){
if(node.innerXML){
return node.innerXML;
}else{
if(typeof XMLSerializer!="undefined"){
return (new XMLSerializer()).serializeToString(node);
}
}
};
dojo.dom.createDocumentFromText=function(str,_198){
if(!_198){
_198="text/xml";
}
if(typeof DOMParser!="undefined"){
var _199=new DOMParser();
return _199.parseFromString(str,_198);
}else{
if(typeof ActiveXObject!="undefined"){
var _200=new ActiveXObject("Microsoft.XMLDOM");
if(_200){
_200.async=false;
_200.loadXML(str);
return _200;
}else{
dojo.debug("toXml didn't work?");
}
}else{
if(document.createElement){
var tmp=document.createElement("xml");
tmp.innerHTML=str;
if(document.implementation&&document.implementation.createDocument){
var _201=document.implementation.createDocument("foo","",null);
for(var i=0;i<tmp.childNodes.length;i++){
_201.importNode(tmp.childNodes.item(i),true);
}
return _201;
}
return tmp.document&&tmp.document.firstChild?tmp.document.firstChild:tmp;
}
}
}
return null;
};
if(dojo.render.html.capable){
dojo.dom.createNodesFromText=function(txt,wrap){
var tn=document.createElement("div");
tn.style.visibility="hidden";
document.body.appendChild(tn);
tn.innerHTML=txt;
tn.normalize();
if(wrap){
var ret=[];
var fc=tn.firstChild;
ret[0]=((fc.nodeValue==" ")||(fc.nodeValue=="\t"))?fc.nextSibling:fc;
document.body.removeChild(tn);
return ret;
}
var _206=[];
for(var x=0;x<tn.childNodes.length;x++){
_206.push(tn.childNodes[x].cloneNode(true));
}
tn.style.display="none";
document.body.removeChild(tn);
return _206;
};
}else{
if(dojo.render.svg.capable){
dojo.dom.createNodesFromText=function(txt,wrap){
var _207;
if(window.parseXML){
_207=parseXML(txt,window.document);
}else{
if(window.DOMParser){
_207=(new DOMParser()).parseFromString(s,"text/xml");
}else{
dojo.raise("dojo.dom.createNodesFromText: environment does not support XML parsing");
}
}
_207.normalize();
if(wrap){
var ret=[_207.firstChild.cloneNode(true)];
return ret;
}
var _208=[];
for(var x=0;x<_207.childNodes.length;x++){
_208.push(_207.childNodes.item(x).cloneNode(true));
}
return _208;
};
}
}
dojo.dom.insertBefore=function(node,ref,_210){
if(_210!=true&&(node===ref||node.nextSibling===ref)){
return false;
}
var _211=ref.parentNode;
_211.insertBefore(node,ref);
return true;
};
dojo.dom.insertAfter=function(node,ref,_212){
var pn=ref.parentNode;
if(ref==pn.lastChild){
if(_212!=true&&node===ref){
return false;
}
pn.appendChild(node);
}else{
return this.insertBefore(node,ref.nextSibling,_212);
}
return true;
};
dojo.dom.insertAtPosition=function(node,ref,_214){
switch(_214.toLowerCase()){
case "before":
dojo.dom.insertBefore(node,ref);
break;
case "after":
dojo.dom.insertAfter(node,ref);
break;
case "first":
if(ref.firstChild){
dojo.dom.insertBefore(node,ref.firstChild);
}else{
ref.appendChild(node);
}
break;
default:
ref.appendChild(node);
break;
}
};
dojo.dom.insertAtIndex=function(node,ref,_215){
var pn=ref.parentNode;
var _216=pn.childNodes;
var _217=false;
for(var i=0;i<_216.length;i++){
if((_216.item(i)["getAttribute"])&&(parseInt(_216.item(i).getAttribute("dojoinsertionindex"))>_215)){
dojo.dom.insertBefore(node,_216.item(i));
_217=true;
break;
}
}
if(!_217){
dojo.dom.insertBefore(node,ref);
}
};
dojo.dom.textContent=function(node,text){
if(text){
dojo.dom.replaceChildren(node,document.createTextNode(text));
return text;
}else{
var _219="";
if(node==null){
return _219;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
_219+=dojo.dom.textContent(node.childNodes[i]);
break;
case 3:
case 2:
case 4:
_219+=node.childNodes[i].nodeValue;
break;
default:
break;
}
}
return _219;
}
};
dojo.dom.collectionToArray=function(_220){
var _221=new Array(_220.length);
for(var i=0;i<_220.length;i++){
_221[i]=_220[i];
}
return _221;
};
dojo.provide("dojo.io.BrowserIO");
dojo.require("dojo.io");
dojo.require("dojo.lang");
dojo.require("dojo.dom");
try{
if((!djConfig.preventBackButtonFix)&&(!dojo.hostenv.post_load_)){
document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+(dojo.hostenv.getBaseScriptUri()+"iframe_history.html")+"'></iframe>");
}
}
catch(e){
}
dojo.io.checkChildrenForFile=function(node){
var _222=false;
var _223=node.getElementsByTagName("input");
dojo.lang.forEach(_223,function(_224){
if(_222){
return;
}
if(_224.getAttribute("type")=="file"){
_222=true;
}
});
return _222;
};
dojo.io.formHasFile=function(_225){
return dojo.io.checkChildrenForFile(_225);
};
dojo.io.encodeForm=function(_226,_227){
if((!_226)||(!_226.tagName)||(!_226.tagName.toLowerCase()=="form")){
dj_throw("Attempted to encode a non-form element.");
}
var enc=/utf/i.test(_227||"")?encodeURIComponent:dojo.string.encodeAscii;
var _228=[];
for(var i=0;i<_226.elements.length;i++){
var elm=_226.elements[i];
if(elm.disabled){
continue;
}
var name=enc(elm.name);
var type=elm.type.toLowerCase();
if(type=="select-multiple"){
for(var j=0;j<elm.options.length;j++){
_228.push(name+"="+enc(elm.options[j].value));
}
}else{
if(dojo.lang.inArray(type,["radio","checkbox"])){
if(elm.checked){
_228.push(name+"="+enc(elm.value));
}
}else{
if(!dojo.lang.inArray(type,["file","submit","reset","button"])){
_228.push(name+"="+enc(elm.value));
}
}
}
}
return _228.join("&")+"&";
};
dojo.io.setIFrameSrc=function(_231,src,_232){
try{
var r=dojo.render.html;
if(!_232){
if(r.safari){
_231.location=src;
}else{
frames[_231.name].location=src;
}
}else{
var idoc=(r.moz)?_231.contentWindow:_231;
idoc.location.replace(src);
dojo.debug(_231.contentWindow.location);
}
}
catch(e){
dojo.debug("setIFrameSrc: "+e);
}
};
dojo.io.XMLHTTPTransport=new function(){
var _234=this;
this.initialHref=window.location.href;
this.initialHash=window.location.hash;
this.moveForward=false;
var _235={};
this.useCache=false;
this.historyStack=[];
this.forwardStack=[];
this.historyIframe=null;
this.bookmarkAnchor=null;
this.locationTimer=null;
function getCacheKey(url,_236,_237){
return url+"|"+_236+"|"+_237.toLowerCase();
}
function addToCache(url,_238,_239,http){
_235[getCacheKey(url,_238,_239)]=http;
}
function getFromCache(url,_241,_242){
return _235[getCacheKey(url,_241,_242)];
}
this.clearCache=function(){
_235={};
};
function doLoad(_243,http,url,_244,_245){
if((http.status==200)||(location.protocol=="file:"&&http.status==0)){
var ret;
if(_243.method.toLowerCase()=="head"){
var _246=http.getAllResponseHeaders();
ret={};
ret.toString=function(){
return _246;
};
var _247=_246.split(/[\r\n]+/g);
for(var i=0;i<_247.length;i++){
var pair=_247[i].match(/^([^:]+)\s*:\s*(.+)$/i);
if(pair){
ret[pair[1]]=pair[2];
}
}
}else{
if(_243.mimetype=="text/javascript"){
try{
ret=dj_eval(http.responseText);
}
catch(e){
dojo.debug(e);
ret=false;
}
}else{
if((_243.mimetype=="application/xml")||(_243.mimetype=="text/xml")){
ret=http.responseXML;
if(!ret||typeof ret=="string"){
ret=dojo.dom.createDocumentFromText(http.responseText);
}
}else{
ret=http.responseText;
}
}
}
if(_245){
addToCache(url,_244,_243.method,http);
}
if(typeof _243.load=="function"){
_243.load("load",ret,http);
}
}else{
var _249=new dojo.io.Error("XMLHttpTransport Error: "+http.status+" "+http.statusText);
if(typeof _243.error=="function"){
_243.error("error",_249,http);
}
}
}
function setHeaders(http,_250){
if(_250["headers"]){
for(var _251 in _250["headers"]){
if(_251.toLowerCase()=="content-type"&&!_250["contentType"]){
_250["contentType"]=_250["headers"][_251];
}else{
http.setRequestHeader(_251,_250["headers"][_251]);
}
}
}
}
this.addToHistory=function(args){
var _252=args["back"]||args["backButton"]||args["handle"];
var hash=null;
if(!this.historyIframe){
this.historyIframe=window.frames["djhistory"];
}
if(!this.bookmarkAnchor){
this.bookmarkAnchor=document.createElement("a");
document.body.appendChild(this.bookmarkAnchor);
this.bookmarkAnchor.style.display="none";
}
if((!args["changeUrl"])||(dojo.render.html.ie)){
var url=dojo.hostenv.getBaseScriptUri()+"iframe_history.html?"+(new Date()).getTime();
this.moveForward=true;
dojo.io.setIFrameSrc(this.historyIframe,url,false);
}
if(args["changeUrl"]){
hash="#"+((args["changeUrl"]!==true)?args["changeUrl"]:(new Date()).getTime());
setTimeout("window.location.href = '"+hash+"';",1);
this.bookmarkAnchor.href=hash;
if(dojo.render.html.ie){
var _254=_252;
var lh=null;
var hsl=this.historyStack.length-1;
if(hsl>=0){
while(!this.historyStack[hsl]["urlHash"]){
hsl--;
}
lh=this.historyStack[hsl]["urlHash"];
}
if(lh){
_252=function(){
if(window.location.hash!=""){
setTimeout("window.location.href = '"+lh+"';",1);
}
_254();
};
}
this.forwardStack=[];
var _257=args["forward"]||args["forwardButton"];
var tfw=function(){
if(window.location.hash!=""){
window.location.href=hash;
}
if(_257){
_257();
}
};
if(args["forward"]){
args.forward=tfw;
}else{
if(args["forwardButton"]){
args.forwardButton=tfw;
}
}
}else{
if(dojo.render.html.moz){
if(!this.locationTimer){
this.locationTimer=setInterval("dojo.io.XMLHTTPTransport.checkLocation();",200);
}
}
}
}
this.historyStack.push({"url":url,"callback":_252,"kwArgs":args,"urlHash":hash});
};
this.checkLocation=function(){
var hsl=this.historyStack.length;
if((window.location.hash==this.initialHash)||(window.location.href==this.initialHref)&&(hsl==1)){
this.handleBackButton();
return;
}
if(this.forwardStack.length>0){
if(this.forwardStack[this.forwardStack.length-1].urlHash==window.location.hash){
this.handleForwardButton();
return;
}
}
if((hsl>=2)&&(this.historyStack[hsl-2])){
if(this.historyStack[hsl-2].urlHash==window.location.hash){
this.handleBackButton();
return;
}
}
};
this.iframeLoaded=function(evt,_259){
var isp=_259.href.split("?");
if(isp.length<2){
if(this.historyStack.length==1){
this.handleBackButton();
}
return;
}
var _261=isp[1];
if(this.moveForward){
this.moveForward=false;
return;
}
var last=this.historyStack.pop();
if(!last){
if(this.forwardStack.length>0){
var next=this.forwardStack[this.forwardStack.length-1];
if(_261==next.url.split("?")[1]){
this.handleForwardButton();
}
}
return;
}
this.historyStack.push(last);
if(this.historyStack.length>=2){
if(isp[1]==this.historyStack[this.historyStack.length-2].url.split("?")[1]){
this.handleBackButton();
}
}else{
this.handleBackButton();
}
};
this.handleBackButton=function(){
var last=this.historyStack.pop();
if(!last){
return;
}
if(last["callback"]){
last.callback();
}else{
if(last.kwArgs["backButton"]){
last.kwArgs["backButton"]();
}else{
if(last.kwArgs["back"]){
last.kwArgs["back"]();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("back");
}
}
}
}
this.forwardStack.push(last);
};
this.handleForwardButton=function(){
var last=this.forwardStack.pop();
if(!last){
return;
}
if(last.kwArgs["forward"]){
last.kwArgs.forward();
}else{
if(last.kwArgs["forwardButton"]){
last.kwArgs.forwardButton();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("forward");
}
}
}
this.historyStack.push(last);
};
this.inFlight=[];
this.inFlightTimer=null;
this.startWatchingInFlight=function(){
if(!this.inFlightTimer){
this.inFlightTimer=setInterval("dojo.io.XMLHTTPTransport.watchInFlight();",10);
}
};
this.watchInFlight=function(){
for(var x=this.inFlight.length-1;x>=0;x--){
var tif=this.inFlight[x];
if(!tif){
this.inFlight.splice(x,1);
continue;
}
if(4==tif.http.readyState){
doLoad(tif.req,tif.http,tif.url,tif.query,tif.useCache);
this.inFlight.splice(x,1);
if(this.inFlight.length==0){
clearInterval(this.inFlightTimer);
this.inFlightTimer=null;
}
}
}
};
var _265=dojo.hostenv.getXmlhttpObject()?true:false;
this.canHandle=function(_266){
return _265&&dojo.lang.inArray(_266["mimetype"],["text/plain","text/html","application/xml","text/xml","text/javascript"])&&dojo.lang.inArray(_266["method"].toLowerCase(),["post","get","head"])&&!(_266["formNode"]&&dojo.io.formHasFile(_266["formNode"]));
};
this.bind=function(_267){
if(!_267["url"]){
if(!_267["formNode"]&&(_267["backButton"]||_267["back"]||_267["changeUrl"]||_267["watchForURL"])&&(!djConfig.preventBackButtonFix)){
this.addToHistory(_267);
return true;
}
}
var url=_267.url;
var _268="";
if(_267["formNode"]){
var ta=_267.formNode.getAttribute("action");
if((ta)&&(!_267["url"])){
url=ta;
}
var tp=_267.formNode.getAttribute("method");
if((tp)&&(!_267["method"])){
_267.method=tp;
}
_268+=dojo.io.encodeForm(_267.formNode,_267.encoding);
}
if(!_267["method"]){
_267.method="get";
}
if(_267["content"]){
_268+=dojo.io.argsFromMap(_267.content,_267.encoding);
}
if(_267["postContent"]&&_267.method.toLowerCase()=="post"){
_268=_267.postContent;
}
if(_267["backButton"]||_267["back"]||_267["changeUrl"]){
this.addToHistory(_267);
}
var _271=_267["sync"]?false:true;
var _272=_267["useCache"]==true||(this.useCache==true&&_267["useCache"]!=false);
if(_272){
var _273=getFromCache(url,_268,_267.method);
if(_273){
doLoad(_267,_273,url,_268,false);
return;
}
}
var http=dojo.hostenv.getXmlhttpObject();
var _274=false;
if(_271){
this.inFlight.push({"req":_267,"http":http,"url":url,"query":_268,"useCache":_272});
this.startWatchingInFlight();
}
if(_267.method.toLowerCase()=="post"){
http.open("POST",url,_271);
setHeaders(http,_267);
http.setRequestHeader("Content-Type",_267["contentType"]||"application/x-www-form-urlencoded");
http.send(_268);
}else{
var _275=url;
if(_268!=""){
_275+=(url.indexOf("?")>-1?"&":"?")+_268;
}
http.open(_267.method.toUpperCase(),_275,_271);
setHeaders(http,_267);
http.send(null);
}
if(!_271){
doLoad(_267,http,url,_268,_272);
}
return;
};
dojo.io.transports.addTransport("XMLHTTPTransport");
};
dojo.require("dojo.lang");
dojo.provide("dojo.event");
dojo.event=new function(){
var _276=0;
this.anon={};
this.nameAnonFunc=function(_277,_278){
var nso=(_278||this.anon);
if((dj_global["djConfig"])&&(djConfig["slowAnonFuncLookups"]==true)){
for(var x in nso){
if(nso[x]===_277){
dojo.debug(x);
return x;
}
}
}
var ret="_"+_276++;
while(typeof nso[ret]!="undefined"){
ret="_"+_276++;
}
nso[ret]=_277;
return ret;
};
this.createFunctionPair=function(obj,cb){
var ret=[];
if(typeof obj=="function"){
ret[1]=dojo.event.nameAnonFunc(obj,dj_global);
ret[0]=dj_global;
return ret;
}else{
if((typeof obj=="object")&&(typeof cb=="string")){
return [obj,cb];
}else{
if((typeof obj=="object")&&(typeof cb=="function")){
ret[1]=dojo.event.nameAnonFunc(cb,obj);
ret[0]=obj;
return ret;
}
}
}
return null;
};
this.matchSignature=function(args,_280){
var end=Math.min(args.length,_280.length);
for(var x=0;x<end;x++){
if(compareTypes){
if((typeof args[x]).toLowerCase()!=(typeof _280[x])){
return false;
}
}else{
if((typeof args[x]).toLowerCase()!=_280[x].toLowerCase()){
return false;
}
}
}
return true;
};
this.matchSignatureSets=function(args){
for(var x=1;x<arguments.length;x++){
if(this.matchSignature(args,arguments[x])){
return true;
}
}
return false;
};
function interpolateArgs(args){
var ao={srcObj:dj_global,srcFunc:null,adviceObj:dj_global,adviceFunc:null,aroundObj:null,aroundFunc:null,adviceType:(args.length>2)?args[0]:"after",precedence:"last",once:false,delay:null};
switch(args.length){
case 0:
return;
case 1:
return;
case 2:
ao.srcFunc=args[0];
ao.adviceFunc=args[1];
break;
case 3:
if((typeof args[0]=="object")&&(typeof args[1]=="string")&&(typeof args[2]=="string")){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((typeof args[1]=="string")&&(typeof args[2]=="string")){
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((typeof args[0]=="object")&&(typeof args[1]=="string")&&(typeof args[2]=="function")){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
var _283=dojo.event.nameAnonFunc(args[2],ao.adviceObj);
ao.adviceObj[_283]=args[2];
ao.adviceFunc=_283;
}else{
if((typeof args[0]=="function")&&(typeof args[1]=="object")&&(typeof args[2]=="string")){
ao.adviceType="after";
ao.srcObj=dj_global;
var _283=dojo.event.nameAnonFunc(args[0],ao.srcObj);
ao.srcObj[_283]=args[0];
ao.srcFunc=_283;
ao.adviceObj=args[1];
ao.adviceFunc=args[2];
}
}
}
}
break;
case 4:
if((typeof args[0]=="object")&&(typeof args[2]=="object")){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((typeof args[1]).toLowerCase()=="object"){
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=dj_global;
ao.adviceFunc=args[3];
}else{
if((typeof args[2]).toLowerCase()=="object"){
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
ao.srcObj=ao.adviceObj=ao.aroundObj=dj_global;
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
ao.aroundFunc=args[3];
}
}
}
break;
case 6:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundFunc=args[5];
ao.aroundObj=dj_global;
break;
default:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundObj=args[5];
ao.aroundFunc=args[6];
ao.once=args[7];
ao.delay=args[8];
break;
}
if((typeof ao.srcFunc).toLowerCase()!="string"){
ao.srcFunc=dojo.lang.getNameInObj(ao.srcObj,ao.srcFunc);
}
if((typeof ao.adviceFunc).toLowerCase()!="string"){
ao.adviceFunc=dojo.lang.getNameInObj(ao.adviceObj,ao.adviceFunc);
}
if((ao.aroundObj)&&((typeof ao.aroundFunc).toLowerCase()!="string")){
ao.aroundFunc=dojo.lang.getNameInObj(ao.aroundObj,ao.aroundFunc);
}
if(!ao.srcObj){
dj_throw("bad srcObj for srcFunc: "+ao.srcFunc);
}
if(!ao.adviceObj){
dj_throw("bad adviceObj for adviceFunc: "+ao.adviceFunc);
}
return ao;
}
this.connect=function(){
var ao=interpolateArgs(arguments);
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
if(ao.adviceFunc){
var mjp2=dojo.event.MethodJoinPoint.getForMethod(ao.adviceObj,ao.adviceFunc);
}
mjp.kwAddAdvice(ao);
return mjp;
};
this.connectBefore=function(){
var args=["before"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.connectAround=function(){
var args=["around"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.kwConnectImpl_=function(_286,_287){
var fn=(_287)?"disconnect":"connect";
if(typeof _286["srcFunc"]=="function"){
_286.srcObj=_286["srcObj"]||dj_global;
var _288=dojo.event.nameAnonFunc(_286.srcFunc,_286.srcObj);
_286.srcFunc=_288;
}
if(typeof _286["adviceFunc"]=="function"){
_286.adviceObj=_286["adviceObj"]||dj_global;
var _288=dojo.event.nameAnonFunc(_286.adviceFunc,_286.adviceObj);
_286.adviceFunc=_288;
}
return dojo.event[fn]((_286["type"]||_286["adviceType"]||"after"),_286["srcObj"],_286["srcFunc"],_286["adviceObj"]||_286["targetObj"],_286["adviceFunc"]||_286["targetFunc"],_286["aroundObj"],_286["aroundFunc"],_286["once"],_286["delay"]);
};
this.kwConnect=function(_289){
return this.kwConnectImpl_(_289,false);
};
this.disconnect=function(){
var ao=interpolateArgs(arguments);
if(!ao.adviceFunc){
return;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
return mjp.removeAdvice(ao.adviceObj,ao.adviceFunc,ao.adviceType,ao.once);
};
this.kwDisconnect=function(_290){
return this.kwConnectImpl_(_290,true);
};
};
dojo.event.MethodInvocation=function(_291,obj,args){
this.jp_=_291;
this.object=obj;
this.args=[];
for(var x=0;x<args.length;x++){
this.args[x]=args[x];
}
this.around_index=-1;
};
dojo.event.MethodInvocation.prototype.proceed=function(){
this.around_index++;
if(this.around_index>=this.jp_.around.length){
return this.jp_.object[this.jp_.methodname].apply(this.jp_.object,this.args);
}else{
var ti=this.jp_.around[this.around_index];
var mobj=ti[0]||dj_global;
var meth=ti[1];
return mobj[meth].call(mobj,this);
}
};
dojo.event.MethodJoinPoint=function(obj,_295){
this.object=obj||dj_global;
this.methodname=_295;
this.methodfunc=this.object[_295];
this.before=[];
this.after=[];
this.around=[];
};
dojo.event.MethodJoinPoint.getForMethod=function(obj,_296){
if(!obj){
obj=dj_global;
}
if(!obj[_296]){
obj[_296]=function(){
};
}else{
if((!dojo.lang.isFunction(obj[_296]))&&(!dojo.lang.isAlien(obj[_296]))){
return null;
}
}
var _297=_296+"$joinpoint";
var _298=_296+"$joinpoint$method";
var _299=obj[_297];
if(!_299){
var _300=false;
if(dojo.event["browser"]){
if((obj["attachEvent"])||(obj["nodeType"])||(obj["addEventListener"])){
_300=true;
dojo.event.browser.addClobberNodeAttrs(obj,[_297,_298,_296]);
}
}
obj[_298]=obj[_296];
_299=obj[_297]=new dojo.event.MethodJoinPoint(obj,_298);
obj[_296]=function(){
var args=[];
if((_300)&&(!arguments.length)&&(window.event)){
args.push(dojo.event.browser.fixEvent(window.event));
}else{
for(var x=0;x<arguments.length;x++){
if((x==0)&&(_300)&&(dojo.event.browser.isEvent(arguments[x]))){
args.push(dojo.event.browser.fixEvent(arguments[x]));
}else{
args.push(arguments[x]);
}
}
}
return _299.run.apply(_299,args);
};
}
return _299;
};
dojo.event.MethodJoinPoint.prototype.unintercept=function(){
this.object[this.methodname]=this.methodfunc;
};
dojo.event.MethodJoinPoint.prototype.run=function(){
var obj=this.object||dj_global;
var args=arguments;
var _301=[];
for(var x=0;x<args.length;x++){
_301[x]=args[x];
}
var _302=function(marr){
if(!marr){
dojo.debug("Null argument to unrollAdvice()");
return;
}
var _304=marr[0]||dj_global;
var _305=marr[1];
if(!_304[_305]){
throw new Error("function \""+_305+"\" does not exist on \""+_304+"\"");
}
var _306=marr[2]||dj_global;
var _307=marr[3];
var _308;
var _309=parseInt(marr[4]);
var _310=((!isNaN(_309))&&(marr[4]!==null)&&(typeof marr[4]!="undefined"));
var to={args:[],jp_:this,object:obj,proceed:function(){
return _304[_305].apply(_304,to.args);
}};
to.args=_301;
if(_307){
_306[_307].call(_306,to);
}else{
if((_310)&&((dojo.render.html)||(dojo.render.svg))){
dj_global["setTimeout"](function(){
_304[_305].apply(_304,args);
},_309);
}else{
_304[_305].apply(_304,args);
}
}
};
if(this.before.length>0){
dojo.lang.forEach(this.before,_302,true);
}
var _312;
if(this.around.length>0){
var mi=new dojo.event.MethodInvocation(this,obj,args);
_312=mi.proceed();
}else{
if(this.methodfunc){
_312=this.object[this.methodname].apply(this.object,args);
}
}
if(this.after.length>0){
dojo.lang.forEach(this.after,_302,true);
}
return (this.methodfunc)?_312:null;
};
dojo.event.MethodJoinPoint.prototype.getArr=function(kind){
var arr=this.after;
if((typeof kind=="string")&&(kind.indexOf("before")!=-1)){
arr=this.before;
}else{
if(kind=="around"){
arr=this.around;
}
}
return arr;
};
dojo.event.MethodJoinPoint.prototype.kwAddAdvice=function(args){
this.addAdvice(args["adviceObj"],args["adviceFunc"],args["aroundObj"],args["aroundFunc"],args["adviceType"],args["precedence"],args["once"],args["delay"]);
};
dojo.event.MethodJoinPoint.prototype.addAdvice=function(_315,_316,_317,_318,_319,_320,once,_322){
var arr=this.getArr(_319);
if(!arr){
dj_throw("bad this: "+this);
}
var ao=[_315,_316,_317,_318,_322];
if(once){
if(this.hasAdvice(_315,_316,_319,arr)>=0){
return;
}
}
if(_320=="first"){
arr.unshift(ao);
}else{
arr.push(ao);
}
};
dojo.event.MethodJoinPoint.prototype.hasAdvice=function(_323,_324,_325,arr){
if(!arr){
arr=this.getArr(_325);
}
var ind=-1;
for(var x=0;x<arr.length;x++){
if((arr[x][0]==_323)&&(arr[x][1]==_324)){
ind=x;
}
}
return ind;
};
dojo.event.MethodJoinPoint.prototype.removeAdvice=function(_327,_328,_329,once){
var arr=this.getArr(_329);
var ind=this.hasAdvice(_327,_328,_329,arr);
if(ind==-1){
return false;
}
while(ind!=-1){
arr.splice(ind,1);
if(once){
break;
}
ind=this.hasAdvice(_327,_328,_329,arr);
}
return true;
};
dojo.provide("dojo.event.topic");
dojo.require("dojo.event");
dojo.event.topic=new function(){
this.topics={};
this.getTopic=function(_330){
if(!this.topics[_330]){
this.topics[_330]=new this.TopicImpl(_330);
}
return this.topics[_330];
};
this.registerPublisher=function(_331,obj,_332){
var _331=this.getTopic(_331);
_331.registerPublisher(obj,_332);
};
this.subscribe=function(_333,obj,_334){
var _333=this.getTopic(_333);
_333.subscribe(obj,_334);
};
this.unsubscribe=function(_335,obj,_336){
var _335=this.getTopic(_335);
_335.unsubscribe(obj,_336);
};
this.publish=function(_337,_338){
var _337=this.getTopic(_337);
var args=[];
if((arguments.length==2)&&(_338.length)&&(typeof _338!="string")){
args=_338;
}else{
var args=[];
for(var x=1;x<arguments.length;x++){
args.push(arguments[x]);
}
}
_337.sendMessage.apply(_337,args);
};
};
dojo.event.topic.TopicImpl=function(_339){
this.topicName=_339;
var self=this;
self.subscribe=function(_341,_342){
dojo.event.connect("before",self,"sendMessage",_341,_342);
};
self.unsubscribe=function(_343,_344){
dojo.event.disconnect("before",self,"sendMessage",_343,_344);
};
self.registerPublisher=function(_345,_346){
dojo.event.connect(_345,_346,self,"sendMessage");
};
self.sendMessage=function(_347){
};
};
dojo.provide("dojo.event.browser");
dojo.require("dojo.event");
dojo_ie_clobber=new function(){
this.clobberArr=["data","onload","onmousedown","onmouseup","onmouseover","onmouseout","onmousemove","onclick","ondblclick","onfocus","onblur","onkeypress","onkeydown","onkeyup","onsubmit","onreset","onselect","onchange","onselectstart","ondragstart","oncontextmenu"];
this.exclusions=[];
this.clobberList={};
this.clobberNodes=[];
this.addClobberAttr=function(type){
if(dojo.render.html.ie){
if(this.clobberList[type]!="set"){
this.clobberArr.push(type);
this.clobberList[type]="set";
}
}
};
this.addExclusionID=function(id){
this.exclusions.push(id);
};
if(dojo.render.html.ie){
for(var x=0;x<this.clobberArr.length;x++){
this.clobberList[this.clobberArr[x]]="set";
}
}
function nukeProp(node,prop){
try{
node[prop]=null;
}
catch(e){
}
try{
delete node[prop];
}
catch(e){
}
}
this.clobber=function(_349){
for(var x=0;x<this.exclusions.length;x++){
try{
var tn=document.getElementById(this.exclusions[x]);
tn.parentNode.removeChild(tn);
}
catch(e){
}
}
var na;
var tna;
if(_349){
tna=_349.getElementsByTagName("*");
na=[_349];
for(var x=0;x<tna.length;x++){
if(!djConfig.ieClobberMinimal){
na.push(tna[x]);
}else{
if(tna[x]["__doClobber__"]){
na.push(tna[x]);
}
}
}
}else{
try{
window.onload=null;
}
catch(e){
}
na=(this.clobberNodes.length)?this.clobberNodes:document.all;
}
tna=null;
var _352={};
for(var i=na.length-1;i>=0;i=i-1){
var el=na[i];
if(djConfig.ieClobberMinimal){
if(el["__clobberAttrs__"]){
for(var j=0;j<el.__clobberAttrs__.length;j++){
nukeProp(el,el.__clobberAttrs__[j]);
}
nukeProp(el,"__clobberAttrs__");
nukeProp(el,"__doClobber__");
}
}else{
for(var p=this.clobberArr.length-1;p>=0;p=p-1){
var ta=this.clobberArr[p];
nukeProp(el,ta);
}
}
}
na=null;
};
};
if((dojo.render.html.ie)&&((!dojo.hostenv.ie_prevent_clobber_)||(djConfig.ieClobberMinimal))){
window.onunload=function(){
dojo_ie_clobber.clobber();
try{
if((dojo["widget"])&&(dojo.widget["manager"])){
dojo.widget.manager.destroyAll();
}
}
catch(e){
}
try{
window.onload=null;
}
catch(e){
}
try{
window.onunload=null;
}
catch(e){
}
dojo_ie_clobber.clobberNodes=[];
};
}
dojo.event.browser=new function(){
var _355=0;
this.clean=function(node){
if(dojo.render.html.ie){
dojo_ie_clobber.clobber(node);
}
};
this.addClobberAttr=function(type){
dojo_ie_clobber.addClobberAttr(type);
};
this.addClobberAttrs=function(){
for(var x=0;x<arguments.length;x++){
this.addClobberAttr(arguments[x]);
}
};
this.addClobberNode=function(node){
if(djConfig.ieClobberMinimal){
if(!node.__doClobber__){
node.__doClobber__=true;
dojo_ie_clobber.clobberNodes.push(node);
node.__clobberAttrs__=[];
}
}
};
this.addClobberNodeAttrs=function(node,_356){
this.addClobberNode(node);
if(djConfig.ieClobberMinimal){
for(var x=0;x<_356.length;x++){
node.__clobberAttrs__.push(_356[x]);
}
}else{
this.addClobberAttrs.apply(this,_356);
}
};
this.removeListener=function(node,_357,fp,_358){
if(!_358){
var _358=false;
}
_357=_357.toLowerCase();
if(_357.substr(0,2)=="on"){
_357=_357.substr(2);
}
if(node.removeEventListener){
node.removeEventListener(_357,fp,_358);
}
};
this.addListener=function(node,_359,fp,_360){
if(!_360){
var _360=false;
}
_359=_359.toLowerCase();
if(_359.substr(0,2)=="on"){
_359=_359.substr(2);
}
if(!node){
return;
}
var _361=function(evt){
if(!evt){
evt=window.event;
}
var ret=fp(dojo.event.browser.fixEvent(evt));
if(_360){
dojo.event.browser.stopEvent(evt);
}
return ret;
};
var _362="on"+_359;
if(node.addEventListener){
node.addEventListener(_359,_361,_360);
return _361;
}else{
if(typeof node[_362]=="function"){
var _363=node[_362];
node[_362]=function(e){
_363(e);
_361(e);
};
}else{
node[_362]=_361;
}
if(dojo.render.html.ie){
this.addClobberNodeAttrs(node,[_362]);
}
return _361;
}
};
this.isEvent=function(obj){
return (typeof Event!="undefined")&&(obj.eventPhase);
};
this.fixEvent=function(evt){
if(evt.type&&evt.type.indexOf("key")==0){
var keys={KEY_BACKSPACE:8,KEY_TAB:9,KEY_ENTER:13,KEY_SHIFT:16,KEY_CTRL:17,KEY_ALT:18,KEY_PAUSE:19,KEY_CAPS_LOCK:20,KEY_ESCAPE:27,KEY_SPACE:32,KEY_PAGE_UP:33,KEY_PAGE_DOWN:34,KEY_END:35,KEY_HOME:36,KEY_LEFT_ARROW:37,KEY_UP_ARROW:38,KEY_RIGHT_ARROW:39,KEY_DOWN_ARROW:40,KEY_INSERT:45,KEY_DELETE:46,KEY_LEFT_WINDOW:91,KEY_RIGHT_WINDOW:92,KEY_SELECT:93,KEY_F1:112,KEY_F2:113,KEY_F3:114,KEY_F4:115,KEY_F5:116,KEY_F6:117,KEY_F7:118,KEY_F8:119,KEY_F9:120,KEY_F10:121,KEY_F11:122,KEY_F12:123,KEY_NUM_LOCK:144,KEY_SCROLL_LOCK:145};
evt.keys=[];
for(var key in keys){
evt[key]=keys[key];
evt.keys[keys[key]]=key;
}
if(dojo.render.html.ie&&evt.type=="keypress"){
evt.charCode=evt.keyCode;
}
}
if(dojo.render.html.ie){
if(!evt.target){
evt.target=evt.srcElement;
}
if(!evt.currentTarget){
evt.currentTarget=evt.srcElement;
}
if(!evt.layerX){
evt.layerX=evt.offsetX;
}
if(!evt.layerY){
evt.layerY=evt.offsetY;
}
if(evt.fromElement){
evt.relatedTarget=evt.fromElement;
}
if(evt.toElement){
evt.relatedTarget=evt.toElement;
}
evt.callListener=function(_366,_367){
if(typeof _366!="function"){
dj_throw("listener not a function: "+_366);
}
evt.currentTarget=_367;
var ret=_366.call(_367,evt);
return ret;
};
evt.stopPropagation=function(){
evt.cancelBubble=true;
};
evt.preventDefault=function(){
evt.returnValue=false;
};
}
return evt;
};
this.stopEvent=function(ev){
if(window.event){
ev.returnValue=false;
ev.cancelBubble=true;
}else{
ev.preventDefault();
ev.stopPropagation();
}
};
};
dojo.hostenv.conditionalLoadModule({common:["dojo.event","dojo.event.topic"],browser:["dojo.event.browser"]});
dojo.hostenv.moduleLoaded("dojo.event.*");
dojo.provide("dojo.alg.Alg");
dojo.require("dojo.lang");
dj_deprecated("dojo.alg.Alg is deprecated, use dojo.lang instead");
dojo.alg.find=function(arr,val){
return dojo.lang.find(arr,val);
};
dojo.alg.inArray=function(arr,val){
return dojo.lang.inArray(arr,val);
};
dojo.alg.inArr=dojo.alg.inArray;
dojo.alg.getNameInObj=function(ns,item){
return dojo.lang.getNameInObj(ns,item);
};
dojo.alg.has=function(obj,name){
return dojo.lang.has(obj,name);
};
dojo.alg.forEach=function(arr,_369,_370){
return dojo.lang.forEach(arr,_369,_370);
};
dojo.alg.for_each=dojo.alg.forEach;
dojo.alg.map=function(arr,obj,_371){
return dojo.lang.map(arr,obj,_371);
};
dojo.alg.tryThese=function(){
return dojo.lang.tryThese.apply(dojo.lang,arguments);
};
dojo.alg.delayThese=function(farr,cb,_372,_373){
return dojo.lang.delayThese.apply(dojo.lang,arguments);
};
dojo.alg.for_each_call=dojo.alg.map;
dojo.require("dojo.alg.Alg",false,true);
dojo.hostenv.moduleLoaded("dojo.alg.*");
dojo.provide("dojo.uri.Uri");
dojo.uri=new function(){
this.joinPath=function(){
var arr=[];
for(var i=0;i<arguments.length;i++){
arr.push(arguments[i]);
}
return arr.join("/").replace(/\/{2,}/g,"/").replace(/((https*|ftps*):)/i,"$1/");
};
this.dojoUri=function(uri){
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri(),uri);
};
this.Uri=function(){
var uri=arguments[0];
for(var i=1;i<arguments.length;i++){
if(!arguments[i]){
continue;
}
var _374=new dojo.uri.Uri(arguments[i].toString());
var _375=new dojo.uri.Uri(uri.toString());
if(_374.path==""&&_374.scheme==null&&_374.authority==null&&_374.query==null){
if(_374.fragment!=null){
_375.fragment=_374.fragment;
}
_374=_375;
}else{
if(_374.scheme==null){
_374.scheme=_375.scheme;
if(_374.authority==null){
_374.authority=_375.authority;
if(_374.path.charAt(0)!="/"){
var path=_375.path.substring(0,_375.path.lastIndexOf("/")+1)+_374.path;
var segs=path.split("/");
for(var j=0;j<segs.length;j++){
if(segs[j]=="."){
if(j==segs.length-1){
segs[j]="";
}else{
segs.splice(j,1);
j--;
}
}else{
if(j>0&&!(j==1&&segs[0]=="")&&segs[j]==".."&&segs[j-1]!=".."){
if(j==segs.length-1){
segs.splice(j,1);
segs[j-1]="";
}else{
segs.splice(j-1,2);
j-=2;
}
}
}
}
_374.path=segs.join("/");
}
}
}
}
uri="";
if(_374.scheme!=null){
uri+=_374.scheme+":";
}
if(_374.authority!=null){
uri+="//"+_374.authority;
}
uri+=_374.path;
if(_374.query!=null){
uri+="?"+_374.query;
}
if(_374.fragment!=null){
uri+="#"+_374.fragment;
}
}
this.uri=uri.toString();
var _378="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$";
var r=this.uri.match(new RegExp(_378));
this.scheme=r[2]||(r[1]?"":null);
this.authority=r[4]||(r[3]?"":null);
this.path=r[5];
this.query=r[7]||(r[6]?"":null);
this.fragment=r[9]||(r[8]?"":null);
if(this.authority!=null){
_378="^((([^:]+:)?([^@]+))@)?([^:]*)(:([0-9]+))?$";
r=this.authority.match(new RegExp(_378));
this.user=r[3]||null;
this.password=r[4]||null;
this.host=r[5];
this.port=r[7]||null;
}
this.toString=function(){
return this.uri;
};
};
};
dojo.provide("dojo.style");
dojo.require("dojo.dom");
dojo.require("dojo.uri.Uri");
dojo.require("dojo.graphics.color");
dojo.style.boxSizing={marginBox:"margin-box",borderBox:"border-box",paddingBox:"padding-box",contentBox:"content-box"};
dojo.style.getBoxSizing=function(node){
var cm=document["compatMode"];
if(cm=="BackCompat"||cm=="QuirksMode"){
return dojo.style.boxSizing.borderBox;
}else{
if(dojo.render.html.ie){
return dojo.style.boxSizing.contentBox;
}else{
if(arguments.length==0){
node=document.documentElement;
}
var _380=dojo.style.getStyle(node,"-moz-box-sizing");
if(!_380){
_380=dojo.style.getStyle(node,"box-sizing");
}
return (_380?_380:dojo.style.boxSizing.contentBox);
}
}
};
dojo.style.isBorderBox=function(node){
return (dojo.style.getBoxSizing(node)==dojo.style.boxSizing.borderBox);
};
dojo.style.getNumericStyle=function(_381,_382){
var s=dojo.style.getComputedStyle(_381,_382);
if(s==""){
return 0;
}
if(dojo.lang.isUndefined(s)){
return NaN;
}
var _383=s.match(/([\d.]+)([a-z]*)/);
if(!_383||!_383[1]){
return NaN;
}
var n=Number(_383[1]);
return (n==0||_383[2]=="px"?n:NaN);
};
dojo.style.getMarginWidth=function(node){
var left=dojo.style.getNumericStyle(node,"margin-left");
var _386=dojo.style.getNumericStyle(node,"margin-right");
return left+_386;
};
dojo.style.getBorderWidth=function(node){
if(node.clientWidth){
return node.offsetWidth-node.clientWidth;
}else{
var left=(dojo.style.getStyle(node,"border-left-style")=="none"?0:dojo.style.getNumericStyle(node,"border-left-width"));
var _387=(dojo.style.getStyle(node,"border-right-style")=="none"?0:dojo.style.getNumericStyle(node,"border-right-width"));
return left+_387;
}
};
dojo.style.getPaddingWidth=function(node){
var left=(dojo.style.getStyle(node,"padding-left")=="auto"?0:dojo.style.getNumericStyle(node,"padding-left"));
var _388=(dojo.style.getStyle(node,"padding-right")=="auto"?0:dojo.style.getNumericStyle(node,"padding-right"));
return left+_388;
};
dojo.style.getContentWidth=function(node){
return node.offsetWidth-dojo.style.getPaddingWidth(node)-dojo.style.getBorderWidth(node);
};
dojo.style.getInnerWidth=function(node){
return node.offsetWidth;
};
dojo.style.getOuterWidth=function(node){
return dojo.style.getInnerWidth(node)+dojo.style.getMarginWidth(node);
};
dojo.style.setOuterWidth=function(node,_389){
if(!dojo.style.isBorderBox(node)){
_389-=dojo.style.getPaddingWidth(node)+dojo.style.getBorderWidth(node);
}
_389-=dojo.style.getMarginWidth(node);
if(!isNaN(_389)&&_389>0){
node.style.width=_389+"px";
return true;
}else{
return false;
}
};
dojo.style.getContentBoxWidth=dojo.style.getContentWidth;
dojo.style.getBorderBoxWidth=dojo.style.getInnerWidth;
dojo.style.getMarginBoxWidth=dojo.style.getOuterWidth;
dojo.style.setMarginBoxWidth=dojo.style.setOuterWidth;
dojo.style.getMarginHeight=function(node){
var top=dojo.style.getNumericStyle(node,"margin-top");
var _391=dojo.style.getNumericStyle(node,"margin-bottom");
return top+_391;
};
dojo.style.getBorderHeight=function(node){
if(node.clientHeight){
return node.offsetHeight-node.clientHeight;
}else{
var top=(dojo.style.getStyle(node,"border-top-style")=="none"?0:dojo.style.getNumericStyle(node,"border-top-width"));
var _392=(dojo.style.getStyle(node,"border-bottom-style")=="none"?0:dojo.style.getNumericStyle(node,"border-bottom-width"));
return top+_392;
}
};
dojo.style.getPaddingHeight=function(node){
var top=(dojo.style.getStyle(node,"padding-top")=="auto"?0:dojo.style.getNumericStyle(node,"padding-top"));
var _393=(dojo.style.getStyle(node,"padding-bottom")=="auto"?0:dojo.style.getNumericStyle(node,"padding-bottom"));
return top+_393;
};
dojo.style.getContentHeight=function(node){
return node.offsetHeight-dojo.style.getPaddingHeight(node)-dojo.style.getBorderHeight(node);
};
dojo.style.getInnerHeight=function(node){
return node.offsetHeight;
};
dojo.style.getOuterHeight=function(node){
return dojo.style.getInnerHeight(node)+dojo.style.getMarginHeight(node);
};
dojo.style.setOuterHeight=function(node,_394){
if(!dojo.style.isBorderBox(node)){
_394-=dojo.style.getPaddingHeight(node)+dojo.style.getBorderHeight(node);
}
_394-=dojo.style.getMarginHeight(node);
if(!isNaN(_394)&&_394>0){
node.style.height=_394+"px";
return true;
}else{
return false;
}
};
dojo.style.getContentBoxHeight=dojo.style.getContentHeight;
dojo.style.getBorderBoxHeight=dojo.style.getInnerHeight;
dojo.style.getMarginBoxHeight=dojo.style.getOuterHeight;
dojo.style.setMarginBoxHeight=dojo.style.setOuterHeight;
dojo.style.getTotalOffset=function(node,type,_395){
var _396=(type=="top")?"offsetTop":"offsetLeft";
var _397=(type=="top")?"scrollTop":"scrollLeft";
var alt=(type=="top")?"y":"x";
var ret=0;
if(node["offsetParent"]){
if(_395){
ret-=dojo.style.sumAncestorProperties(node,_397);
}
do{
ret+=node[_396];
node=node.offsetParent;
}while(node!=document.body.parentNode&&node!=null);
}else{
if(node[alt]){
ret+=node[alt];
}
}
return ret;
};
dojo.style.sumAncestorProperties=function(node,prop){
if(!node){
return 0;
}
var _399=0;
while(node){
var val=node[prop];
if(val){
_399+=val-0;
}
node=node.parentNode;
}
return _399;
};
dojo.style.totalOffsetLeft=function(node,_400){
return dojo.style.getTotalOffset(node,"left",_400);
};
dojo.style.getAbsoluteX=dojo.style.totalOffsetLeft;
dojo.style.totalOffsetTop=function(node,_401){
return dojo.style.getTotalOffset(node,"top",_401);
};
dojo.style.getAbsoluteY=dojo.style.totalOffsetTop;
dojo.style.styleSheet=null;
dojo.style.insertCssRule=function(_402,_403,_404){
if(!dojo.style.styleSheet){
if(document.createStyleSheet){
dojo.style.styleSheet=document.createStyleSheet();
}else{
if(document.styleSheets[0]){
dojo.style.styleSheet=document.styleSheets[0];
}else{
return null;
}
}
}
if(arguments.length<3){
if(dojo.style.styleSheet.cssRules){
_404=dojo.style.styleSheet.cssRules.length;
}else{
if(dojo.style.styleSheet.rules){
_404=dojo.style.styleSheet.rules.length;
}else{
return null;
}
}
}
if(dojo.style.styleSheet.insertRule){
var rule=_402+" { "+_403+" }";
return dojo.style.styleSheet.insertRule(rule,_404);
}else{
if(dojo.style.styleSheet.addRule){
return dojo.style.styleSheet.addRule(_402,_403,_404);
}else{
return null;
}
}
};
dojo.style.removeCssRule=function(_406){
if(!dojo.style.styleSheet){
dojo.debug("no stylesheet defined for removing rules");
return false;
}
if(dojo.render.html.ie){
if(!_406){
_406=dojo.style.styleSheet.rules.length;
dojo.style.styleSheet.removeRule(_406);
}
}else{
if(document.styleSheets[0]){
if(!_406){
_406=dojo.style.styleSheet.cssRules.length;
}
dojo.style.styleSheet.deleteRule(_406);
}
}
return true;
};
dojo.style.insertCssFile=function(URI,doc,_409){
if(!URI){
return;
}
if(!doc){
doc=document;
}
if(doc.baseURI){
URI=new dojo.uri.Uri(doc.baseURI,URI);
}
if(_409&&doc.styleSheets){
var loc=location.href.split("#")[0].substring(0,location.href.indexOf(location.pathname));
for(var i=0;i<doc.styleSheets.length;i++){
if(doc.styleSheets[i].href&&URI.toString()==new dojo.uri.Uri(doc.styleSheets[i].href.toString())){
return;
}
}
}
var file=doc.createElement("link");
file.setAttribute("type","text/css");
file.setAttribute("rel","stylesheet");
file.setAttribute("href",URI);
var head=doc.getElementsByTagName("head")[0];
if(head){
head.appendChild(file);
}
};
dojo.style.getBackgroundColor=function(node){
var _413;
do{
_413=dojo.style.getStyle(node,"background-color");
dojo.debug("node:",node,node.tagName,node.id);
dojo.debug("color:",_413);
if(_413.toLowerCase()=="rgba(0, 0, 0, 0)"){
_413="transparent";
}
if(node==document.body){
node=null;
break;
}
node=node.parentNode;
}while(node&&dojo.lang.inArray(_413,["transparent",""]));
if(_413=="transparent"){
_413=[255,255,255,0];
}else{
_413=dojo.graphics.color.extractRGB(_413);
}
return _413;
};
dojo.style.getComputedStyle=function(_414,_415,_416){
var _417=_416;
if(_414.style.getPropertyValue){
_417=_414.style.getPropertyValue(_415);
}
if(!_417){
if(document.defaultView){
_417=document.defaultView.getComputedStyle(_414,"").getPropertyValue(_415);
}else{
if(_414.currentStyle){
_417=_414.currentStyle[dojo.style.toCamelCase(_415)];
}
}
}
return _417;
};
dojo.style.getStyle=function(_418,_419){
var _420=dojo.style.toCamelCase(_419);
var _421=_418.style[_420];
return (_421?_421:dojo.style.getComputedStyle(_418,_419,_421));
};
dojo.style.toCamelCase=function(_422){
var arr=_422.split("-"),cc=arr[0];
for(var i=1;i<arr.length;i++){
cc+=arr[i].charAt(0).toUpperCase()+arr[i].substring(1);
}
return cc;
};
dojo.style.toSelectorCase=function(_423){
return _423.replace(/([A-Z])/g,"-$1").toLowerCase();
};
dojo.style.setOpacity=function setOpacity(node,_424,_425){
var h=dojo.render.html;
if(!_425){
if(_424>=1){
if(h.ie){
dojo.style.clearOpacity(node);
return;
}else{
_424=0.999999;
}
}else{
if(_424<0){
_424=0;
}
}
}
if(h.ie){
if(node.nodeName.toLowerCase()=="tr"){
var tds=node.getElementsByTagName("td");
for(var x=0;x<tds.length;x++){
tds[x].style.filter="Alpha(Opacity="+_424*100+")";
}
}
node.style.filter="Alpha(Opacity="+_424*100+")";
}else{
if(h.moz){
node.style.opacity=_424;
node.style.MozOpacity=_424;
}else{
if(h.safari){
node.style.opacity=_424;
node.style.KhtmlOpacity=_424;
}else{
node.style.opacity=_424;
}
}
}
};
dojo.style.getOpacity=function getOpacity(node){
if(dojo.render.html.ie){
var opac=(node.filters&&node.filters.alpha&&typeof node.filters.alpha.opacity=="number"?node.filters.alpha.opacity:100)/100;
}else{
var opac=node.style.opacity||node.style.MozOpacity||node.style.KhtmlOpacity||1;
}
return opac>=0.999999?1:Number(opac);
};
dojo.style.clearOpacity=function clearOpacity(node){
var h=dojo.render.html;
if(h.ie){
if(node.filters&&node.filters.alpha){
node.style.filter="";
}
}else{
if(h.moz){
node.style.opacity=1;
node.style.MozOpacity=1;
}else{
if(h.safari){
node.style.opacity=1;
node.style.KhtmlOpacity=1;
}else{
node.style.opacity=1;
}
}
}
};
dojo.provide("dojo.html");
dojo.require("dojo.dom");
dojo.require("dojo.style");
dojo.require("dojo.string");
dojo.require("dojo.event.*");
dojo.require("dojo.uri.Uri");
dojo.require("dojo.graphics.color");
dojo.lang.mixin(dojo.html,dojo.dom);
dojo.lang.mixin(dojo.html,dojo.style);
dojo.html.clearSelection=function(){
try{
if(window.getSelection){
window.getSelection().removeAllRanges();
}else{
if(document.selection&&document.selection.clear){
document.selection.clear();
}
}
}
catch(e){
dojo.debug(e);
}
};
dojo.html.disableSelection=function(_429){
if(arguments.length==0){
_429=document.body;
}
if(dojo.render.html.mozilla){
_429.style.MozUserSelect="none";
}else{
if(dojo.render.html.safari){
_429.style.KhtmlUserSelect="none";
}else{
if(dojo.render.html.ie){
_429.unselectable="on";
}
}
}
};
dojo.html.enableSelection=function(_430){
if(arguments.length==0){
_430=document.body;
}
if(dojo.render.html.mozilla){
_430.style.MozUserSelect="";
}else{
if(dojo.render.html.safari){
_430.style.KhtmlUserSelect="";
}else{
if(dojo.render.html.ie){
_430.unselectable="off";
}
}
}
};
dojo.html.selectElement=function(_431){
if(document.selection&&document.body.createTextRange){
var _432=document.body.createTextRange();
_432.moveToElementText(_431);
_432.select();
}else{
if(window.getSelection){
var _433=window.getSelection();
if(_433.selectAllChildren){
_433.selectAllChildren(_431);
}
}
}
};
dojo.html.isSelectionCollapsed=function(){
if(document.selection){
return document.selection.createRange().text=="";
}else{
if(window.getSelection){
var _434=window.getSelection();
if(dojo.lang.isString(_434)){
return _434=="";
}else{
return _434.isCollapsed;
}
}
}
};
dojo.html.getEventTarget=function(evt){
if((window["event"])&&(window.event["srcElement"])){
return window.event.srcElement;
}else{
if((evt)&&(evt.target)){
return evt.target;
}
}
};
dojo.html.getScrollTop=function(){
return document.documentElement.scrollTop||document.body.scrollTop||0;
};
dojo.html.getScrollLeft=function(){
return document.documentElement.scrollLeft||document.body.scrollLeft||0;
};
dojo.html.getParentOfType=function(node,type){
var _435=node;
type=type.toLowerCase();
while(_435.nodeName.toLowerCase()!=type){
if((!_435)||(_435==(document["body"]||document["documentElement"]))){
return null;
}
_435=_435.parentNode;
}
return _435;
};
dojo.html.getAttribute=function(node,attr){
if((!node)||(!node.getAttribute)){
return null;
}
var ta=typeof attr=="string"?attr:new String(attr);
var v=node.getAttribute(ta.toUpperCase());
if((v)&&(typeof v=="string")&&(v!="")){
return v;
}
if(v&&typeof v=="object"&&v.value){
return v.value;
}
if((node.getAttributeNode)&&(node.getAttributeNode(ta))){
return (node.getAttributeNode(ta)).value;
}else{
if(node.getAttribute(ta)){
return node.getAttribute(ta);
}else{
if(node.getAttribute(ta.toLowerCase())){
return node.getAttribute(ta.toLowerCase());
}
}
}
return null;
};
dojo.html.hasAttribute=function(node,attr){
var v=this.getAttribute(node,attr);
return v?true:false;
};
dojo.html.getClass=function(node){
if(node.className){
return node.className;
}else{
if(dojo.html.hasAttribute(node,"class")){
return dojo.html.getAttribute(node,"class");
}
}
return "";
};
dojo.html.hasClass=function(node,_438){
var _439=dojo.html.getClass(node).split(/\s+/g);
for(var x=0;x<_439.length;x++){
if(_438==_439[x]){
return true;
}
}
return false;
};
dojo.html.prependClass=function(node,_440){
if(!node){
return null;
}
if(dojo.html.hasAttribute(node,"class")||node.className){
_440+=" "+(node.className||dojo.html.getAttribute(node,"class"));
}
return dojo.html.setClass(node,_440);
};
dojo.html.addClass=function(node,_441){
if(!node){
throw new Error("addClass: node does not exist");
}
if(dojo.html.hasAttribute(node,"class")||node.className){
_441=(node.className||dojo.html.getAttribute(node,"class"))+" "+_441;
}
return dojo.html.setClass(node,_441);
};
dojo.html.setClass=function(node,_442){
if(!node){
return false;
}
var cs=new String(_442);
try{
if(typeof node.className=="string"){
node.className=cs;
}else{
if(node.setAttribute){
node.setAttribute("class",_442);
node.className=cs;
}else{
return false;
}
}
}
catch(e){
dojo.debug("__util__.setClass() failed",e);
}
return true;
};
dojo.html.removeClass=function(node,_444){
if(!node){
return false;
}
var _444=dojo.string.trim(new String(_444));
try{
var cs=String(node.className).split(" ");
var nca=[];
for(var i=0;i<cs.length;i++){
if(cs[i]!=_444){
nca.push(cs[i]);
}
}
node.className=nca.join(" ");
}
catch(e){
dojo.debug("__util__.removeClass() failed",e);
}
return true;
};
dojo.html.classMatchType={ContainsAll:0,ContainsAny:1,IsOnly:2};
dojo.html.getElementsByClass=function(_446,_447,_448,_449){
if(!_447){
_447=document;
}
var _450=_446.split(/\s+/g);
var _451=[];
if(_449!=1&&_449!=2){
_449=0;
}
if(false&&document.evaluate){
var _452="//"+(_448||"*")+"[contains(";
if(_449!=dojo.html.classMatchType.ContainsAny){
_452+="concat(' ',@class,' '), ' "+_450.join(" ') and contains(concat(' ',@class,' '), ' ")+" ')]";
}else{
_452+="concat(' ',@class,' '), ' "+_450.join(" ')) or contains(concat(' ',@class,' '), ' ")+" ')]";
}
var _453=document.evaluate(_452,_447,null,XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE,null);
outer:
for(var node=null,i=0;node=_453.snapshotItem(i);i++){
if(_449!=dojo.html.classMatchType.IsOnly){
_451.push(node);
}else{
if(!dojo.html.getClass(node)){
continue outer;
}
var _454=dojo.html.getClass(node).split(/\s+/g);
var _455=new RegExp("(\\s|^)("+_450.join(")|(")+")(\\s|$)");
for(var j=0;j<_454.length;j++){
if(!_454[j].match(_455)){
continue outer;
}
}
_451.push(node);
}
}
}else{
if(!_448){
_448="*";
}
var _456=_447.getElementsByTagName(_448);
outer:
for(var i=0;i<_456.length;i++){
var node=_456[i];
if(!dojo.html.getClass(node)){
continue outer;
}
var _454=dojo.html.getClass(node).split(/\s+/g);
var _455=new RegExp("(\\s|^)(("+_450.join(")|(")+"))(\\s|$)");
var _457=0;
for(var j=0;j<_454.length;j++){
if(_455.test(_454[j])){
if(_449==dojo.html.classMatchType.ContainsAny){
_451.push(node);
continue outer;
}else{
_457++;
}
}else{
if(_449==dojo.html.classMatchType.IsOnly){
continue outer;
}
}
}
if(_457==_450.length){
if(_449==dojo.html.classMatchType.IsOnly&&_457==_454.length){
_451.push(node);
}else{
if(_449==dojo.html.classMatchType.ContainsAll){
_451.push(node);
}
}
}
}
}
return _451;
};
dojo.html.gravity=function(node,e){
var _458=e.pageX||e.clientX+document.body.scrollLeft;
var _459=e.pageY||e.clientY+document.body.scrollTop;
with(dojo.html){
var _460=getAbsoluteX(node)+(getInnerWidth(node)/2);
var _461=getAbsoluteY(node)+(getInnerHeight(node)/2);
}
with(dojo.html.gravity){
return ((_458<_460?WEST:EAST)|(_459<_461?NORTH:SOUTH));
}
};
dojo.html.gravity.NORTH=1;
dojo.html.gravity.SOUTH=1<<1;
dojo.html.gravity.EAST=1<<2;
dojo.html.gravity.WEST=1<<3;
dojo.html.overElement=function(_462,e){
var _463=e.pageX||e.clientX+document.body.scrollLeft;
var _464=e.pageY||e.clientY+document.body.scrollTop;
with(dojo.html){
var top=getAbsoluteY(_462);
var _465=top+getInnerHeight(_462);
var left=getAbsoluteX(_462);
var _466=left+getInnerWidth(_462);
}
return (_463>=left&&_463<=_466&&_464>=top&&_464<=_465);
};
dojo.html.renderedTextContent=function(node){
var _467="";
if(node==null){
return _467;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
switch(dojo.style.getStyle(node.childNodes[i],"display")){
case "block":
case "list-item":
case "run-in":
case "table":
case "table-row-group":
case "table-header-group":
case "table-footer-group":
case "table-row":
case "table-column-group":
case "table-column":
case "table-cell":
case "table-caption":
_467+="\n";
_467+=dojo.html.renderedTextContent(node.childNodes[i]);
_467+="\n";
break;
case "none":
break;
default:
_467+=dojo.html.renderedTextContent(node.childNodes[i]);
break;
}
break;
case 3:
case 2:
case 4:
var text=node.childNodes[i].nodeValue;
switch(dojo.style.getStyle(node,"text-transform")){
case "capitalize":
text=dojo.string.capitalize(text);
break;
case "uppercase":
text=text.toUpperCase();
break;
case "lowercase":
text=text.toLowerCase();
break;
default:
break;
}
switch(dojo.style.getStyle(node,"text-transform")){
case "nowrap":
break;
case "pre-wrap":
break;
case "pre-line":
break;
case "pre":
break;
default:
text=text.replace(/\s+/," ");
if(/\s$/.test(_467)){
text.replace(/^\s/,"");
}
break;
}
_467+=text;
break;
default:
break;
}
}
return _467;
};
dojo.html.setActiveStyleSheet=function(_468){
var i,a,main;
for(i=0;(a=document.getElementsByTagName("link")[i]);i++){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")){
a.disabled=true;
if(a.getAttribute("title")==_468){
a.disabled=false;
}
}
}
};
dojo.html.getActiveStyleSheet=function(){
var i,a;
for(i=0;(a=document.getElementsByTagName("link")[i]);i++){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")&&!a.disabled){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.getPreferredStyleSheet=function(){
var i,a;
for(i=0;(a=document.getElementsByTagName("link")[i]);i++){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("rel").indexOf("alt")==-1&&a.getAttribute("title")){
return a.getAttribute("title");
}
}
return null;
};
dojo.provide("dojo.math");
dojo.math.degToRad=function(x){
return (x*Math.PI)/180;
};
dojo.math.radToDeg=function(x){
return (x*180)/Math.PI;
};
dojo.math.factorial=function(n){
if(n<1){
return 0;
}
var _469=1;
for(var i=1;i<=n;i++){
_469*=i;
}
return _469;
};
dojo.math.permutations=function(n,k){
if(n==0||k==0){
return 1;
}
return (dojo.math.factorial(n)/dojo.math.factorial(n-k));
};
dojo.math.combinations=function(n,r){
if(n==0||r==0){
return 1;
}
return (dojo.math.factorial(n)/(dojo.math.factorial(n-r)*dojo.math.factorial(r)));
};
dojo.math.bernstein=function(t,n,i){
return (dojo.math.combinations(n,i)*Math.pow(t,i)*Math.pow(1-t,n-i));
};
dojo.math.gaussianRandom=function(){
var k=2;
do{
var i=2*Math.random()-1;
var j=2*Math.random()-1;
k=i*i+j*j;
}while(k>=1);
k=Math.sqrt((-2*Math.log(k))/k);
return i*k;
};
dojo.math.mean=function(){
var _471=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0;
for(var i=0;i<_471.length;i++){
mean+=_471[i];
}
return mean/_471.length;
};
dojo.math.round=function(_473,_474){
if(!_474){
var _475=1;
}else{
var _475=Math.pow(10,_474);
}
return Math.round(_473*_475)/_475;
};
dojo.math.sd=function(){
var _476=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
return Math.sqrt(dojo.math.variance(_476));
};
dojo.math.variance=function(){
var _477=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0,squares=0;
for(var i=0;i<_477.length;i++){
mean+=_477[i];
squares+=Math.pow(_477[i],2);
}
return (squares/_477.length)-Math.pow(mean/_477.length,2);
};
dojo.provide("dojo.math.curves");
dojo.require("dojo.math");
dojo.math.curves={Line:function(_478,end){
this.start=_478;
this.end=end;
this.dimensions=_478.length;
for(var i=0;i<_478.length;i++){
_478[i]=Number(_478[i]);
}
for(var i=0;i<end.length;i++){
end[i]=Number(end[i]);
}
this.getValue=function(n){
var _479=new Array(this.dimensions);
for(var i=0;i<this.dimensions;i++){
_479[i]=((this.end[i]-this.start[i])*n)+this.start[i];
}
return _479;
};
return this;
},Bezier:function(pnts){
this.getValue=function(step){
if(step>=1){
return this.p[this.p.length-1];
}
if(step<=0){
return this.p[0];
}
var _482=new Array(this.p[0].length);
for(var k=0;j<this.p[0].length;k++){
_482[k]=0;
}
for(var j=0;j<this.p[0].length;j++){
var C=0;
var D=0;
for(var i=0;i<this.p.length;i++){
C+=this.p[i][j]*this.p[this.p.length-1][0]*dojo.math.bernstein(step,this.p.length,i);
}
for(var l=0;l<this.p.length;l++){
D+=this.p[this.p.length-1][0]*dojo.math.bernstein(step,this.p.length,l);
}
_482[j]=C/D;
}
return _482;
};
this.p=pnts;
return this;
},CatmullRom:function(pnts,c){
this.getValue=function(step){
var _486=step*(this.p.length-1);
var node=Math.floor(_486);
var _487=_486-node;
var i0=node-1;
if(i0<0){
i0=0;
}
var i=node;
var i1=node+1;
if(i1>=this.p.length){
i1=this.p.length-1;
}
var i2=node+2;
if(i2>=this.p.length){
i2=this.p.length-1;
}
var u=_487;
var u2=_487*_487;
var u3=_487*_487*_487;
var _494=new Array(this.p[0].length);
for(var k=0;k<this.p[0].length;k++){
var x1=(-this.c*this.p[i0][k])+((2-this.c)*this.p[i][k])+((this.c-2)*this.p[i1][k])+(this.c*this.p[i2][k]);
var x2=(2*this.c*this.p[i0][k])+((this.c-3)*this.p[i][k])+((3-2*this.c)*this.p[i1][k])+(-this.c*this.p[i2][k]);
var x3=(-this.c*this.p[i0][k])+(this.c*this.p[i1][k]);
var x4=this.p[i][k];
_494[k]=x1*u3+x2*u2+x3*u+x4;
}
return _494;
};
if(!c){
this.c=0.7;
}else{
this.c=c;
}
this.p=pnts;
return this;
},Arc:function(_499,end,ccw){
var _501=dojo.math.points.midpoint(_499,end);
var _502=dojo.math.points.translate(dojo.math.points.invert(_501),_499);
var rad=Math.sqrt(Math.pow(_502[0],2)+Math.pow(_502[1],2));
var _504=dojo.math.radToDeg(Math.atan(_502[1]/_502[0]));
if(_502[0]<0){
_504-=90;
}else{
_504+=90;
}
dojo.math.curves.CenteredArc.call(this,_501,rad,_504,_504+(ccw?-180:180));
},CenteredArc:function(_505,_506,_507,end){
this.center=_505;
this.radius=_506;
this.start=_507||0;
this.end=end;
this.getValue=function(n){
var _508=new Array(2);
var _509=dojo.math.degToRad(this.start+((this.end-this.start)*n));
_508[0]=this.center[0]+this.radius*Math.sin(_509);
_508[1]=this.center[1]-this.radius*Math.cos(_509);
return _508;
};
return this;
},Circle:function(_510,_511){
dojo.math.curves.CenteredArc.call(this,_510,_511,0,360);
return this;
},Path:function(){
var _512=[];
var _513=[];
var _514=[];
var _515=0;
this.add=function(_516,_517){
if(_517<0){
dj_throw("dojo.math.curves.Path.add: weight cannot be less than 0");
}
_512.push(_516);
_513.push(_517);
_515+=_517;
computeRanges();
};
this.remove=function(_518){
for(var i=0;i<_512.length;i++){
if(_512[i]==_518){
_512.splice(i,1);
_515-=_513.splice(i,1)[0];
break;
}
}
computeRanges();
};
this.removeAll=function(){
_512=[];
_513=[];
_515=0;
};
this.getValue=function(n){
var _519=false,value=0;
for(var i=0;i<_514.length;i++){
var r=_514[i];
if(n>=r[0]&&n<r[1]){
var subN=(n-r[0])/r[2];
value=_512[i].getValue(subN);
_519=true;
break;
}
}
if(!_519){
value=_512[_512.length-1].getValue(1);
}
for(j=0;j<i;j++){
value=dojo.math.points.translate(value,_512[j].getValue(1));
}
return value;
};
function computeRanges(){
var _521=0;
for(var i=0;i<_513.length;i++){
var end=_521+_513[i]/_515;
var len=end-_521;
_514[i]=[_521,end,len];
_521=end;
}
}
return this;
}};
dojo.provide("dojo.animation");
dojo.provide("dojo.animation.Animation");
dojo.require("dojo.math");
dojo.require("dojo.math.curves");
dojo.animation={};
dojo.animation.Animation=function(_522,_523,_524,_525){
var _526=this;
this.curve=_522;
this.duration=_523;
this.repeatCount=_525||0;
this.animSequence_=null;
if(_524){
if(dojo.lang.isFunction(_524.getValue)){
this.accel=_524;
}else{
var i=0.35*_524+0.5;
this.accel=new dojo.math.curves.CatmullRom([[0],[i],[1]],0.45);
}
}
this.onBegin=null;
this.onAnimate=null;
this.onEnd=null;
this.onPlay=null;
this.onPause=null;
this.onStop=null;
this.handler=null;
var _527=null,endTime=null,lastFrame=null,timer=null,percent=0,active=false,paused=false;
this.play=function(_528){
if(_528){
clearTimeout(timer);
active=false;
paused=false;
percent=0;
}else{
if(active&&!paused){
return;
}
}
_527=new Date().valueOf();
if(paused){
_527-=(_526.duration*percent/100);
}
endTime=_527+_526.duration;
lastFrame=_527;
var e=new dojo.animation.AnimationEvent(_526,null,_526.curve.getValue(percent),_527,_527,endTime,_526.duration,percent,0);
active=true;
paused=false;
if(percent==0){
e.type="begin";
if(typeof _526.handler=="function"){
_526.handler(e);
}
if(typeof _526.onBegin=="function"){
_526.onBegin(e);
}
}
e.type="play";
if(typeof _526.handler=="function"){
_526.handler(e);
}
if(typeof _526.onPlay=="function"){
_526.onPlay(e);
}
if(this.animSequence_){
this.animSequence_.setCurrent(this);
}
cycle();
};
this.pause=function(){
clearTimeout(timer);
if(!active){
return;
}
paused=true;
var e=new dojo.animation.AnimationEvent(_526,"pause",_526.curve.getValue(percent),_527,new Date().valueOf(),endTime,_526.duration,percent,0);
if(typeof _526.handler=="function"){
_526.handler(e);
}
if(typeof _526.onPause=="function"){
_526.onPause(e);
}
};
this.playPause=function(){
if(!active||paused){
_526.play();
}else{
_526.pause();
}
};
this.gotoPercent=function(pct,_530){
clearTimeout(timer);
active=true;
paused=true;
percent=pct;
if(_530){
this.play();
}
};
this.stop=function(_531){
clearTimeout(timer);
var step=percent/100;
if(_531){
step=1;
}
var e=new dojo.animation.AnimationEvent(_526,"stop",_526.curve.getValue(step),_527,new Date().valueOf(),endTime,_526.duration,percent,Math.round(fps));
if(typeof _526.handler=="function"){
_526.handler(e);
}
if(typeof _526.onStop=="function"){
_526.onStop(e);
}
active=false;
paused=false;
};
this.status=function(){
if(active){
return paused?"paused":"playing";
}else{
return "stopped";
}
};
function cycle(){
clearTimeout(timer);
if(active){
var curr=new Date().valueOf();
var step=(curr-_527)/(endTime-_527);
fps=1000/(curr-lastFrame);
lastFrame=curr;
if(step>=1){
step=1;
percent=100;
}else{
percent=step*100;
}
if(_526.accel&&_526.accel.getValue){
step=_526.accel.getValue(step);
}
var e=new dojo.animation.AnimationEvent(_526,"animate",_526.curve.getValue(step),_527,curr,endTime,_526.duration,percent,Math.round(fps));
if(typeof _526.handler=="function"){
_526.handler(e);
}
if(typeof _526.onAnimate=="function"){
_526.onAnimate(e);
}
if(step<1){
timer=setTimeout(cycle,10);
}else{
e.type="end";
active=false;
if(typeof _526.handler=="function"){
_526.handler(e);
}
if(typeof _526.onEnd=="function"){
_526.onEnd(e);
}
if(_526.repeatCount>0){
_526.repeatCount--;
_526.play(true);
}else{
if(_526.repeatCount==-1){
_526.play(true);
}else{
if(_526.animSequence_){
_526.animSequence_.playNext();
}
}
}
}
}
}
};
dojo.animation.AnimationEvent=function(anim,type,_533,_534,_535,_536,dur,pct,fps){
this.type=type;
this.animation=anim;
this.coords=_533;
this.x=_533[0];
this.y=_533[1];
this.z=_533[2];
this.startTime=_534;
this.currentTime=_535;
this.endTime=_536;
this.duration=dur;
this.percent=pct;
this.fps=fps;
this.coordsAsInts=function(){
var _539=new Array(this.coords.length);
for(var i=0;i<this.coords.length;i++){
_539[i]=Math.round(this.coords[i]);
}
return _539;
};
return this;
};
dojo.animation.AnimationSequence=function(_540){
var _541=[];
var _542=-1;
this.repeatCount=_540||0;
this.onBegin=null;
this.onEnd=null;
this.onNext=null;
this.handler=null;
this.add=function(){
for(var i=0;i<arguments.length;i++){
_541.push(arguments[i]);
arguments[i].animSequence_=this;
}
};
this.remove=function(anim){
for(var i=0;i<_541.length;i++){
if(_541[i]==anim){
_541[i].animSequence_=null;
_541.splice(i,1);
break;
}
}
};
this.removeAll=function(){
for(var i=0;i<_541.length;i++){
_541[i].animSequence_=null;
}
_541=[];
_542=-1;
};
this.play=function(_543){
if(_541.length==0){
return;
}
if(_543||!_541[_542]){
_542=0;
}
if(_541[_542]){
if(_542==0){
var e={type:"begin",animation:_541[_542]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onBegin=="function"){
this.onBegin(e);
}
}
_541[_542].play(_543);
}
};
this.pause=function(){
if(_541[_542]){
_541[_542].pause();
}
};
this.playPause=function(){
if(_541.length==0){
return;
}
if(_542==-1){
_542=0;
}
if(_541[_542]){
_541[_542].playPause();
}
};
this.stop=function(){
if(_541[_542]){
_541[_542].stop();
}
};
this.status=function(){
if(_541[_542]){
return _541[_542].status();
}else{
return "stopped";
}
};
this.setCurrent=function(anim){
for(var i=0;i<_541.length;i++){
if(_541[i]==anim){
_542=i;
break;
}
}
};
this.playNext=function(){
if(_542==-1||_541.length==0){
return;
}
_542++;
if(_541[_542]){
var e={type:"next",animation:_541[_542]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onNext=="function"){
this.onNext(e);
}
_541[_542].play(true);
}else{
var e={type:"end",animation:_541[_541.length-1]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onEnd=="function"){
this.onEnd(e);
}
if(this.repeatCount>0){
_542=0;
this.repeatCount--;
_541[_542].play(true);
}else{
if(this.repeatCount==-1){
_542=0;
_541[_542].play(true);
}else{
_542=-1;
}
}
}
};
};
dojo.hostenv.conditionalLoadModule({common:["dojo.animation.Animation",false,false]});
dojo.hostenv.moduleLoaded("dojo.animation.*");
dojo.provide("dojo.fx.html");
dojo.require("dojo.html");
dojo.require("dojo.style");
dojo.require("dojo.lang");
dojo.require("dojo.animation.*");
dojo.require("dojo.event.*");
dojo.fx.html.fadeOut=function(node,_544,_545){
return dojo.fx.html.fade(node,_544,dojo.html.getOpacity(node),0,_545);
};
dojo.fx.html.fadeIn=function(node,_546,_547){
return dojo.fx.html.fade(node,_546,dojo.html.getOpacity(node),1,_547);
};
dojo.fx.html.fadeHide=function(node,_548,_549){
if(!_548){
_548=150;
}
return dojo.fx.html.fadeOut(node,_548,function(node){
node.style.display="none";
if(typeof _549=="function"){
_549(node);
}
});
};
dojo.fx.html.fadeShow=function(node,_550,_551){
if(!_550){
_550=150;
}
node.style.display="block";
return dojo.fx.html.fade(node,_550,0,1,_551);
};
dojo.fx.html.fade=function(node,_552,_553,_554,_555){
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([_553],[_554]),_552,0);
dojo.event.connect(anim,"onAnimate",function(e){
dojo.html.setOpacity(node,e.x);
});
if(_555){
dojo.event.connect(anim,"onEnd",function(e){
_555(node,anim);
});
}
anim.play(true);
return anim;
};
dojo.fx.html.slideTo=function(node,_556,_557,_558){
return dojo.fx.html.slide(node,[node.offsetLeft,node.offsetTop],_556,_557,_558);
};
dojo.fx.html.slideBy=function(node,_559,_560,_561){
return dojo.fx.html.slideTo(node,[node.offsetLeft+_559[0],node.offsetTop+_559[1]],_560,_561);
};
dojo.fx.html.slide=function(node,_562,_563,_564,_565){
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_562,_563),_564,0);
dojo.event.connect(anim,"onAnimate",function(e){
with(node.style){
left=e.x+"px";
top=e.y+"px";
}
});
if(_565){
dojo.event.connect(anim,"onEnd",function(e){
_565(node,anim);
});
}
anim.play(true);
return anim;
};
dojo.fx.html.colorFadeIn=function(node,_566,_567,_568,_569){
var _570=dojo.html.getBackgroundColor(node);
var bg=dojo.style.getStyle(node,"background-color").toLowerCase();
var _572=bg=="transparent"||bg=="rgba(0, 0, 0, 0)";
while(_570.length>3){
_570.pop();
}
while(_566.length>3){
_566.pop();
}
var anim=dojo.fx.html.colorFade(node,_566,_570,_567,_569,true);
dojo.event.connect(anim,"onEnd",function(e){
if(_572){
node.style.backgroundColor="transparent";
}
});
if(_568>0){
node.style.backgroundColor="rgb("+_566.join(",")+")";
setTimeout(function(){
anim.play(true);
},_568);
}else{
anim.play(true);
}
return anim;
};
dojo.fx.html.highlight=dojo.fx.html.colorFadeIn;
dojo.fx.html.colorFadeFrom=dojo.fx.html.colorFadeIn;
dojo.fx.html.colorFadeOut=function(node,_573,_574,_575,_576){
var _577=dojo.html.getBackgroundColor(node);
while(_577.length>3){
_577.pop();
}
while(_573.length>3){
_573.pop();
}
var anim=dojo.fx.html.colorFade(node,_577,_573,_574,_576,_575>0);
if(_575>0){
node.style.backgroundColor="rgb("+_577.join(",")+")";
setTimeout(function(){
anim.play(true);
},_575);
}
return anim;
};
dojo.fx.html.unhighlight=dojo.fx.html.colorFadeOut;
dojo.fx.html.colorFadeTo=dojo.fx.html.colorFadeOut;
dojo.fx.html.colorFade=function(node,_578,_579,_580,_581,_582){
while(_578.length>3){
_578.pop();
}
while(_579.length>3){
_579.pop();
}
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_578,_579),_580,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.backgroundColor="rgb("+e.coordsAsInts().join(",")+")";
});
if(_581){
dojo.event.connect(anim,"onEnd",function(e){
_581(node,anim);
});
}
if(!_582){
anim.play(true);
}
return anim;
};
dojo.fx.html.wipeIn=function(node,_583,_584,_585){
var _586=dojo.html.getStyle(node,"height");
var _587=dojo.lang.inArray(node.tagName.toLowerCase(),["tr","td","th"])?"":"block";
node.style.display=_587;
var _588=node.offsetHeight;
var anim=dojo.fx.html.wipeInToHeight(node,_583,_588,function(e){
node.style.height=_586||"auto";
if(_584){
_584(node,anim);
}
},_585);
};
dojo.fx.html.wipeInToHeight=function(node,_589,_590,_591,_592){
var _593=dojo.html.getStyle(node,"overflow");
node.style.display="none";
node.style.height=0;
if(_593=="visible"){
node.style.overflow="hidden";
}
var _594=dojo.lang.inArray(node.tagName.toLowerCase(),["tr","td","th"])?"":"block";
node.style.display=_594;
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([0],[_590]),_589,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.height=Math.round(e.x)+"px";
});
dojo.event.connect(anim,"onEnd",function(e){
if(_593!="visible"){
node.style.overflow=_593;
}
if(_591){
_591(node,anim);
}
});
if(!_592){
anim.play(true);
}
return anim;
};
dojo.fx.html.wipeOut=function(node,_595,_596,_597){
var _598=dojo.html.getStyle(node,"overflow");
var _599=dojo.html.getStyle(node,"height");
var _600=node.offsetHeight;
node.style.overflow="hidden";
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([_600],[0]),_595,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.height=Math.round(e.x)+"px";
});
dojo.event.connect(anim,"onEnd",function(e){
node.style.display="none";
node.style.overflow=_598;
node.style.height=_599||"auto";
if(_596){
_596(node,anim);
}
});
if(!_597){
anim.play(true);
}
return anim;
};
dojo.fx.html.explode=function(_601,_602,_603,_604){
var _605=[dojo.html.getAbsoluteX(_601),dojo.html.getAbsoluteY(_601),dojo.html.getInnerWidth(_601),dojo.html.getInnerHeight(_601)];
return dojo.fx.html.explodeFromBox(_605,_602,_603,_604);
};
dojo.fx.html.explodeFromBox=function(_606,_607,_608,_609){
var _610=document.createElement("div");
with(_610.style){
position="absolute";
border="1px solid black";
display="none";
}
document.body.appendChild(_610);
with(_607.style){
visibility="hidden";
display="block";
}
var _611=[dojo.html.getAbsoluteX(_607),dojo.html.getAbsoluteY(_607),dojo.html.getInnerWidth(_607),dojo.html.getInnerHeight(_607)];
with(_607.style){
display="none";
visibility="visible";
}
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_606,_611),_608,0);
dojo.event.connect(anim,"onBegin",function(e){
_610.style.display="block";
});
dojo.event.connect(anim,"onAnimate",function(e){
with(_610.style){
left=e.x+"px";
top=e.y+"px";
width=e.coords[2]+"px";
height=e.coords[3]+"px";
}
});
dojo.event.connect(anim,"onEnd",function(){
_607.style.display="block";
_610.parentNode.removeChild(_610);
if(_609){
_609(_607,anim);
}
});
anim.play();
return anim;
};
dojo.fx.html.implode=function(_612,_613,_614,_615){
var _616=[dojo.html.getAbsoluteX(_613),dojo.html.getAbsoluteY(_613),dojo.html.getInnerWidth(_613),dojo.html.getInnerHeight(_613)];
return dojo.fx.html.implodeToBox(_612,_616,_614,_615);
};
dojo.fx.html.implodeToBox=function(_617,_618,_619,_620){
var _621=document.createElement("div");
with(_621.style){
position="absolute";
border="1px solid black";
display="none";
}
document.body.appendChild(_621);
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([dojo.html.getAbsoluteX(_617),dojo.html.getAbsoluteY(_617),dojo.html.getInnerWidth(_617),dojo.html.getInnerHeight(_617)],_618),_619,0);
dojo.event.connect(anim,"onBegin",function(e){
_617.style.display="none";
_621.style.display="block";
});
dojo.event.connect(anim,"onAnimate",function(e){
with(_621.style){
left=e.x+"px";
top=e.y+"px";
width=e.coords[2]+"px";
height=e.coords[3]+"px";
}
});
dojo.event.connect(anim,"onEnd",function(){
_621.parentNode.removeChild(_621);
if(_620){
_620(_617,anim);
}
});
anim.play();
return anim;
};
dojo.fx.html.Exploder=function(_622,_623){
var _624=this;
this.waitToHide=500;
this.timeToShow=100;
this.waitToShow=200;
this.timeToHide=70;
this.autoShow=false;
this.autoHide=false;
var _625=null;
var _626=null;
var _627=null;
var _628=null;
var _629=null;
var _630=null;
this.showing=false;
this.onBeforeExplode=null;
this.onAfterExplode=null;
this.onBeforeImplode=null;
this.onAfterImplode=null;
this.onExploding=null;
this.onImploding=null;
this.timeShow=function(){
clearTimeout(_627);
_627=setTimeout(_624.show,_624.waitToShow);
};
this.show=function(){
clearTimeout(_627);
clearTimeout(_628);
if((_626&&_626.status()=="playing")||(_625&&_625.status()=="playing")||_624.showing){
return;
}
if(typeof _624.onBeforeExplode=="function"){
_624.onBeforeExplode(_622,_623);
}
_625=dojo.fx.html.explode(_622,_623,_624.timeToShow,function(e){
_624.showing=true;
if(typeof _624.onAfterExplode=="function"){
_624.onAfterExplode(_622,_623);
}
});
if(typeof _624.onExploding=="function"){
dojo.event.connect(_625,"onAnimate",this,"onExploding");
}
};
this.timeHide=function(){
clearTimeout(_627);
clearTimeout(_628);
if(_624.showing){
_628=setTimeout(_624.hide,_624.waitToHide);
}
};
this.hide=function(){
clearTimeout(_627);
clearTimeout(_628);
if(_625&&_625.status()=="playing"){
return;
}
_624.showing=false;
if(typeof _624.onBeforeImplode=="function"){
_624.onBeforeImplode(_622,_623);
}
_626=dojo.fx.html.implode(_623,_622,_624.timeToHide,function(e){
if(typeof _624.onAfterImplode=="function"){
_624.onAfterImplode(_622,_623);
}
});
if(typeof _624.onImploding=="function"){
dojo.event.connect(_626,"onAnimate",this,"onImploding");
}
};
dojo.event.connect(_622,"onclick",function(e){
if(_624.showing){
_624.hide();
}else{
_624.show();
}
});
dojo.event.connect(_622,"onmouseover",function(e){
if(_624.autoShow){
_624.timeShow();
}
});
dojo.event.connect(_622,"onmouseout",function(e){
if(_624.autoHide){
_624.timeHide();
}
});
dojo.event.connect(_623,"onmouseover",function(e){
clearTimeout(_628);
});
dojo.event.connect(_623,"onmouseout",function(e){
if(_624.autoHide){
_624.timeHide();
}
});
dojo.event.connect(document.documentElement||document.body,"onclick",function(e){
if(_624.autoHide&&_624.showing&&!dojo.dom.isDescendantOf(e.target,_623)&&!dojo.dom.isDescendantOf(e.target,_622)){
_624.hide();
}
});
return this;
};
dojo.lang.mixin(dojo.fx,dojo.fx.html);
dojo.hostenv.conditionalLoadModule({browser:["dojo.fx.html"]});
dojo.hostenv.moduleLoaded("dojo.fx.*");
dojo.provide("dojo.graphics.htmlEffects");
dojo.require("dojo.fx.*");
dj_deprecated("dojo.graphics.htmlEffects is deprecated, use dojo.fx.html instead");
dojo.graphics.htmlEffects=dojo.fx.html;
dojo.hostenv.conditionalLoadModule({browser:["dojo.graphics.htmlEffects"]});
dojo.hostenv.moduleLoaded("dojo.graphics.*");

