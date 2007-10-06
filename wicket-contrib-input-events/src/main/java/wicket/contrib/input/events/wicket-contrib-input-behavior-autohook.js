<script type="text/javascript">
function init${wicketComponentId}() {
	shortcut.add("${keys}",function() {
		${wicketComponentId}.${event}();
	},{'disable_in_input':true});
}
SafeAddOnload(init${wicketComponentId});
</script>
