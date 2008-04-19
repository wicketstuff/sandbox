function close() {
       // Check to see if we are still in a iframe
       if (parent.frames.length > 0) {
           setTimeout( "parent.WicketStuffPicnikCloseModal()", 10 );
       } else {
           document.location.href = "${fallbackUrl}";
       }
}
	