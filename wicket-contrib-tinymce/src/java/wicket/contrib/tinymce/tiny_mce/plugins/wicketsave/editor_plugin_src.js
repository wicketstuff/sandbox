/**
 * @author Pointbreak, Amsterdam
 * @copyright Copyright © 2008, Pointbreak, Amsterdam, All rights reserved.
 */

(function() {
	tinymce.create('tinymce.plugins.WicketSave', {
		init : function(ed, url) {
			var t = this;

			t.editor = ed;

			// Register commands
			ed.addCommand('mceSave', t._save, t);
			ed.addCommand('mceCancel', t._cancel, t);

			// Register buttons
			ed.addButton('save', {title : 'save.save_desc', cmd : 'mceSave'});
			ed.addButton('cancel', {title : 'save.cancel_desc', cmd : 'mceCancel'});

			ed.onNodeChange.add(t._nodeChange, t);
			ed.addShortcut('ctrl+s', ed.getLang('save.save_desc'), 'mceSave');
		},

		getInfo : function() {
			return {
				longname : 'WicketSave',
				author : 'Pointbreak, Amsterdam',
				authorurl : 'http://pointbreak.biz',
				infourl : 'http://pointbreak.biz',
				version : tinymce.majorVersion + "." + tinymce.minorVersion
			};
		},

		// Private methods

		_nodeChange : function(ed, cm, n) {
			var ed = this.editor;

			if (ed.getParam('save_enablewhendirty')) {
				cm.setDisabled('save', !ed.isDirty());
			}
		},

		// Private methods

		_save : function() {
			var ed = this.editor;

			tinyMCE.triggerSave();

			if (ed.execCallback('save_onwicketsavecallback', ed)) {
				ed.startContent = tinymce.trim(ed.getContent({format : 'raw'}));
				ed.nodeChanged();
			}

			return;
		},

		_cancel : function() {
			var ed = this.editor, h = tinymce.trim(ed.startContent);
			ed.setContent(h);
			ed.undoManager.clear();
			ed.nodeChanged();
			tinyMCE.execCommand('mceRemoveControl', true, ed.id);
		}
	});

	// Register plugin
	tinymce.PluginManager.add('wicketsave', tinymce.plugins.WicketSave);
})();