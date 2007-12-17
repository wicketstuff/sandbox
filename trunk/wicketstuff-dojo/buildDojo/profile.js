var dependencies = [
         "dojo.widget.*",
         "dojo.io.*",
         "dojo.string.*",
         "dojo.date.*",
         "dojo.dnd.*",
         "dojo.html.*",
         //"dojo.i18n.*",
         "dojo.namespaces.*",
         "dojo.lfx.*",
         //"dojo.nls.*",
         
         "dojo.widget.Menu2",
       	 "dojo.widget.FloatingPane",
         "dojo.widget.DropDownDatePicker",
         //"dojo.widget.DropDownTimePicker",
         "dojo.widget.SplitContainer",
         "dojo.widget.ContentPane",
         "dojo.widget.Dialog",
         "dojo.widget.TabContainer",
         "dojo.widget.PopupContainer",
         "dojo.widget.LayoutContainer",
         
         "dojoWicket.widget.ErrorDisplayer",
         "dojoWicket.widget.SelectableTable"
         
];

dependencies.prefixes = [
   ["dojoWicket","../dojo-wicket"]
];

load("getDependencyList.js");