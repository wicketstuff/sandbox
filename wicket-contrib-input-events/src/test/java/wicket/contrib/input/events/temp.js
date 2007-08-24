/*
if( document.captureEvents && Event.KEYUP ) {
  //remove this part if you do not need Netscape 4 to work
  document.captureEvents( Event.KEYUP );
}
 this next line tells the browser to detect a keyup
event over the whole document and when it detects it,
it should run the event handler function 'alertkey' 
document.onkeyup = alertkey;

document.onmousemove = alertCoord;
  if( !e ) {
    //if the browser did not pass the event information to the
    //function, we will have to obtain it from the event register
    if( window.event ) {
      //Internet Explorer
      e = window.event;
    } else {
      //total failure, we have no way of referencing the event
      return;
    }
  }
  if( typeof( e.keyCode ) == 'number'  ) {
    //DOM
    e = e.keyCode;
  } else if( typeof( e.which ) == 'number' ) {
    //NS 4 compatible
    e = e.which;
  } else if( typeof( e.charCode ) == 'number'  ) {
    //also NS 6+, Mozilla 0.9+
    e = e.charCode;
  } else {
    //total failure, we have no way of obtaining the key code
    return;
  }
  mouse event
   if( !e ) {
    if( window.event ) {
      //Internet Explorer
      e = window.event;
    } else {
      //total failure, we have no way of referencing the event
      return;
    }
  }
  if( typeof( e.pageX ) == 'number' ) {
    //most browsers
    var xcoord = e.pageX;
    var ycoord = e.pageY;
  } else if( typeof( e.clientX ) == 'number' ) {
    //Internet Explorer and older browsers
    //other browsers provide this, but follow the pageX/Y branch
    var xcoord = e.clientX;
    var ycoord = e.clientY;
    var badOldBrowser = ( window.navigator.userAgent.indexOf( 'Opera' ) + 1 ) ||
     ( window.ScriptEngine && ScriptEngine().indexOf( 'InScript' ) + 1 ) ||
     ( navigator.vendor == 'KDE' )
    if( !badOldBrowser ) {
      if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
        //IE 4, 5 & 6 (in non-standards compliant mode)
        xcoord += document.body.scrollLeft;
        ycoord += document.body.scrollTop;
      } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
        //IE 6 (in standards compliant mode)
        xcoord += document.documentElement.scrollLeft;
        ycoord += document.documentElement.scrollTop;
      }
    }
  } else {
    //total failure, we have no way of obtaining the mouse coordinates
    return;
  }
  
  // mouse buttons
  window.onload = function () { document.links[0].onmousedown=alertBut; }
  function alertBut( e, evElement ) {
  if( !e ) {
    if( window.event ) {
      //Internet Explorer
      e = window.event;
    } else {
      //total failure, we have no way of referencing the event
      return;
    }
  }
  if( typeof( e.which ) == 'number' ) {
    //Netscape compatible
    e = e.which;
  } else if( typeof( e.button ) == 'number' ) {
    //DOM
    e = e.button;
  } else {
    //total failure, we have no way of obtaining the button
    return;
  }
  if( !evElement ) { evElement = this; }
  /* 'this' will exist if I have used object.onEventName = alertBut;
  If I have passed evElement from the onmouseup attribute,
  'this' will refer to window */
  window.alert( evElement + ' was clicked with button ' + e );
  
}


</script>	
