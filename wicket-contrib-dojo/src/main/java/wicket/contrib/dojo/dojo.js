/*
	Copyright (c) 2004-2005, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

var dj_global=this;
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
dojo.version={major:0,minor:2,patch:0,flag:"",revision:Number("$Rev: 2495 $".match(/[0-9]+/)[0]),toString:function(){
with(dojo.version){
return major+"."+minor+"."+patch+flag+" ("+revision+")";
}
}};
dojo.evalObjPath=function(_3,_4){
if(typeof _3!="string"){
return dj_global;
}
if(_3.indexOf(".")==-1){
if((dj_undef(_3,dj_global))&&(_4)){
dj_global[_3]={};
}
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
dojo.raise=function(_9,_a){
if(_a){
_9=_9+": "+dojo.errorToString(_a);
}
var he=dojo.hostenv;
if((!dj_undef("hostenv",dojo))&&(!dj_undef("println",dojo.hostenv))){
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
var _e=arguments;
if(dj_undef("println",dojo.hostenv)){
dojo.raise("dojo.debug not available (yet?)");
}
var _f=dj_global["jum"]&&!dj_global["jum"].isBrowser;
var s=[(_f?"":"DEBUG: ")];
for(var i=0;i<_e.length;++i){
if(!false&&_e[i] instanceof Error){
var msg="["+_e[i].name+": "+dojo.errorToString(_e[i])+(_e[i].fileName?", file: "+_e[i].fileName:"")+(_e[i].lineNumber?", line: "+_e[i].lineNumber:"")+"]";
}else{
try{
var msg=String(_e[i]);
}
catch(e){
if(dojo.render.html.ie){
var msg="[ActiveXObject]";
}else{
var msg="[unknown]";
}
}
}
s.push(msg);
}
if(_f){
jum.debug(s.join(" "));
}else{
dojo.hostenv.println(s.join(" "));
}
};
dojo.debugShallow=function(obj){
if(!djConfig.isDebug){
return;
}
dojo.debug("------------------------------------------------------------");
dojo.debug("Object: "+obj);
for(i in obj){
dojo.debug(i+": "+obj[i]);
}
dojo.debug("------------------------------------------------------------");
};
var dj_debug=dojo.debug;
function dj_eval(s){
return dj_global.eval?dj_global.eval(s):eval(s);
}
dj_unimplemented=dojo.unimplemented=function(_15,_16){
var _17="'"+_15+"' not implemented";
if((!dj_undef(_16))&&(_16)){
_17+=" "+_16;
}
dojo.raise(_17);
};
dj_deprecated=dojo.deprecated=function(_18,_19,_1a){
var _1b="DEPRECATED: "+_18;
if(_19){
_1b+=" "+_19;
}
if(_1a){
_1b+=" -- will be removed in version: "+_1a;
}
dojo.debug(_1b);
};
dojo.inherits=function(_1c,_1d){
if(typeof _1d!="function"){
dojo.raise("superclass: "+_1d+" borken");
}
_1c.prototype=new _1d();
_1c.prototype.constructor=_1c;
_1c.superclass=_1d.prototype;
_1c["super"]=_1d.prototype;
};
dj_inherits=function(_1e,_1f){
dojo.deprecated("dj_inherits deprecated, use dojo.inherits instead");
dojo.inherits(_1e,_1f);
};
dojo.render=(function(){
function vscaffold(_20,_21){
var tmp={capable:false,support:{builtin:false,plugin:false},prefixes:_20};
for(var x in _21){
tmp[x]=false;
}
return tmp;
}
return {name:"",ver:dojo.version,os:{win:false,linux:false,osx:false},html:vscaffold(["html"],["ie","opera","khtml","safari","moz"]),svg:vscaffold(["svg"],["corel","adobe","batik"]),vml:vscaffold(["vml"],["ie"]),swf:vscaffold(["Swf","Flash","Mm"],["mm"]),swt:vscaffold(["Swt"],["ibm"])};
})();
dojo.hostenv=(function(){
var _24={isDebug:false,allowQueryConfig:false,baseScriptUri:"",baseRelativePath:"",libraryScriptUri:"",iePreventClobber:false,ieClobberMinimal:true,preventBackButtonFix:true,searchIds:[],parseWidgets:true};
if(typeof djConfig=="undefined"){
djConfig=_24;
}else{
for(var _25 in _24){
if(typeof djConfig[_25]=="undefined"){
djConfig[_25]=_24[_25];
}
}
}
var djc=djConfig;
function _def(obj,_28,def){
return (dj_undef(_28,obj)?def:obj[_28]);
}
return {name_:"(unset)",version_:"(unset)",pkgFileName:"__package__",loading_modules_:{},loaded_modules_:{},addedToLoadingCount:[],removedFromLoadingCount:[],inFlightCount:0,modulePrefixes_:{dojo:{name:"dojo",value:"src"}},setModulePrefix:function(_2a,_2b){
this.modulePrefixes_[_2a]={name:_2a,value:_2b};
},getModulePrefix:function(_2c){
var mp=this.modulePrefixes_;
if((mp[_2c])&&(mp[_2c]["name"])){
return mp[_2c].value;
}
return _2c;
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
var _30=uri.lastIndexOf("/");
djConfig.baseScriptUri=djConfig.baseRelativePath;
return djConfig.baseScriptUri;
};
dojo.hostenv.setBaseScriptUri=function(uri){
djConfig.baseScriptUri=uri;
};
dojo.hostenv.loadPath=function(_32,_33,cb){
if((_32.charAt(0)=="/")||(_32.match(/^\w+:/))){
dojo.raise("relpath '"+_32+"'; must be relative");
}
var uri=this.getBaseScriptUri()+_32;
if(djConfig.cacheBust&&dojo.render.html.capable){
uri+="?"+djConfig.cacheBust.replace(/\W+/g,"");
}
try{
return ((!_33)?this.loadUri(uri,cb):this.loadUriAndCheck(uri,_33,cb));
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
var _38=this.getText(uri,null,true);
if(_38==null){
return 0;
}
var _39=dj_eval(_38);
return 1;
};
dojo.hostenv.loadUriAndCheck=function(uri,_3b,cb){
var ok=true;
try{
ok=this.loadUri(uri,cb);
}
catch(e){
dojo.debug("failed loading ",uri," with error: ",e);
}
return ((ok)&&(this.findModule(_3b,false)))?true:false;
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
dojo.addOnLoad=function(obj,_41){
if(arguments.length==1){
dojo.hostenv.modulesLoadedListeners.push(obj);
}else{
if(arguments.length>1){
dojo.hostenv.modulesLoadedListeners.push(function(){
obj[_41]();
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
dojo.hostenv.moduleLoaded=function(_42){
var _43=dojo.evalObjPath((_42.split(".").slice(0,-1)).join("."));
this.loaded_modules_[(new String(_42)).toLowerCase()]=_43;
};
dojo.hostenv._global_omit_module_check=false;
dojo.hostenv.loadModule=function(_44,_45,_46){
_46=this._global_omit_module_check||_46;
var _47=this.findModule(_44,false);
if(_47){
return _47;
}
if(dj_undef(_44,this.loading_modules_)){
this.addedToLoadingCount.push(_44);
}
this.loading_modules_[_44]=1;
var _48=_44.replace(/\./g,"/")+".js";
var _49=_44.split(".");
var _4a=_44.split(".");
for(var i=_49.length-1;i>0;i--){
var _4c=_49.slice(0,i).join(".");
var _4d=this.getModulePrefix(_4c);
if(_4d!=_4c){
_49.splice(0,i,_4d);
break;
}
}
var _4e=_49[_49.length-1];
if(_4e=="*"){
_44=(_4a.slice(0,-1)).join(".");
while(_49.length){
_49.pop();
_49.push(this.pkgFileName);
_48=_49.join("/")+".js";
if(_48.charAt(0)=="/"){
_48=_48.slice(1);
}
ok=this.loadPath(_48,((!_46)?_44:null));
if(ok){
break;
}
_49.pop();
}
}else{
_48=_49.join("/")+".js";
_44=_4a.join(".");
var ok=this.loadPath(_48,((!_46)?_44:null));
if((!ok)&&(!_45)){
_49.pop();
while(_49.length){
_48=_49.join("/")+".js";
ok=this.loadPath(_48,((!_46)?_44:null));
if(ok){
break;
}
_49.pop();
_48=_49.join("/")+"/"+this.pkgFileName+".js";
if(_48.charAt(0)=="/"){
_48=_48.slice(1);
}
ok=this.loadPath(_48,((!_46)?_44:null));
if(ok){
break;
}
}
}
if((!ok)&&(!_46)){
dojo.raise("Could not load '"+_44+"'; last tried '"+_48+"'");
}
}
if(!_46){
_47=this.findModule(_44,false);
if(!_47){
dojo.raise("symbol '"+_44+"' is not defined after loading '"+_48+"'");
}
}
return _47;
};
dojo.hostenv.startPackage=function(_50){
var _51=_50.split(/\./);
if(_51[_51.length-1]=="*"){
_51.pop();
}
return dojo.evalObjPath(_51.join("."),true);
};
dojo.hostenv.findModule=function(_52,_53){
if(this.loaded_modules_[(new String(_52)).toLowerCase()]){
return this.loaded_modules_[_52];
}
var _54=dojo.evalObjPath(_52);
if((typeof _54!=="undefined")&&(_54)){
return _54;
}
if(_53){
dojo.raise("no loaded module named '"+_52+"'");
}
return null;
};
if(typeof window=="undefined"){
dojo.raise("no window object");
}
(function(){
if(djConfig.allowQueryConfig){
var _55=document.location.toString();
var _56=_55.split("?",2);
if(_56.length>1){
var _57=_56[1];
var _58=_57.split("&");
for(var x in _58){
var sp=_58[x].split("=");
if((sp[0].length>9)&&(sp[0].substr(0,9)=="djConfig.")){
var opt=sp[0].substr(9);
try{
djConfig[opt]=eval(sp[1]);
}
catch(e){
djConfig[opt]=sp[1];
}
}
}
}
}
if(((djConfig["baseScriptUri"]=="")||(djConfig["baseRelativePath"]==""))&&(document&&document.getElementsByTagName)){
var _5c=document.getElementsByTagName("script");
var _5d=/(__package__|dojo)\.js(\?|$)/i;
for(var i=0;i<_5c.length;i++){
var src=_5c[i].getAttribute("src");
if(!src){
continue;
}
var m=src.match(_5d);
if(m){
root=src.substring(0,m.index);
if(!this["djConfig"]){
djConfig={};
}
if(djConfig["baseScriptUri"]==""){
djConfig["baseScriptUri"]=root;
}
if(djConfig["baseRelativePath"]==""){
djConfig["baseRelativePath"]=root;
}
break;
}
}
if(djConfig["debugAtAllCosts"]){
document.write("<scr"+"ipt type='text/javascript' src='"+djConfig["baseRelativePath"]+"src/browser_debug.js"+"'></scr"+"ipt>");
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
dr.os.linux=dav.indexOf("X11")>=0;
drh.opera=dua.indexOf("Opera")>=0;
drh.khtml=(dav.indexOf("Konqueror")>=0)||(dav.indexOf("Safari")>=0);
drh.safari=dav.indexOf("Safari")>=0;
var _67=dua.indexOf("Gecko");
drh.mozilla=drh.moz=(_67>=0)&&(!drh.khtml);
if(drh.mozilla){
drh.geckoVersion=dua.substring(_67+6,_67+14);
}
drh.ie=(document.all)&&(!drh.opera);
drh.ie50=drh.ie&&dav.indexOf("MSIE 5.0")>=0;
drh.ie55=drh.ie&&dav.indexOf("MSIE 5.5")>=0;
drh.ie60=drh.ie&&dav.indexOf("MSIE 6.0")>=0;
dr.vml.capable=drh.ie;
dr.svg.capable=f;
dr.svg.support.plugin=f;
dr.svg.support.builtin=f;
dr.svg.adobe=f;
if(document.implementation&&document.implementation.hasFeature&&document.implementation.hasFeature("org.w3c.dom.svg","1.0")){
dr.svg.capable=t;
dr.svg.support.builtin=t;
dr.svg.support.plugin=f;
dr.svg.adobe=f;
}else{
if(navigator.mimeTypes&&navigator.mimeTypes.length>0){
var _68=navigator.mimeTypes["image/svg+xml"]||navigator.mimeTypes["image/svg"]||navigator.mimeTypes["image/svg-xml"];
if(_68){
dr.svg.adobe=_68&&_68.enabledPlugin&&_68.enabledPlugin.description&&(_68.enabledPlugin.description.indexOf("Adobe")>-1);
if(dr.svg.adobe){
dr.svg.capable=t;
dr.svg.support.plugin=t;
}
}
}else{
if(drh.ie&&dr.os.win){
var _68=f;
try{
var _69=new ActiveXObject("Adobe.SVGCtl");
_68=t;
}
catch(e){
}
if(_68){
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
var _6a=null;
var _6b=null;
try{
_6a=new XMLHttpRequest();
}
catch(e){
}
if(!_6a){
for(var i=0;i<3;++i){
var _6d=DJ_XMLHTTP_PROGIDS[i];
try{
_6a=new ActiveXObject(_6d);
}
catch(e){
_6b=e;
}
if(_6a){
DJ_XMLHTTP_PROGIDS=[_6d];
break;
}
}
}
if(!_6a){
return dojo.raise("XMLHTTP not available",_6b);
}
return _6a;
};
dojo.hostenv.getText=function(uri,_6f,_70){
var _71=this.getXmlhttpObject();
if(_6f){
_71.onreadystatechange=function(){
if((4==_71.readyState)&&(_71["status"])){
if(_71.status==200){
dojo.debug("LOADED URI: "+uri);
_6f(_71.responseText);
}
}
};
}
_71.open("GET",uri,_6f?true:false);
_71.send(null);
if(_6f){
return null;
}
return _71.responseText;
};
dojo.hostenv.defaultDebugContainerId="dojoDebug";
dojo.hostenv._println_buffer=[];
dojo.hostenv._println_safe=false;
dojo.hostenv.println=function(_72){
if(!dojo.hostenv._println_safe){
dojo.hostenv._println_buffer.push(_72);
}else{
try{
var _73=document.getElementById(djConfig.debugContainerId?djConfig.debugContainerId:dojo.hostenv.defaultDebugContainerId);
if(!_73){
_73=document.getElementsByTagName("body")[0]||document.body;
}
var div=document.createElement("div");
div.appendChild(document.createTextNode(_72));
_73.appendChild(div);
}
catch(e){
try{
document.write("<div>"+_72+"</div>");
}
catch(e2){
window.status=_72;
}
}
}
};
dojo.addOnLoad(function(){
dojo.hostenv._println_safe=true;
while(dojo.hostenv._println_buffer.length>0){
dojo.hostenv.println(dojo.hostenv._println_buffer.shift());
}
});
function dj_addNodeEvtHdlr(_75,_76,fp,_78){
var _79=_75["on"+_76]||function(){
};
_75["on"+_76]=function(){
fp.apply(_75,arguments);
_79.apply(_75,arguments);
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
var _7a=[];
if(djConfig.searchIds&&djConfig.searchIds.length>0){
_7a=_7a.concat(djConfig.searchIds);
}
if(dojo.hostenv.searchIds&&dojo.hostenv.searchIds.length>0){
_7a=_7a.concat(dojo.hostenv.searchIds);
}
if((djConfig.parseWidgets)||(_7a.length>0)){
if(dojo.evalObjPath("dojo.widget.Parse")){
try{
var _7b=new dojo.xml.Parse();
if(_7a.length>0){
for(var x=0;x<_7a.length;x++){
var _7d=document.getElementById(_7a[x]);
if(!_7d){
continue;
}
var _7e=_7b.parseElement(_7d,null,true);
dojo.widget.getParser().createComponents(_7e);
}
}else{
if(djConfig.parseWidgets){
var _7e=_7b.parseElement(document.getElementsByTagName("body")[0]||document.body,null,true);
dojo.widget.getParser().createComponents(_7e);
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
dojo.hostenv.byId=dojo.byId=function(id,doc){
if(typeof id=="string"||id instanceof String){
if(!doc){
doc=document;
}
return doc.getElementById(id);
}
return id;
};
dojo.hostenv.byIdArray=dojo.byIdArray=function(){
var ids=[];
for(var i=0;i<arguments.length;i++){
if((arguments[i] instanceof Array)||(typeof arguments[i]=="array")){
for(var j=0;j<arguments[i].length;j++){
ids=ids.concat(dojo.hostenv.byIdArray(arguments[i][j]));
}
}else{
ids.push(dojo.hostenv.byId(arguments[i]));
}
}
return ids;
};
dojo.hostenv.conditionalLoadModule=function(_84){
var _85=_84["common"]||[];
var _86=(_84[dojo.hostenv.name_])?_85.concat(_84[dojo.hostenv.name_]||[]):_85.concat(_84["default"]||[]);
for(var x=0;x<_86.length;x++){
var _88=_86[x];
if(_88.constructor==Array){
dojo.hostenv.loadModule.apply(dojo.hostenv,_88);
}else{
dojo.hostenv.loadModule(_88);
}
}
};
dojo.hostenv.require=dojo.hostenv.loadModule;
dojo.require=function(){
dojo.hostenv.loadModule.apply(dojo.hostenv,arguments);
};
dojo.requireAfter=dojo.require;
dojo.requireIf=function(){
if((arguments[0]===true)||(arguments[0]=="common")||(dojo.render[arguments[0]].capable)){
var _89=[];
for(var i=1;i<arguments.length;i++){
_89.push(arguments[i]);
}
dojo.require.apply(dojo,_89);
}
};
dojo.requireAfterIf=dojo.requireIf;
dojo.conditionalRequire=dojo.requireIf;
dojo.kwCompoundRequire=function(){
dojo.hostenv.conditionalLoadModule.apply(dojo.hostenv,arguments);
};
dojo.hostenv.provide=dojo.hostenv.startPackage;
dojo.provide=function(){
return dojo.hostenv.startPackage.apply(dojo.hostenv,arguments);
};
dojo.setModulePrefix=function(_8b,_8c){
return dojo.hostenv.setModulePrefix(_8b,_8c);
};
dojo.profile={start:function(){
},end:function(){
},dump:function(){
}};
dojo.provide("dojo.lang");
dojo.provide("dojo.AdapterRegistry");
dojo.provide("dojo.lang.Lang");
dojo.lang.mixin=function(obj,_8e,_8f){
if(typeof _8f!="object"){
_8f={};
}
for(var x in _8e){
if(typeof _8f[x]=="undefined"||_8f[x]!=_8e[x]){
obj[x]=_8e[x];
}
}
return obj;
};
dojo.lang.extend=function(_91,_92){
this.mixin(_91.prototype,_92);
};
dojo.lang.extendPrototype=function(obj,_94){
this.extend(obj.constructor,_94);
};
dojo.lang.anonCtr=0;
dojo.lang.anon={};
dojo.lang.nameAnonFunc=function(_95,_96){
var nso=(_96||dojo.lang.anon);
if((dj_global["djConfig"])&&(djConfig["slowAnonFuncLookups"]==true)){
for(var x in nso){
if(nso[x]===_95){
return x;
}
}
}
var ret="__"+dojo.lang.anonCtr++;
while(typeof nso[ret]!="undefined"){
ret="__"+dojo.lang.anonCtr++;
}
nso[ret]=_95;
return ret;
};
dojo.lang.hitch=function(_9a,_9b){
if(dojo.lang.isString(_9b)){
var fcn=_9a[_9b];
}else{
var fcn=_9b;
}
return function(){
return fcn.apply(_9a,arguments);
};
};
dojo.lang.setTimeout=function(_9d,_9e){
var _9f=window,argsStart=2;
if(!dojo.lang.isFunction(_9d)){
_9f=_9d;
_9d=_9e;
_9e=arguments[2];
argsStart++;
}
if(dojo.lang.isString(_9d)){
_9d=_9f[_9d];
}
var _a0=[];
for(var i=argsStart;i<arguments.length;i++){
_a0.push(arguments[i]);
}
return setTimeout(function(){
_9d.apply(_9f,_a0);
},_9e);
};
dojo.lang.isObject=function(wh){
return typeof wh=="object"||dojo.lang.isArray(wh)||dojo.lang.isFunction(wh);
};
dojo.lang.isArray=function(wh){
return (wh instanceof Array||typeof wh=="array");
};
dojo.lang.isArrayLike=function(wh){
if(dojo.lang.isString(wh)){
return false;
}
if(dojo.lang.isArray(wh)){
return true;
}
if(dojo.lang.isNumber(wh.length)&&isFinite(wh)){
return true;
}
return false;
};
dojo.lang.isFunction=function(wh){
return (wh instanceof Function||typeof wh=="function");
};
dojo.lang.isString=function(wh){
return (wh instanceof String||typeof wh=="string");
};
dojo.lang.isAlien=function(wh){
return !dojo.lang.isFunction()&&/\{\s*\[native code\]\s*\}/.test(String(wh));
};
dojo.lang.isBoolean=function(wh){
return (wh instanceof Boolean||typeof wh=="boolean");
};
dojo.lang.isNumber=function(wh){
return (wh instanceof Number||typeof wh=="number");
};
dojo.lang.isUndefined=function(wh){
return ((wh==undefined)&&(typeof wh=="undefined"));
};
dojo.lang.whatAmI=function(wh){
try{
if(dojo.lang.isArray(wh)){
return "array";
}
if(dojo.lang.isFunction(wh)){
return "function";
}
if(dojo.lang.isString(wh)){
return "string";
}
if(dojo.lang.isNumber(wh)){
return "number";
}
if(dojo.lang.isBoolean(wh)){
return "boolean";
}
if(dojo.lang.isAlien(wh)){
return "alien";
}
if(dojo.lang.isUndefined(wh)){
return "undefined";
}
for(var _ac in dojo.lang.whatAmI.custom){
if(dojo.lang.whatAmI.custom[_ac](wh)){
return _ac;
}
}
if(dojo.lang.isObject(wh)){
return "object";
}
}
catch(E){
}
return "unknown";
};
dojo.lang.whatAmI.custom={};
dojo.lang.find=function(arr,val,_af){
if(!dojo.lang.isArray(arr)&&dojo.lang.isArray(val)){
var a=arr;
arr=val;
val=a;
}
var _b1=dojo.lang.isString(arr);
if(_b1){
arr=arr.split("");
}
if(_af){
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
dojo.lang.indexOf=dojo.lang.find;
dojo.lang.findLast=function(arr,val,_b5){
if(!dojo.lang.isArray(arr)&&dojo.lang.isArray(val)){
var a=arr;
arr=val;
val=a;
}
var _b7=dojo.lang.isString(arr);
if(_b7){
arr=arr.split("");
}
if(_b5){
for(var i=arr.length-1;i>=0;i--){
if(arr[i]===val){
return i;
}
}
}else{
for(var i=arr.length-1;i>=0;i--){
if(arr[i]==val){
return i;
}
}
}
return -1;
};
dojo.lang.lastIndexOf=dojo.lang.findLast;
dojo.lang.inArray=function(arr,val){
return dojo.lang.find(arr,val)>-1;
};
dojo.lang.getNameInObj=function(ns,_bc){
if(!ns){
ns=dj_global;
}
for(var x in ns){
if(ns[x]===_bc){
return new String(x);
}
}
return null;
};
dojo.lang.has=function(obj,_bf){
return (typeof obj[_bf]!=="undefined");
};
dojo.lang.isEmpty=function(obj){
if(dojo.lang.isObject(obj)){
var tmp={};
var _c2=0;
for(var x in obj){
if(obj[x]&&(!tmp[x])){
_c2++;
break;
}
}
return (_c2==0);
}else{
if(dojo.lang.isArray(obj)||dojo.lang.isString(obj)){
return obj.length==0;
}
}
};
dojo.lang.forEach=function(arr,_c5,_c6){
var _c7=dojo.lang.isString(arr);
if(_c7){
arr=arr.split("");
}
var il=arr.length;
for(var i=0;i<((_c6)?il:arr.length);i++){
if(_c5(arr[i],i,arr)=="break"){
break;
}
}
};
dojo.lang.map=function(arr,obj,_cc){
var _cd=dojo.lang.isString(arr);
if(_cd){
arr=arr.split("");
}
if(dojo.lang.isFunction(obj)&&(!_cc)){
_cc=obj;
obj=dj_global;
}else{
if(dojo.lang.isFunction(obj)&&_cc){
var _ce=obj;
obj=_cc;
_cc=_ce;
}
}
if(Array.map){
var _cf=Array.map(arr,_cc,obj);
}else{
var _cf=[];
for(var i=0;i<arr.length;++i){
_cf.push(_cc.call(obj,arr[i]));
}
}
if(_cd){
return _cf.join("");
}else{
return _cf;
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
dojo.lang.delayThese=function(_d3,cb,_d5,_d6){
if(!_d3.length){
if(typeof _d6=="function"){
_d6();
}
return;
}
if((typeof _d5=="undefined")&&(typeof cb=="number")){
_d5=cb;
cb=function(){
};
}else{
if(!cb){
cb=function(){
};
if(!_d5){
_d5=0;
}
}
}
setTimeout(function(){
(_d3.shift())();
cb();
dojo.lang.delayThese(_d3,cb,_d5,_d6);
},_d5);
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
dojo.lang.every=function(arr,_da,_db){
var _dc=dojo.lang.isString(arr);
if(_dc){
arr=arr.split("");
}
if(Array.every){
return Array.every(arr,_da,_db);
}else{
if(!_db){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_db=dj_global;
}
for(var i=0;i<arr.length;i++){
if(!_da.call(_db,arr[i],i,arr)){
return false;
}
}
return true;
}
};
dojo.lang.some=function(arr,_df,_e0){
var _e1=dojo.lang.isString(arr);
if(_e1){
arr=arr.split("");
}
if(Array.some){
return Array.some(arr,_df,_e0);
}else{
if(!_e0){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_e0=dj_global;
}
for(var i=0;i<arr.length;i++){
if(_df.call(_e0,arr[i],i,arr)){
return true;
}
}
return false;
}
};
dojo.lang.filter=function(arr,_e4,_e5){
var _e6=dojo.lang.isString(arr);
if(_e6){
arr=arr.split("");
}
if(Array.filter){
var _e7=Array.filter(arr,_e4,_e5);
}else{
if(!_e5){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_e5=dj_global;
}
var _e7=[];
for(var i=0;i<arr.length;i++){
if(_e4.call(_e5,arr[i],i,arr)){
_e7.push(arr[i]);
}
}
}
if(_e6){
return _e7.join("");
}else{
return _e7;
}
};
dojo.AdapterRegistry=function(){
this.pairs=[];
};
dojo.lang.extend(dojo.AdapterRegistry,{register:function(_e9,_ea,_eb,_ec){
if(_ec){
this.pairs.unshift([_e9,_ea,_eb]);
}else{
this.pairs.push([_e9,_ea,_eb]);
}
},match:function(){
for(var i=0;i<this.pairs.length;i++){
var _ee=this.pairs[i];
if(_ee[1].apply(this,arguments)){
return _ee[2].apply(this,arguments);
}
}
dojo.raise("No match found");
},unregister:function(_ef){
for(var i=0;i<this.pairs.length;i++){
var _f1=this.pairs[i];
if(_f1[0]==_ef){
this.pairs.splice(i,1);
return true;
}
}
return false;
}});
dojo.lang.reprRegistry=new dojo.AdapterRegistry();
dojo.lang.registerRepr=function(_f2,_f3,_f4,_f5){
dojo.lang.reprRegistry.register(_f2,_f3,_f4,_f5);
};
dojo.lang.repr=function(obj){
if(typeof (obj)=="undefined"){
return "undefined";
}else{
if(obj===null){
return "null";
}
}
try{
if(typeof (obj["__repr__"])=="function"){
return obj["__repr__"]();
}else{
if((typeof (obj["repr"])=="function")&&(obj.repr!=arguments.callee)){
return obj["repr"]();
}
}
return dojo.lang.reprRegistry.match(obj);
}
catch(e){
if(typeof (obj.NAME)=="string"&&(obj.toString==Function.prototype.toString||obj.toString==Object.prototype.toString)){
return o.NAME;
}
}
if(typeof (obj)=="function"){
obj=(obj+"").replace(/^\s+/,"");
var idx=obj.indexOf("{");
if(idx!=-1){
obj=obj.substr(0,idx)+"{...}";
}
}
return obj+"";
};
dojo.lang.reprArrayLike=function(arr){
try{
var na=dojo.lang.map(arr,dojo.lang.repr);
return "["+na.join(", ")+"]";
}
catch(e){
}
};
dojo.lang.reprString=function(str){
return ("\""+str.replace(/(["\\])/g,"\\$1")+"\"").replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r");
};
dojo.lang.reprNumber=function(num){
return num+"";
};
(function(){
var m=dojo.lang;
m.registerRepr("arrayLike",m.isArrayLike,m.reprArrayLike);
m.registerRepr("string",m.isString,m.reprString);
m.registerRepr("numbers",m.isNumber,m.reprNumber);
m.registerRepr("boolean",m.isBoolean,m.reprNumber);
})();
dojo.lang.unnest=function(){
var out=[];
for(var i=0;i<arguments.length;i++){
if(dojo.lang.isArrayLike(arguments[i])){
var add=dojo.lang.unnest.apply(this,arguments[i]);
out=out.concat(add);
}else{
out.push(arguments[i]);
}
}
return out;
};
dojo.provide("dojo.dom");
dojo.require("dojo.lang");
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
dojo.dom.isNode=dojo.lang.isDomNode=function(wh){
if(typeof Element=="object"){
try{
return wh instanceof Element;
}
catch(E){
}
}else{
return wh&&!isNaN(wh.nodeType);
}
};
dojo.lang.whatAmI.custom["node"]=dojo.dom.isNode;
dojo.dom.getTagName=function(node){
var _102=node.tagName;
if(_102.substr(0,5).toLowerCase()!="dojo:"){
if(_102.substr(0,4).toLowerCase()=="dojo"){
return "dojo:"+_102.substring(4).toLowerCase();
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
var _104=node.className||node.getAttribute("class");
if((_104)&&(_104.indexOf("dojo-")!=-1)){
var _105=_104.split(" ");
for(var x=0;x<_105.length;x++){
if((_105[x].length>5)&&(_105[x].indexOf("dojo-")>=0)){
return "dojo:"+_105[x].substr(5).toLowerCase();
}
}
}
}
}
return _102.toLowerCase();
};
dojo.dom.getUniqueId=function(){
do{
var id="dj_unique_"+(++arguments.callee._idIncrement);
}while(document.getElementById(id));
return id;
};
dojo.dom.getUniqueId._idIncrement=0;
dojo.dom.firstElement=dojo.dom.getFirstChildElement=function(_108,_109){
var node=_108.firstChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.nextSibling;
}
if(_109&&node&&node.tagName&&node.tagName.toLowerCase()!=_109.toLowerCase()){
node=dojo.dom.nextElement(node,_109);
}
return node;
};
dojo.dom.lastElement=dojo.dom.getLastChildElement=function(_10b,_10c){
var node=_10b.lastChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.previousSibling;
}
if(_10c&&node&&node.tagName&&node.tagName.toLowerCase()!=_10c.toLowerCase()){
node=dojo.dom.prevElement(node,_10c);
}
return node;
};
dojo.dom.nextElement=dojo.dom.getNextSiblingElement=function(node,_10f){
if(!node){
return null;
}
do{
node=node.nextSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_10f&&_10f.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.nextElement(node,_10f);
}
return node;
};
dojo.dom.prevElement=dojo.dom.getPreviousSiblingElement=function(node,_111){
if(!node){
return null;
}
if(_111){
_111=_111.toLowerCase();
}
do{
node=node.previousSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_111&&_111.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.prevElement(node,_111);
}
return node;
};
dojo.dom.moveChildren=function(_112,_113,trim){
var _115=0;
if(trim){
while(_112.hasChildNodes()&&_112.firstChild.nodeType==dojo.dom.TEXT_NODE){
_112.removeChild(_112.firstChild);
}
while(_112.hasChildNodes()&&_112.lastChild.nodeType==dojo.dom.TEXT_NODE){
_112.removeChild(_112.lastChild);
}
}
while(_112.hasChildNodes()){
_113.appendChild(_112.firstChild);
_115++;
}
return _115;
};
dojo.dom.copyChildren=function(_116,_117,trim){
var _119=_116.cloneNode(true);
return this.moveChildren(_119,_117,trim);
};
dojo.dom.removeChildren=function(node){
var _11b=node.childNodes.length;
while(node.hasChildNodes()){
node.removeChild(node.firstChild);
}
return _11b;
};
dojo.dom.replaceChildren=function(node,_11d){
dojo.dom.removeChildren(node);
node.appendChild(_11d);
};
dojo.dom.removeNode=function(node){
if(node&&node.parentNode){
return node.parentNode.removeChild(node);
}
};
dojo.dom.getAncestors=function(node,_120,_121){
var _122=[];
var _123=dojo.lang.isFunction(_120);
while(node){
if(!_123||_120(node)){
_122.push(node);
}
if(_121&&_122.length>0){
return _122[0];
}
node=node.parentNode;
}
if(_121){
return null;
}
return _122;
};
dojo.dom.getAncestorsByTag=function(node,tag,_126){
tag=tag.toLowerCase();
return dojo.dom.getAncestors(node,function(el){
return ((el.tagName)&&(el.tagName.toLowerCase()==tag));
},_126);
};
dojo.dom.getFirstAncestorByTag=function(node,tag){
return dojo.dom.getAncestorsByTag(node,tag,true);
};
dojo.dom.isDescendantOf=function(node,_12b,_12c){
if(_12c&&node){
node=node.parentNode;
}
while(node){
if(node==_12b){
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
dojo.dom.createDocumentFromText=function(str,_12f){
if(!_12f){
_12f="text/xml";
}
if(typeof DOMParser!="undefined"){
var _130=new DOMParser();
return _130.parseFromString(str,_12f);
}else{
if(typeof ActiveXObject!="undefined"){
var _131=new ActiveXObject("Microsoft.XMLDOM");
if(_131){
_131.async=false;
_131.loadXML(str);
return _131;
}else{
dojo.debug("toXml didn't work?");
}
}else{
if(document.createElement){
var tmp=document.createElement("xml");
tmp.innerHTML=str;
if(document.implementation&&document.implementation.createDocument){
var _133=document.implementation.createDocument("foo","",null);
for(var i=0;i<tmp.childNodes.length;i++){
_133.importNode(tmp.childNodes.item(i),true);
}
return _133;
}
return tmp.document&&tmp.document.firstChild?tmp.document.firstChild:tmp;
}
}
}
return null;
};
dojo.dom.insertBefore=function(node,ref,_137){
if(_137!=true&&(node===ref||node.nextSibling===ref)){
return false;
}
var _138=ref.parentNode;
_138.insertBefore(node,ref);
return true;
};
dojo.dom.insertAfter=function(node,ref,_13b){
var pn=ref.parentNode;
if(ref==pn.lastChild){
if((_13b!=true)&&(node===ref)){
return false;
}
pn.appendChild(node);
}else{
return this.insertBefore(node,ref.nextSibling,_13b);
}
return true;
};
dojo.dom.insertAtPosition=function(node,ref,_13f){
if((!node)||(!ref)||(!_13f)){
return false;
}
switch(_13f.toLowerCase()){
case "before":
return dojo.dom.insertBefore(node,ref);
case "after":
return dojo.dom.insertAfter(node,ref);
case "first":
if(ref.firstChild){
return dojo.dom.insertBefore(node,ref.firstChild);
}else{
ref.appendChild(node);
return true;
}
break;
default:
ref.appendChild(node);
return true;
}
};
dojo.dom.insertAtIndex=function(node,_141,_142){
var _143=_141.childNodes;
if(!_143.length){
_141.appendChild(node);
return true;
}
var _144=null;
for(var i=0;i<_143.length;i++){
var _146=_143.item(i)["getAttribute"]?parseInt(_143.item(i).getAttribute("dojoinsertionindex")):-1;
if(_146<_142){
_144=_143.item(i);
}
}
if(_144){
return dojo.dom.insertAfter(node,_144);
}else{
return dojo.dom.insertBefore(node,_143.item(0));
}
};
dojo.dom.textContent=function(node,text){
if(text){
dojo.dom.replaceChildren(node,document.createTextNode(text));
return text;
}else{
var _149="";
if(node==null){
return _149;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
_149+=dojo.dom.textContent(node.childNodes[i]);
break;
case 3:
case 2:
case 4:
_149+=node.childNodes[i].nodeValue;
break;
default:
break;
}
}
return _149;
}
};
dojo.dom.collectionToArray=function(_14b){
var _14c=new Array(_14b.length);
for(var i=0;i<_14b.length;i++){
_14c[i]=_14b[i];
}
return _14c;
};
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
var _153=new dojo.uri.Uri(arguments[i].toString());
var _154=new dojo.uri.Uri(uri.toString());
if(_153.path==""&&_153.scheme==null&&_153.authority==null&&_153.query==null){
if(_153.fragment!=null){
_154.fragment=_153.fragment;
}
_153=_154;
}else{
if(_153.scheme==null){
_153.scheme=_154.scheme;
if(_153.authority==null){
_153.authority=_154.authority;
if(_153.path.charAt(0)!="/"){
var path=_154.path.substring(0,_154.path.lastIndexOf("/")+1)+_153.path;
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
_153.path=segs.join("/");
}
}
}
}
uri="";
if(_153.scheme!=null){
uri+=_153.scheme+":";
}
if(_153.authority!=null){
uri+="//"+_153.authority;
}
uri+=_153.path;
if(_153.query!=null){
uri+="?"+_153.query;
}
if(_153.fragment!=null){
uri+="#"+_153.fragment;
}
}
this.uri=uri.toString();
var _158="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$";
var r=this.uri.match(new RegExp(_158));
this.scheme=r[2]||(r[1]?"":null);
this.authority=r[4]||(r[3]?"":null);
this.path=r[5];
this.query=r[7]||(r[6]?"":null);
this.fragment=r[9]||(r[8]?"":null);
if(this.authority!=null){
_158="^((([^:]+:)?([^@]+))@)?([^:]*)(:([0-9]+))?$";
r=this.authority.match(new RegExp(_158));
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
dojo.provide("dojo.string");
dojo.require("dojo.lang");
dojo.string.trim=function(str,wh){
if(!dojo.lang.isString(str)){
return str;
}
if(!str.length){
return str;
}
if(wh>0){
return str.replace(/^\s+/,"");
}else{
if(wh<0){
return str.replace(/\s+$/,"");
}else{
return str.replace(/^\s+|\s+$/g,"");
}
}
};
dojo.string.trimStart=function(str){
return dojo.string.trim(str,1);
};
dojo.string.trimEnd=function(str){
return dojo.string.trim(str,-1);
};
dojo.string.paramString=function(str,_15f,_160){
for(var name in _15f){
var re=new RegExp("\\%\\{"+name+"\\}","g");
str=str.replace(re,_15f[name]);
}
if(_160){
str=str.replace(/%\{([^\}\s]+)\}/g,"");
}
return str;
};
dojo.string.capitalize=function(str){
if(!dojo.lang.isString(str)){
return "";
}
if(arguments.length==0){
str=this;
}
var _164=str.split(" ");
var _165="";
var len=_164.length;
for(var i=0;i<len;i++){
var word=_164[i];
word=word.charAt(0).toUpperCase()+word.substring(1,word.length);
_165+=word;
if(i<len-1){
_165+=" ";
}
}
return new String(_165);
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
var _16c=escape(str);
var _16d,re=/%u([0-9A-F]{4})/i;
while((_16d=_16c.match(re))){
var num=Number("0x"+_16d[1]);
var _16f=escape("&#"+num+";");
ret+=_16c.substring(0,_16d.index)+_16f;
_16c=_16c.substring(_16d.index+_16d[0].length);
}
ret+=_16c.replace(/\+/g,"%2B");
return ret;
};
dojo.string.summary=function(str,len){
if(!len||str.length<=len){
return str;
}else{
return str.substring(0,len).replace(/\.+$/,"")+"...";
}
};
dojo.string.escape=function(type,str){
switch(type.toLowerCase()){
case "xml":
case "html":
case "xhtml":
return dojo.string.escapeXml(str);
case "sql":
return dojo.string.escapeSql(str);
case "regexp":
case "regex":
return dojo.string.escapeRegExp(str);
case "javascript":
case "jscript":
case "js":
return dojo.string.escapeJavaScript(str);
case "ascii":
return dojo.string.encodeAscii(str);
default:
return str;
}
};
dojo.string.escapeXml=function(str){
return str.replace(/&/gm,"&amp;").replace(/</gm,"&lt;").replace(/>/gm,"&gt;").replace(/"/gm,"&quot;").replace(/'/gm,"&#39;");
};
dojo.string.escapeSql=function(str){
return str.replace(/'/gm,"''");
};
dojo.string.escapeRegExp=function(str){
return str.replace(/\\/gm,"\\\\").replace(/([\f\b\n\t\r])/gm,"\\$1");
};
dojo.string.escapeJavaScript=function(str){
return str.replace(/(["'\f\b\n\t\r])/gm,"\\$1");
};
dojo.string.repeat=function(str,_179,_17a){
var out="";
for(var i=0;i<_179;i++){
out+=str;
if(_17a&&i<_179-1){
out+=_17a;
}
}
return out;
};
dojo.string.endsWith=function(str,end,_17f){
if(_17f){
str=str.toLowerCase();
end=end.toLowerCase();
}
return str.lastIndexOf(end)==str.length-end.length;
};
dojo.string.endsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.endsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.startsWith=function(str,_183,_184){
if(_184){
str=str.toLowerCase();
_183=_183.toLowerCase();
}
return str.indexOf(_183)==0;
};
dojo.string.startsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.startsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.has=function(str){
for(var i=1;i<arguments.length;i++){
if(str.indexOf(arguments[i]>-1)){
return true;
}
}
return false;
};
dojo.string.pad=function(str,len,c,dir){
var out=String(str);
if(!c){
c="0";
}
if(!dir){
dir=1;
}
while(out.length<len){
if(dir>0){
out=c+out;
}else{
out+=c;
}
}
return out;
};
dojo.string.padLeft=function(str,len,c){
return dojo.string.pad(str,len,c,1);
};
dojo.string.padRight=function(str,len,c){
return dojo.string.pad(str,len,c,-1);
};
dojo.string.addToPrototype=function(){
for(var _194 in dojo.string){
if(dojo.lang.isFunction(dojo.string[_194])){
var func=(function(){
var meth=_194;
switch(meth){
case "addToPrototype":
return null;
break;
case "escape":
return function(type){
return dojo.string.escape(type,this);
};
break;
default:
return function(){
var args=[this];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
dojo.debug(args);
return dojo.string[meth].apply(dojo.string,args);
};
}
})();
if(func){
String.prototype[_194]=func;
}
}
}
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
var _19d=1;
for(var i=1;i<=n;i++){
_19d*=i;
}
return _19d;
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
var _1a9=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0;
for(var i=0;i<_1a9.length;i++){
mean+=_1a9[i];
}
return mean/_1a9.length;
};
dojo.math.round=function(_1ac,_1ad){
if(!_1ad){
var _1ae=1;
}else{
var _1ae=Math.pow(10,_1ad);
}
return Math.round(_1ac*_1ae)/_1ae;
};
dojo.math.sd=function(){
var _1af=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
return Math.sqrt(dojo.math.variance(_1af));
};
dojo.math.variance=function(){
var _1b0=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0,squares=0;
for(var i=0;i<_1b0.length;i++){
mean+=_1b0[i];
squares+=Math.pow(_1b0[i],2);
}
return (squares/_1b0.length)-Math.pow(mean/_1b0.length,2);
};
dojo.provide("dojo.graphics.color");
dojo.require("dojo.lang");
dojo.require("dojo.string");
dojo.require("dojo.math");
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
this.a=g||1;
}else{
if(r instanceof dojo.graphics.color.Color){
this.r=r.r;
this.b=r.b;
this.g=r.g;
this.a=r.a;
}else{
this.r=r;
this.g=g;
this.b=b;
this.a=a;
}
}
}
};
dojo.lang.extend(dojo.graphics.color.Color,{toRgb:function(_1b8){
if(_1b8){
return this.toRgba();
}else{
return [this.r,this.g,this.b];
}
},toRgba:function(){
return [this.r,this.g,this.b,this.a];
},toHex:function(){
return dojo.graphics.color.rgb2hex(this.toRgb());
},toCss:function(){
return "rgb("+this.toRgb().join()+")";
},toString:function(){
return this.toHex();
},toHsv:function(){
return dojo.graphics.color.rgb2hsv(this.toRgb());
},toHsl:function(){
return dojo.graphics.color.rgb2hsl(this.toRgb());
},blend:function(_1b9,_1ba){
return dojo.graphics.color.blend(this.toRgb(),new Color(_1b9).toRgb(),_1ba);
}});
dojo.graphics.color.named={white:[255,255,255],black:[0,0,0],red:[255,0,0],green:[0,255,0],blue:[0,0,255],navy:[0,0,128],gray:[128,128,128],silver:[192,192,192]};
dojo.graphics.color.blend=function(a,b,_1bd){
if(typeof a=="string"){
return dojo.graphics.color.blendHex(a,b,_1bd);
}
if(!_1bd){
_1bd=0;
}else{
if(_1bd>1){
_1bd=1;
}else{
if(_1bd<-1){
_1bd=-1;
}
}
}
var c=new Array(3);
for(var i=0;i<3;i++){
var half=Math.abs(a[i]-b[i])/2;
c[i]=Math.floor(Math.min(a[i],b[i])+half+(half*_1bd));
}
return c;
};
dojo.graphics.color.blendHex=function(a,b,_1c3){
return dojo.graphics.color.rgb2hex(dojo.graphics.color.blend(dojo.graphics.color.hex2rgb(a),dojo.graphics.color.hex2rgb(b),_1c3));
};
dojo.graphics.color.extractRGB=function(_1c4){
var hex="0123456789abcdef";
_1c4=_1c4.toLowerCase();
if(_1c4.indexOf("rgb")==0){
var _1c6=_1c4.match(/rgba*\((\d+), *(\d+), *(\d+)/i);
var ret=_1c6.splice(1,3);
return ret;
}else{
var _1c8=dojo.graphics.color.hex2rgb(_1c4);
if(_1c8){
return _1c8;
}else{
return dojo.graphics.color.named[_1c4]||[255,255,255];
}
}
};
dojo.graphics.color.hex2rgb=function(hex){
var _1ca="0123456789ABCDEF";
var rgb=new Array(3);
if(hex.indexOf("#")==0){
hex=hex.substring(1);
}
hex=hex.toUpperCase();
if(hex.replace(new RegExp("["+_1ca+"]","g"),"")!=""){
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
rgb[i]=_1ca.indexOf(rgb[i].charAt(0))*16+_1ca.indexOf(rgb[i].charAt(1));
}
return rgb;
};
dojo.graphics.color.rgb2hex=function(r,g,b){
if(dojo.lang.isArray(r)){
g=r[1]||0;
b=r[2]||0;
r=r[0]||0;
}
return ["#",dojo.string.pad(r.toString(16),2),dojo.string.pad(g.toString(16),2),dojo.string.pad(b.toString(16),2)].join("");
};
dojo.graphics.color.rgb2hsv=function(r,g,b){
if(dojo.lang.isArray(r)){
b=r[2]||0;
g=r[1]||0;
r=r[0]||0;
}
var h=null;
var s=null;
var v=null;
var min=Math.min(r,g,b);
v=Math.max(r,g,b);
var _1d7=v-min;
s=(v==0)?0:_1d7/v;
if(s==0){
h=0;
}else{
if(r==v){
h=60*(g-b)/_1d7;
}else{
if(g==v){
h=120+60*(b-r)/_1d7;
}else{
if(b==v){
h=240+60*(r-g)/_1d7;
}
}
}
if(h<0){
h+=360;
}
}
h=(h==0)?360:Math.ceil((h/360)*255);
s=Math.ceil(s*255);
return [h,s,v];
};
dojo.graphics.color.hsv2rgb=function(h,s,v){
if(dojo.lang.isArray(h)){
v=h[2]||0;
s=h[1]||0;
h=h[0]||0;
}
h=(h/255)*360;
if(h==360){
h=0;
}
s=s/255;
v=v/255;
var r=null;
var g=null;
var b=null;
if(s==0){
r=v;
g=v;
b=v;
}else{
var _1de=h/60;
var i=Math.floor(_1de);
var f=_1de-i;
var p=v*(1-s);
var q=v*(1-(s*f));
var t=v*(1-(s*(1-f)));
switch(i){
case 0:
r=v;
g=t;
b=p;
break;
case 1:
r=q;
g=v;
b=p;
break;
case 2:
r=p;
g=v;
b=t;
break;
case 3:
r=p;
g=q;
b=v;
break;
case 4:
r=t;
g=p;
b=v;
break;
case 5:
r=v;
g=p;
b=q;
break;
}
}
r=Math.ceil(r*255);
g=Math.ceil(g*255);
b=Math.ceil(b*255);
return [r,g,b];
};
dojo.graphics.color.rgb2hsl=function(r,g,b){
if(dojo.lang.isArray(r)){
b=r[2]||0;
g=r[1]||0;
r=r[0]||0;
}
r/=255;
g/=255;
b/=255;
var h=null;
var s=null;
var l=null;
var min=Math.min(r,g,b);
var max=Math.max(r,g,b);
var _1ec=max-min;
l=(min+max)/2;
s=0;
if((l>0)&&(l<1)){
s=_1ec/((l<0.5)?(2*l):(2-2*l));
}
h=0;
if(_1ec>0){
if((max==r)&&(max!=g)){
h+=(g-b)/_1ec;
}
if((max==g)&&(max!=b)){
h+=(2+(b-r)/_1ec);
}
if((max==b)&&(max!=r)){
h+=(4+(r-g)/_1ec);
}
h*=60;
}
h=(h==0)?360:Math.ceil((h/360)*255);
s=Math.ceil(s*255);
l=Math.ceil(l*255);
return [h,s,l];
};
dojo.graphics.color.hsl2rgb=function(h,s,l){
if(dojo.lang.isArray(h)){
l=h[2]||0;
s=h[1]||0;
h=h[0]||0;
}
h=(h/255)*360;
if(h==360){
h=0;
}
s=s/255;
l=l/255;
while(h<0){
h+=360;
}
while(h>360){
h-=360;
}
if(h<120){
r=(120-h)/60;
g=h/60;
b=0;
}else{
if(h<240){
r=0;
g=(240-h)/60;
b=(h-120)/60;
}else{
r=(h-240)/60;
g=0;
b=(360-h)/60;
}
}
r=Math.min(r,1);
g=Math.min(g,1);
b=Math.min(b,1);
r=2*s*r+(1-s);
g=2*s*g+(1-s);
b=2*s*b+(1-s);
if(l<0.5){
r=l*r;
g=l*g;
b=l*b;
}else{
r=(1-l)*r+2*l-1;
g=(1-l)*g+2*l-1;
b=(1-l)*b+2*l-1;
}
r=Math.ceil(r*255);
g=Math.ceil(g*255);
b=Math.ceil(b*255);
return [r,g,b];
};
dojo.provide("dojo.style");
dojo.require("dojo.dom");
dojo.require("dojo.uri.Uri");
dojo.require("dojo.graphics.color");
dojo.style.boxSizing={marginBox:"margin-box",borderBox:"border-box",paddingBox:"padding-box",contentBox:"content-box"};
dojo.style.getBoxSizing=function(node){
if(dojo.render.html.ie||dojo.render.html.opera){
var cm=document["compatMode"];
if(cm=="BackCompat"||cm=="QuirksMode"){
return dojo.style.boxSizing.borderBox;
}else{
return dojo.style.boxSizing.contentBox;
}
}else{
if(arguments.length==0){
node=document.documentElement;
}
var _1f2=dojo.style.getStyle(node,"-moz-box-sizing");
if(!_1f2){
_1f2=dojo.style.getStyle(node,"box-sizing");
}
return (_1f2?_1f2:dojo.style.boxSizing.contentBox);
}
};
dojo.style.isBorderBox=function(node){
return (dojo.style.getBoxSizing(node)==dojo.style.boxSizing.borderBox);
};
dojo.style.getUnitValue=function(_1f4,_1f5,_1f6){
var _1f7={value:0,units:"px"};
var s=dojo.style.getComputedStyle(_1f4,_1f5);
if(s==""||(s=="auto"&&_1f6)){
return _1f7;
}
if(dojo.lang.isUndefined(s)){
_1f7.value=NaN;
}else{
var _1f9=s.match(/([\d.]+)([a-z%]*)/i);
if(!_1f9){
_1f7.value=NaN;
}else{
_1f7.value=Number(_1f9[1]);
_1f7.units=_1f9[2].toLowerCase();
}
}
return _1f7;
};
dojo.style.getPixelValue=function(_1fa,_1fb,_1fc){
var _1fd=dojo.style.getUnitValue(_1fa,_1fb,_1fc);
if(isNaN(_1fd.value)||(_1fd.value&&_1fd.units!="px")){
return NaN;
}
return _1fd.value;
};
dojo.style.getNumericStyle=dojo.style.getPixelValue;
dojo.style.isPositionAbsolute=function(node){
return (dojo.style.getComputedStyle(node,"position")=="absolute");
};
dojo.style.getMarginWidth=function(node){
var _200=dojo.style.isPositionAbsolute(node);
var left=dojo.style.getPixelValue(node,"margin-left",_200);
var _202=dojo.style.getPixelValue(node,"margin-right",_200);
return left+_202;
};
dojo.style.getBorderWidth=function(node){
var left=(dojo.style.getStyle(node,"border-left-style")=="none"?0:dojo.style.getPixelValue(node,"border-left-width"));
var _205=(dojo.style.getStyle(node,"border-right-style")=="none"?0:dojo.style.getPixelValue(node,"border-right-width"));
return left+_205;
};
dojo.style.getPaddingWidth=function(node){
var left=dojo.style.getPixelValue(node,"padding-left",true);
var _208=dojo.style.getPixelValue(node,"padding-right",true);
return left+_208;
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
dojo.style.setOuterWidth=function(node,_20d){
if(!dojo.style.isBorderBox(node)){
_20d-=dojo.style.getPaddingWidth(node)+dojo.style.getBorderWidth(node);
}
_20d-=dojo.style.getMarginWidth(node);
if(!isNaN(_20d)&&_20d>0){
node.style.width=_20d+"px";
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
var _20f=dojo.style.isPositionAbsolute(node);
var top=dojo.style.getPixelValue(node,"margin-top",_20f);
var _211=dojo.style.getPixelValue(node,"margin-bottom",_20f);
return top+_211;
};
dojo.style.getBorderHeight=function(node){
var top=(dojo.style.getStyle(node,"border-top-style")=="none"?0:dojo.style.getPixelValue(node,"border-top-width"));
var _214=(dojo.style.getStyle(node,"border-bottom-style")=="none"?0:dojo.style.getPixelValue(node,"border-bottom-width"));
return top+_214;
};
dojo.style.getPaddingHeight=function(node){
var top=dojo.style.getPixelValue(node,"padding-top",true);
var _217=dojo.style.getPixelValue(node,"padding-bottom",true);
return top+_217;
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
dojo.style.setOuterHeight=function(node,_21c){
if(!dojo.style.isBorderBox(node)){
_21c-=dojo.style.getPaddingHeight(node)+dojo.style.getBorderHeight(node);
}
_21c-=dojo.style.getMarginHeight(node);
if(!isNaN(_21c)&&_21c>0){
node.style.height=_21c+"px";
return true;
}else{
return false;
}
};
dojo.style.setContentWidth=function(node,_21e){
if(dojo.style.isBorderBox(node)){
_21e+=dojo.style.getPaddingWidth(node)+dojo.style.getBorderWidth(node);
}
if(!isNaN(_21e)&&_21e>0){
node.style.width=_21e+"px";
return true;
}else{
return false;
}
};
dojo.style.setContentHeight=function(node,_220){
if(dojo.style.isBorderBox(node)){
_220+=dojo.style.getPaddingHeight(node)+dojo.style.getBorderHeight(node);
}
if(!isNaN(_220)&&_220>0){
node.style.height=_220+"px";
return true;
}else{
return false;
}
};
dojo.style.getContentBoxHeight=dojo.style.getContentHeight;
dojo.style.getBorderBoxHeight=dojo.style.getInnerHeight;
dojo.style.getMarginBoxHeight=dojo.style.getOuterHeight;
dojo.style.setMarginBoxHeight=dojo.style.setOuterHeight;
dojo.style.getTotalOffset=function(node,type,_223){
var _224=(type=="top")?"offsetTop":"offsetLeft";
var _225=(type=="top")?"scrollTop":"scrollLeft";
var alt=(type=="top")?"y":"x";
var ret=0;
if(node["offsetParent"]){
if(_223&&node.parentNode!=document.body){
ret-=dojo.style.sumAncestorProperties(node,_225);
}
do{
ret+=node[_224];
node=node.offsetParent;
}while(node!=document.getElementsByTagName("body")[0].parentNode&&node!=null);
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
var _22a=0;
while(node){
var val=node[prop];
if(val){
_22a+=val-0;
}
node=node.parentNode;
}
return _22a;
};
dojo.style.totalOffsetLeft=function(node,_22d){
return dojo.style.getTotalOffset(node,"left",_22d);
};
dojo.style.getAbsoluteX=dojo.style.totalOffsetLeft;
dojo.style.totalOffsetTop=function(node,_22f){
return dojo.style.getTotalOffset(node,"top",_22f);
};
dojo.style.getAbsoluteY=dojo.style.totalOffsetTop;
dojo.style.getAbsolutePosition=function(node,_231){
var _232=[dojo.style.getAbsoluteX(node,_231),dojo.style.getAbsoluteY(node,_231)];
_232.x=_232[0];
_232.y=_232[1];
return _232;
};
dojo.style.styleSheet=null;
dojo.style.insertCssRule=function(_233,_234,_235){
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
_235=dojo.style.styleSheet.cssRules.length;
}else{
if(dojo.style.styleSheet.rules){
_235=dojo.style.styleSheet.rules.length;
}else{
return null;
}
}
}
if(dojo.style.styleSheet.insertRule){
var rule=_233+" { "+_234+" }";
return dojo.style.styleSheet.insertRule(rule,_235);
}else{
if(dojo.style.styleSheet.addRule){
return dojo.style.styleSheet.addRule(_233,_234,_235);
}else{
return null;
}
}
};
dojo.style.removeCssRule=function(_237){
if(!dojo.style.styleSheet){
dojo.debug("no stylesheet defined for removing rules");
return false;
}
if(dojo.render.html.ie){
if(!_237){
_237=dojo.style.styleSheet.rules.length;
dojo.style.styleSheet.removeRule(_237);
}
}else{
if(document.styleSheets[0]){
if(!_237){
_237=dojo.style.styleSheet.cssRules.length;
}
dojo.style.styleSheet.deleteRule(_237);
}
}
return true;
};
dojo.style.insertCssFile=function(URI,doc,_23a){
if(!URI){
return;
}
if(!doc){
doc=document;
}
if(doc.baseURI){
URI=new dojo.uri.Uri(doc.baseURI,URI);
}
if(_23a&&doc.styleSheets){
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
var _240;
do{
_240=dojo.style.getStyle(node,"background-color");
if(_240.toLowerCase()=="rgba(0, 0, 0, 0)"){
_240="transparent";
}
if(node==document.getElementsByTagName("body")[0]){
node=null;
break;
}
node=node.parentNode;
}while(node&&dojo.lang.inArray(_240,["transparent",""]));
if(_240=="transparent"){
_240=[255,255,255,0];
}else{
_240=dojo.graphics.color.extractRGB(_240);
}
return _240;
};
dojo.style.getComputedStyle=function(_241,_242,_243){
var _244=_243;
if(_241.style.getPropertyValue){
_244=_241.style.getPropertyValue(_242);
}
if(!_244){
if(document.defaultView){
_244=document.defaultView.getComputedStyle(_241,"").getPropertyValue(_242);
}else{
if(_241.currentStyle){
_244=_241.currentStyle[dojo.style.toCamelCase(_242)];
}
}
}
return _244;
};
dojo.style.getStyle=function(_245,_246){
var _247=dojo.style.toCamelCase(_246);
var _248=_245.style[_247];
return (_248?_248:dojo.style.getComputedStyle(_245,_246,_248));
};
dojo.style.toCamelCase=function(_249){
var arr=_249.split("-"),cc=arr[0];
for(var i=1;i<arr.length;i++){
cc+=arr[i].charAt(0).toUpperCase()+arr[i].substring(1);
}
return cc;
};
dojo.style.toSelectorCase=function(_24c){
return _24c.replace(/([A-Z])/g,"-$1").toLowerCase();
};
dojo.style.setOpacity=function setOpacity(node,_24e,_24f){
node=dojo.byId(node);
var h=dojo.render.html;
if(!_24f){
if(_24e>=1){
if(h.ie){
dojo.style.clearOpacity(node);
return;
}else{
_24e=0.999999;
}
}else{
if(_24e<0){
_24e=0;
}
}
}
if(h.ie){
if(node.nodeName.toLowerCase()=="tr"){
var tds=node.getElementsByTagName("td");
for(var x=0;x<tds.length;x++){
tds[x].style.filter="Alpha(Opacity="+_24e*100+")";
}
}
node.style.filter="Alpha(Opacity="+_24e*100+")";
}else{
if(h.moz){
node.style.opacity=_24e;
node.style.MozOpacity=_24e;
}else{
if(h.safari){
node.style.opacity=_24e;
node.style.KhtmlOpacity=_24e;
}else{
node.style.opacity=_24e;
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
dojo.lang.mixin(dojo.html,dojo.dom);
dojo.lang.mixin(dojo.html,dojo.style);
dojo.html.clearSelection=function(){
try{
if(window["getSelection"]){
if(dojo.render.html.safari){
window.getSelection().collapse();
}else{
window.getSelection().removeAllRanges();
}
}else{
if((document.selection)&&(document.selection.clear)){
document.selection.clear();
}
}
return true;
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.html.disableSelection=function(_257){
_257=_257||dojo.html.body();
var h=dojo.render.html;
if(h.mozilla){
_257.style.MozUserSelect="none";
}else{
if(h.safari){
_257.style.KhtmlUserSelect="none";
}else{
if(h.ie){
_257.unselectable="on";
}else{
return false;
}
}
}
return true;
};
dojo.html.enableSelection=function(_259){
_259=_259||dojo.html.body();
var h=dojo.render.html;
if(h.mozilla){
_259.style.MozUserSelect="";
}else{
if(h.safari){
_259.style.KhtmlUserSelect="";
}else{
if(h.ie){
_259.unselectable="off";
}else{
return false;
}
}
}
return true;
};
dojo.html.selectElement=function(_25b){
if(document.selection&&dojo.html.body().createTextRange){
var _25c=dojo.html.body().createTextRange();
_25c.moveToElementText(_25b);
_25c.select();
}else{
if(window["getSelection"]){
var _25d=window.getSelection();
if(_25d["selectAllChildren"]){
_25d.selectAllChildren(_25b);
}
}
}
};
dojo.html.isSelectionCollapsed=function(){
if(document["selection"]){
return document.selection.createRange().text=="";
}else{
if(window["getSelection"]){
var _25e=window.getSelection();
if(dojo.lang.isString(_25e)){
return _25e=="";
}else{
return _25e.isCollapsed;
}
}
}
};
dojo.html.getEventTarget=function(evt){
if(!evt){
evt=window.event||{};
}
if(evt.srcElement){
return evt.srcElement;
}else{
if(evt.target){
return evt.target;
}
}
return null;
};
dojo.html.getScrollTop=function(){
return document.documentElement.scrollTop||dojo.html.body().scrollTop||0;
};
dojo.html.getScrollLeft=function(){
return document.documentElement.scrollLeft||dojo.html.body().scrollLeft||0;
};
dojo.html.getDocumentWidth=function(){
dojo.deprecated("dojo.html.getDocument* has been deprecated in favor of dojo.html.getViewport*");
return dojo.html.getViewportWidth();
};
dojo.html.getDocumentHeight=function(){
dojo.deprecated("dojo.html.getDocument* has been deprecated in favor of dojo.html.getViewport*");
return dojo.html.getViewportHeight();
};
dojo.html.getDocumentSize=function(){
dojo.deprecated("dojo.html.getDocument* has been deprecated in favor of dojo.html.getViewport*");
return dojo.html.getViewportSize();
};
dojo.html.getViewportWidth=function(){
var w=0;
if(window.innerWidth){
w=window.innerWidth;
}
if(document.documentElement&&document.documentElement.clientWidth){
var w2=document.documentElement.clientWidth;
if(!w||w2&&w2<w){
w=w2;
}
return w;
}
if(document.body){
return document.body.clientWidth;
}
return 0;
};
dojo.html.getViewportHeight=function(){
if(window.innerHeight){
return window.innerHeight;
}
if(document.documentElement&&document.documentElement.clientHeight){
return document.documentElement.clientHeight;
}
if(document.body){
return document.body.clientHeight;
}
return 0;
};
dojo.html.getViewportSize=function(){
var ret=[dojo.html.getViewportWidth(),dojo.html.getViewportHeight()];
ret.w=ret[0];
ret.h=ret[1];
return ret;
};
dojo.html.getScrollOffset=function(){
var ret=[0,0];
if(window.pageYOffset){
ret=[window.pageXOffset,window.pageYOffset];
}else{
if(document.documentElement&&document.documentElement.scrollTop){
ret=[document.documentElement.scrollLeft,document.documentElement.scrollTop];
}else{
if(document.body){
ret=[document.body.scrollLeft,document.body.scrollTop];
}
}
}
ret.x=ret[0];
ret.y=ret[1];
return ret;
};
dojo.html.getParentOfType=function(node,type){
dojo.deprecated("dojo.html.getParentOfType has been deprecated in favor of dojo.html.getParentByType*");
return dojo.html.getParentByType(node,type);
};
dojo.html.getParentByType=function(node,type){
var _268=node;
type=type.toLowerCase();
while(_268.nodeName.toLowerCase()!=type){
if((!_268)||(_268==(document["body"]||document["documentElement"]))){
return null;
}
_268=_268.parentNode;
}
return _268;
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
var v=dojo.html.getAttribute(node,attr);
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
dojo.html.hasClass=function(node,_272){
var _273=dojo.html.getClass(node).split(/\s+/g);
for(var x=0;x<_273.length;x++){
if(_272==_273[x]){
return true;
}
}
return false;
};
dojo.html.prependClass=function(node,_276){
if(!node){
return null;
}
if(dojo.html.hasAttribute(node,"class")||node.className){
_276+=" "+(node.className||dojo.html.getAttribute(node,"class"));
}
return dojo.html.setClass(node,_276);
};
dojo.html.addClass=function(node,_278){
if(!node){
throw new Error("addClass: node does not exist");
}
if(dojo.html.hasClass(node,_278)){
return false;
}
if(dojo.html.hasAttribute(node,"class")||node.className){
_278=(node.className||dojo.html.getAttribute(node,"class"))+" "+_278;
}
return dojo.html.setClass(node,_278);
};
dojo.html.setClass=function(node,_27a){
if(!node){
return false;
}
var cs=new String(_27a);
try{
if(typeof node.className=="string"){
node.className=cs;
}else{
if(node.setAttribute){
node.setAttribute("class",_27a);
node.className=cs;
}else{
return false;
}
}
}
catch(e){
dojo.debug("dojo.html.setClass() failed",e);
}
return true;
};
dojo.html.removeClass=function(node,_27d,_27e){
if(!node){
return false;
}
var _27d=dojo.string.trim(new String(_27d));
try{
var cs=String(node.className).split(" ");
var nca=[];
if(_27e){
for(var i=0;i<cs.length;i++){
if(cs[i].indexOf(_27d)==-1){
nca.push(cs[i]);
}
}
}else{
for(var i=0;i<cs.length;i++){
if(cs[i]!=_27d){
nca.push(cs[i]);
}
}
}
node.className=nca.join(" ");
}
catch(e){
dojo.debug("dojo.html.removeClass() failed",e);
}
return true;
};
dojo.html.replaceClass=function(node,_283,_284){
dojo.html.removeClass(node,_284);
dojo.html.addClass(node,_283);
};
dojo.html.classMatchType={ContainsAll:0,ContainsAny:1,IsOnly:2};
dojo.html.getElementsByClass=function(_285,_286,_287,_288){
if(!_286){
_286=document;
}
var _289=_285.split(/\s+/g);
var _28a=[];
if(_288!=1&&_288!=2){
_288=0;
}
var _28b=new RegExp("(\\s|^)(("+_289.join(")|(")+"))(\\s|$)");
if(false&&document.evaluate){
var _28c="//"+(_287||"*")+"[contains(";
if(_288!=dojo.html.classMatchType.ContainsAny){
_28c+="concat(' ',@class,' '), ' "+_289.join(" ') and contains(concat(' ',@class,' '), ' ")+" ')]";
}else{
_28c+="concat(' ',@class,' '), ' "+_289.join(" ')) or contains(concat(' ',@class,' '), ' ")+" ')]";
}
var _28d=document.evaluate(_28c,_286,null,XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE,null);
outer:
for(var node=null,i=0;node=_28d.snapshotItem(i);i++){
if(_288!=dojo.html.classMatchType.IsOnly){
_28a.push(node);
}else{
if(!dojo.html.getClass(node)){
continue outer;
}
var _28f=dojo.html.getClass(node).split(/\s+/g);
for(var j=0;j<_28f.length;j++){
if(!_28f[j].match(_28b)){
continue outer;
}
}
_28a.push(node);
}
}
}else{
if(!_287){
_287="*";
}
var _291=_286.getElementsByTagName(_287);
outer:
for(var i=0;i<_291.length;i++){
var node=_291[i];
if(!dojo.html.getClass(node)){
continue outer;
}
var _28f=dojo.html.getClass(node).split(/\s+/g);
var _293=0;
for(var j=0;j<_28f.length;j++){
if(_28b.test(_28f[j])){
if(_288==dojo.html.classMatchType.ContainsAny){
_28a.push(node);
continue outer;
}else{
_293++;
}
}else{
if(_288==dojo.html.classMatchType.IsOnly){
continue outer;
}
}
}
if(_293==_289.length){
if(_288==dojo.html.classMatchType.IsOnly&&_293==_28f.length){
_28a.push(node);
}else{
if(_288==dojo.html.classMatchType.ContainsAll){
_28a.push(node);
}
}
}
}
}
return _28a;
};
dojo.html.getElementsByClassName=dojo.html.getElementsByClass;
dojo.html.gravity=function(node,e){
var _296=e.pageX||e.clientX+dojo.html.body().scrollLeft;
var _297=e.pageY||e.clientY+dojo.html.body().scrollTop;
with(dojo.html){
var _298=getAbsoluteX(node)+(getInnerWidth(node)/2);
var _299=getAbsoluteY(node)+(getInnerHeight(node)/2);
}
with(dojo.html.gravity){
return ((_296<_298?WEST:EAST)|(_297<_299?NORTH:SOUTH));
}
};
dojo.html.gravity.NORTH=1;
dojo.html.gravity.SOUTH=1<<1;
dojo.html.gravity.EAST=1<<2;
dojo.html.gravity.WEST=1<<3;
dojo.html.overElement=function(_29a,e){
var _29c=e.pageX||e.clientX+dojo.html.body().scrollLeft;
var _29d=e.pageY||e.clientY+dojo.html.body().scrollTop;
with(dojo.html){
var top=getAbsoluteY(_29a);
var _29f=top+getInnerHeight(_29a);
var left=getAbsoluteX(_29a);
var _2a1=left+getInnerWidth(_29a);
}
return (_29c>=left&&_29c<=_2a1&&_29d>=top&&_29d<=_29f);
};
dojo.html.renderedTextContent=function(node){
var _2a3="";
if(node==null){
return _2a3;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
var _2a5="unknown";
try{
_2a5=dojo.style.getStyle(node.childNodes[i],"display");
}
catch(E){
}
switch(_2a5){
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
_2a3+="\n";
_2a3+=dojo.html.renderedTextContent(node.childNodes[i]);
_2a3+="\n";
break;
case "none":
break;
default:
if(node.childNodes[i].tagName&&node.childNodes[i].tagName.toLowerCase()=="br"){
_2a3+="\n";
}else{
_2a3+=dojo.html.renderedTextContent(node.childNodes[i]);
}
break;
}
break;
case 3:
case 2:
case 4:
var text=node.childNodes[i].nodeValue;
var _2a7="unknown";
try{
_2a7=dojo.style.getStyle(node,"text-transform");
}
catch(E){
}
switch(_2a7){
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
switch(_2a7){
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
if(/\s$/.test(_2a3)){
text.replace(/^\s/,"");
}
break;
}
_2a3+=text;
break;
default:
break;
}
}
return _2a3;
};
dojo.html.setActiveStyleSheet=function(_2a8){
var i,a,main;
for(i=0;(a=document.getElementsByTagName("link")[i]);i++){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")){
a.disabled=true;
if(a.getAttribute("title")==_2a8){
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
dojo.html.body=function(){
return document.body||document.getElementsByTagName("body")[0];
};
dojo.html.createNodesFromText=function(txt,wrap){
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
var _2b1=[];
for(var x=0;x<tn.childNodes.length;x++){
_2b1.push(tn.childNodes[x].cloneNode(true));
}
tn.style.display="none";
document.body.removeChild(tn);
return _2b1;
};
if(!dojo.evalObjPath("dojo.dom.createNodesFromText")){
dojo.dom.createNodesFromText=function(){
dojo.deprecated("dojo.dom.createNodesFromText","use dojo.html.createNodesFromText instead");
return dojo.html.createNodesFromText.apply(dojo.html,arguments);
};
}
dojo.html.getAncestorsByTag=function(node,tag,_2b5){
tag=tag.toLowerCase();
return dojo.dom.getAncestors(node,function(el){
return el.tagName&&(el.tagName.toLowerCase()==tag);
},_2b5);
};
dojo.html.getFirstAncestorByTag=function(node,tag){
return dojo.html.getAncestorsByTag(node,tag,true);
};
dojo.html.isVisible=function(node){
return dojo.style.getComputedStyle(node||this.domNode,"display")!="none";
};
dojo.html.show=function(node){
if(node.style){
node.style.display=dojo.lang.inArray(node.tagName.toLowerCase(),["tr","td","th"])?"":"block";
}
};
dojo.html.hide=function(node){
if(node.style){
node.style.display="none";
}
};
dojo.html.toCoordinateArray=function(_2bc,_2bd){
if(dojo.lang.isArray(_2bc)){
while(_2bc.length<4){
_2bc.push(0);
}
while(_2bc.length>4){
_2bc.pop();
}
var ret=_2bc;
}else{
var node=dojo.byId(_2bc);
var ret=[dojo.html.getAbsoluteX(node,_2bd),dojo.html.getAbsoluteY(node,_2bd),dojo.html.getInnerWidth(node),dojo.html.getInnerHeight(node)];
}
ret.x=ret[0];
ret.y=ret[1];
ret.w=ret[2];
ret.h=ret[3];
return ret;
};
dojo.html.placeOnScreen=function(node,_2c1,_2c2,_2c3,_2c4){
if(dojo.lang.isArray(_2c1)){
_2c4=_2c3;
_2c3=_2c2;
_2c2=_2c1[1];
_2c1=_2c1[0];
}
if(!isNaN(_2c3)){
_2c3=[Number(_2c3),Number(_2c3)];
}else{
if(!dojo.lang.isArray(_2c3)){
_2c3=[0,0];
}
}
var _2c5=dojo.html.getScrollOffset();
var view=dojo.html.getViewportSize();
node=dojo.byId(node);
var w=node.offsetWidth+_2c3[0];
var h=node.offsetHeight+_2c3[1];
if(_2c4){
_2c1-=_2c5.x;
_2c2-=_2c5.y;
}
var x=_2c1+w;
if(x>view.w){
x=view.w-w;
}else{
x=_2c1;
}
x=Math.max(_2c3[0],x)+_2c5.x;
var y=_2c2+h;
if(y>view.h){
y=view.h-h;
}else{
y=_2c2;
}
y=Math.max(_2c3[1],y)+_2c5.y;
node.style.left=x+"px";
node.style.top=y+"px";
var ret=[x,y];
ret.x=x;
ret.y=y;
return ret;
};
dojo.html.placeOnScreenPoint=function(node,_2cd,_2ce,_2cf,_2d0){
if(dojo.lang.isArray(_2cd)){
_2d0=_2cf;
_2cf=_2ce;
_2ce=_2cd[1];
_2cd=_2cd[0];
}
var _2d1=dojo.html.getScrollOffset();
var view=dojo.html.getViewportSize();
node=dojo.byId(node);
var w=node.offsetWidth;
var h=node.offsetHeight;
if(_2d0){
_2cd-=_2d1.x;
_2ce-=_2d1.y;
}
var x=-1,y=-1;
if(_2cd+w<=view.w&&_2ce+h<=view.h){
x=_2cd;
y=_2ce;
}
if((x<0||y<0)&&_2cd<=view.w&&_2ce+h<=view.h){
x=_2cd-w;
y=_2ce;
}
if((x<0||y<0)&&_2cd+w<=view.w&&_2ce<=view.h){
x=_2cd;
y=_2ce-h;
}
if((x<0||y<0)&&_2cd<=view.w&&_2ce<=view.h){
x=_2cd-w;
y=_2ce-h;
}
if(x<0||y<0||(x+w>view.w)||(y+h>view.h)){
return dojo.html.placeOnScreen(node,_2cd,_2ce,_2cf,_2d0);
}
x+=_2d1.x;
y+=_2d1.y;
node.style.left=x+"px";
node.style.top=y+"px";
var ret=[x,y];
ret.x=x;
ret.y=y;
return ret;
};
dojo.html.BackgroundIframe=function(){
if(this.ie){
this.iframe=document.createElement("<iframe frameborder='0' src='about:blank'>");
var s=this.iframe.style;
s.position="absolute";
s.left=s.top="0px";
s.zIndex=2;
s.display="none";
dojo.style.setOpacity(this.iframe,0);
dojo.html.body().appendChild(this.iframe);
}else{
this.enabled=false;
}
};
dojo.lang.extend(dojo.html.BackgroundIframe,{ie:dojo.render.html.ie,enabled:true,visibile:false,iframe:null,sizeNode:null,sizeCoords:null,size:function(node){
if(!this.ie||!this.enabled){
return;
}
if(dojo.dom.isNode(node)){
this.sizeNode=node;
}else{
if(arguments.length>0){
this.sizeNode=null;
this.sizeCoords=node;
}
}
this.update();
},update:function(){
if(!this.ie||!this.enabled){
return;
}
if(this.sizeNode){
this.sizeCoords=dojo.html.toCoordinateArray(this.sizeNode,true);
}else{
if(this.sizeCoords){
this.sizeCoords=dojo.html.toCoordinateArray(this.sizeCoords,true);
}else{
return;
}
}
var s=this.iframe.style;
var dims=this.sizeCoords;
s.width=dims.w+"px";
s.height=dims.h+"px";
s.left=dims.x+"px";
s.top=dims.y+"px";
},setZIndex:function(node){
if(!this.ie||!this.enabled){
return;
}
if(dojo.dom.isNode(node)){
this.iframe.zIndex=dojo.html.getStyle(node,"z-index")-1;
}else{
if(!isNaN(node)){
this.iframe.zIndex=node;
}
}
},show:function(node){
if(!this.ie||!this.enabled){
return;
}
this.size(node);
this.iframe.style.display="block";
},hide:function(){
if(!this.ie){
return;
}
var s=this.iframe.style;
s.display="none";
s.width=s.height="1px";
},remove:function(){
dojo.dom.removeNode(this.iframe);
}});
dojo.provide("dojo.math.curves");
dojo.require("dojo.math");
dojo.math.curves={Line:function(_2de,end){
this.start=_2de;
this.end=end;
this.dimensions=_2de.length;
for(var i=0;i<_2de.length;i++){
_2de[i]=Number(_2de[i]);
}
for(var i=0;i<end.length;i++){
end[i]=Number(end[i]);
}
this.getValue=function(n){
var _2e2=new Array(this.dimensions);
for(var i=0;i<this.dimensions;i++){
_2e2[i]=((this.end[i]-this.start[i])*n)+this.start[i];
}
return _2e2;
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
var _2e6=new Array(this.p[0].length);
for(var k=0;j<this.p[0].length;k++){
_2e6[k]=0;
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
_2e6[j]=C/D;
}
return _2e6;
};
this.p=pnts;
return this;
},CatmullRom:function(pnts,c){
this.getValue=function(step){
var _2f0=step*(this.p.length-1);
var node=Math.floor(_2f0);
var _2f2=_2f0-node;
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
var u=_2f2;
var u2=_2f2*_2f2;
var u3=_2f2*_2f2*_2f2;
var _2fa=new Array(this.p[0].length);
for(var k=0;k<this.p[0].length;k++){
var x1=(-this.c*this.p[i0][k])+((2-this.c)*this.p[i][k])+((this.c-2)*this.p[i1][k])+(this.c*this.p[i2][k]);
var x2=(2*this.c*this.p[i0][k])+((this.c-3)*this.p[i][k])+((3-2*this.c)*this.p[i1][k])+(-this.c*this.p[i2][k]);
var x3=(-this.c*this.p[i0][k])+(this.c*this.p[i1][k]);
var x4=this.p[i][k];
_2fa[k]=x1*u3+x2*u2+x3*u+x4;
}
return _2fa;
};
if(!c){
this.c=0.7;
}else{
this.c=c;
}
this.p=pnts;
return this;
},Arc:function(_300,end,ccw){
var _303=dojo.math.points.midpoint(_300,end);
var _304=dojo.math.points.translate(dojo.math.points.invert(_303),_300);
var rad=Math.sqrt(Math.pow(_304[0],2)+Math.pow(_304[1],2));
var _306=dojo.math.radToDeg(Math.atan(_304[1]/_304[0]));
if(_304[0]<0){
_306-=90;
}else{
_306+=90;
}
dojo.math.curves.CenteredArc.call(this,_303,rad,_306,_306+(ccw?-180:180));
},CenteredArc:function(_307,_308,_309,end){
this.center=_307;
this.radius=_308;
this.start=_309||0;
this.end=end;
this.getValue=function(n){
var _30c=new Array(2);
var _30d=dojo.math.degToRad(this.start+((this.end-this.start)*n));
_30c[0]=this.center[0]+this.radius*Math.sin(_30d);
_30c[1]=this.center[1]-this.radius*Math.cos(_30d);
return _30c;
};
return this;
},Circle:function(_30e,_30f){
dojo.math.curves.CenteredArc.call(this,_30e,_30f,0,360);
return this;
},Path:function(){
var _310=[];
var _311=[];
var _312=[];
var _313=0;
this.add=function(_314,_315){
if(_315<0){
dojo.raise("dojo.math.curves.Path.add: weight cannot be less than 0");
}
_310.push(_314);
_311.push(_315);
_313+=_315;
computeRanges();
};
this.remove=function(_316){
for(var i=0;i<_310.length;i++){
if(_310[i]==_316){
_310.splice(i,1);
_313-=_311.splice(i,1)[0];
break;
}
}
computeRanges();
};
this.removeAll=function(){
_310=[];
_311=[];
_313=0;
};
this.getValue=function(n){
var _319=false,value=0;
for(var i=0;i<_312.length;i++){
var r=_312[i];
if(n>=r[0]&&n<r[1]){
var subN=(n-r[0])/r[2];
value=_310[i].getValue(subN);
_319=true;
break;
}
}
if(!_319){
value=_310[_310.length-1].getValue(1);
}
for(j=0;j<i;j++){
value=dojo.math.points.translate(value,_310[j].getValue(1));
}
return value;
};
function computeRanges(){
var _31d=0;
for(var i=0;i<_311.length;i++){
var end=_31d+_311[i]/_313;
var len=end-_31d;
_312[i]=[_31d,end,len];
_31d=end;
}
}
return this;
}};
dojo.provide("dojo.animation");
dojo.provide("dojo.animation.Animation");
dojo.require("dojo.lang");
dojo.require("dojo.math");
dojo.require("dojo.math.curves");
dojo.animation.Animation=function(_321,_322,_323,_324,rate){
this.curve=_321;
this.duration=_322;
this.repeatCount=_324||0;
this.rate=rate||10;
if(_323){
if(dojo.lang.isFunction(_323.getValue)){
this.accel=_323;
}else{
var i=0.35*_323+0.5;
this.accel=new dojo.math.curves.CatmullRom([[0],[i],[1]],0.45);
}
}
};
dojo.lang.extend(dojo.animation.Animation,{curve:null,duration:0,repeatCount:0,accel:null,onBegin:null,onAnimate:null,onEnd:null,onPlay:null,onPause:null,onStop:null,handler:null,_animSequence:null,_startTime:null,_endTime:null,_lastFrame:null,_timer:null,_percent:0,_active:false,_paused:false,_startRepeatCount:0,play:function(_327){
if(_327){
clearTimeout(this._timer);
this._active=false;
this._paused=false;
this._percent=0;
}else{
if(this._active&&!this._paused){
return;
}
}
this._startTime=new Date().valueOf();
if(this._paused){
this._startTime-=(this.duration*this._percent/100);
}
this._endTime=this._startTime+this.duration;
this._lastFrame=this._startTime;
var e=new dojo.animation.AnimationEvent(this,null,this.curve.getValue(this._percent),this._startTime,this._startTime,this._endTime,this.duration,this._percent,0);
this._active=true;
this._paused=false;
if(this._percent==0){
if(!this._startRepeatCount){
this._startRepeatCount=this.repeatCount;
}
e.type="begin";
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onBegin=="function"){
this.onBegin(e);
}
}
e.type="play";
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onPlay=="function"){
this.onPlay(e);
}
if(this._animSequence){
this._animSequence.setCurrent(this);
}
this._cycle();
},pause:function(){
clearTimeout(this._timer);
if(!this._active){
return;
}
this._paused=true;
var e=new dojo.animation.AnimationEvent(this,"pause",this.curve.getValue(this._percent),this._startTime,new Date().valueOf(),this._endTime,this.duration,this._percent,0);
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onPause=="function"){
this.onPause(e);
}
},playPause:function(){
if(!this._active||this._paused){
this.play();
}else{
this.pause();
}
},gotoPercent:function(pct,_32b){
clearTimeout(this._timer);
this._active=true;
this._paused=true;
this._percent=pct;
if(_32b){
this.play();
}
},stop:function(_32c){
clearTimeout(this._timer);
var step=this._percent/100;
if(_32c){
step=1;
}
var e=new dojo.animation.AnimationEvent(this,"stop",this.curve.getValue(step),this._startTime,new Date().valueOf(),this._endTime,this.duration,this._percent,Math.round(fps));
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onStop=="function"){
this.onStop(e);
}
this._active=false;
this._paused=false;
},status:function(){
if(this._active){
return this._paused?"paused":"playing";
}else{
return "stopped";
}
},_cycle:function(){
clearTimeout(this._timer);
if(this._active){
var curr=new Date().valueOf();
var step=(curr-this._startTime)/(this._endTime-this._startTime);
fps=1000/(curr-this._lastFrame);
this._lastFrame=curr;
if(step>=1){
step=1;
this._percent=100;
}else{
this._percent=step*100;
}
if(this.accel&&this.accel.getValue){
step=this.accel.getValue(step);
}
var e=new dojo.animation.AnimationEvent(this,"animate",this.curve.getValue(step),this._startTime,curr,this._endTime,this.duration,this._percent,Math.round(fps));
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onAnimate=="function"){
this.onAnimate(e);
}
if(step<1){
this._timer=setTimeout(dojo.lang.hitch(this,"_cycle"),this.rate);
}else{
e.type="end";
this._active=false;
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onEnd=="function"){
this.onEnd(e);
}
if(this.repeatCount>0){
this.repeatCount--;
this.play(true);
}else{
if(this.repeatCount==-1){
this.play(true);
}else{
if(this._startRepeatCount){
this.repeatCount=this._startRepeatCount;
this._startRepeatCount=0;
}
if(this._animSequence){
this._animSequence.playNext();
}
}
}
}
}
}});
dojo.animation.AnimationEvent=function(anim,type,_334,_335,_336,_337,dur,pct,fps){
this.type=type;
this.animation=anim;
this.coords=_334;
this.x=_334[0];
this.y=_334[1];
this.z=_334[2];
this.startTime=_335;
this.currentTime=_336;
this.endTime=_337;
this.duration=dur;
this.percent=pct;
this.fps=fps;
};
dojo.lang.extend(dojo.animation.AnimationEvent,{coordsAsInts:function(){
var _33b=new Array(this.coords.length);
for(var i=0;i<this.coords.length;i++){
_33b[i]=Math.round(this.coords[i]);
}
return _33b;
}});
dojo.animation.AnimationSequence=function(_33d){
this.repeatCount=_33d||0;
};
dojo.lang.extend(dojo.animation.AnimationSequence,{repeateCount:0,_anims:[],_currAnim:-1,onBegin:null,onEnd:null,onNext:null,handler:null,add:function(){
for(var i=0;i<arguments.length;i++){
this._anims.push(arguments[i]);
arguments[i]._animSequence=this;
}
},remove:function(anim){
for(var i=0;i<this._anims.length;i++){
if(this._anims[i]==anim){
this._anims[i]._animSequence=null;
this._anims.splice(i,1);
break;
}
}
},removeAll:function(){
for(var i=0;i<this._anims.length;i++){
this._anims[i]._animSequence=null;
}
this._anims=[];
this._currAnim=-1;
},clear:function(){
this.removeAll();
},play:function(_342){
if(this._anims.length==0){
return;
}
if(_342||!this._anims[this._currAnim]){
this._currAnim=0;
}
if(this._anims[this._currAnim]){
if(this._currAnim==0){
var e={type:"begin",animation:this._anims[this._currAnim]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onBegin=="function"){
this.onBegin(e);
}
}
this._anims[this._currAnim].play(_342);
}
},pause:function(){
if(this._anims[this._currAnim]){
this._anims[this._currAnim].pause();
}
},playPause:function(){
if(this._anims.length==0){
return;
}
if(this._currAnim==-1){
this._currAnim=0;
}
if(this._anims[this._currAnim]){
this._anims[this._currAnim].playPause();
}
},stop:function(){
if(this._anims[this._currAnim]){
this._anims[this._currAnim].stop();
}
},status:function(){
if(this._anims[this._currAnim]){
return this._anims[this._currAnim].status();
}else{
return "stopped";
}
},setCurrent:function(anim){
for(var i=0;i<this._anims.length;i++){
if(this._anims[i]==anim){
this._currAnim=i;
break;
}
}
},playNext:function(){
if(this._currAnim==-1||this._anims.length==0){
return;
}
this._currAnim++;
if(this._anims[this._currAnim]){
var e={type:"next",animation:this._anims[this._currAnim]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onNext=="function"){
this.onNext(e);
}
this._anims[this._currAnim].play(true);
}else{
var e={type:"end",animation:this._anims[this._anims.length-1]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onEnd=="function"){
this.onEnd(e);
}
if(this.repeatCount>0){
this._currAnim=0;
this.repeatCount--;
this._anims[this._currAnim].play(true);
}else{
if(this.repeatCount==-1){
this._currAnim=0;
this._anims[this._currAnim].play(true);
}else{
this._currAnim=-1;
}
}
}
}});
dojo.hostenv.conditionalLoadModule({common:["dojo.animation.Animation",false,false]});
dojo.hostenv.moduleLoaded("dojo.animation.*");
dojo.require("dojo.lang");
dojo.provide("dojo.event");
dojo.event=new function(){
this.canTimeout=dojo.lang.isFunction(dj_global["setTimeout"])||dojo.lang.isAlien(dj_global["setTimeout"]);
this.nameAnonFunc=dojo.lang.nameAnonFunc;
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
this.matchSignature=function(args,_34b){
var end=Math.min(args.length,_34b.length);
for(var x=0;x<end;x++){
if(compareTypes){
if((typeof args[x]).toLowerCase()!=(typeof _34b[x])){
return false;
}
}else{
if((typeof args[x]).toLowerCase()!=_34b[x].toLowerCase()){
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
var ao={srcObj:dj_global,srcFunc:null,adviceObj:dj_global,adviceFunc:null,aroundObj:null,aroundFunc:null,adviceType:(args.length>2)?args[0]:"after",precedence:"last",once:false,delay:null,rate:0,adviceMsg:false};
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
var _352=dojo.event.nameAnonFunc(args[2],ao.adviceObj);
ao.adviceObj[_352]=args[2];
ao.adviceFunc=_352;
}else{
if((typeof args[0]=="function")&&(typeof args[1]=="object")&&(typeof args[2]=="string")){
ao.adviceType="after";
ao.srcObj=dj_global;
var _352=dojo.event.nameAnonFunc(args[0],ao.srcObj);
ao.srcObj[_352]=args[0];
ao.srcFunc=_352;
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
ao.rate=args[9];
ao.adviceMsg=args[10];
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
dojo.raise("bad srcObj for srcFunc: "+ao.srcFunc);
}
if(!ao.adviceObj){
dojo.raise("bad adviceObj for adviceFunc: "+ao.adviceFunc);
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
this.kwConnectImpl_=function(_35a,_35b){
var fn=(_35b)?"disconnect":"connect";
if(typeof _35a["srcFunc"]=="function"){
_35a.srcObj=_35a["srcObj"]||dj_global;
var _35d=dojo.event.nameAnonFunc(_35a.srcFunc,_35a.srcObj);
_35a.srcFunc=_35d;
}
if(typeof _35a["adviceFunc"]=="function"){
_35a.adviceObj=_35a["adviceObj"]||dj_global;
var _35d=dojo.event.nameAnonFunc(_35a.adviceFunc,_35a.adviceObj);
_35a.adviceFunc=_35d;
}
return dojo.event[fn]((_35a["type"]||_35a["adviceType"]||"after"),_35a["srcObj"]||dj_global,_35a["srcFunc"],_35a["adviceObj"]||_35a["targetObj"]||dj_global,_35a["adviceFunc"]||_35a["targetFunc"],_35a["aroundObj"],_35a["aroundFunc"],_35a["once"],_35a["delay"],_35a["rate"],_35a["adviceMsg"]||false);
};
this.kwConnect=function(_35e){
return this.kwConnectImpl_(_35e,false);
};
this.disconnect=function(){
var ao=interpolateArgs(arguments);
if(!ao.adviceFunc){
return;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
return mjp.removeAdvice(ao.adviceObj,ao.adviceFunc,ao.adviceType,ao.once);
};
this.kwDisconnect=function(_361){
return this.kwConnectImpl_(_361,true);
};
};
dojo.event.MethodInvocation=function(_362,obj,args){
this.jp_=_362;
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
dojo.event.MethodJoinPoint=function(obj,_36a){
this.object=obj||dj_global;
this.methodname=_36a;
this.methodfunc=this.object[_36a];
this.before=[];
this.after=[];
this.around=[];
};
dojo.event.MethodJoinPoint.getForMethod=function(obj,_36c){
if(!obj){
obj=dj_global;
}
if(!obj[_36c]){
obj[_36c]=function(){
};
}else{
if((!dojo.lang.isFunction(obj[_36c]))&&(!dojo.lang.isAlien(obj[_36c]))){
return null;
}
}
var _36d=_36c+"$joinpoint";
var _36e=_36c+"$joinpoint$method";
var _36f=obj[_36d];
if(!_36f){
var _370=false;
if(dojo.event["browser"]){
if((obj["attachEvent"])||(obj["nodeType"])||(obj["addEventListener"])){
_370=true;
dojo.event.browser.addClobberNodeAttrs(obj,[_36d,_36e,_36c]);
}
}
obj[_36e]=obj[_36c];
_36f=obj[_36d]=new dojo.event.MethodJoinPoint(obj,_36e);
obj[_36c]=function(){
var args=[];
if((_370)&&(!arguments.length)&&(window.event)){
args.push(dojo.event.browser.fixEvent(window.event));
}else{
for(var x=0;x<arguments.length;x++){
if((x==0)&&(_370)&&(dojo.event.browser.isEvent(arguments[x]))){
args.push(dojo.event.browser.fixEvent(arguments[x]));
}else{
args.push(arguments[x]);
}
}
}
return _36f.run.apply(_36f,args);
};
}
return _36f;
};
dojo.event.MethodJoinPoint.prototype.unintercept=function(){
this.object[this.methodname]=this.methodfunc;
};
dojo.event.MethodJoinPoint.prototype.run=function(){
var obj=this.object||dj_global;
var args=arguments;
var _375=[];
for(var x=0;x<args.length;x++){
_375[x]=args[x];
}
var _377=function(marr){
if(!marr){
dojo.debug("Null argument to unrollAdvice()");
return;
}
var _379=marr[0]||dj_global;
var _37a=marr[1];
if(!_379[_37a]){
throw new Error("function \""+_37a+"\" does not exist on \""+_379+"\"");
}
var _37b=marr[2]||dj_global;
var _37c=marr[3];
var msg=marr[6];
var _37e;
var to={args:[],jp_:this,object:obj,proceed:function(){
return _379[_37a].apply(_379,to.args);
}};
to.args=_375;
var _380=parseInt(marr[4]);
var _381=((!isNaN(_380))&&(marr[4]!==null)&&(typeof marr[4]!="undefined"));
if(marr[5]){
var rate=parseInt(marr[5]);
var cur=new Date();
var _384=false;
if((marr["last"])&&((cur-marr.last)<=rate)){
if(dojo.event.canTimeout){
if(marr["delayTimer"]){
clearTimeout(marr.delayTimer);
}
var tod=parseInt(rate*2);
var mcpy=dojo.lang.shallowCopy(marr);
marr.delayTimer=setTimeout(function(){
mcpy[5]=0;
_377(mcpy);
},tod);
}
return;
}else{
marr.last=cur;
}
}
if(_37c){
_37b[_37c].call(_37b,to);
}else{
if((_381)&&((dojo.render.html)||(dojo.render.svg))){
dj_global["setTimeout"](function(){
if(msg){
_379[_37a].call(_379,to);
}else{
_379[_37a].apply(_379,args);
}
},_380);
}else{
if(msg){
_379[_37a].call(_379,to);
}else{
_379[_37a].apply(_379,args);
}
}
}
};
if(this.before.length>0){
dojo.lang.forEach(this.before,_377,true);
}
var _387;
if(this.around.length>0){
var mi=new dojo.event.MethodInvocation(this,obj,args);
_387=mi.proceed();
}else{
if(this.methodfunc){
_387=this.object[this.methodname].apply(this.object,args);
}
}
if(this.after.length>0){
dojo.lang.forEach(this.after,_377,true);
}
return (this.methodfunc)?_387:null;
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
this.addAdvice(args["adviceObj"],args["adviceFunc"],args["aroundObj"],args["aroundFunc"],args["adviceType"],args["precedence"],args["once"],args["delay"],args["rate"],args["adviceMsg"]);
};
dojo.event.MethodJoinPoint.prototype.addAdvice=function(_38c,_38d,_38e,_38f,_390,_391,once,_393,rate,_395){
var arr=this.getArr(_390);
if(!arr){
dojo.raise("bad this: "+this);
}
var ao=[_38c,_38d,_38e,_38f,_393,rate,_395];
if(once){
if(this.hasAdvice(_38c,_38d,_390,arr)>=0){
return;
}
}
if(_391=="first"){
arr.unshift(ao);
}else{
arr.push(ao);
}
};
dojo.event.MethodJoinPoint.prototype.hasAdvice=function(_398,_399,_39a,arr){
if(!arr){
arr=this.getArr(_39a);
}
var ind=-1;
for(var x=0;x<arr.length;x++){
if((arr[x][0]==_398)&&(arr[x][1]==_399)){
ind=x;
}
}
return ind;
};
dojo.event.MethodJoinPoint.prototype.removeAdvice=function(_39e,_39f,_3a0,once){
var arr=this.getArr(_3a0);
var ind=this.hasAdvice(_39e,_39f,_3a0,arr);
if(ind==-1){
return false;
}
while(ind!=-1){
arr.splice(ind,1);
if(once){
break;
}
ind=this.hasAdvice(_39e,_39f,_3a0,arr);
}
return true;
};
dojo.require("dojo.event");
dojo.provide("dojo.event.topic");
dojo.event.topic=new function(){
this.topics={};
this.getTopic=function(_3a4){
if(!this.topics[_3a4]){
this.topics[_3a4]=new this.TopicImpl(_3a4);
}
return this.topics[_3a4];
};
this.registerPublisher=function(_3a5,obj,_3a7){
var _3a5=this.getTopic(_3a5);
_3a5.registerPublisher(obj,_3a7);
};
this.subscribe=function(_3a8,obj,_3aa){
var _3a8=this.getTopic(_3a8);
_3a8.subscribe(obj,_3aa);
};
this.unsubscribe=function(_3ab,obj,_3ad){
var _3ab=this.getTopic(_3ab);
_3ab.unsubscribe(obj,_3ad);
};
this.publish=function(_3ae,_3af){
var _3ae=this.getTopic(_3ae);
var args=[];
if((arguments.length==2)&&(_3af.length)&&(typeof _3af!="string")){
args=_3af;
}else{
var args=[];
for(var x=1;x<arguments.length;x++){
args.push(arguments[x]);
}
}
_3ae.sendMessage.apply(_3ae,args);
};
};
dojo.event.topic.TopicImpl=function(_3b2){
this.topicName=_3b2;
var self=this;
self.subscribe=function(_3b4,_3b5){
dojo.event.connect("before",self,"sendMessage",_3b4,_3b5);
};
self.unsubscribe=function(_3b6,_3b7){
dojo.event.disconnect("before",self,"sendMessage",_3b6,_3b7);
};
self.registerPublisher=function(_3b8,_3b9){
dojo.event.connect(_3b8,_3b9,self,"sendMessage");
};
self.sendMessage=function(_3ba){
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
try{
node.removeAttribute(prop);
}
catch(e){
}
}
this.clobber=function(_3c0){
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
if(_3c0){
tna=_3c0.getElementsByTagName("*");
na=[_3c0];
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
var _3c5={};
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
var _3cb=0;
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
if(!node["__doClobber__"]){
node.__doClobber__=true;
dojo_ie_clobber.clobberNodes.push(node);
node.__clobberAttrs__=[];
}
}
};
this.addClobberNodeAttrs=function(node,_3d1){
this.addClobberNode(node);
if(djConfig.ieClobberMinimal){
for(var x=0;x<_3d1.length;x++){
node.__clobberAttrs__.push(_3d1[x]);
}
}else{
this.addClobberAttrs.apply(this,_3d1);
}
};
this.removeListener=function(node,_3d4,fp,_3d6){
if(!_3d6){
var _3d6=false;
}
_3d4=_3d4.toLowerCase();
if(_3d4.substr(0,2)=="on"){
_3d4=_3d4.substr(2);
}
if(node.removeEventListener){
node.removeEventListener(_3d4,fp,_3d6);
}
};
this.addListener=function(node,_3d8,fp,_3da,_3db){
if(!node){
return;
}
if(!_3da){
var _3da=false;
}
_3d8=_3d8.toLowerCase();
if(_3d8.substr(0,2)!="on"){
_3d8="on"+_3d8;
}
if(!_3db){
var _3dc=function(evt){
if(!evt){
evt=window.event;
}
var ret=fp(dojo.event.browser.fixEvent(evt));
if(_3da){
dojo.event.browser.stopEvent(evt);
}
return ret;
};
}else{
_3dc=fp;
}
if(node.addEventListener){
node.addEventListener(_3d8.substr(2),_3dc,_3da);
return _3dc;
}else{
if(typeof node[_3d8]=="function"){
var _3df=node[_3d8];
node[_3d8]=function(e){
_3df(e);
return _3dc(e);
};
}else{
node[_3d8]=_3dc;
}
if(dojo.render.html.ie){
this.addClobberNodeAttrs(node,[_3d8]);
}
return _3dc;
}
};
this.isEvent=function(obj){
return (typeof Event!="undefined")&&(obj.eventPhase);
};
this.currentEvent=null;
this.callListener=function(_3e2,_3e3){
if(typeof _3e2!="function"){
dojo.raise("listener not a function: "+_3e2);
}
dojo.event.browser.currentEvent.currentTarget=_3e3;
return _3e2.call(_3e3,dojo.event.browser.currentEvent);
};
this.stopPropagation=function(){
dojo.event.browser.currentEvent.cancelBubble=true;
};
this.preventDefault=function(){
dojo.event.browser.currentEvent.returnValue=false;
};
this.keys={KEY_BACKSPACE:8,KEY_TAB:9,KEY_ENTER:13,KEY_SHIFT:16,KEY_CTRL:17,KEY_ALT:18,KEY_PAUSE:19,KEY_CAPS_LOCK:20,KEY_ESCAPE:27,KEY_SPACE:32,KEY_PAGE_UP:33,KEY_PAGE_DOWN:34,KEY_END:35,KEY_HOME:36,KEY_LEFT_ARROW:37,KEY_UP_ARROW:38,KEY_RIGHT_ARROW:39,KEY_DOWN_ARROW:40,KEY_INSERT:45,KEY_DELETE:46,KEY_LEFT_WINDOW:91,KEY_RIGHT_WINDOW:92,KEY_SELECT:93,KEY_F1:112,KEY_F2:113,KEY_F3:114,KEY_F4:115,KEY_F5:116,KEY_F6:117,KEY_F7:118,KEY_F8:119,KEY_F9:120,KEY_F10:121,KEY_F11:122,KEY_F12:123,KEY_NUM_LOCK:144,KEY_SCROLL_LOCK:145};
this.revKeys=[];
for(var key in this.keys){
this.revKeys[this.keys[key]]=key;
}
this.fixEvent=function(evt){
if((!evt)&&(window["event"])){
var evt=window.event;
}
if((evt["type"])&&(evt["type"].indexOf("key")==0)){
evt.keys=this.revKeys;
for(var key in this.keys){
evt[key]=this.keys[key];
}
if((dojo.render.html.ie)&&(evt["type"]=="keypress")){
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
this.currentEvent=evt;
evt.callListener=this.callListener;
evt.stopPropagation=this.stopPropagation;
evt.preventDefault=this.preventDefault;
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
dojo.provide("dojo.fx.html");
dojo.require("dojo.html");
dojo.require("dojo.style");
dojo.require("dojo.lang");
dojo.require("dojo.animation.*");
dojo.require("dojo.event.*");
dojo.require("dojo.graphics.color");
dojo.fx.html._makeFadeable=function(node){
if(dojo.render.html.ie){
if((node.style.zoom.length==0)&&(dojo.style.getStyle(node,"zoom")=="normal")){
node.style.zoom="1";
}
if((node.style.width.length==0)&&(dojo.style.getStyle(node,"width")=="auto")){
node.style.width="auto";
}
}
};
dojo.fx.html.fadeOut=function(node,_3ea,_3eb,_3ec){
return dojo.fx.html.fade(node,_3ea,dojo.style.getOpacity(node),0,_3eb,_3ec);
};
dojo.fx.html.fadeIn=function(node,_3ee,_3ef,_3f0){
return dojo.fx.html.fade(node,_3ee,dojo.style.getOpacity(node),1,_3ef,_3f0);
};
dojo.fx.html.fadeHide=function(node,_3f2,_3f3,_3f4){
node=dojo.byId(node);
if(!_3f2){
_3f2=150;
}
return dojo.fx.html.fadeOut(node,_3f2,function(node){
node.style.display="none";
if(typeof _3f3=="function"){
_3f3(node);
}
});
};
dojo.fx.html.fadeShow=function(node,_3f7,_3f8,_3f9){
node=dojo.byId(node);
if(!_3f7){
_3f7=150;
}
node.style.display="block";
return dojo.fx.html.fade(node,_3f7,0,1,_3f8,_3f9);
};
dojo.fx.html.fade=function(node,_3fb,_3fc,_3fd,_3fe,_3ff){
node=dojo.byId(node);
dojo.fx.html._makeFadeable(node);
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([_3fc],[_3fd]),_3fb,0);
dojo.event.connect(anim,"onAnimate",function(e){
dojo.style.setOpacity(node,e.x);
});
if(_3fe){
dojo.event.connect(anim,"onEnd",function(e){
_3fe(node,anim);
});
}
if(!_3ff){
anim.play(true);
}
return anim;
};
dojo.fx.html.slideTo=function(node,_404,_405,_406,_407){
if(!dojo.lang.isNumber(_404)){
var tmp=_404;
_404=_405;
_405=tmp;
}
node=dojo.byId(node);
var top=node.offsetTop;
var left=node.offsetLeft;
var pos=dojo.style.getComputedStyle(node,"position");
if(pos=="relative"||pos=="static"){
top=parseInt(dojo.style.getComputedStyle(node,"top"))||0;
left=parseInt(dojo.style.getComputedStyle(node,"left"))||0;
}
return dojo.fx.html.slide(node,_404,[left,top],_405,_406,_407);
};
dojo.fx.html.slideBy=function(node,_40d,_40e,_40f,_410){
if(!dojo.lang.isNumber(_40d)){
var tmp=_40d;
_40d=_40e;
_40e=tmp;
}
node=dojo.byId(node);
var top=node.offsetTop;
var left=node.offsetLeft;
var pos=dojo.style.getComputedStyle(node,"position");
if(pos=="relative"||pos=="static"){
top=parseInt(dojo.style.getComputedStyle(node,"top"))||0;
left=parseInt(dojo.style.getComputedStyle(node,"left"))||0;
}
return dojo.fx.html.slideTo(node,_40d,[left+_40e[0],top+_40e[1]],_40f,_410);
};
dojo.fx.html.slide=function(node,_416,_417,_418,_419,_41a){
if(!dojo.lang.isNumber(_416)){
var tmp=_416;
_416=_418;
_418=_417;
_417=tmp;
}
node=dojo.byId(node);
if(dojo.style.getComputedStyle(node,"position")=="static"){
node.style.position="relative";
}
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_417,_418),_416,0);
dojo.event.connect(anim,"onAnimate",function(e){
with(node.style){
left=e.x+"px";
top=e.y+"px";
}
});
if(_419){
dojo.event.connect(anim,"onEnd",function(e){
_419(node,anim);
});
}
if(!_41a){
anim.play(true);
}
return anim;
};
dojo.fx.html.colorFadeIn=function(node,_420,_421,_422,_423,_424){
if(!dojo.lang.isNumber(_420)){
var tmp=_420;
_420=_421;
_421=tmp;
}
node=dojo.byId(node);
var _426=dojo.html.getBackgroundColor(node);
var bg=dojo.style.getStyle(node,"background-color").toLowerCase();
var _428=bg=="transparent"||bg=="rgba(0, 0, 0, 0)";
while(_426.length>3){
_426.pop();
}
var rgb=new dojo.graphics.color.Color(_421).toRgb();
var anim=dojo.fx.html.colorFade(node,_420,_421,_426,_423,true);
dojo.event.connect(anim,"onEnd",function(e){
if(_428){
node.style.backgroundColor="transparent";
}
});
if(_422>0){
node.style.backgroundColor="rgb("+rgb.join(",")+")";
if(!_424){
setTimeout(function(){
anim.play(true);
},_422);
}
}else{
if(!_424){
anim.play(true);
}
}
return anim;
};
dojo.fx.html.highlight=dojo.fx.html.colorFadeIn;
dojo.fx.html.colorFadeFrom=dojo.fx.html.colorFadeIn;
dojo.fx.html.colorFadeOut=function(node,_42d,_42e,_42f,_430,_431){
if(!dojo.lang.isNumber(_42d)){
var tmp=_42d;
_42d=_42e;
_42e=tmp;
}
node=dojo.byId(node);
var _433=new dojo.graphics.color.Color(dojo.html.getBackgroundColor(node)).toRgb();
var rgb=new dojo.graphics.color.Color(_42e).toRgb();
var anim=dojo.fx.html.colorFade(node,_42d,_433,rgb,_430,_42f>0||_431);
if(_42f>0){
node.style.backgroundColor="rgb("+_433.join(",")+")";
if(!_431){
setTimeout(function(){
anim.play(true);
},_42f);
}
}
return anim;
};
dojo.fx.html.unhighlight=dojo.fx.html.colorFadeOut;
dojo.fx.html.colorFadeTo=dojo.fx.html.colorFadeOut;
dojo.fx.html.colorFade=function(node,_437,_438,_439,_43a,_43b){
if(!dojo.lang.isNumber(_437)){
var tmp=_437;
_437=_439;
_439=_438;
_438=tmp;
}
node=dojo.byId(node);
var _43d=new dojo.graphics.color.Color(_438).toRgb();
var _43e=new dojo.graphics.color.Color(_439).toRgb();
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_43d,_43e),_437,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.backgroundColor="rgb("+e.coordsAsInts().join(",")+")";
});
if(_43a){
dojo.event.connect(anim,"onEnd",function(e){
_43a(node,anim);
});
}
if(!_43b){
anim.play(true);
}
return anim;
};
dojo.fx.html.wipeIn=function(node,_443,_444,_445){
node=dojo.byId(node);
var _446=dojo.html.getStyle(node,"height");
var _447=dojo.lang.inArray(node.tagName.toLowerCase(),["tr","td","th"])?"":"block";
node.style.display=_447;
var _448=node.offsetHeight;
var anim=dojo.fx.html.wipeInToHeight(node,_443,_448,function(e){
node.style.height=_446||"auto";
if(_444){
_444(node,anim);
}
},_445);
};
dojo.fx.html.wipeInToHeight=function(node,_44c,_44d,_44e,_44f){
node=dojo.byId(node);
var _450=dojo.html.getStyle(node,"overflow");
node.style.height="0px";
node.style.display="none";
if(_450=="visible"){
node.style.overflow="hidden";
}
var _451=dojo.lang.inArray(node.tagName.toLowerCase(),["tr","td","th"])?"":"block";
node.style.display=_451;
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([0],[_44d]),_44c,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.height=Math.round(e.x)+"px";
});
dojo.event.connect(anim,"onEnd",function(e){
if(_450!="visible"){
node.style.overflow=_450;
}
if(_44e){
_44e(node,anim);
}
});
if(!_44f){
anim.play(true);
}
return anim;
};
dojo.fx.html.wipeOut=function(node,_456,_457,_458){
node=dojo.byId(node);
var _459=dojo.html.getStyle(node,"overflow");
var _45a=dojo.html.getStyle(node,"height");
var _45b=node.offsetHeight;
node.style.overflow="hidden";
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([_45b],[0]),_456,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.height=Math.round(e.x)+"px";
});
dojo.event.connect(anim,"onEnd",function(e){
node.style.display="none";
node.style.overflow=_459;
node.style.height=_45a||"auto";
if(_457){
_457(node,anim);
}
});
if(!_458){
anim.play(true);
}
return anim;
};
dojo.fx.html.explode=function(_45f,_460,_461,_462,_463){
var _464=dojo.html.toCoordinateArray(_45f);
var _465=document.createElement("div");
with(_465.style){
position="absolute";
border="1px solid black";
display="none";
}
dojo.html.body().appendChild(_465);
_460=dojo.byId(_460);
with(_460.style){
visibility="hidden";
display="block";
}
var _466=dojo.html.toCoordinateArray(_460);
with(_460.style){
display="none";
visibility="visible";
}
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_464,_466),_461,0);
dojo.event.connect(anim,"onBegin",function(e){
_465.style.display="block";
});
dojo.event.connect(anim,"onAnimate",function(e){
with(_465.style){
left=e.x+"px";
top=e.y+"px";
width=e.coords[2]+"px";
height=e.coords[3]+"px";
}
});
dojo.event.connect(anim,"onEnd",function(){
_460.style.display="block";
_465.parentNode.removeChild(_465);
if(_462){
_462(_460,anim);
}
});
if(!_463){
anim.play();
}
return anim;
};
dojo.fx.html.implode=function(_46a,end,_46c,_46d,_46e){
var _46f=dojo.html.toCoordinateArray(_46a);
var _470=dojo.html.toCoordinateArray(end);
_46a=dojo.byId(_46a);
var _471=document.createElement("div");
with(_471.style){
position="absolute";
border="1px solid black";
display="none";
}
dojo.html.body().appendChild(_471);
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_46f,_470),_46c,0);
dojo.event.connect(anim,"onBegin",function(e){
_46a.style.display="none";
_471.style.display="block";
});
dojo.event.connect(anim,"onAnimate",function(e){
with(_471.style){
left=e.x+"px";
top=e.y+"px";
width=e.coords[2]+"px";
height=e.coords[3]+"px";
}
});
dojo.event.connect(anim,"onEnd",function(){
_471.parentNode.removeChild(_471);
if(_46d){
_46d(_46a,anim);
}
});
if(!_46e){
anim.play();
}
return anim;
};
dojo.fx.html.Exploder=function(_475,_476){
_475=dojo.byId(_475);
_476=dojo.byId(_476);
var _477=this;
this.waitToHide=500;
this.timeToShow=100;
this.waitToShow=200;
this.timeToHide=70;
this.autoShow=false;
this.autoHide=false;
var _478=null;
var _479=null;
var _47a=null;
var _47b=null;
var _47c=null;
var _47d=null;
this.showing=false;
this.onBeforeExplode=null;
this.onAfterExplode=null;
this.onBeforeImplode=null;
this.onAfterImplode=null;
this.onExploding=null;
this.onImploding=null;
this.timeShow=function(){
clearTimeout(_47a);
_47a=setTimeout(_477.show,_477.waitToShow);
};
this.show=function(){
clearTimeout(_47a);
clearTimeout(_47b);
if((_479&&_479.status()=="playing")||(_478&&_478.status()=="playing")||_477.showing){
return;
}
if(typeof _477.onBeforeExplode=="function"){
_477.onBeforeExplode(_475,_476);
}
_478=dojo.fx.html.explode(_475,_476,_477.timeToShow,function(e){
_477.showing=true;
if(typeof _477.onAfterExplode=="function"){
_477.onAfterExplode(_475,_476);
}
});
if(typeof _477.onExploding=="function"){
dojo.event.connect(_478,"onAnimate",this,"onExploding");
}
};
this.timeHide=function(){
clearTimeout(_47a);
clearTimeout(_47b);
if(_477.showing){
_47b=setTimeout(_477.hide,_477.waitToHide);
}
};
this.hide=function(){
clearTimeout(_47a);
clearTimeout(_47b);
if(_478&&_478.status()=="playing"){
return;
}
_477.showing=false;
if(typeof _477.onBeforeImplode=="function"){
_477.onBeforeImplode(_475,_476);
}
_479=dojo.fx.html.implode(_476,_475,_477.timeToHide,function(e){
if(typeof _477.onAfterImplode=="function"){
_477.onAfterImplode(_475,_476);
}
});
if(typeof _477.onImploding=="function"){
dojo.event.connect(_479,"onAnimate",this,"onImploding");
}
};
dojo.event.connect(_475,"onclick",function(e){
if(_477.showing){
_477.hide();
}else{
_477.show();
}
});
dojo.event.connect(_475,"onmouseover",function(e){
if(_477.autoShow){
_477.timeShow();
}
});
dojo.event.connect(_475,"onmouseout",function(e){
if(_477.autoHide){
_477.timeHide();
}
});
dojo.event.connect(_476,"onmouseover",function(e){
clearTimeout(_47b);
});
dojo.event.connect(_476,"onmouseout",function(e){
if(_477.autoHide){
_477.timeHide();
}
});
dojo.event.connect(document.documentElement||dojo.html.body(),"onclick",function(e){
if(_477.autoHide&&_477.showing&&!dojo.dom.isDescendantOf(e.target,_476)&&!dojo.dom.isDescendantOf(e.target,_475)){
_477.hide();
}
});
return this;
};
dojo.lang.mixin(dojo.fx,dojo.fx.html);
dojo.hostenv.conditionalLoadModule({browser:["dojo.fx.html"]});
dojo.hostenv.moduleLoaded("dojo.fx.*");
dojo.provide("dojo.logging.Logger");
dojo.provide("dojo.log");
dojo.require("dojo.lang");
dojo.logging.Record=function(lvl,msg){
this.level=lvl;
this.message=msg;
this.time=new Date();
};
dojo.logging.LogFilter=function(_488){
this.passChain=_488||"";
this.filter=function(_489){
return true;
};
};
dojo.logging.Logger=function(){
this.cutOffLevel=0;
this.propagate=true;
this.parent=null;
this.data=[];
this.filters=[];
this.handlers=[];
};
dojo.lang.extend(dojo.logging.Logger,{argsToArr:function(args){
var ret=[];
for(var x=0;x<args.length;x++){
ret.push(args[x]);
}
return ret;
},setLevel:function(lvl){
this.cutOffLevel=parseInt(lvl);
},isEnabledFor:function(lvl){
return parseInt(lvl)>=this.cutOffLevel;
},getEffectiveLevel:function(){
if((this.cutOffLevel==0)&&(this.parent)){
return this.parent.getEffectiveLevel();
}
return this.cutOffLevel;
},addFilter:function(flt){
this.filters.push(flt);
return this.filters.length-1;
},removeFilterByIndex:function(_490){
if(this.filters[_490]){
delete this.filters[_490];
return true;
}
return false;
},removeFilter:function(_491){
for(var x=0;x<this.filters.length;x++){
if(this.filters[x]===_491){
delete this.filters[x];
return true;
}
}
return false;
},removeAllFilters:function(){
this.filters=[];
},filter:function(rec){
for(var x=0;x<this.filters.length;x++){
if((this.filters[x]["filter"])&&(!this.filters[x].filter(rec))||(rec.level<this.cutOffLevel)){
return false;
}
}
return true;
},addHandler:function(hdlr){
this.handlers.push(hdlr);
return this.handlers.length-1;
},handle:function(rec){
if((!this.filter(rec))||(rec.level<this.cutOffLevel)){
return false;
}
for(var x=0;x<this.handlers.length;x++){
if(this.handlers[x]["handle"]){
this.handlers[x].handle(rec);
}
}
return true;
},log:function(lvl,msg){
if((this.propagate)&&(this.parent)&&(this.parent.rec.level>=this.cutOffLevel)){
this.parent.log(lvl,msg);
return false;
}
this.handle(new dojo.logging.Record(lvl,msg));
return true;
},debug:function(msg){
return this.logType("DEBUG",this.argsToArr(arguments));
},info:function(msg){
return this.logType("INFO",this.argsToArr(arguments));
},warning:function(msg){
return this.logType("WARNING",this.argsToArr(arguments));
},error:function(msg){
return this.logType("ERROR",this.argsToArr(arguments));
},critical:function(msg){
return this.logType("CRITICAL",this.argsToArr(arguments));
},exception:function(msg,e,_4a1){
if(e){
var _4a2=[e.name,(e.description||e.message)];
if(e.fileName){
_4a2.push(e.fileName);
_4a2.push("line "+e.lineNumber);
}
msg+=" "+_4a2.join(" : ");
}
this.logType("ERROR",msg);
if(!_4a1){
throw e;
}
},logType:function(type,args){
var na=[dojo.logging.log.getLevel(type)];
if(typeof args=="array"){
na=na.concat(args);
}else{
if((typeof args=="object")&&(args["length"])){
na=na.concat(this.argsToArr(args));
}else{
na=na.concat(this.argsToArr(arguments).slice(1));
}
}
return this.log.apply(this,na);
}});
void (function(){
var _4a6=dojo.logging.Logger.prototype;
_4a6.warn=_4a6.warning;
_4a6.err=_4a6.error;
_4a6.crit=_4a6.critical;
})();
dojo.logging.LogHandler=function(_4a7){
this.cutOffLevel=(_4a7)?_4a7:0;
this.formatter=null;
this.data=[];
this.filters=[];
};
dojo.logging.LogHandler.prototype.setFormatter=function(fmtr){
dj_unimplemented("setFormatter");
};
dojo.logging.LogHandler.prototype.flush=function(){
dj_unimplemented("flush");
};
dojo.logging.LogHandler.prototype.close=function(){
dj_unimplemented("close");
};
dojo.logging.LogHandler.prototype.handleError=function(){
dj_unimplemented("handleError");
};
dojo.logging.LogHandler.prototype.handle=function(_4a9){
if((this.filter(_4a9))&&(_4a9.level>=this.cutOffLevel)){
this.emit(_4a9);
}
};
dojo.logging.LogHandler.prototype.emit=function(_4aa){
dj_unimplemented("emit");
};
void (function(){
var _4ab=["setLevel","addFilter","removeFilterByIndex","removeFilter","removeAllFilters","filter"];
var tgt=dojo.logging.LogHandler.prototype;
var src=dojo.logging.Logger.prototype;
for(var x=0;x<_4ab.length;x++){
tgt[_4ab[x]]=src[_4ab[x]];
}
})();
dojo.logging.log=new dojo.logging.Logger();
dojo.logging.log.levels=[{"name":"DEBUG","level":1},{"name":"INFO","level":2},{"name":"WARNING","level":3},{"name":"ERROR","level":4},{"name":"CRITICAL","level":5}];
dojo.logging.log.loggers={};
dojo.logging.log.getLogger=function(name){
if(!this.loggers[name]){
this.loggers[name]=new dojo.logging.Logger();
this.loggers[name].parent=this;
}
return this.loggers[name];
};
dojo.logging.log.getLevelName=function(lvl){
for(var x=0;x<this.levels.length;x++){
if(this.levels[x].level==lvl){
return this.levels[x].name;
}
}
return null;
};
dojo.logging.log.addLevelName=function(name,lvl){
if(this.getLevelName(name)){
this.err("could not add log level "+name+" because a level with that name already exists");
return false;
}
this.levels.append({"name":name,"level":parseInt(lvl)});
return true;
};
dojo.logging.log.getLevel=function(name){
for(var x=0;x<this.levels.length;x++){
if(this.levels[x].name.toUpperCase()==name.toUpperCase()){
return this.levels[x].level;
}
}
return null;
};
dojo.logging.MemoryLogHandler=function(_4b6,_4b7,_4b8,_4b9){
dojo.logging.LogHandler.call(this,_4b6);
this.numRecords=(typeof djConfig["loggingNumRecords"]!="undefined")?djConfig["loggingNumRecords"]:((_4b7)?_4b7:-1);
this.postType=(typeof djConfig["loggingPostType"]!="undefined")?djConfig["loggingPostType"]:(_4b8||-1);
this.postInterval=(typeof djConfig["loggingPostInterval"]!="undefined")?djConfig["loggingPostInterval"]:(_4b8||-1);
};
dojo.logging.MemoryLogHandler.prototype=new dojo.logging.LogHandler();
dojo.logging.MemoryLogHandler.prototype.emit=function(_4ba){
this.data.push(_4ba);
if(this.numRecords!=-1){
while(this.data.length>this.numRecords){
this.data.shift();
}
}
};
dojo.logging.logQueueHandler=new dojo.logging.MemoryLogHandler(0,50,0,10000);
dojo.logging.logQueueHandler.emit=function(_4bb){
var _4bc=String(dojo.log.getLevelName(_4bb.level)+": "+_4bb.time.toLocaleTimeString())+": "+_4bb.message;
if(!dj_undef("debug",dj_global)){
dojo.debug(_4bc);
}else{
if((typeof dj_global["print"]=="function")&&(!dojo.render.html.capable)){
print(_4bc);
}
}
this.data.push(_4bb);
if(this.numRecords!=-1){
while(this.data.length>this.numRecords){
this.data.shift();
}
}
};
dojo.logging.log.addHandler(dojo.logging.logQueueHandler);
dojo.log=dojo.logging.log;
dojo.hostenv.conditionalLoadModule({common:["dojo.logging.Logger",false,false],rhino:["dojo.logging.RhinoLogger"]});
dojo.hostenv.moduleLoaded("dojo.logging.*");
dojo.provide("dojo.io.IO");
dojo.require("dojo.string");
dojo.io.transports=[];
dojo.io.hdlrFuncNames=["load","error"];
dojo.io.Request=function(url,_4be,_4bf,_4c0){
if((arguments.length==1)&&(arguments[0].constructor==Object)){
this.fromKwArgs(arguments[0]);
}else{
this.url=url;
if(_4be){
this.mimetype=_4be;
}
if(_4bf){
this.transport=_4bf;
}
if(arguments.length>=4){
this.changeUrl=_4c0;
}
}
};
dojo.lang.extend(dojo.io.Request,{url:"",mimetype:"text/plain",method:"GET",content:undefined,transport:undefined,changeUrl:undefined,formNode:undefined,sync:false,bindSuccess:false,useCache:false,preventCache:false,load:function(type,data,evt){
},error:function(type,_4c5){
},handle:function(){
},abort:function(){
},fromKwArgs:function(_4c6){
if(_4c6["url"]){
_4c6.url=_4c6.url.toString();
}
if(!_4c6["method"]&&_4c6["formNode"]&&_4c6["formNode"].method){
_4c6.method=_4c6["formNode"].method;
}
if(!_4c6["handle"]&&_4c6["handler"]){
_4c6.handle=_4c6.handler;
}
if(!_4c6["load"]&&_4c6["loaded"]){
_4c6.load=_4c6.loaded;
}
if(!_4c6["changeUrl"]&&_4c6["changeURL"]){
_4c6.changeUrl=_4c6.changeURL;
}
if(!_4c6["encoding"]){
if(!dojo.lang.isUndefined(djConfig["bindEncoding"])){
_4c6.encoding=djConfig.bindEncoding;
}else{
_4c6.encoding="";
}
}
var _4c7=dojo.lang.isFunction;
for(var x=0;x<dojo.io.hdlrFuncNames.length;x++){
var fn=dojo.io.hdlrFuncNames[x];
if(_4c7(_4c6[fn])){
continue;
}
if(_4c7(_4c6["handle"])){
_4c6[fn]=_4c6.handle;
}
}
dojo.lang.mixin(this,_4c6);
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
dojo.io.bind=function(_4ce){
if(!(_4ce instanceof dojo.io.Request)){
try{
_4ce=new dojo.io.Request(_4ce);
}
catch(e){
dojo.debug(e);
}
}
var _4cf="";
if(_4ce["transport"]){
_4cf=_4ce["transport"];
if(!this[_4cf]){
return _4ce;
}
}else{
for(var x=0;x<dojo.io.transports.length;x++){
var tmp=dojo.io.transports[x];
if((this[tmp])&&(this[tmp].canHandle(_4ce))){
_4cf=tmp;
}
}
if(_4cf==""){
return _4ce;
}
}
this[_4cf].bind(_4ce);
_4ce.bindSuccess=true;
return _4ce;
};
dojo.io.queueBind=function(_4d2){
if(!(_4d2 instanceof dojo.io.Request)){
try{
_4d2=new dojo.io.Request(_4d2);
}
catch(e){
dojo.debug(e);
}
}
var _4d3=_4d2.load;
_4d2.load=function(){
dojo.io._queueBindInFlight=false;
var ret=_4d3.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
var _4d5=_4d2.error;
_4d2.error=function(){
dojo.io._queueBindInFlight=false;
var ret=_4d5.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
dojo.io._bindQueue.push(_4d2);
dojo.io._dispatchNextQueueBind();
return _4d2;
};
dojo.io._dispatchNextQueueBind=function(){
if(!dojo.io._queueBindInFlight){
dojo.io._queueBindInFlight=true;
dojo.io.bind(dojo.io._bindQueue.shift());
}
};
dojo.io._bindQueue=[];
dojo.io._queueBindInFlight=false;
dojo.io.argsFromMap=function(map,_4d8){
var _4d9=new Object();
var _4da="";
var enc=/utf/i.test(_4d8||"")?encodeURIComponent:dojo.string.encodeAscii;
for(var x in map){
if(!_4d9[x]){
_4da+=enc(x)+"="+enc(map[x])+"&";
}
}
return _4da;
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
var _4de=false;
var _4df=node.getElementsByTagName("input");
dojo.lang.forEach(_4df,function(_4e0){
if(_4de){
return;
}
if(_4e0.getAttribute("type")=="file"){
_4de=true;
}
});
return _4de;
};
dojo.io.formHasFile=function(_4e1){
return dojo.io.checkChildrenForFile(_4e1);
};
dojo.io.encodeForm=function(_4e2,_4e3){
if((!_4e2)||(!_4e2.tagName)||(!_4e2.tagName.toLowerCase()=="form")){
dojo.raise("Attempted to encode a non-form element.");
}
var enc=/utf/i.test(_4e3||"")?encodeURIComponent:dojo.string.encodeAscii;
var _4e5=[];
for(var i=0;i<_4e2.elements.length;i++){
var elm=_4e2.elements[i];
if(elm.disabled||elm.tagName.toLowerCase()=="fieldset"||!elm.name){
continue;
}
var name=enc(elm.name);
var type=elm.type.toLowerCase();
if(type=="select-multiple"){
for(var j=0;j<elm.options.length;j++){
if(elm.options[j].selected){
_4e5.push(name+"="+enc(elm.options[j].value));
}
}
}else{
if(dojo.lang.inArray(type,["radio","checkbox"])){
if(elm.checked){
_4e5.push(name+"="+enc(elm.value));
}
}else{
if(!dojo.lang.inArray(type,["file","submit","reset","button"])){
_4e5.push(name+"="+enc(elm.value));
}
}
}
}
var _4eb=_4e2.getElementsByTagName("input");
for(var i=0;i<_4eb.length;i++){
var _4ec=_4eb[i];
if(_4ec.type.toLowerCase()=="image"&&_4ec.form==_4e2){
var name=enc(_4ec.name);
_4e5.push(name+"="+enc(_4ec.value));
_4e5.push(name+".x=0");
_4e5.push(name+".y=0");
}
}
return _4e5.join("&")+"&";
};
dojo.io.setIFrameSrc=function(_4ed,src,_4ef){
try{
var r=dojo.render.html;
if(!_4ef){
if(r.safari){
_4ed.location=src;
}else{
frames[_4ed.name].location=src;
}
}else{
var idoc;
if(r.ie){
idoc=_4ed.contentWindow.document;
}else{
if(r.moz){
idoc=_4ed.contentWindow;
}
}
idoc.location.replace(src);
}
}
catch(e){
dojo.debug(e);
dojo.debug("setIFrameSrc: "+e);
}
};
dojo.io.XMLHTTPTransport=new function(){
var _4f2=this;
this.initialHref=window.location.href;
this.initialHash=window.location.hash;
this.moveForward=false;
var _4f3={};
this.useCache=false;
this.preventCache=false;
this.historyStack=[];
this.forwardStack=[];
this.historyIframe=null;
this.bookmarkAnchor=null;
this.locationTimer=null;
function getCacheKey(url,_4f5,_4f6){
return url+"|"+_4f5+"|"+_4f6.toLowerCase();
}
function addToCache(url,_4f8,_4f9,http){
_4f3[getCacheKey(url,_4f8,_4f9)]=http;
}
function getFromCache(url,_4fc,_4fd){
return _4f3[getCacheKey(url,_4fc,_4fd)];
}
this.clearCache=function(){
_4f3={};
};
function doLoad(_4fe,http,url,_501,_502){
if((http.status==200)||(location.protocol=="file:"&&http.status==0)){
var ret;
if(_4fe.method.toLowerCase()=="head"){
var _504=http.getAllResponseHeaders();
ret={};
ret.toString=function(){
return _504;
};
var _505=_504.split(/[\r\n]+/g);
for(var i=0;i<_505.length;i++){
var pair=_505[i].match(/^([^:]+)\s*:\s*(.+)$/i);
if(pair){
ret[pair[1]]=pair[2];
}
}
}else{
if(_4fe.mimetype=="text/javascript"){
try{
ret=dj_eval(http.responseText);
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=null;
}
}else{
if(_4fe.mimetype=="text/json"){
try{
ret=dj_eval("("+http.responseText+")");
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=false;
}
}else{
if((_4fe.mimetype=="application/xml")||(_4fe.mimetype=="text/xml")){
ret=http.responseXML;
if(!ret||typeof ret=="string"){
ret=dojo.dom.createDocumentFromText(http.responseText);
}
}else{
ret=http.responseText;
}
}
}
}
if(_502){
addToCache(url,_501,_4fe.method,http);
}
_4fe[(typeof _4fe.load=="function")?"load":"handle"]("load",ret,http);
}else{
var _508=new dojo.io.Error("XMLHttpTransport Error: "+http.status+" "+http.statusText);
_4fe[(typeof _4fe.error=="function")?"error":"handle"]("error",_508,http);
}
}
function setHeaders(http,_50a){
if(_50a["headers"]){
for(var _50b in _50a["headers"]){
if(_50b.toLowerCase()=="content-type"&&!_50a["contentType"]){
_50a["contentType"]=_50a["headers"][_50b];
}else{
http.setRequestHeader(_50b,_50a["headers"][_50b]);
}
}
}
}
this.addToHistory=function(args){
var _50d=args["back"]||args["backButton"]||args["handle"];
var hash=null;
if(!this.historyIframe){
this.historyIframe=window.frames["djhistory"];
}
if(!this.bookmarkAnchor){
this.bookmarkAnchor=document.createElement("a");
(document.body||document.getElementsByTagName("body")[0]).appendChild(this.bookmarkAnchor);
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
var _510=_50d;
var lh=null;
var hsl=this.historyStack.length-1;
if(hsl>=0){
while(!this.historyStack[hsl]["urlHash"]){
hsl--;
}
lh=this.historyStack[hsl]["urlHash"];
}
if(lh){
_50d=function(){
if(window.location.hash!=""){
setTimeout("window.location.href = '"+lh+"';",1);
}
_510();
};
}
this.forwardStack=[];
var _513=args["forward"]||args["forwardButton"];
var tfw=function(){
if(window.location.hash!=""){
window.location.href=hash;
}
if(_513){
_513();
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
this.historyStack.push({"url":url,"callback":_50d,"kwArgs":args,"urlHash":hash});
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
this.iframeLoaded=function(evt,_517){
var isp=_517.href.split("?");
if(isp.length<2){
if(this.historyStack.length==1){
this.handleBackButton();
}
return;
}
var _519=isp[1];
if(this.moveForward){
this.moveForward=false;
return;
}
var last=this.historyStack.pop();
if(!last){
if(this.forwardStack.length>0){
var next=this.forwardStack[this.forwardStack.length-1];
if(_519==next.url.split("?")[1]){
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
this.inFlight.splice(x,1);
doLoad(tif.req,tif.http,tif.url,tif.query,tif.useCache);
if(this.inFlight.length==0){
clearInterval(this.inFlightTimer);
this.inFlightTimer=null;
}
}
}
};
var _520=dojo.hostenv.getXmlhttpObject()?true:false;
this.canHandle=function(_521){
return _520&&dojo.lang.inArray((_521["mimetype"]||"".toLowerCase()),["text/plain","text/html","application/xml","text/xml","text/javascript","text/json"])&&dojo.lang.inArray(_521["method"].toLowerCase(),["post","get","head"])&&!(_521["formNode"]&&dojo.io.formHasFile(_521["formNode"]));
};
this.multipartBoundary="45309FFF-BD65-4d50-99C9-36986896A96F";
this.bind=function(_522){
if(!_522["url"]){
if(!_522["formNode"]&&(_522["backButton"]||_522["back"]||_522["changeUrl"]||_522["watchForURL"])&&(!djConfig.preventBackButtonFix)){
this.addToHistory(_522);
return true;
}
}
var url=_522.url;
var _524="";
if(_522["formNode"]){
var ta=_522.formNode.getAttribute("action");
if((ta)&&(!_522["url"])){
url=ta;
}
var tp=_522.formNode.getAttribute("method");
if((tp)&&(!_522["method"])){
_522.method=tp;
}
_524+=dojo.io.encodeForm(_522.formNode,_522.encoding);
}
if(url.indexOf("#")>-1){
dojo.debug("Warning: dojo.io.bind: stripping hash values from url:",url);
url=url.split("#")[0];
}
if(_522["file"]){
_522.method="post";
}
if(!_522["method"]){
_522.method="get";
}
if(_522.method.toLowerCase()=="get"){
_522.multipart=false;
}else{
if(_522["file"]){
_522.multipart=true;
}else{
if(!_522["multipart"]){
_522.multipart=false;
}
}
}
if(_522["backButton"]||_522["back"]||_522["changeUrl"]){
this.addToHistory(_522);
}
do{
if(_522.postContent){
_524=_522.postContent;
break;
}
if(_522["content"]){
_524+=dojo.io.argsFromMap(_522.content,_522.encoding);
}
if(_522.method.toLowerCase()=="get"||!_522.multipart){
break;
}
var t=[];
if(_524.length){
var q=_524.split("&");
for(var i=0;i<q.length;++i){
if(q[i].length){
var p=q[i].split("=");
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+p[0]+"\"","",p[1]);
}
}
}
if(_522.file){
if(dojo.lang.isArray(_522.file)){
for(var i=0;i<_522.file.length;++i){
var o=_522.file[i];
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}else{
var o=_522.file;
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}
if(t.length){
t.push("--"+this.multipartBoundary+"--","");
_524=t.join("\r\n");
}
}while(false);
var _52c=_522["sync"]?false:true;
var _52d=_522["preventCache"]||(this.preventCache==true&&_522["preventCache"]!=false);
var _52e=_522["useCache"]==true||(this.useCache==true&&_522["useCache"]!=false);
if(!_52d&&_52e){
var _52f=getFromCache(url,_524,_522.method);
if(_52f){
doLoad(_522,_52f,url,_524,false);
return;
}
}
var http=dojo.hostenv.getXmlhttpObject();
var _531=false;
if(_52c){
this.inFlight.push({"req":_522,"http":http,"url":url,"query":_524,"useCache":_52e});
this.startWatchingInFlight();
}
if(_522.method.toLowerCase()=="post"){
http.open("POST",url,_52c);
setHeaders(http,_522);
http.setRequestHeader("Content-Type",_522.multipart?("multipart/form-data; boundary="+this.multipartBoundary):(_522.contentType||"application/x-www-form-urlencoded"));
http.send(_524);
}else{
var _532=url;
if(_524!=""){
_532+=(_532.indexOf("?")>-1?"&":"?")+_524;
}
if(_52d){
_532+=(dojo.string.endsWithAny(_532,"?","&")?"":(_532.indexOf("?")>-1?"&":"?"))+"dojo.preventCache="+new Date().valueOf();
}
http.open(_522.method.toUpperCase(),_532,_52c);
setHeaders(http,_522);
http.send(null);
}
if(!_52c){
doLoad(_522,http,url,_524,_52e);
}
_522.abort=function(){
return http.abort();
};
return;
};
dojo.io.transports.addTransport("XMLHTTPTransport");
};
dojo.provide("dojo.io.cookie");
dojo.io.cookie.setCookie=function(name,_534,days,path,_537,_538){
var _539=-1;
if(typeof days=="number"&&days>=0){
var d=new Date();
d.setTime(d.getTime()+(days*24*60*60*1000));
_539=d.toGMTString();
}
_534=escape(_534);
document.cookie=name+"="+_534+";"+(_539!=-1?" expires="+_539+";":"")+(path?"path="+path:"")+(_537?"; domain="+_537:"")+(_538?"; secure":"");
};
dojo.io.cookie.set=dojo.io.cookie.setCookie;
dojo.io.cookie.getCookie=function(name){
var idx=document.cookie.indexOf(name+"=");
if(idx==-1){
return null;
}
value=document.cookie.substring(idx+name.length+1);
var end=value.indexOf(";");
if(end==-1){
end=value.length;
}
value=value.substring(0,end);
value=unescape(value);
return value;
};
dojo.io.cookie.get=dojo.io.cookie.getCookie;
dojo.io.cookie.deleteCookie=function(name){
dojo.io.cookie.setCookie(name,"-",0);
};
dojo.io.cookie.setObjectCookie=function(name,obj,days,path,_543,_544,_545){
if(arguments.length==5){
_545=_543;
_543=null;
_544=null;
}
var _546=[],cookie,value="";
if(!_545){
cookie=dojo.io.cookie.getObjectCookie(name);
}
if(days>=0){
if(!cookie){
cookie={};
}
for(var prop in obj){
if(prop==null){
delete cookie[prop];
}else{
if(typeof obj[prop]=="string"||typeof obj[prop]=="number"){
cookie[prop]=obj[prop];
}
}
}
prop=null;
for(var prop in cookie){
_546.push(escape(prop)+"="+escape(cookie[prop]));
}
value=_546.join("&");
}
dojo.io.cookie.setCookie(name,value,days,path,_543,_544);
};
dojo.io.cookie.getObjectCookie=function(name){
var _549=null,cookie=dojo.io.cookie.getCookie(name);
if(cookie){
_549={};
var _54a=cookie.split("&");
for(var i=0;i<_54a.length;i++){
var pair=_54a[i].split("=");
var _54d=pair[1];
if(isNaN(_54d)){
_54d=unescape(pair[1]);
}
_549[unescape(pair[0])]=_54d;
}
}
return _549;
};
dojo.io.cookie.isSupported=function(){
if(typeof navigator.cookieEnabled!="boolean"){
dojo.io.cookie.setCookie("__TestingYourBrowserForCookieSupport__","CookiesAllowed",90,null);
var _54e=dojo.io.cookie.getCookie("__TestingYourBrowserForCookieSupport__");
navigator.cookieEnabled=(_54e=="CookiesAllowed");
if(navigator.cookieEnabled){
this.deleteCookie("__TestingYourBrowserForCookieSupport__");
}
}
return navigator.cookieEnabled;
};
if(!dojo.io.cookies){
dojo.io.cookies=dojo.io.cookie;
}
dojo.hostenv.conditionalLoadModule({common:["dojo.io",false,false],rhino:["dojo.io.RhinoIO",false,false],browser:[["dojo.io.BrowserIO",false,false],["dojo.io.cookie",false,false]]});
dojo.hostenv.moduleLoaded("dojo.io.*");
dojo.hostenv.conditionalLoadModule({common:["dojo.uri.Uri",false,false]});
dojo.hostenv.moduleLoaded("dojo.uri.*");
dojo.provide("dojo.io.IframeIO");
dojo.require("dojo.io.BrowserIO");
dojo.require("dojo.uri.*");
dojo.io.createIFrame=function(_54f,_550){
if(window[_54f]){
return window[_54f];
}
if(window.frames[_54f]){
return window.frames[_54f];
}
var r=dojo.render.html;
var _552=null;
var turi=dojo.uri.dojoUri("iframe_history.html?noInit=true");
var _554=((r.ie)&&(dojo.render.os.win))?"<iframe name='"+_54f+"' src='"+turi+"' onload='"+_550+"'>":"iframe";
_552=document.createElement(_554);
with(_552){
name=_54f;
setAttribute("name",_54f);
id=_54f;
}
(document.body||document.getElementsByTagName("body")[0]).appendChild(_552);
window[_54f]=_552;
with(_552.style){
position="absolute";
left=top="0px";
height=width="1px";
visibility="hidden";
}
if(!r.ie){
dojo.io.setIFrameSrc(_552,turi,true);
_552.onload=new Function(_550);
}
return _552;
};
dojo.io.iframeContentWindow=function(_555){
var win=_555.contentWindow||dojo.io.iframeContentDocument(_555).defaultView||dojo.io.iframeContentDocument(_555).__parent__||(_555.name&&document.frames[_555.name])||null;
return win;
};
dojo.io.iframeContentDocument=function(_557){
var doc=_557.contentDocument||((_557.contentWindow)&&(_557.contentWindow.document))||((_557.name)&&(document.frames[_557.name])&&(document.frames[_557.name].document))||null;
return doc;
};
dojo.io.IframeTransport=new function(){
var _559=this;
this.currentRequest=null;
this.requestQueue=[];
this.iframeName="dojoIoIframe";
this.fireNextRequest=function(){
if((this.currentRequest)||(this.requestQueue.length==0)){
return;
}
var cr=this.currentRequest=this.requestQueue.shift();
var fn=cr["formNode"];
if(fn){
if(cr["content"]){
for(var x in cr.content){
if(!fn[x]){
var tn;
if(dojo.render.html.ie){
tn=document.createElement("<input type='hidden' name='"+x+"' value='"+cr.content[x]+"'>");
fn.appendChild(tn);
}else{
tn=document.createElement("input");
fn.appendChild(tn);
tn.type="hidden";
tn.name=x;
tn.value=cr.content[x];
}
}else{
fn[x].value=cr.content[x];
}
}
}
if(cr["url"]){
fn.setAttribute("action",cr.url);
}
if(!fn.getAttribute("method")){
fn.setAttribute("method",(cr["method"])?cr["method"]:"post");
}
fn.setAttribute("target",this.iframeName);
fn.target=this.iframeName;
fn.submit();
}else{
var _55e=dojo.io.argsFromMap(this.currentRequest.content);
var _55f=(cr.url.indexOf("?")>-1?"&":"?")+_55e;
dojo.io.setIFrameSrc(this.iframe,_55f,true);
}
};
this.canHandle=function(_560){
return ((dojo.lang.inArray(_560["mimetype"],["text/plain","text/html","application/xml","text/xml","text/javascript","text/json"]))&&((_560["formNode"])&&(dojo.io.checkChildrenForFile(_560["formNode"])))&&(dojo.lang.inArray(_560["method"].toLowerCase(),["post","get"]))&&(!((_560["sync"])&&(_560["sync"]==true))));
};
this.bind=function(_561){
this.requestQueue.push(_561);
this.fireNextRequest();
return;
};
this.setUpIframe=function(){
this.iframe=dojo.io.createIFrame(this.iframeName,"dojo.io.IframeTransport.iframeOnload();");
};
this.iframeOnload=function(){
if(!_559.currentRequest){
_559.fireNextRequest();
return;
}
var ifr=_559.iframe;
var ifw=dojo.io.iframeContentWindow(ifr);
var _564;
try{
var cmt=_559.currentRequest.mimetype;
if((cmt=="text/javascript")||(cmt=="text/json")){
var cd=dojo.io.iframeContentDocument(_559.iframe);
var js=cd.getElementsByTagName("textarea")[0].value;
if(cmt=="text/json"){
js="("+js+")";
}
_564=dj_eval(js);
}else{
if((cmt=="application/xml")||(cmt=="text/xml")){
_564=dojo.io.iframeContentDocument(_559.iframe);
}else{
_564=ifw.innerHTML;
}
}
if(typeof _559.currentRequest.load=="function"){
_559.currentRequest.load("load",_564,_559.currentRequest);
}
}
catch(e){
var _568=new dojo.io.Error("IframeTransport Error");
if(typeof _559.currentRequest["error"]=="function"){
_559.currentRequest.error("error",_568,_559.currentRequest);
}
}
_559.currentRequest=null;
_559.fireNextRequest();
};
dojo.io.transports.addTransport("IframeTransport");
};
dojo.addOnLoad(function(){
dojo.io.IframeTransport.setUpIframe();
});
dojo.provide("dojo.date");
dojo.date.setIso8601=function(_569,_56a){
var _56b=_56a.split("T");
dojo.date.setIso8601Date(_569,_56b[0]);
if(_56b.length==2){
dojo.date.setIso8601Time(_569,_56b[1]);
}
return _569;
};
dojo.date.fromIso8601=function(_56c){
return dojo.date.setIso8601(new Date(0),_56c);
};
dojo.date.setIso8601Date=function(_56d,_56e){
var _56f="^([0-9]{4})((-?([0-9]{2})(-?([0-9]{2}))?)|"+"(-?([0-9]{3}))|(-?W([0-9]{2})(-?([1-7]))?))?$";
var d=_56e.match(new RegExp(_56f));
var year=d[1];
var _572=d[4];
var date=d[6];
var _574=d[8];
var week=d[10];
var _576=(d[12])?d[12]:1;
_56d.setYear(year);
if(_574){
dojo.date.setDayOfYear(_56d,Number(_574));
}else{
if(week){
_56d.setMonth(0);
_56d.setDate(1);
var gd=_56d.getDay();
var day=(gd)?gd:7;
var _579=Number(_576)+(7*Number(week));
if(day<=4){
_56d.setDate(_579+1-day);
}else{
_56d.setDate(_579+8-day);
}
}else{
if(_572){
_56d.setMonth(_572-1);
}
if(date){
_56d.setDate(date);
}
}
}
return _56d;
};
dojo.date.fromIso8601Date=function(_57a){
return dojo.date.setIso8601Date(new Date(0),_57a);
};
dojo.date.setIso8601Time=function(_57b,_57c){
var _57d="Z|(([-+])([0-9]{2})(:?([0-9]{2}))?)$";
var d=_57c.match(new RegExp(_57d));
var _57f=0;
if(d){
if(d[0]!="Z"){
_57f=(Number(d[3])*60)+Number(d[5]);
_57f*=((d[2]=="-")?1:-1);
}
_57f-=_57b.getTimezoneOffset();
_57c=_57c.substr(0,_57c.length-d[0].length);
}
var _580="^([0-9]{2})(:?([0-9]{2})(:?([0-9]{2})(.([0-9]+))?)?)?$";
var d=_57c.match(new RegExp(_580));
var _581=d[1];
var mins=Number((d[3])?d[3]:0)+_57f;
var secs=(d[5])?d[5]:0;
var ms=d[7]?(Number("0."+d[7])*1000):0;
_57b.setHours(_581);
_57b.setMinutes(mins);
_57b.setSeconds(secs);
_57b.setMilliseconds(ms);
return _57b;
};
dojo.date.fromIso8601Time=function(_585){
return dojo.date.setIso8601Time(new Date(0),_585);
};
dojo.date.setDayOfYear=function(_586,_587){
_586.setMonth(0);
_586.setDate(_587);
};
dojo.date.getDayOfYear=function(_588){
var _589=new Date(0);
_589.setMonth(_588.getMonth());
_589.setDate(_588.getDate());
return Number(_589)/86400000;
};
dojo.date.daysInMonth=function(_58a,year){
var days=[31,28,31,30,31,30,31,31,30,31,30,31];
if(_58a==1&&year){
if((!(year%4)&&(year%100))||(!(year%4)&&!(year%100)&&!(year%400))){
return 29;
}else{
return 28;
}
}else{
return days[_58a];
}
};
dojo.date.months=["January","February","March","April","May","June","July","August","September","October","November","December"];
dojo.date.shortMonths=["Jan","Feb","Mar","Apr","May","June","July","Aug","Sep","Oct","Nov","Dec"];
dojo.date.days=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
dojo.date.shortDays=["Sun","Mon","Tues","Wed","Thur","Fri","Sat"];
dojo.date.toLongDateString=function(date){
return dojo.date.months[date.getMonth()]+" "+date.getDate()+", "+date.getFullYear();
};
dojo.date.toShortDateString=function(date){
return dojo.date.shortMonths[date.getMonth()]+" "+date.getDate()+", "+date.getFullYear();
};
dojo.date.toMilitaryTimeString=function(date){
var h="00"+date.getHours();
var m="00"+date.getMinutes();
var s="00"+date.getSeconds();
return h.substr(h.length-2,2)+":"+m.substr(m.length-2,2)+":"+s.substr(s.length-2,2);
};
dojo.date.toRelativeString=function(date){
var now=new Date();
var diff=(now-date)/1000;
var end=" ago";
var _597=false;
if(diff<0){
_597=true;
end=" from now";
diff=-diff;
}
if(diff<60){
diff=Math.round(diff);
return diff+" second"+(diff==1?"":"s")+end;
}else{
if(diff<3600){
diff=Math.round(diff/60);
return diff+" minute"+(diff==1?"":"s")+end;
}else{
if(diff<3600*24&&date.getDay()==now.getDay()){
diff=Math.round(diff/3600);
return diff+" hour"+(diff==1?"":"s")+end;
}else{
if(diff<3600*24*7){
diff=Math.round(diff/(3600*24));
if(diff==1){
return _597?"Tomorrow":"Yesterday";
}else{
return diff+" days"+end;
}
}else{
return dojo.date.toShortDateString(date);
}
}
}
}
};
dojo.provide("dojo.string.Builder");
dojo.require("dojo.string");
dojo.string.Builder=function(str){
this.arrConcat=(dojo.render.html.capable&&dojo.render.html["ie"]);
var a=[];
var b=str||"";
var _59b=this.length=b.length;
if(this.arrConcat){
if(b.length>0){
a.push(b);
}
b="";
}
this.toString=this.valueOf=function(){
return (this.arrConcat)?a.join(""):b;
};
this.append=function(s){
if(this.arrConcat){
a.push(s);
}else{
b+=s;
}
_59b+=s.length;
this.length=_59b;
return this;
};
this.clear=function(){
a=[];
b="";
_59b=this.length=0;
return this;
};
this.remove=function(f,l){
var s="";
if(this.arrConcat){
b=a.join("");
}
a=[];
if(f>0){
s=b.substring(0,(f-1));
}
b=s+b.substring(f+l);
_59b=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
this.replace=function(o,n){
if(this.arrConcat){
b=a.join("");
}
a=[];
b=b.replace(o,n);
_59b=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
this.insert=function(idx,s){
if(this.arrConcat){
b=a.join("");
}
a=[];
if(idx==0){
b=s+b;
}else{
var t=b.split("");
t.splice(idx,0,s);
b=t.join("");
}
_59b=this.length=b.length;
if(this.arrConcat){
a.push(b);
b="";
}
return this;
};
};
dojo.hostenv.conditionalLoadModule({common:["dojo.string","dojo.string.Builder"]});
dojo.hostenv.moduleLoaded("dojo.string.*");
if(!this["dojo"]){
alert("\"dojo/__package__.js\" is now located at \"dojo/dojo.js\". Please update your includes accordingly");
}
dojo.provide("dojo.json");
dojo.require("dojo.lang");
dojo.json={jsonRegistry:new dojo.AdapterRegistry(),register:function(name,_5a6,wrap,_5a8){
dojo.json.jsonRegistry.register(name,_5a6,wrap,_5a8);
},evalJSON:function(){
return eval("("+arguments[0]+")");
},serialize:function(o){
var _5aa=typeof (o);
if(_5aa=="undefined"){
return "undefined";
}else{
if((_5aa=="number")||(_5aa=="boolean")){
return o+"";
}else{
if(o===null){
return "null";
}
}
}
var m=dojo.lang;
if(_5aa=="string"){
return m.reprString(o);
}
var me=arguments.callee;
var _5ad;
if(typeof (o.__json__)=="function"){
_5ad=o.__json__();
if(o!==_5ad){
return me(_5ad);
}
}
if(typeof (o.json)=="function"){
_5ad=o.json();
if(o!==_5ad){
return me(_5ad);
}
}
if(_5aa!="function"&&typeof (o.length)=="number"){
var res=[];
for(var i=0;i<o.length;i++){
var val=me(o[i]);
if(typeof (val)!="string"){
val="undefined";
}
res.push(val);
}
return "["+res.join(",")+"]";
}
try{
_5ad=dojo.json.jsonRegistry.match(o);
return me(_5ad);
}
catch(e){
dojo.debug(e);
}
if(_5aa=="function"){
return null;
}
res=[];
for(var k in o){
var _5b2;
if(typeof (k)=="number"){
_5b2="\""+k+"\"";
}else{
if(typeof (k)=="string"){
_5b2=m.reprString(k);
}else{
continue;
}
}
val=me(o[k]);
if(typeof (val)!="string"){
continue;
}
res.push(_5b2+":"+val);
}
return "{"+res.join(",")+"}";
}};
dojo.provide("dojo.rpc.JsonService");
dojo.require("dojo.io.*");
dojo.require("dojo.json");
dojo.require("dojo.lang");
dojo.rpc.JsonService=function(url){
if(url){
this.connect(url);
}
};
dojo.lang.extend(dojo.rpc.JsonService,{status:"LOADING",lastSubmissionId:0,createJsonRpcRequest:function(_5b4,_5b5,id){
dojo.debug("JsonService: Create JSON-RPC Request.");
var req={"params":_5b4,"method":_5b5,"id":id};
data=dojo.json.serialize(req);
dojo.debug("JsonService: JSON-RPC Request: "+data);
return data;
},JsonRpcCallback:function(_5b8,_5b9){
return function(type,_5bb,e){
this.error=function(e){
dojo.debug("JsonService: Error in Callback: "+e);
};
if(dojo.lang.isFunction(_5b8)){
this.results=_5b8;
}else{
dojo.raise("JsonService: First argument to JsonRpcCallback must be the resultCallbackFunction");
}
if(dojo.lang.isFunction(_5b9)){
this.error=_5b9;
}
if(_5bb.e!=null){
if(dojo.lang.isFunction(this.error)){
this.error(_5bb.error);
}else{
}
}else{
if(dojo.lang.isFunction(this.results)){
this.results(_5bb.result,_5bb.id);
}else{
dojo.debug("JsonService: Results received but no callback method was specified.");
}
}
};
},createRemoteJsonRpcMethod:function(_5be,_5bf,_5c0){
return function(){
dojo.debug("JsonService: Executing Remote Method");
if(_5c0){
var _5c1=_5c0.length;
}else{
var _5c1=0;
}
if(arguments.length<_5c1){
dojo.raise("Invalid number of parameters for remote method.");
}else{
if(arguments.length>_5c1){
if(dojo.lang.isFunction(arguments[arguments.length-1])){
if(arguments.length-1==_5c1){
var p=[];
for(var n=0;n<_5c1;n++){
p[n]=arguments[n];
}
dojo.io.bind({url:_5be,postContent:this.createJsonRpcRequest(p,_5bf,this.lastSubmissionId++),method:"POST",mimetype:"text/json",load:this.JsonRpcCallback(arguments[arguments.length-1])});
return this.lastSubmissionId-1;
}else{
dojo.raise("Too many parameters supplied for remote method.");
}
}else{
dojo.raise("More parameters than require and/or the extra parameter isn't a callback function");
}
}else{
dojo.raise("No Callback function supplied and synchronous rpc calls haven't been implemented");
}
}
};
},processJSDL:function(type,_5c5,e){
dojo.debug("JsonService: Processing returned JSDL.");
dojo.debug("JsonService: Creating "+_5c5.className+" object.");
for(var n=0;n<_5c5.methods.length;n++){
dojo.debug("JsonService: Creating Method: this."+_5c5.methods[n].name+"()");
this[_5c5.methods[n].name]=this.createRemoteJsonRpcMethod(_5c5.serviceURL,_5c5.methods[n].name,_5c5.methods[n].parameters);
}
this.status="READY";
dojo.debug("JsonService: Dojo RPC Object is ready for use.");
},viewJSDL:function(type,_5c9,e){
dojo.debug(_5c9);
},connect:function(_5cb){
dojo.debug("JsonService: Attempting to load jsdl document from "+_5cb);
dojo.io.bind({url:_5cb,mimetype:"text/json",load:dojo.lang.hitch(this,function(type,_5cd,e){
return this.processJSDL(type,_5cd,e);
})});
}});
dojo.hostenv.conditionalLoadModule({common:["dojo.rpc.JsonService",false,false]});
dojo.hostenv.moduleLoaded("dojo.rpc.*");
dojo.provide("dojo.xml.Parse");
dojo.require("dojo.dom");
dojo.xml.Parse=function(){
this.parseFragment=function(_5cf){
var _5d0={};
var _5d1=dojo.dom.getTagName(_5cf);
_5d0[_5d1]=new Array(_5cf.tagName);
var _5d2=this.parseAttributes(_5cf);
for(var attr in _5d2){
if(!_5d0[attr]){
_5d0[attr]=[];
}
_5d0[attr][_5d0[attr].length]=_5d2[attr];
}
var _5d4=_5cf.childNodes;
for(var _5d5 in _5d4){
switch(_5d4[_5d5].nodeType){
case dojo.dom.ELEMENT_NODE:
_5d0[_5d1].push(this.parseElement(_5d4[_5d5]));
break;
case dojo.dom.TEXT_NODE:
if(_5d4.length==1){
if(!_5d0[_5cf.tagName]){
_5d0[_5d1]=[];
}
_5d0[_5d1].push({value:_5d4[0].nodeValue});
}
break;
}
}
return _5d0;
};
this.parseElement=function(node,_5d7,_5d8,_5d9){
var _5da={};
var _5db=dojo.dom.getTagName(node);
_5da[_5db]=[];
if((!_5d8)||(_5db.substr(0,4).toLowerCase()=="dojo")){
var _5dc=this.parseAttributes(node);
for(var attr in _5dc){
if((!_5da[_5db][attr])||(typeof _5da[_5db][attr]!="array")){
_5da[_5db][attr]=[];
}
_5da[_5db][attr].push(_5dc[attr]);
}
_5da[_5db].nodeRef=node;
_5da.tagName=_5db;
_5da.index=_5d9||0;
}
var _5de=0;
for(var i=0;i<node.childNodes.length;i++){
var tcn=node.childNodes.item(i);
switch(tcn.nodeType){
case dojo.dom.ELEMENT_NODE:
_5de++;
var ctn=dojo.dom.getTagName(tcn);
if(!_5da[ctn]){
_5da[ctn]=[];
}
_5da[ctn].push(this.parseElement(tcn,true,_5d8,_5de));
if((tcn.childNodes.length==1)&&(tcn.childNodes.item(0).nodeType==dojo.dom.TEXT_NODE)){
_5da[ctn][_5da[ctn].length-1].value=tcn.childNodes.item(0).nodeValue;
}
break;
case dojo.dom.TEXT_NODE:
if(node.childNodes.length==1){
_5da[_5db].push({value:node.childNodes.item(0).nodeValue});
}
break;
default:
break;
}
}
return _5da;
};
this.parseAttributes=function(node){
var _5e3={};
var atts=node.attributes;
for(var i=0;i<atts.length;i++){
var _5e6=atts.item(i);
if((dojo.render.html.capable)&&(dojo.render.html.ie)){
if(!_5e6){
continue;
}
if((typeof _5e6=="object")&&(typeof _5e6.nodeValue=="undefined")||(_5e6.nodeValue==null)||(_5e6.nodeValue=="")){
continue;
}
}
var nn=(_5e6.nodeName.indexOf("dojo:")==-1)?_5e6.nodeName:_5e6.nodeName.split("dojo:")[1];
_5e3[nn]={value:_5e6.nodeValue};
}
return _5e3;
};
};
dojo.provide("dojo.xml.domUtil");
dojo.require("dojo.graphics.color");
dojo.require("dojo.dom");
dojo.require("dojo.style");
dj_deprecated("dojo.xml.domUtil is deprecated, use dojo.dom instead");
dojo.xml.domUtil=new function(){
this.nodeTypes={ELEMENT_NODE:1,ATTRIBUTE_NODE:2,TEXT_NODE:3,CDATA_SECTION_NODE:4,ENTITY_REFERENCE_NODE:5,ENTITY_NODE:6,PROCESSING_INSTRUCTION_NODE:7,COMMENT_NODE:8,DOCUMENT_NODE:9,DOCUMENT_TYPE_NODE:10,DOCUMENT_FRAGMENT_NODE:11,NOTATION_NODE:12};
this.dojoml="http://www.dojotoolkit.org/2004/dojoml";
this.idIncrement=0;
this.getTagName=function(){
return dojo.dom.getTagName.apply(dojo.dom,arguments);
};
this.getUniqueId=function(){
return dojo.dom.getUniqueId.apply(dojo.dom,arguments);
};
this.getFirstChildTag=function(){
return dojo.dom.getFirstChildElement.apply(dojo.dom,arguments);
};
this.getLastChildTag=function(){
return dojo.dom.getLastChildElement.apply(dojo.dom,arguments);
};
this.getNextSiblingTag=function(){
return dojo.dom.getNextSiblingElement.apply(dojo.dom,arguments);
};
this.getPreviousSiblingTag=function(){
return dojo.dom.getPreviousSiblingElement.apply(dojo.dom,arguments);
};
this.forEachChildTag=function(node,_5e9){
var _5ea=this.getFirstChildTag(node);
while(_5ea){
if(_5e9(_5ea)=="break"){
break;
}
_5ea=this.getNextSiblingTag(_5ea);
}
};
this.moveChildren=function(){
return dojo.dom.moveChildren.apply(dojo.dom,arguments);
};
this.copyChildren=function(){
return dojo.dom.copyChildren.apply(dojo.dom,arguments);
};
this.clearChildren=function(){
return dojo.dom.removeChildren.apply(dojo.dom,arguments);
};
this.replaceChildren=function(){
return dojo.dom.replaceChildren.apply(dojo.dom,arguments);
};
this.getStyle=function(){
return dojo.style.getStyle.apply(dojo.style,arguments);
};
this.toCamelCase=function(){
return dojo.style.toCamelCase.apply(dojo.style,arguments);
};
this.toSelectorCase=function(){
return dojo.style.toSelectorCase.apply(dojo.style,arguments);
};
this.getAncestors=function(){
return dojo.dom.getAncestors.apply(dojo.dom,arguments);
};
this.isChildOf=function(){
return dojo.dom.isDescendantOf.apply(dojo.dom,arguments);
};
this.createDocumentFromText=function(){
return dojo.dom.createDocumentFromText.apply(dojo.dom,arguments);
};
if(dojo.render.html.capable||dojo.render.svg.capable){
this.createNodesFromText=function(txt,wrap){
return dojo.dom.createNodesFromText.apply(dojo.dom,arguments);
};
}
this.extractRGB=function(_5ed){
return dojo.graphics.color.extractRGB(_5ed);
};
this.hex2rgb=function(hex){
return dojo.graphics.color.hex2rgb(hex);
};
this.rgb2hex=function(r,g,b){
return dojo.graphics.color.rgb2hex(r,g,b);
};
this.insertBefore=function(){
return dojo.dom.insertBefore.apply(dojo.dom,arguments);
};
this.before=this.insertBefore;
this.insertAfter=function(){
return dojo.dom.insertAfter.apply(dojo.dom,arguments);
};
this.after=this.insertAfter;
this.insert=function(){
return dojo.dom.insertAtPosition.apply(dojo.dom,arguments);
};
this.insertAtIndex=function(){
return dojo.dom.insertAtIndex.apply(dojo.dom,arguments);
};
this.textContent=function(){
return dojo.dom.textContent.apply(dojo.dom,arguments);
};
this.renderedTextContent=function(){
return dojo.dom.renderedTextContent.apply(dojo.dom,arguments);
};
this.remove=function(node){
return dojo.dom.removeNode.apply(dojo.dom,arguments);
};
};
dojo.provide("dojo.xml.htmlUtil");
dojo.require("dojo.html");
dojo.require("dojo.style");
dojo.require("dojo.dom");
dj_deprecated("dojo.xml.htmlUtil is deprecated, use dojo.html instead");
dojo.xml.htmlUtil=new function(){
this.styleSheet=dojo.style.styleSheet;
this._clobberSelection=function(){
return dojo.html.clearSelection.apply(dojo.html,arguments);
};
this.disableSelect=function(){
return dojo.html.disableSelection.apply(dojo.html,arguments);
};
this.enableSelect=function(){
return dojo.html.enableSelection.apply(dojo.html,arguments);
};
this.getInnerWidth=function(){
return dojo.style.getInnerWidth.apply(dojo.style,arguments);
};
this.getOuterWidth=function(node){
dj_unimplemented("dojo.xml.htmlUtil.getOuterWidth");
};
this.getInnerHeight=function(){
return dojo.style.getInnerHeight.apply(dojo.style,arguments);
};
this.getOuterHeight=function(node){
dj_unimplemented("dojo.xml.htmlUtil.getOuterHeight");
};
this.getTotalOffset=function(){
return dojo.style.getTotalOffset.apply(dojo.style,arguments);
};
this.totalOffsetLeft=function(){
return dojo.style.totalOffsetLeft.apply(dojo.style,arguments);
};
this.getAbsoluteX=this.totalOffsetLeft;
this.totalOffsetTop=function(){
return dojo.style.totalOffsetTop.apply(dojo.style,arguments);
};
this.getAbsoluteY=this.totalOffsetTop;
this.getEventTarget=function(){
return dojo.html.getEventTarget.apply(dojo.html,arguments);
};
this.getScrollTop=function(){
return dojo.html.getScrollTop.apply(dojo.html,arguments);
};
this.getScrollLeft=function(){
return dojo.html.getScrollLeft.apply(dojo.html,arguments);
};
this.evtTgt=this.getEventTarget;
this.getParentOfType=function(){
return dojo.html.getParentOfType.apply(dojo.html,arguments);
};
this.getAttribute=function(){
return dojo.html.getAttribute.apply(dojo.html,arguments);
};
this.getAttr=function(node,attr){
dj_deprecated("dojo.xml.htmlUtil.getAttr is deprecated, use dojo.xml.htmlUtil.getAttribute instead");
return dojo.xml.htmlUtil.getAttribute(node,attr);
};
this.hasAttribute=function(){
return dojo.html.hasAttribute.apply(dojo.html,arguments);
};
this.hasAttr=function(node,attr){
dj_deprecated("dojo.xml.htmlUtil.hasAttr is deprecated, use dojo.xml.htmlUtil.hasAttribute instead");
return dojo.xml.htmlUtil.hasAttribute(node,attr);
};
this.getClass=function(){
return dojo.html.getClass.apply(dojo.html,arguments);
};
this.hasClass=function(){
return dojo.html.hasClass.apply(dojo.html,arguments);
};
this.prependClass=function(){
return dojo.html.prependClass.apply(dojo.html,arguments);
};
this.addClass=function(){
return dojo.html.addClass.apply(dojo.html,arguments);
};
this.setClass=function(){
return dojo.html.setClass.apply(dojo.html,arguments);
};
this.removeClass=function(){
return dojo.html.removeClass.apply(dojo.html,arguments);
};
this.classMatchType={ContainsAll:0,ContainsAny:1,IsOnly:2};
this.getElementsByClass=function(){
return dojo.html.getElementsByClass.apply(dojo.html,arguments);
};
this.getElementsByClassName=this.getElementsByClass;
this.setOpacity=function(){
return dojo.style.setOpacity.apply(dojo.style,arguments);
};
this.getOpacity=function(){
return dojo.style.getOpacity.apply(dojo.style,arguments);
};
this.clearOpacity=function(){
return dojo.style.clearOpacity.apply(dojo.style,arguments);
};
this.gravity=function(){
return dojo.html.gravity.apply(dojo.html,arguments);
};
this.gravity.NORTH=1;
this.gravity.SOUTH=1<<1;
this.gravity.EAST=1<<2;
this.gravity.WEST=1<<3;
this.overElement=function(){
return dojo.html.overElement.apply(dojo.html,arguments);
};
this.insertCssRule=function(){
return dojo.style.insertCssRule.apply(dojo.style,arguments);
};
this.insertCSSRule=function(_5f9,_5fa,_5fb){
dj_deprecated("dojo.xml.htmlUtil.insertCSSRule is deprecated, use dojo.xml.htmlUtil.insertCssRule instead");
return dojo.xml.htmlUtil.insertCssRule(_5f9,_5fa,_5fb);
};
this.removeCssRule=function(){
return dojo.style.removeCssRule.apply(dojo.style,arguments);
};
this.removeCSSRule=function(_5fc){
dj_deprecated("dojo.xml.htmlUtil.removeCSSRule is deprecated, use dojo.xml.htmlUtil.removeCssRule instead");
return dojo.xml.htmlUtil.removeCssRule(_5fc);
};
this.insertCssFile=function(){
return dojo.style.insertCssFile.apply(dojo.style,arguments);
};
this.insertCSSFile=function(URI,doc,_5ff){
dj_deprecated("dojo.xml.htmlUtil.insertCSSFile is deprecated, use dojo.xml.htmlUtil.insertCssFile instead");
return dojo.xml.htmlUtil.insertCssFile(URI,doc,_5ff);
};
this.getBackgroundColor=function(){
return dojo.style.getBackgroundColor.apply(dojo.style,arguments);
};
this.getUniqueId=function(){
return dojo.dom.getUniqueId();
};
this.getStyle=function(){
return dojo.style.getStyle.apply(dojo.style,arguments);
};
};
dojo.require("dojo.xml.Parse");
dojo.hostenv.conditionalLoadModule({common:["dojo.xml.domUtil"],browser:["dojo.xml.htmlUtil"],svg:["dojo.xml.svgUtil"]});
dojo.hostenv.moduleLoaded("dojo.xml.*");
dojo.hostenv.conditionalLoadModule({common:["dojo.lang"]});
dojo.hostenv.moduleLoaded("dojo.lang.*");
dojo.require("dojo.lang.*");
dojo.provide("dojo.storage");
dojo.provide("dojo.storage.StorageProvider");
dojo.storage=new function(){
this.provider=null;
this.setProvider=function(obj){
this.provider=obj;
};
this.set=function(key,_602,_603){
if(!this.provider){
return false;
}
return this.provider.set(key,_602,_603);
};
this.get=function(key,_605){
if(!this.provider){
return false;
}
return this.provider.get(key,_605);
};
this.remove=function(key,_607){
return this.provider.remove(key,_607);
};
};
dojo.storage.StorageProvider=function(){
};
dojo.lang.extend(dojo.storage.StorageProvider,{namespace:"*",initialized:false,free:function(){
dojo.unimplemented("dojo.storage.StorageProvider.free");
return 0;
},freeK:function(){
return dojo.math.round(this.free()/1024,0);
},set:function(key,_609,_60a){
dojo.unimplemented("dojo.storage.StorageProvider.set");
},get:function(key,_60c){
dojo.unimplemented("dojo.storage.StorageProvider.get");
},remove:function(key,_60e,_60f){
dojo.unimplemented("dojo.storage.StorageProvider.set");
},});
dojo.provide("dojo.storage.browser");
dojo.require("dojo.storage");
dojo.require("dojo.uri.*");
dojo.storage.browser.StorageProvider=function(){
this.initialized=false;
this.flash=null;
this.backlog=[];
};
dojo.inherits(dojo.storage.browser.StorageProvider,dojo.storage.StorageProvider);
dojo.lang.extend(dojo.storage.browser.StorageProvider,{storageOnLoad:function(){
this.initialized=true;
this.hideStore();
while(this.backlog.length){
this.set.apply(this,this.backlog.shift());
}
},unHideStore:function(){
var _610=dojo.byId("dojo-storeContainer");
with(_610.style){
position="absolute";
overflow="visible";
width="215px";
height="138px";
left="30px";
top="30px";
visiblity="visible";
zIndex="20";
border="1px solid black";
}
},hideStore:function(_611){
var _612=dojo.byId("dojo-storeContainer");
with(_612.style){
left="-300px";
top="-300px";
}
},set:function(key,_614,ns){
if(!this.initialized){
this.backlog.push([key,_614,ns]);
return "pending";
}
return this.flash.set(key,_614,ns||this.namespace);
},get:function(key,ns){
return this.flash.get(key,ns||this.namespace);
},writeStorage:function(){
var _618=dojo.uri.dojoUri("src/storage/Storage.swf").toString();
var _619=["<div id=\"dojo-storeContainer\"","style=\"position: absolute; left: -300px; top: -300px;\">"];
if(dojo.render.html.ie){
_619.push("<object");
_619.push("\tstyle=\"border: 1px solid black;\"");
_619.push("\tclassid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\"");
_619.push("\tcodebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\"");
_619.push("\twidth=\"215\" height=\"138\" id=\"dojoStorage\">");
_619.push("\t<param name=\"movie\" value=\""+_618+"\">");
_619.push("\t<param name=\"quality\" value=\"high\">");
_619.push("</object>");
}else{
_619.push("<embed src=\""+_618+"\" width=\"215\" height=\"138\" ");
_619.push("\tquality=\"high\" ");
_619.push("\tpluginspage=\"http://www.macromedia.com/go/getflashplayer\" ");
_619.push("\ttype=\"application/x-shockwave-flash\" ");
_619.push("\tname=\"dojoStorage\">");
_619.push("</embed>");
}
_619.push("</div>");
document.write(_619.join(""));
}});
dojo.storage.setProvider(new dojo.storage.browser.StorageProvider());
dojo.storage.provider.writeStorage();
dojo.addOnLoad(function(){
dojo.storage.provider.flash=(dojo.render.html.ie)?window["dojoStorage"]:document["dojoStorage"];
});
dojo.hostenv.conditionalLoadModule({common:["dojo.storage"],browser:["dojo.storage.browser"]});
dojo.hostenv.moduleLoaded("dojo.storage.*");
dojo.provide("dojo.crypto");
dojo.crypto.toBase64=function(data){
if(typeof (data)=="string"){
data=this.toByteArray(data);
}
var _61b=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9","+","/","="];
var _61c=64;
var _61d=[];
var oc=0;
var len=data.length;
for(var i=0;i<len;){
var now=data[i++]<<16;
now|=data[i++]<<8;
now|=data[i++];
_61d[oc++]=_61b[now>>>18&63];
_61d[oc++]=_61b[now>>>12&63];
_61d[oc++]=_61b[now>>>6&63];
_61d[oc++]=_61b[now&63];
}
var _622=i-len;
if(_622>0){
oc-=_622;
}
_622=Math.abs(_622);
while(_622-->0){
_61d[oc++]=_61b[_61c];
}
return _61d.join("");
};
dojo.crypto.fromBase64=function(data){
if(typeof (data)=="string"){
data=data.split("");
}
var _624={"A":0,"B":1,"C":2,"D":3,"E":4,"F":5,"G":6,"H":7,"I":8,"J":9,"K":10,"L":11,"M":12,"N":13,"O":14,"P":15,"Q":16,"R":17,"S":18,"T":19,"U":20,"V":21,"W":22,"X":23,"Y":24,"Z":25,"a":26,"b":27,"c":28,"d":29,"e":30,"f":31,"g":32,"h":33,"i":34,"j":35,"k":36,"l":37,"m":38,"n":39,"o":40,"p":41,"q":42,"r":43,"s":44,"t":45,"u":46,"v":47,"w":48,"x":49,"y":50,"z":51,"0":52,"1":53,"2":54,"3":55,"4":56,"5":57,"6":58,"7":59,"8":60,"9":61,"+":62,"/":63,"=":64};
var _625=64;
var _626=[];
var oc=0;
var len=data.length;
while(data[--len]==_624[_625]){
}
for(var i=0;i<len;){
var now=_624[data[i++]]<<18;
now|=_624[data[i++]]<<12;
now|=_624[data[i++]]<<6;
now|=_624[data[i++]];
_626[oc++]=now>>>16&255;
_626[oc++]=now>>>8&255;
_626[oc++]=now&255;
}
return _626;
};
dojo.crypto.toByteArray=function(data){
var bin=[];
for(var i=0;i<data.length;i++){
bin.push(data.charCodeAt(i));
}
return bin;
};
dojo.crypto.fromByteArray=function(data){
var s=[];
for(var i=0;i<data.length;i++){
s.push(String.fromCharCode(data[i]));
}
return s.join("");
};
dojo.crypto.toDWordArray=function(data){
var _632=8;
var bin=[];
var mask=(1<<_632)-1;
for(var i=0;i<data.length*_632;i+=_632){
bin[i>>5]|=(data.charCodeAt(i/_632)&mask)<<(i%32);
}
return bin;
};
dojo.crypto.fromDWordArray=function(data){
var _637=8;
var s=[];
var mask=(1<<_637)-1;
for(var i=0;i<data.length*32;i+=_637){
s.push(String.fromCharCode((data[i>>5]>>>(i%32))&mask));
}
while(s[s.length-1]=="\x00"){
s.pop();
}
return s.join("");
};
dojo.provide("dojo.crypto.MD5");
dojo.crypto.MD5=new function(){
var _63b=8;
var mask=(1<<_63b)-1;
function toWord(s){
var wa=[];
for(var i=0;i<s.length*_63b;i+=_63b){
wa[i>>5]|=(s.charCodeAt(i/_63b)&mask)<<(i%32);
}
return wa;
}
function toString(wa){
var s=[];
for(var i=0;i<wa.length*32;i+=_63b){
s.push(String.fromCharCode((wa[i>>5]>>>(i%32))&mask));
}
return s.join("");
}
function toHex(wa){
var h="0123456789abcdef";
var s=[];
for(var i=0;i<wa.length*4;i++){
s.push(h.charAt((wa[i>>2]>>((i%4)*8+4))&15)+h.charAt((wa[i>>2]>>((i%4)*8))&15));
}
return s.join("");
}
function toBase64(wa){
var p="=";
var tab="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var s=[];
for(var i=0;i<wa.length*4;i+=3){
var t=(((wa[i>>2]>>8*(i%4))&255)<<16)|(((wa[i+1>>2]>>8*((i+1)%4))&255)<<8)|((wa[i+2>>2]>>8*((i+2)%4))&255);
for(var j=0;j<4;j++){
if(i*8+j*6>wa.length*32){
s.push(p);
}else{
s.push(tab.charAt((t>>6*(3-j))&63));
}
}
}
return s.join("");
}
function add(x,y){
var l=(x&65535)+(y&65535);
var m=(x>>16)+(y>>16)+(l>>16);
return (m<<16)|(l&65535);
}
function R(n,c){
return (n<<c)|(n>>>(32-c));
}
function C(q,a,b,x,s,t){
return add(R(add(add(a,q),add(x,t)),s),b);
}
function FF(a,b,c,d,x,s,t){
return C((b&c)|((~b)&d),a,b,x,s,t);
}
function GG(a,b,c,d,x,s,t){
return C((b&d)|(c&(~d)),a,b,x,s,t);
}
function HH(a,b,c,d,x,s,t){
return C(b^c^d,a,b,x,s,t);
}
function II(a,b,c,d,x,s,t){
return C(c^(b|(~d)),a,b,x,s,t);
}
function core(x,len){
x[len>>5]|=128<<((len)%32);
x[(((len+64)>>>9)<<4)+14]=len;
var a=1732584193;
var b=-271733879;
var c=-1732584194;
var d=271733878;
for(var i=0;i<x.length;i+=16){
var olda=a;
var oldb=b;
var oldc=c;
var oldd=d;
a=FF(a,b,c,d,x[i+0],7,-680876936);
d=FF(d,a,b,c,x[i+1],12,-389564586);
c=FF(c,d,a,b,x[i+2],17,606105819);
b=FF(b,c,d,a,x[i+3],22,-1044525330);
a=FF(a,b,c,d,x[i+4],7,-176418897);
d=FF(d,a,b,c,x[i+5],12,1200080426);
c=FF(c,d,a,b,x[i+6],17,-1473231341);
b=FF(b,c,d,a,x[i+7],22,-45705983);
a=FF(a,b,c,d,x[i+8],7,1770035416);
d=FF(d,a,b,c,x[i+9],12,-1958414417);
c=FF(c,d,a,b,x[i+10],17,-42063);
b=FF(b,c,d,a,x[i+11],22,-1990404162);
a=FF(a,b,c,d,x[i+12],7,1804603682);
d=FF(d,a,b,c,x[i+13],12,-40341101);
c=FF(c,d,a,b,x[i+14],17,-1502002290);
b=FF(b,c,d,a,x[i+15],22,1236535329);
a=GG(a,b,c,d,x[i+1],5,-165796510);
d=GG(d,a,b,c,x[i+6],9,-1069501632);
c=GG(c,d,a,b,x[i+11],14,643717713);
b=GG(b,c,d,a,x[i+0],20,-373897302);
a=GG(a,b,c,d,x[i+5],5,-701558691);
d=GG(d,a,b,c,x[i+10],9,38016083);
c=GG(c,d,a,b,x[i+15],14,-660478335);
b=GG(b,c,d,a,x[i+4],20,-405537848);
a=GG(a,b,c,d,x[i+9],5,568446438);
d=GG(d,a,b,c,x[i+14],9,-1019803690);
c=GG(c,d,a,b,x[i+3],14,-187363961);
b=GG(b,c,d,a,x[i+8],20,1163531501);
a=GG(a,b,c,d,x[i+13],5,-1444681467);
d=GG(d,a,b,c,x[i+2],9,-51403784);
c=GG(c,d,a,b,x[i+7],14,1735328473);
b=GG(b,c,d,a,x[i+12],20,-1926607734);
a=HH(a,b,c,d,x[i+5],4,-378558);
d=HH(d,a,b,c,x[i+8],11,-2022574463);
c=HH(c,d,a,b,x[i+11],16,1839030562);
b=HH(b,c,d,a,x[i+14],23,-35309556);
a=HH(a,b,c,d,x[i+1],4,-1530992060);
d=HH(d,a,b,c,x[i+4],11,1272893353);
c=HH(c,d,a,b,x[i+7],16,-155497632);
b=HH(b,c,d,a,x[i+10],23,-1094730640);
a=HH(a,b,c,d,x[i+13],4,681279174);
d=HH(d,a,b,c,x[i+0],11,-358537222);
c=HH(c,d,a,b,x[i+3],16,-722521979);
b=HH(b,c,d,a,x[i+6],23,76029189);
a=HH(a,b,c,d,x[i+9],4,-640364487);
d=HH(d,a,b,c,x[i+12],11,-421815835);
c=HH(c,d,a,b,x[i+15],16,530742520);
b=HH(b,c,d,a,x[i+2],23,-995338651);
a=II(a,b,c,d,x[i+0],6,-198630844);
d=II(d,a,b,c,x[i+7],10,1126891415);
c=II(c,d,a,b,x[i+14],15,-1416354905);
b=II(b,c,d,a,x[i+5],21,-57434055);
a=II(a,b,c,d,x[i+12],6,1700485571);
d=II(d,a,b,c,x[i+3],10,-1894986606);
c=II(c,d,a,b,x[i+10],15,-1051523);
b=II(b,c,d,a,x[i+1],21,-2054922799);
a=II(a,b,c,d,x[i+8],6,1873313359);
d=II(d,a,b,c,x[i+15],10,-30611744);
c=II(c,d,a,b,x[i+6],15,-1560198380);
b=II(b,c,d,a,x[i+13],21,1309151649);
a=II(a,b,c,d,x[i+4],6,-145523070);
d=II(d,a,b,c,x[i+11],10,-1120210379);
c=II(c,d,a,b,x[i+2],15,718787259);
b=II(b,c,d,a,x[i+9],21,-343485551);
a=add(a,olda);
b=add(b,oldb);
c=add(c,oldc);
d=add(d,oldd);
}
return [a,b,c,d];
}
function hmac(data,key){
var wa=toWord(key);
if(wa.length>16){
wa=core(wa,key.length*_63b);
}
var l=[],r=[];
for(var i=0;i<16;i++){
l[i]=wa[i]^909522486;
r[i]=wa[i]^1549556828;
}
var h=core(l.concat(toWord(data)),512+data.length*_63b);
return core(r.concat(h),640);
}
this.outputTypes={Base64:0,Hex:1,String:2};
this.compute=function(data,_688){
var out=_688||this.outputTypes.Base64;
switch(out){
case this.outputTypes.Hex:
return toHex(core(toWord(data),data.length*_63b));
case this.outputTypes.String:
return toString(core(toWord(data),data.length*_63b));
default:
return toBase64(core(toWord(data),data.length*_63b));
}
};
this.getHMAC=function(data,key,_68c){
var out=_68c||this.outputTypes.Base64;
switch(out){
case this.outputTypes.Hex:
return toHex(hmac(data,key));
case this.outputTypes.String:
return toString(hmac(data,key));
default:
return toBase64(hmac(data,key));
}
};
}();
dojo.hostenv.conditionalLoadModule({common:["dojo.crypto","dojo.crypto.MD5"]});
dojo.hostenv.moduleLoaded("dojo.crypto.*");
dojo.provide("dojo.collections.Collections");
dojo.collections={Collections:true};
dojo.collections.DictionaryEntry=function(k,v){
this.key=k;
this.value=v;
this.valueOf=function(){
return this.value;
};
this.toString=function(){
return this.value;
};
};
dojo.collections.Iterator=function(a){
var obj=a;
var _692=0;
this.current=null;
this.atEnd=false;
this.moveNext=function(){
if(this.atEnd){
dojo.raise("dojo.collections.Iterator.moveNext: iterator is at end position.");
}
this.current=obj[_692];
if(++_692==obj.length){
this.atEnd=true;
}
};
this.reset=function(){
_692=0;
this.atEnd=false;
};
};
dojo.collections.DictionaryIterator=function(obj){
var arr=[];
for(var p in obj){
arr.push(obj[p]);
}
var _696=0;
this.current=null;
this.entry=null;
this.key=null;
this.value=null;
this.atEnd=false;
this.moveNext=function(){
if(this.atEnd){
dojo.raise("dojo.collections.Iterator.moveNext: iterator is at end position.");
}
this.entry=this.current=arr[_696];
if(this.entry){
this.key=this.entry.key;
this.value=this.entry.value;
}
if(++_696==arr.length){
this.atEnd=true;
}
};
this.reset=function(){
_696=0;
this.atEnd=false;
};
};
dojo.provide("dojo.collections.ArrayList");
dojo.require("dojo.collections.Collections");
dojo.collections.ArrayList=function(arr){
var _698=[];
if(arr){
_698=_698.concat(arr);
}
this.count=_698.length;
this.add=function(obj){
_698.push(obj);
this.count=_698.length;
};
this.addRange=function(a){
if(a.getIterator){
var e=a.getIterator();
while(!e.atEnd){
this.add(e.current);
e.moveNext();
}
this.count=_698.length;
}else{
for(var i=0;i<a.length;i++){
_698.push(a[i]);
}
this.count=_698.length;
}
};
this.clear=function(){
_698.splice(0,_698.length);
this.count=0;
};
this.clone=function(){
return new dojo.collections.ArrayList(_698);
};
this.contains=function(obj){
for(var i=0;i<_698.length;i++){
if(_698[i]==obj){
return true;
}
}
return false;
};
this.getIterator=function(){
return new dojo.collections.Iterator(_698);
};
this.indexOf=function(obj){
for(var i=0;i<_698.length;i++){
if(_698[i]==obj){
return i;
}
}
return -1;
};
this.insert=function(i,obj){
_698.splice(i,0,obj);
this.count=_698.length;
};
this.item=function(k){
return _698[k];
};
this.remove=function(obj){
var i=this.indexOf(obj);
if(i>=0){
_698.splice(i,1);
}
this.count=_698.length;
};
this.removeAt=function(i){
_698.splice(i,1);
this.count=_698.length;
};
this.reverse=function(){
_698.reverse();
};
this.sort=function(fn){
if(fn){
_698.sort(fn);
}else{
_698.sort();
}
};
this.toArray=function(){
return [].concat(_698);
};
this.toString=function(){
return _698.join(",");
};
};
dojo.provide("dojo.collections.Queue");
dojo.require("dojo.collections.Collections");
dojo.collections.Queue=function(arr){
var q=[];
if(arr){
q=q.concat(arr);
}
this.count=q.length;
this.clear=function(){
q=[];
this.count=q.length;
};
this.clone=function(){
return new dojo.collections.Queue(q);
};
this.contains=function(o){
for(var i=0;i<q.length;i++){
if(q[i]==o){
return true;
}
}
return false;
};
this.copyTo=function(arr,i){
arr.splice(i,0,q);
};
this.dequeue=function(){
var r=q.shift();
this.count=q.length;
return r;
};
this.enqueue=function(o){
this.count=q.push(o);
};
this.getIterator=function(){
return new dojo.collections.Iterator(q);
};
this.peek=function(){
return q[0];
};
this.toArray=function(){
return [].concat(q);
};
};
dojo.provide("dojo.collections.Stack");
dojo.require("dojo.collections.Collections");
dojo.collections.Stack=function(arr){
var q=[];
if(arr){
q=q.concat(arr);
}
this.count=q.length;
this.clear=function(){
q=[];
this.count=q.length;
};
this.clone=function(){
return new dojo.collections.Stack(q);
};
this.contains=function(o){
for(var i=0;i<q.length;i++){
if(q[i]==o){
return true;
}
}
return false;
};
this.copyTo=function(arr,i){
arr.splice(i,0,q);
};
this.getIterator=function(){
return new dojo.collections.Iterator(q);
};
this.peek=function(){
return q[(q.length-1)];
};
this.pop=function(){
var r=q.pop();
this.count=q.length;
return r;
};
this.push=function(o){
this.count=q.push(o);
};
this.toArray=function(){
return [].concat(q);
};
};
dojo.provide("dojo.graphics.htmlEffects");
dojo.require("dojo.fx.*");
dj_deprecated("dojo.graphics.htmlEffects is deprecated, use dojo.fx.html instead");
dojo.graphics.htmlEffects=dojo.fx.html;
dojo.hostenv.conditionalLoadModule({browser:["dojo.graphics.htmlEffects"]});
dojo.hostenv.moduleLoaded("dojo.graphics.*");
dojo.require("dojo.lang");
dojo.provide("dojo.dnd.DragSource");
dojo.provide("dojo.dnd.DropTarget");
dojo.provide("dojo.dnd.DragObject");
dojo.provide("dojo.dnd.DragManager");
dojo.provide("dojo.dnd.DragAndDrop");
dojo.dnd.DragSource=function(){
dojo.dnd.dragManager.registerDragSource(this);
};
dojo.lang.extend(dojo.dnd.DragSource,{type:"",onDragEnd:function(){
},onDragStart:function(){
},unregister:function(){
dojo.dnd.dragManager.unregisterDragSource(this);
},reregister:function(){
dojo.dnd.dragManager.registerDragSource(this);
}});
dojo.dnd.DragObject=function(){
dojo.dnd.dragManager.registerDragObject(this);
};
dojo.lang.extend(dojo.dnd.DragObject,{type:"",onDragStart:function(){
},onDragMove:function(){
},onDragOver:function(){
},onDragOut:function(){
},onDragEnd:function(){
},onDragLeave:this.onDragOut,onDragEnter:this.onDragOver,ondragout:this.onDragOut,ondragover:this.onDragOver});
dojo.dnd.DropTarget=function(){
if(this.constructor==dojo.dnd.DropTarget){
return;
}
dojo.dnd.dragManager.registerDropTarget(this);
};
dojo.lang.extend(dojo.dnd.DropTarget,{acceptedTypes:[],onDragOver:function(){
},onDragOut:function(){
},onDragMove:function(){
},onDrop:function(){
}});
dojo.dnd.DragEvent=function(){
this.dragSource=null;
this.dragObject=null;
this.target=null;
this.eventStatus="success";
};
dojo.dnd.DragManager=function(){
};
dojo.lang.extend(dojo.dnd.DragManager,{selectedSources:[],dragObjects:[],dragSources:[],registerDragSource:function(){
},dropTargets:[],registerDropTarget:function(){
},lastDragTarget:null,currentDragTarget:null,onKeyDown:function(){
},onMouseOut:function(){
},onMouseMove:function(){
},onMouseUp:function(){
}});
dojo.dnd.dragManager=null;
dojo.provide("dojo.dnd.HtmlDragManager");
dojo.require("dojo.event.*");
dojo.require("dojo.lang");
dojo.require("dojo.html");
dojo.require("dojo.style");
dojo.dnd.HtmlDragManager=function(){
};
dojo.inherits(dojo.dnd.HtmlDragManager,dojo.dnd.DragManager);
dojo.lang.extend(dojo.dnd.HtmlDragManager,{disabled:false,nestedTargets:false,mouseDownTimer:null,dsCounter:0,dsPrefix:"dojoDragSource",dropTargetDimensions:[],currentDropTarget:null,currentDropTargetPoints:null,previousDropTarget:null,selectedSources:[],dragObjects:[],currentX:null,currentY:null,lastX:null,lastY:null,mouseDownX:null,mouseDownY:null,dropAcceptable:false,registerDragSource:function(ds){
if(ds["domNode"]){
var dp=this.dsPrefix;
var _6ba=dp+"Idx_"+(this.dsCounter++);
ds.dragSourceId=_6ba;
this.dragSources[_6ba]=ds;
ds.domNode.setAttribute(dp,_6ba);
}
},unregisterDragSource:function(ds){
if(ds["domNode"]){
var dp=this.dsPrefix;
var _6bd=ds.dragSourceId;
delete ds.dragSourceId;
delete this.dragSources[_6bd];
ds.domNode.setAttribute(dp,null);
}
},registerDropTarget:function(dt){
this.dropTargets.push(dt);
},getDragSource:function(e){
var tn=e.target;
if(tn===dojo.html.body()){
return;
}
var ta=dojo.html.getAttribute(tn,this.dsPrefix);
while((!ta)&&(tn)){
tn=tn.parentNode;
if((!tn)||(tn===dojo.html.body())){
return;
}
ta=dojo.html.getAttribute(tn,this.dsPrefix);
}
return this.dragSources[ta];
},onKeyDown:function(e){
},onMouseDown:function(e){
if(this.disabled){
return;
}
switch(e.target.tagName.toLowerCase()){
case "a":
case "button":
case "textarea":
case "input":
return;
}
var ds=this.getDragSource(e);
if(!ds){
return;
}
if(!dojo.lang.inArray(this.selectedSources,ds)){
this.selectedSources.push(ds);
}
e.preventDefault();
dojo.event.connect(document,"onmousemove",this,"onMouseMove");
},onMouseUp:function(e){
var _6c6=this;
e.dragSource=this.dragSource;
if((!e.shiftKey)&&(!e.ctrlKey)){
dojo.lang.forEach(this.dragObjects,function(_6c7){
var ret=null;
if(!_6c7){
return;
}
if(_6c6.currentDropTarget){
e.dragObject=_6c7;
var ce=_6c6.currentDropTarget.domNode.childNodes;
if(ce.length>0){
e.dropTarget=ce[0];
while(e.dropTarget==_6c7.domNode){
e.dropTarget=e.dropTarget.nextSibling;
}
}else{
e.dropTarget=_6c6.currentDropTarget.domNode;
}
if(_6c6.dropAcceptable){
ret=_6c6.currentDropTarget.onDrop(e);
}else{
_6c6.currentDropTarget.onDragOut(e);
}
}
e.dragStatus=_6c6.dropAcceptable&&ret?"dropSuccess":"dropFailure";
_6c7.onDragEnd(e);
});
this.selectedSources=[];
this.dragObjects=[];
this.dragSource=null;
}
dojo.event.disconnect(document,"onmousemove",this,"onMouseMove");
this.currentDropTarget=null;
this.currentDropTargetPoints=null;
},scrollBy:function(x,y){
for(var i=0;i<this.dragObjects.length;i++){
if(this.dragObjects[i].updateDragOffset){
this.dragObjects[i].updateDragOffset();
}
}
},onMouseMove:function(e){
var _6ce=this;
if((this.selectedSources.length)&&(!this.dragObjects.length)){
if(this.selectedSources.length==1){
this.dragSource=this.selectedSources[0];
}
dojo.lang.forEach(this.selectedSources,function(_6cf){
if(!_6cf){
return;
}
var tdo=_6cf.onDragStart(e);
if(tdo){
tdo.onDragStart(e);
_6ce.dragObjects.push(tdo);
}
});
this.dropTargetDimensions=[];
dojo.lang.forEach(this.dropTargets,function(_6d1){
var tn=_6d1.domNode;
if(!tn){
return;
}
var ttx=dojo.style.getAbsoluteX(tn,true);
var tty=dojo.style.getAbsoluteY(tn,true);
_6ce.dropTargetDimensions.push([[ttx,tty],[ttx+dojo.style.getInnerWidth(tn),tty+dojo.style.getInnerHeight(tn)],_6d1]);
});
}
for(var i=0;i<this.dragObjects.length;i++){
if(this.dragObjects[i]){
this.dragObjects[i].onDragMove(e);
}
}
var dtp=this.currentDropTargetPoints;
if((!this.nestedTargets)&&(dtp)&&(this.isInsideBox(e,dtp))){
if(this.dropAcceptable){
this.currentDropTarget.onDragMove(e);
}
}else{
if(this.currentDropTarget){
this.currentDropTarget.onDragOut(e);
}
this.currentDropTarget=null;
this.currentDropTargetPoints=null;
this.dropAcceptable=false;
dojo.lang.forEach(this.dropTargetDimensions,function(_6d7){
if(((!_6ce.currentDropTarget)||(_6ce.nestedTargets))&&(_6ce.isInsideBox(e,_6d7))){
_6ce.currentDropTarget=_6d7[2];
_6ce.currentDropTargetPoints=_6d7;
if(!_6ce.nestedTargets){
return "break";
}
}
});
e.dragObjects=this.dragObjects;
if(this.currentDropTarget){
this.dropAcceptable=this.currentDropTarget.onDragOver(e);
}
}
},isInsideBox:function(e,_6d9){
if((e.clientX>_6d9[0][0])&&(e.clientX<_6d9[1][0])&&(e.clientY>_6d9[0][1])&&(e.clientY<_6d9[1][1])){
return true;
}
return false;
},onMouseOver:function(e){
},onMouseOut:function(e){
}});
dojo.dnd.dragManager=new dojo.dnd.HtmlDragManager();
(function(){
var d=document;
var dm=dojo.dnd.dragManager;
dojo.event.connect(d,"onkeydown",dm,"onKeyDown");
dojo.event.connect(d,"onmouseover",dm,"onMouseOver");
dojo.event.connect(d,"onmouseout",dm,"onMouseOut");
dojo.event.connect(d,"onmousedown",dm,"onMouseDown");
dojo.event.connect(d,"onmouseup",dm,"onMouseUp");
dojo.event.connect(window,"scrollBy",dm,"scrollBy");
})();
dojo.provide("dojo.dnd.HtmlDragAndDrop");
dojo.provide("dojo.dnd.HtmlDragSource");
dojo.provide("dojo.dnd.HtmlDropTarget");
dojo.provide("dojo.dnd.HtmlDragObject");
dojo.require("dojo.dnd.HtmlDragManager");
dojo.require("dojo.animation.*");
dojo.require("dojo.dom");
dojo.require("dojo.style");
dojo.require("dojo.html");
dojo.require("dojo.lang");
dojo.dnd.HtmlDragSource=function(node,type){
if(node){
this.domNode=node;
this.dragObject=node;
dojo.dnd.DragSource.call(this);
this.type=type||this.domNode.nodeName.toLowerCase();
}
};
dojo.lang.extend(dojo.dnd.HtmlDragSource,{onDragStart:function(){
return new dojo.dnd.HtmlDragObject(this.dragObject,this.type);
},setDragHandle:function(node){
dojo.dnd.dragManager.unregisterDragSource(this);
this.domNode=node;
dojo.dnd.dragManager.registerDragSource(this);
},setDragTarget:function(node){
this.dragObject=node;
}});
dojo.dnd.HtmlDragObject=function(node,type){
this.type=type;
this.domNode=node;
};
dojo.lang.extend(dojo.dnd.HtmlDragObject,{onDragStart:function(e){
dojo.html.clearSelection();
this.scrollOffset={top:dojo.html.getScrollTop(),left:dojo.html.getScrollLeft()};
this.dragStartPosition={top:dojo.style.getAbsoluteY(this.domNode,true)+this.scrollOffset.top,left:dojo.style.getAbsoluteX(this.domNode,true)+this.scrollOffset.left};
this.dragOffset={top:this.dragStartPosition.top-e.clientY,left:this.dragStartPosition.left-e.clientX};
this.dragClone=this.domNode.cloneNode(true);
with(this.dragClone.style){
position="absolute";
top=this.dragOffset.top+e.clientY+"px";
left=this.dragOffset.left+e.clientX+"px";
}
dojo.style.setOpacity(this.dragClone,0.5);
dojo.html.body().appendChild(this.dragClone);
},updateDragOffset:function(){
var sTop=dojo.html.getScrollTop();
var _6e6=dojo.html.getScrollLeft();
if(sTop!=this.scrollOffset.top){
var diff=sTop-this.scrollOffset.top;
this.dragOffset.top+=diff;
this.scrollOffset.top=sTop;
}
},onDragMove:function(e){
this.dragClone.style.top=this.dragOffset.top+e.clientY+"px";
this.dragClone.style.left=this.dragOffset.left+e.clientX+"px";
},onDragEnd:function(e){
switch(e.dragStatus){
case "dropSuccess":
dojo.dom.removeNode(this.dragClone);
this.dragClone=null;
break;
case "dropFailure":
var _6ea=[dojo.style.getAbsoluteX(this.dragClone),dojo.style.getAbsoluteY(this.dragClone)];
var _6eb=[this.dragStartPosition.left+1,this.dragStartPosition.top+1];
var line=new dojo.math.curves.Line(_6ea,_6eb);
var anim=new dojo.animation.Animation(line,300,0,0);
var _6ee=this;
dojo.event.connect(anim,"onAnimate",function(e){
_6ee.dragClone.style.left=e.x+"px";
_6ee.dragClone.style.top=e.y+"px";
});
dojo.event.connect(anim,"onEnd",function(e){
dojo.lang.setTimeout(dojo.dom.removeNode,200,_6ee.dragClone);
});
anim.play();
break;
}
}});
dojo.dnd.HtmlDropTarget=function(node,_6f2){
if(arguments.length==0){
return;
}
this.domNode=node;
dojo.dnd.DropTarget.call(this);
this.acceptedTypes=_6f2||[];
};
dojo.inherits(dojo.dnd.HtmlDropTarget,dojo.dnd.DropTarget);
dojo.lang.extend(dojo.dnd.HtmlDropTarget,{onDragOver:function(e){
if(!dojo.lang.inArray(this.acceptedTypes,"*")){
for(var i=0;i<e.dragObjects.length;i++){
if(!dojo.lang.inArray(this.acceptedTypes,e.dragObjects[i].type)){
return false;
}
}
}
this.childBoxes=[];
for(var i=0,child;i<this.domNode.childNodes.length;i++){
child=this.domNode.childNodes[i];
if(child.nodeType!=dojo.dom.ELEMENT_NODE){
continue;
}
var top=dojo.style.getAbsoluteY(child);
var _6f6=top+dojo.style.getInnerHeight(child);
var left=dojo.style.getAbsoluteX(child);
var _6f8=left+dojo.style.getInnerWidth(child);
this.childBoxes.push({top:top,bottom:_6f6,left:left,right:_6f8,node:child});
}
return true;
},_getNodeUnderMouse:function(e){
var _6fa=e.pageX||e.clientX+dojo.html.body().scrollLeft;
var _6fb=e.pageY||e.clientY+dojo.html.body().scrollTop;
for(var i=0,child;i<this.childBoxes.length;i++){
with(this.childBoxes[i]){
if(_6fa>=left&&_6fa<=right&&_6fb>=top&&_6fb<=bottom){
return i;
}
}
}
return -1;
},onDragMove:function(e){
var i=this._getNodeUnderMouse(e);
if(!this.dropIndicator){
this.dropIndicator=document.createElement("div");
with(this.dropIndicator.style){
position="absolute";
zIndex=1;
borderTopWidth="1px";
borderTopColor="black";
borderTopStyle="solid";
width=dojo.style.getInnerWidth(this.domNode)+"px";
left=dojo.style.getAbsoluteX(this.domNode)+"px";
}
}
with(this.dropIndicator.style){
if(i<0){
if(this.childBoxes.length){
top=((dojo.html.gravity(this.childBoxes[0].node,e)&dojo.html.gravity.NORTH)?this.childBoxes[0].top:this.childBoxes[this.childBoxes.length-1].bottom)+"px";
}else{
top=dojo.style.getAbsoluteY(this.domNode)+"px";
}
}else{
var _6ff=this.childBoxes[i];
top=((dojo.html.gravity(_6ff.node,e)&dojo.html.gravity.NORTH)?_6ff.top:_6ff.bottom)+"px";
}
}
if(!this.dropIndicator.parentNode){
dojo.html.body().appendChild(this.dropIndicator);
}
},onDragOut:function(e){
dojo.dom.removeNode(this.dropIndicator);
delete this.dropIndicator;
},onDrop:function(e){
this.onDragOut(e);
var i=this._getNodeUnderMouse(e);
if(i<0){
if(this.childBoxes.length){
if(dojo.html.gravity(this.childBoxes[0].node,e)&dojo.html.gravity.NORTH){
return dojo.dom.insertBefore(e.dragObject.domNode,this.childBoxes[0].node);
}else{
return dojo.dom.insertAfter(e.dragObject.domNode,this.childBoxes[this.childBoxes.length-1].node);
}
}
this.domNode.appendChild(e.dragObject.domNode);
return true;
}
var _703=this.childBoxes[i];
if(dojo.html.gravity(_703.node,e)&dojo.html.gravity.NORTH){
return dojo.dom.insertBefore(e.dragObject.domNode,_703.node);
}else{
return dojo.dom.insertAfter(e.dragObject.domNode,_703.node);
}
}});
dojo.hostenv.conditionalLoadModule({common:["dojo.dnd.DragAndDrop"],browser:["dojo.dnd.HtmlDragAndDrop"]});
dojo.hostenv.moduleLoaded("dojo.dnd.*");
dojo.provide("dojo.widget.Manager");
dojo.require("dojo.lang");
dojo.require("dojo.event.*");
dojo.widget.manager=new function(){
this.widgets=[];
this.widgetIds=[];
this.topWidgets={};
var _704={};
var _705=[];
this.getUniqueId=function(_706){
return _706+"_"+(_704[_706]!=undefined?++_704[_706]:_704[_706]=0);
};
this.add=function(_707){
dojo.profile.start("dojo.widget.manager.add");
this.widgets.push(_707);
if(_707.widgetId==""){
if(_707["id"]){
_707.widgetId=_707["id"];
}else{
if(_707.extraArgs["id"]){
_707.widgetId=_707.extraArgs["id"];
}else{
_707.widgetId=this.getUniqueId(_707.widgetType);
}
}
}
if(this.widgetIds[_707.widgetId]){
dojo.debug("widget ID collision on ID: "+_707.widgetId);
}
this.widgetIds[_707.widgetId]=_707;
dojo.profile.end("dojo.widget.manager.add");
};
this.destroyAll=function(){
for(var x=this.widgets.length-1;x>=0;x--){
try{
this.widgets[x].destroy(true);
delete this.widgets[x];
}
catch(e){
}
}
};
this.remove=function(_709){
var tw=this.widgets[_709].widgetId;
delete this.widgetIds[tw];
this.widgets.splice(_709,1);
};
this.removeById=function(id){
for(var i=0;i<this.widgets.length;i++){
if(this.widgets[i].widgetId==id){
this.remove(i);
break;
}
}
};
this.getWidgetById=function(id){
return this.widgetIds[id];
};
this.getWidgetsByType=function(type){
var lt=type.toLowerCase();
var ret=[];
dojo.lang.forEach(this.widgets,function(x){
if(x.widgetType.toLowerCase()==lt){
ret.push(x);
}
});
return ret;
};
this.getWidgetsOfType=function(id){
dj_deprecated("getWidgetsOfType is depecrecated, use getWidgetsByType");
return dojo.widget.manager.getWidgetsByType(id);
};
this.getWidgetsByFilter=function(_713){
var ret=[];
dojo.lang.forEach(this.widgets,function(x){
if(_713(x)){
ret.push(x);
}
});
return ret;
};
this.getAllWidgets=function(){
return this.widgets.concat();
};
this.byId=this.getWidgetById;
this.byType=this.getWidgetsByType;
this.byFilter=this.getWidgetsByFilter;
var _716={};
var _717=["dojo.widget","dojo.webui.widgets"];
for(var i=0;i<_717.length;i++){
_717[_717[i]]=true;
}
this.registerWidgetPackage=function(_719){
if(!_717[_719]){
_717[_719]=true;
_717.push(_719);
}
};
this.getWidgetPackageList=function(){
return dojo.lang.map(_717,function(elt){
return (elt!==true?elt:undefined);
});
};
this.getImplementation=function(_71b,_71c,_71d){
var impl=this.getImplementationName(_71b);
if(impl){
var ret=new impl(_71c);
return ret;
}
};
this.getImplementationName=function(_720){
var _721=_720.toLowerCase();
var impl=_716[_721];
if(impl){
return impl;
}
if(!_705.length){
for(var _723 in dojo.render){
if(dojo.render[_723]["capable"]===true){
var _724=dojo.render[_723].prefixes;
for(var i=0;i<_724.length;i++){
_705.push(_724[i].toLowerCase());
}
}
}
_705.push("");
}
for(var i=0;i<_717.length;i++){
var _726=dojo.evalObjPath(_717[i]);
if(!_726){
continue;
}
for(var j=0;j<_705.length;j++){
if(!_726[_705[j]]){
continue;
}
for(var _728 in _726[_705[j]]){
if(_728.toLowerCase()!=_721){
continue;
}
_716[_721]=_726[_705[j]][_728];
return _716[_721];
}
}
for(var j=0;j<_705.length;j++){
for(var _728 in _726){
if(_728.toLowerCase()!=(_705[j]+_721)){
continue;
}
_716[_721]=_726[_728];
return _716[_721];
}
}
}
throw new Error("Could not locate \""+_720+"\" class");
};
this.onResized=function(){
for(var id in this.topWidgets){
var _72a=this.topWidgets[id];
if(_72a.onResized){
_72a.onResized();
}
}
};
if(typeof window!="undefined"){
dojo.addOnLoad(this,"onResized");
dojo.event.connect(window,"onresize",this,"onResized");
}
};
dojo.widget.getUniqueId=function(){
return dojo.widget.manager.getUniqueId.apply(dojo.widget.manager,arguments);
};
dojo.widget.addWidget=function(){
return dojo.widget.manager.add.apply(dojo.widget.manager,arguments);
};
dojo.widget.destroyAllWidgets=function(){
return dojo.widget.manager.destroyAll.apply(dojo.widget.manager,arguments);
};
dojo.widget.removeWidget=function(){
return dojo.widget.manager.remove.apply(dojo.widget.manager,arguments);
};
dojo.widget.removeWidgetById=function(){
return dojo.widget.manager.removeById.apply(dojo.widget.manager,arguments);
};
dojo.widget.getWidgetById=function(){
return dojo.widget.manager.getWidgetById.apply(dojo.widget.manager,arguments);
};
dojo.widget.getWidgetsByType=function(){
return dojo.widget.manager.getWidgetsByType.apply(dojo.widget.manager,arguments);
};
dojo.widget.getWidgetsByFilter=function(){
return dojo.widget.manager.getWidgetsByFilter.apply(dojo.widget.manager,arguments);
};
dojo.widget.byId=function(){
return dojo.widget.manager.getWidgetById.apply(dojo.widget.manager,arguments);
};
dojo.widget.byType=function(){
return dojo.widget.manager.getWidgetsByType.apply(dojo.widget.manager,arguments);
};
dojo.widget.byFilter=function(){
return dojo.widget.manager.getWidgetsByFilter.apply(dojo.widget.manager,arguments);
};
dojo.widget.all=function(){
return dojo.widget.manager.getAllWidgets.apply(dojo.widget.manager,arguments);
};
dojo.widget.registerWidgetPackage=function(){
return dojo.widget.manager.registerWidgetPackage.apply(dojo.widget.manager,arguments);
};
dojo.widget.getWidgetImplementation=function(){
return dojo.widget.manager.getImplementation.apply(dojo.widget.manager,arguments);
};
dojo.widget.getWidgetImplementationName=function(){
return dojo.widget.manager.getImplementationName.apply(dojo.widget.manager,arguments);
};
dojo.widget.widgets=dojo.widget.manager.widgets;
dojo.widget.widgetIds=dojo.widget.manager.widgetIds;
dojo.widget.root=dojo.widget.manager.root;
dojo.provide("dojo.widget.Widget");
dojo.provide("dojo.widget.tags");
dojo.require("dojo.lang");
dojo.require("dojo.widget.Manager");
dojo.require("dojo.event.*");
dojo.require("dojo.string");
dojo.widget.Widget=function(){
this.children=[];
this.extraArgs={};
};
dojo.lang.extend(dojo.widget.Widget,{parent:null,isTopLevel:false,isModal:false,isEnabled:true,isHidden:false,isContainer:false,widgetId:"",widgetType:"Widget",toString:function(){
return "[Widget "+this.widgetType+", "+(this.widgetId||"NO ID")+"]";
},repr:function(){
return this.toString();
},enable:function(){
this.isEnabled=true;
},disable:function(){
this.isEnabled=false;
},hide:function(){
this.isHidden=true;
},show:function(){
this.isHidden=false;
},create:function(args,_72c,_72d){
this.satisfyPropertySets(args,_72c,_72d);
this.mixInProperties(args,_72c,_72d);
this.postMixInProperties(args,_72c,_72d);
dojo.widget.manager.add(this);
this.buildRendering(args,_72c,_72d);
this.initialize(args,_72c,_72d);
this.postInitialize(args,_72c,_72d);
this.postCreate(args,_72c,_72d);
return this;
},destroy:function(_72e){
this.uninitialize();
this.destroyRendering(_72e);
dojo.widget.manager.removeById(this.widgetId);
},destroyChildren:function(_72f){
_72f=(!_72f)?function(){
return true;
}:_72f;
for(var x=0;x<this.children.length;x++){
var tc=this.children[x];
if((tc)&&(_72f(tc))){
tc.destroy();
}
}
},destroyChildrenOfType:function(type){
type=type.toLowerCase();
this.destroyChildren(function(item){
if(item.widgetType.toLowerCase()==type){
return true;
}else{
return false;
}
});
},getChildrenOfType:function(type,_735){
var ret=[];
type=type.toLowerCase();
for(var x=0;x<this.children.length;x++){
if(this.children[x].widgetType.toLowerCase()==type){
ret.push(this.children[x]);
}
if(_735){
ret=ret.concat(this.children[x].getChildrenOfType(type,_735));
}
}
return ret;
},satisfyPropertySets:function(args){
return args;
},mixInProperties:function(args,frag){
if((args["fastMixIn"])||(frag["fastMixIn"])){
for(var x in args){
this[x]=args[x];
}
return;
}
var _73c;
var _73d=dojo.widget.lcArgsCache[this.widgetType];
if(_73d==null){
_73d={};
for(var y in this){
_73d[((new String(y)).toLowerCase())]=y;
}
dojo.widget.lcArgsCache[this.widgetType]=_73d;
}
var _73f={};
for(var x in args){
if(!this[x]){
var y=_73d[(new String(x)).toLowerCase()];
if(y){
args[y]=args[x];
x=y;
}
}
if(_73f[x]){
continue;
}
_73f[x]=true;
if((typeof this[x])!=(typeof _73c)){
if(typeof args[x]!="string"){
this[x]=args[x];
}else{
if(dojo.lang.isString(this[x])){
this[x]=args[x];
}else{
if(dojo.lang.isNumber(this[x])){
this[x]=new Number(args[x]);
}else{
if(dojo.lang.isBoolean(this[x])){
this[x]=(args[x].toLowerCase()=="false")?false:true;
}else{
if(dojo.lang.isFunction(this[x])){
var tn=dojo.event.nameAnonFunc(new Function(args[x]),this);
dojo.event.connect(this,x,this,tn);
}else{
if(dojo.lang.isArray(this[x])){
this[x]=args[x].split(";");
}else{
if(this[x] instanceof Date){
this[x]=new Date(Number(args[x]));
}else{
if(typeof this[x]=="object"){
var _741=args[x].split(";");
for(var y=0;y<_741.length;y++){
var si=_741[y].indexOf(":");
if((si!=-1)&&(_741[y].length>si)){
this[x][dojo.string.trim(_741[y].substr(0,si))]=_741[y].substr(si+1);
}
}
}else{
this[x]=args[x];
}
}
}
}
}
}
}
}
}else{
this.extraArgs[x]=args[x];
}
}
},postMixInProperties:function(){
},initialize:function(args,frag){
return false;
},postInitialize:function(args,frag){
return false;
},postCreate:function(args,frag){
return false;
},uninitialize:function(){
return false;
},buildRendering:function(){
dj_unimplemented("dojo.widget.Widget.buildRendering, on "+this.toString()+", ");
return false;
},destroyRendering:function(){
dj_unimplemented("dojo.widget.Widget.destroyRendering");
return false;
},cleanUp:function(){
dj_unimplemented("dojo.widget.Widget.cleanUp");
return false;
},addedTo:function(_749){
},addChild:function(_74a){
dj_unimplemented("dojo.widget.Widget.addChild");
return false;
},addChildAtIndex:function(_74b,_74c){
dj_unimplemented("dojo.widget.Widget.addChildAtIndex");
return false;
},removeChild:function(_74d){
dj_unimplemented("dojo.widget.Widget.removeChild");
return false;
},removeChildAtIndex:function(_74e){
dj_unimplemented("dojo.widget.Widget.removeChildAtIndex");
return false;
},resize:function(_74f,_750){
this.setWidth(_74f);
this.setHeight(_750);
},setWidth:function(_751){
if((typeof _751=="string")&&(_751.substr(-1)=="%")){
this.setPercentageWidth(_751);
}else{
this.setNativeWidth(_751);
}
},setHeight:function(_752){
if((typeof _752=="string")&&(_752.substr(-1)=="%")){
this.setPercentageHeight(_752);
}else{
this.setNativeHeight(_752);
}
},setPercentageHeight:function(_753){
return false;
},setNativeHeight:function(_754){
return false;
},setPercentageWidth:function(_755){
return false;
},setNativeWidth:function(_756){
return false;
}});
dojo.widget.lcArgsCache={};
dojo.widget.tags={};
dojo.widget.tags.addParseTreeHandler=function(type){
var _758=type.toLowerCase();
this[_758]=function(_759,_75a,_75b,_75c){
return dojo.widget.buildWidgetFromParseTree(_758,_759,_75a,_75b,_75c);
};
};
dojo.widget.tags.addParseTreeHandler("dojo:widget");
dojo.widget.tags["dojo:propertyset"]=function(_75d,_75e,_75f){
var _760=_75e.parseProperties(_75d["dojo:propertyset"]);
};
dojo.widget.tags["dojo:connect"]=function(_761,_762,_763){
var _764=_762.parseProperties(_761["dojo:connect"]);
};
dojo.widget.buildWidgetFromParseTree=function(type,frag,_767,_768,_769){
var _76a={};
var _76b=type.split(":");
_76b=(_76b.length==2)?_76b[1]:type;
var _76a=_767.parseProperties(frag["dojo:"+_76b]);
var _76c=dojo.widget.manager.getImplementation(_76b);
if(!_76c){
throw new Error("cannot find \""+_76b+"\" widget");
}else{
if(!_76c.create){
throw new Error("\""+_76b+"\" widget object does not appear to implement *Widget");
}
}
_76a["dojoinsertionindex"]=_769;
var ret=_76c.create(_76a,frag,_768);
return ret;
};
dojo.provide("dojo.widget.Parse");
dojo.require("dojo.widget.Manager");
dojo.require("dojo.string");
dojo.require("dojo.dom");
dojo.widget.Parse=function(_76e){
this.propertySetsList=[];
this.fragment=_76e;
this.createComponents=function(_76f,_770){
var _771=dojo.widget.tags;
var _772=[];
for(var item in _76f){
var _774=false;
try{
if(_76f[item]&&(_76f[item]["tagName"])&&(_76f[item]!=_76f["nodeRef"])){
var tn=new String(_76f[item]["tagName"]);
var tna=tn.split(";");
for(var x=0;x<tna.length;x++){
var ltn=dojo.string.trim(tna[x]).toLowerCase();
if(_771[ltn]){
_774=true;
_76f[item].tagName=ltn;
_772.push(_771[ltn](_76f[item],this,_770,_76f[item]["index"]));
}else{
if(ltn.substr(0,5)=="dojo:"){
dojo.debug("no tag handler registed for type: ",ltn);
}
}
}
}
}
catch(e){
dojo.debug(e);
}
if((!_774)&&(typeof _76f[item]=="object")&&(_76f[item]!=_76f.nodeRef)&&(_76f[item]!=_76f["tagName"])){
_772.push(this.createComponents(_76f[item],_770));
}
}
return _772;
};
this.parsePropertySets=function(_779){
return [];
var _77a=[];
for(var item in _779){
if((_779[item]["tagName"]=="dojo:propertyset")){
_77a.push(_779[item]);
}
}
this.propertySetsList.push(_77a);
return _77a;
};
this.parseProperties=function(_77c){
var _77d={};
for(var item in _77c){
if((_77c[item]==_77c["tagName"])||(_77c[item]==_77c.nodeRef)){
}else{
if((_77c[item]["tagName"])&&(dojo.widget.tags[_77c[item].tagName.toLowerCase()])){
}else{
if((_77c[item][0])&&(_77c[item][0].value!="")){
try{
if(item.toLowerCase()=="dataprovider"){
var _77f=this;
this.getDataProvider(_77f,_77c[item][0].value);
_77d.dataProvider=this.dataProvider;
}
_77d[item]=_77c[item][0].value;
var _780=this.parseProperties(_77c[item]);
for(var _781 in _780){
_77d[_781]=_780[_781];
}
}
catch(e){
dj_debug(e);
}
}
}
}
}
return _77d;
};
this.getDataProvider=function(_782,_783){
dojo.io.bind({url:_783,load:function(type,_785){
if(type=="load"){
_782.dataProvider=_785;
}
},mimetype:"text/javascript",sync:true});
};
this.getPropertySetById=function(_786){
for(var x=0;x<this.propertySetsList.length;x++){
if(_786==this.propertySetsList[x]["id"][0].value){
return this.propertySetsList[x];
}
}
return "";
};
this.getPropertySetsByType=function(_788){
var _789=[];
for(var x=0;x<this.propertySetsList.length;x++){
var cpl=this.propertySetsList[x];
var cpcc=cpl["componentClass"]||cpl["componentType"]||null;
if((cpcc)&&(propertySetId==cpcc[0].value)){
_789.push(cpl);
}
}
return _789;
};
this.getPropertySets=function(_78d){
var ppl="dojo:propertyproviderlist";
var _78f=[];
var _790=_78d["tagName"];
if(_78d[ppl]){
var _791=_78d[ppl].value.split(" ");
for(propertySetId in _791){
if((propertySetId.indexOf("..")==-1)&&(propertySetId.indexOf("://")==-1)){
var _792=this.getPropertySetById(propertySetId);
if(_792!=""){
_78f.push(_792);
}
}else{
}
}
}
return (this.getPropertySetsByType(_790)).concat(_78f);
};
this.createComponentFromScript=function(_793,_794,_795,_796){
var frag={};
var _798="dojo:"+_794.toLowerCase();
frag[_798]={};
var bo={};
_795.dojotype=_794;
for(var prop in _795){
if(typeof bo[prop]=="undefined"){
frag[_798][prop]=[{value:_795[prop]}];
}
}
frag[_798].nodeRef=_793;
frag.tagName=_798;
var _79b=[frag];
if(_796){
_79b[0].fastMixIn=true;
}
return this.createComponents(_79b);
};
};
dojo.widget._parser_collection={"dojo":new dojo.widget.Parse()};
dojo.widget.getParser=function(name){
if(!name){
name="dojo";
}
if(!this._parser_collection[name]){
this._parser_collection[name]=new dojo.widget.Parse();
}
return this._parser_collection[name];
};
dojo.widget.fromScript=function(name,_79e,_79f,_7a0){
if((typeof name!="string")&&(typeof _79e=="string")){
return dojo.widget._oldFromScript(name,_79e,_79f);
}
_79e=_79e||{};
var _7a1=false;
var tn=null;
var h=dojo.render.html.capable;
if(h){
tn=document.createElement("span");
}
if(!_79f){
_7a1=true;
_79f=tn;
if(h){
dojo.html.body().appendChild(_79f);
}
}else{
if(_7a0){
dojo.dom.insertAtPosition(tn,_79f,_7a0);
}else{
tn=_79f;
}
}
var _7a4=dojo.widget._oldFromScript(tn,name,_79e);
if(!_7a4[0]||typeof _7a4[0].widgetType=="undefined"){
throw new Error("Creation of \""+name+"\" widget fromScript failed.");
}
if(_7a1){
if(_7a4[0].domNode.parentNode){
_7a4[0].domNode.parentNode.removeChild(_7a4[0].domNode);
}
}
return _7a4[0];
};
dojo.widget._oldFromScript=function(_7a5,name,_7a7){
var ln=name.toLowerCase();
var tn="dojo:"+ln;
_7a7[tn]={dojotype:[{value:ln}],nodeRef:_7a5,fastMixIn:true};
var ret=dojo.widget.getParser().createComponentFromScript(_7a5,name,_7a7,true);
return ret;
};
dojo.provide("dojo.widget.DomWidget");
dojo.require("dojo.event.*");
dojo.require("dojo.string");
dojo.require("dojo.widget.Widget");
dojo.require("dojo.dom");
dojo.require("dojo.xml.Parse");
dojo.require("dojo.uri.*");
dojo.widget._cssFiles={};
dojo.widget.defaultStrings={dojoRoot:dojo.hostenv.getBaseScriptUri(),baseScriptUri:dojo.hostenv.getBaseScriptUri()};
dojo.widget.buildFromTemplate=function(obj,_7ac,_7ad,_7ae){
var _7af=_7ac||obj.templatePath;
var _7b0=_7ad||obj.templateCssPath;
if(!_7b0&&obj.templateCSSPath){
obj.templateCssPath=_7b0=obj.templateCSSPath;
obj.templateCSSPath=null;
dj_deprecated("templateCSSPath is deprecated, use templateCssPath");
}
if(_7af&&!(_7af instanceof dojo.uri.Uri)){
_7af=dojo.uri.dojoUri(_7af);
dj_deprecated("templatePath should be of type dojo.uri.Uri");
}
if(_7b0&&!(_7b0 instanceof dojo.uri.Uri)){
_7b0=dojo.uri.dojoUri(_7b0);
dj_deprecated("templateCssPath should be of type dojo.uri.Uri");
}
var _7b1=dojo.widget.DomWidget.templates;
if(!obj["widgetType"]){
do{
var _7b2="__dummyTemplate__"+dojo.widget.buildFromTemplate.dummyCount++;
}while(_7b1[_7b2]);
obj.widgetType=_7b2;
}
if((_7b0)&&(!dojo.widget._cssFiles[_7b0])){
dojo.html.insertCssFile(_7b0);
obj.templateCssPath=null;
dojo.widget._cssFiles[_7b0]=true;
}
var ts=_7b1[obj.widgetType];
if(!ts){
_7b1[obj.widgetType]={};
ts=_7b1[obj.widgetType];
}
if(!obj.templateString){
obj.templateString=_7ae||ts["string"];
}
if(!obj.templateNode){
obj.templateNode=ts["node"];
}
if((!obj.templateNode)&&(!obj.templateString)&&(_7af)){
var _7b4=dojo.hostenv.getText(_7af);
if(_7b4){
var _7b5=_7b4.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
if(_7b5){
_7b4=_7b5[1];
}
}else{
_7b4="";
}
obj.templateString=_7b4;
ts.string=_7b4;
}
if(!ts["string"]){
ts.string=obj.templateString;
}
};
dojo.widget.buildFromTemplate.dummyCount=0;
dojo.widget.attachProperties=["dojoAttachPoint","id"];
dojo.widget.eventAttachProperty="dojoAttachEvent";
dojo.widget.onBuildProperty="dojoOnBuild";
dojo.widget.attachTemplateNodes=function(_7b6,_7b7,_7b8){
var _7b9=dojo.dom.ELEMENT_NODE;
if(!_7b6){
_7b6=_7b7.domNode;
}
if(_7b6.nodeType!=_7b9){
return;
}
var _7ba=_7b6.getElementsByTagName("*");
var _7bb=_7b7;
for(var x=-1;x<_7ba.length;x++){
var _7bd=(x==-1)?_7b6:_7ba[x];
var _7be=[];
for(var y=0;y<this.attachProperties.length;y++){
var _7c0=_7bd.getAttribute(this.attachProperties[y]);
if(_7c0){
_7be=_7c0.split(";");
for(var z=0;z<this.attachProperties.length;z++){
if((_7b7[_7be[z]])&&(dojo.lang.isArray(_7b7[_7be[z]]))){
_7b7[_7be[z]].push(_7bd);
}else{
_7b7[_7be[z]]=_7bd;
}
}
break;
}
}
var _7c2=_7bd.getAttribute(this.templateProperty);
if(_7c2){
_7b7[_7c2]=_7bd;
}
var _7c3=_7bd.getAttribute(this.eventAttachProperty);
if(_7c3){
var evts=_7c3.split(";");
for(var y=0;y<evts.length;y++){
if((!evts[y])||(!evts[y].length)){
continue;
}
var _7c5=null;
var tevt=dojo.string.trim(evts[y]);
if(evts[y].indexOf(":")>=0){
var _7c7=tevt.split(":");
tevt=dojo.string.trim(_7c7[0]);
_7c5=dojo.string.trim(_7c7[1]);
}
if(!_7c5){
_7c5=tevt;
}
var tf=function(){
var ntf=new String(_7c5);
return function(evt){
if(_7bb[ntf]){
_7bb[ntf](dojo.event.browser.fixEvent(evt));
}
};
}();
dojo.event.browser.addListener(_7bd,tevt,tf,false,true);
}
}
for(var y=0;y<_7b8.length;y++){
var _7cb=_7bd.getAttribute(_7b8[y]);
if((_7cb)&&(_7cb.length)){
var _7c5=null;
var _7cc=_7b8[y].substr(4);
_7c5=dojo.string.trim(_7cb);
var tf=function(){
var ntf=new String(_7c5);
return function(evt){
if(_7bb[ntf]){
_7bb[ntf](dojo.event.browser.fixEvent(evt));
}
};
}();
dojo.event.browser.addListener(_7bd,_7cc,tf,false,true);
}
}
var _7cf=_7bd.getAttribute(this.onBuildProperty);
if(_7cf){
eval("var node = baseNode; var widget = targetObj; "+_7cf);
}
_7bd.id="";
}
};
dojo.widget.getDojoEventsFromStr=function(str){
var re=/(dojoOn([a-z]+)(\s?))=/gi;
var evts=str?str.match(re)||[]:[];
var ret=[];
var lem={};
for(var x=0;x<evts.length;x++){
if(evts[x].legth<1){
continue;
}
var cm=evts[x].replace(/\s/,"");
cm=(cm.slice(0,cm.length-1));
if(!lem[cm]){
lem[cm]=true;
ret.push(cm);
}
}
return ret;
};
dojo.widget.buildAndAttachTemplate=function(obj,_7d8,_7d9,_7da,_7db){
this.buildFromTemplate(obj,_7d8,_7d9,_7da);
var node=dojo.dom.createNodesFromText(obj.templateString,true)[0];
this.attachTemplateNodes(node,_7db||obj,dojo.widget.getDojoEventsFromStr(_7da));
return node;
};
dojo.widget.DomWidget=function(){
dojo.widget.Widget.call(this);
if((arguments.length>0)&&(typeof arguments[0]=="object")){
this.create(arguments[0]);
}
};
dojo.inherits(dojo.widget.DomWidget,dojo.widget.Widget);
dojo.lang.extend(dojo.widget.DomWidget,{templateNode:null,templateString:null,preventClobber:false,domNode:null,containerNode:null,addChild:function(_7dd,_7de,pos,ref,_7e1){
if(!this.isContainer){
dojo.debug("dojo.widget.DomWidget.addChild() attempted on non-container widget");
return null;
}else{
this.addWidgetAsDirectChild(_7dd,_7de,pos,ref,_7e1);
this.registerChild(_7dd);
}
return _7dd;
},addWidgetAsDirectChild:function(_7e2,_7e3,pos,ref,_7e6){
if((!this.containerNode)&&(!_7e3)){
this.containerNode=this.domNode;
}
var cn=(_7e3)?_7e3:this.containerNode;
if(!pos){
pos="after";
}
if(!ref){
ref=cn.lastChild;
}
if(!_7e6){
_7e6=0;
}
_7e2.domNode.setAttribute("dojoinsertionindex",_7e6);
if(!ref){
cn.appendChild(_7e2.domNode);
}else{
if(pos=="insertAtIndex"){
dojo.dom.insertAtIndex(_7e2.domNode,ref.parentNode,_7e6);
}else{
if((pos=="after")&&(ref===cn.lastChild)){
cn.appendChild(_7e2.domNode);
}else{
dojo.dom.insertAtPosition(_7e2.domNode,cn,pos);
}
}
}
},registerChild:function(_7e8,_7e9){
_7e8.dojoInsertionIndex=_7e9;
var idx=-1;
for(var i=0;i<this.children.length;i++){
if(this.children[i].dojoInsertionIndex<_7e9){
idx=i;
}
}
this.children.splice(idx+1,0,_7e8);
_7e8.parent=this;
_7e8.addedTo(this);
delete dojo.widget.manager.topWidgets[_7e8.widgetId];
},removeChild:function(_7ec){
for(var x=0;x<this.children.length;x++){
if(this.children[x]===_7ec){
this.children.splice(x,1);
break;
}
}
return _7ec;
},getFragNodeRef:function(frag){
if(!frag["dojo:"+this.widgetType.toLowerCase()]){
dojo.raise("Error: no frag for widget type "+this.widgetType+", id "+this.widgetId+" (maybe a widget has set it's type incorrectly)");
}
return (frag?frag["dojo:"+this.widgetType.toLowerCase()]["nodeRef"]:null);
},postInitialize:function(args,frag,_7f1){
var _7f2=this.getFragNodeRef(frag);
if(_7f1&&(_7f1.snarfChildDomOutput||!_7f2)){
_7f1.addWidgetAsDirectChild(this,"","insertAtIndex","",args["dojoinsertionindex"],_7f2);
}else{
if(_7f2){
if(this.domNode&&(this.domNode!==_7f2)){
var _7f3=_7f2.parentNode.replaceChild(this.domNode,_7f2);
}
}
}
if(_7f1){
_7f1.registerChild(this,args.dojoinsertionindex);
}else{
dojo.widget.manager.topWidgets[this.widgetId]=this;
}
if(this.isContainer){
var _7f4=dojo.widget.getParser();
_7f4.createComponents(frag,this);
}
},startResize:function(_7f5){
dj_unimplemented("dojo.widget.DomWidget.startResize");
},updateResize:function(_7f6){
dj_unimplemented("dojo.widget.DomWidget.updateResize");
},endResize:function(_7f7){
dj_unimplemented("dojo.widget.DomWidget.endResize");
},buildRendering:function(args,frag){
var ts=dojo.widget.DomWidget.templates[this.widgetType];
if((!this.preventClobber)&&((this.templatePath)||(this.templateNode)||((this["templateString"])&&(this.templateString.length))||((typeof ts!="undefined")&&((ts["string"])||(ts["node"]))))){
this.buildFromTemplate(args,frag);
}else{
this.domNode=this.getFragNodeRef(frag);
}
this.fillInTemplate(args,frag);
},buildFromTemplate:function(args,frag){
var ts=dojo.widget.DomWidget.templates[this.widgetType];
if(ts){
if(!this.templateString.length){
this.templateString=ts["string"];
}
if(!this.templateNode){
this.templateNode=ts["node"];
}
}
var node=null;
if((!this.templateNode)&&(this.templateString)){
var _7ff=this.templateString.match(/\$\{([^\}]+)\}/g);
if(_7ff){
var hash=this.strings||{};
for(var key in dojo.widget.defaultStrings){
if(dojo.lang.isUndefined(hash[key])){
hash[key]=dojo.widget.defaultStrings[key];
}
}
for(var i=0;i<_7ff.length;i++){
var key=_7ff[i];
key=key.substring(2,key.length-1);
if(hash[key]){
if(dojo.lang.isFunction(hash[key])){
var _803=hash[key].call(this,key,this.templateString);
}else{
var _803=hash[key];
}
this.templateString=this.templateString.replace(_7ff[i],_803);
}
}
}
this.templateNode=this.createNodesFromText(this.templateString,true)[0];
ts.node=this.templateNode;
}
if(!this.templateNode){
dojo.debug("weren't able to create template!");
return false;
}
var node=this.templateNode.cloneNode(true);
if(!node){
return false;
}
this.domNode=node;
this.attachTemplateNodes(this.domNode,this);
if(this.isContainer&&this.containerNode){
var src=this.getFragNodeRef(frag);
if(src){
dojo.dom.moveChildren(src,this.containerNode);
}
}
},attachTemplateNodes:function(_805,_806){
if(!_806){
_806=this;
}
return dojo.widget.attachTemplateNodes(_805,_806,dojo.widget.getDojoEventsFromStr(this.templateString));
},fillInTemplate:function(){
},destroyRendering:function(){
try{
var _807=this.domNode.parentNode.removeChild(this.domNode);
delete _807;
}
catch(e){
}
},cleanUp:function(){
},getContainerHeight:function(){
return dojo.html.getInnerHeight(this.domNode.parentNode);
},getContainerWidth:function(){
return dojo.html.getInnerWidth(this.domNode.parentNode);
},createNodesFromText:function(){
dj_unimplemented("dojo.widget.DomWidget.createNodesFromText");
}});
dojo.widget.DomWidget.templates={};
dojo.provide("dojo.widget.HtmlWidget");
dojo.require("dojo.widget.DomWidget");
dojo.require("dojo.html");
dojo.require("dojo.string");
dojo.widget.HtmlWidget=function(args){
dojo.widget.DomWidget.call(this);
};
dojo.inherits(dojo.widget.HtmlWidget,dojo.widget.DomWidget);
dojo.lang.extend(dojo.widget.HtmlWidget,{widgetType:"HtmlWidget",templateCssPath:null,templatePath:null,allowResizeX:true,allowResizeY:true,resizeGhost:null,initialResizeCoords:null,toggle:"plain",toggleDuration:150,initialize:function(args,frag){
},postMixInProperties:function(args,frag){
dojo.lang.mixin(this,dojo.widget.HtmlWidget.Toggle[dojo.string.capitalize(this.toggle)]||dojo.widget.HtmlWidget.Toggle.Plain);
},getContainerHeight:function(){
dj_unimplemented("dojo.widget.HtmlWidget.getContainerHeight");
},getContainerWidth:function(){
return this.parent.domNode.offsetWidth;
},setNativeHeight:function(_80d){
var ch=this.getContainerHeight();
},startResize:function(_80f){
_80f.offsetLeft=dojo.html.totalOffsetLeft(this.domNode);
_80f.offsetTop=dojo.html.totalOffsetTop(this.domNode);
_80f.innerWidth=dojo.html.getInnerWidth(this.domNode);
_80f.innerHeight=dojo.html.getInnerHeight(this.domNode);
if(!this.resizeGhost){
this.resizeGhost=document.createElement("div");
var rg=this.resizeGhost;
rg.style.position="absolute";
rg.style.backgroundColor="white";
rg.style.border="1px solid black";
dojo.html.setOpacity(rg,0.3);
dojo.html.body().appendChild(rg);
}
with(this.resizeGhost.style){
left=_80f.offsetLeft+"px";
top=_80f.offsetTop+"px";
}
this.initialResizeCoords=_80f;
this.resizeGhost.style.display="";
this.updateResize(_80f,true);
},updateResize:function(_811,_812){
var dx=_811.x-this.initialResizeCoords.x;
var dy=_811.y-this.initialResizeCoords.y;
with(this.resizeGhost.style){
if((this.allowResizeX)||(_812)){
width=this.initialResizeCoords.innerWidth+dx+"px";
}
if((this.allowResizeY)||(_812)){
height=this.initialResizeCoords.innerHeight+dy+"px";
}
}
},endResize:function(_815){
var dx=_815.x-this.initialResizeCoords.x;
var dy=_815.y-this.initialResizeCoords.y;
with(this.domNode.style){
if(this.allowResizeX){
width=this.initialResizeCoords.innerWidth+dx+"px";
}
if(this.allowResizeY){
height=this.initialResizeCoords.innerHeight+dy+"px";
}
}
this.resizeGhost.style.display="none";
},createNodesFromText:function(txt,wrap){
return dojo.html.createNodesFromText(txt,wrap);
},_old_buildFromTemplate:dojo.widget.DomWidget.prototype.buildFromTemplate,buildFromTemplate:function(args,frag){
if(dojo.widget.DomWidget.templates[this.widgetType]){
var ot=dojo.widget.DomWidget.templates[this.widgetType];
dojo.widget.DomWidget.templates[this.widgetType]={};
}
dojo.widget.buildFromTemplate(this,args["templatePath"],args["templateCssPath"]);
this._old_buildFromTemplate(args,frag);
dojo.widget.DomWidget.templates[this.widgetType]=ot;
},destroyRendering:function(_81d){
try{
var _81e=this.domNode.parentNode.removeChild(this.domNode);
if(!_81d){
dojo.event.browser.clean(_81e);
}
delete _81e;
}
catch(e){
}
},isVisible:function(){
return dojo.html.isVisible(this.domNode);
},doToggle:function(){
this.isVisible()?this.hide():this.show();
},show:function(){
this.showMe();
},onShow:function(){
},hide:function(){
this.hideMe();
},onHide:function(){
}});
dojo.widget.HtmlWidget.Toggle={};
dojo.widget.HtmlWidget.Toggle.Plain={showMe:function(){
dojo.html.show(this.domNode);
if(dojo.lang.isFunction(this.onShow)){
this.onShow();
}
},hideMe:function(){
dojo.html.hide(this.domNode);
if(dojo.lang.isFunction(this.onHide)){
this.onHide();
}
}};
dojo.widget.HtmlWidget.Toggle.Fade={showMe:function(){
dojo.fx.html.fadeShow(this.domNode,this.toggleDuration,dojo.lang.hitch(this,this.onShow));
},hideMe:function(){
dojo.fx.html.fadeHide(this.domNode,this.toggleDuration,dojo.lang.hitch(this,this.onHide));
}};
dojo.widget.HtmlWidget.Toggle.Wipe={showMe:function(){
dojo.fx.html.wipeIn(this.domNode,this.toggleDuration,dojo.lang.hitch(this,this.onShow));
},hideMe:function(){
dojo.fx.html.wipeOut(this.domNode,this.toggleDuration,dojo.lang.hitch(this,this.onHide));
}};
dojo.widget.HtmlWidget.Toggle.Explode={showMe:function(){
dojo.fx.html.explode(this.explodeSrc,this.domNode,this.toggleDuration,dojo.lang.hitch(this,this.onShow));
},hideMe:function(){
dojo.fx.html.implode(this.domNode,this.explodeSrc,this.toggleDuration,dojo.lang.hitch(this,this.onHide));
}};
dojo.hostenv.conditionalLoadModule({common:["dojo.xml.Parse","dojo.widget.Widget","dojo.widget.Parse","dojo.widget.Manager"],browser:["dojo.widget.DomWidget","dojo.widget.HtmlWidget"],svg:["dojo.widget.SvgWidget"]});
dojo.hostenv.moduleLoaded("dojo.widget.*");
dojo.provide("dojo.math.points");
dojo.require("dojo.math");
dojo.math.points={translate:function(a,b){
if(a.length!=b.length){
dojo.raise("dojo.math.translate: points not same size (a:["+a+"], b:["+b+"])");
}
var c=new Array(a.length);
for(var i=0;i<a.length;i++){
c[i]=a[i]+b[i];
}
return c;
},midpoint:function(a,b){
if(a.length!=b.length){
dojo.raise("dojo.math.midpoint: points not same size (a:["+a+"], b:["+b+"])");
}
var c=new Array(a.length);
for(var i=0;i<a.length;i++){
c[i]=(a[i]+b[i])/2;
}
return c;
},invert:function(a){
var b=new Array(a.length);
for(var i=0;i<a.length;i++){
b[i]=-a[i];
}
return b;
},distance:function(a,b){
return Math.sqrt(Math.pow(b[0]-a[0],2)+Math.pow(b[1]-a[1],2));
}};
dojo.hostenv.conditionalLoadModule({common:[["dojo.math",false,false],["dojo.math.curves",false,false],["dojo.math.points",false,false]]});
dojo.hostenv.moduleLoaded("dojo.math.*");

