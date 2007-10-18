/**
 * 
 * Animator acts as the orchestrator of all Animation within a page where Animations are used.
 * It's role is to create keep a list of Animations and create its listeners.Itis a Singleton
 * 
 */
WicketYuiAnimator = function () {

    /**
     * array of AnimGroups. to keep track of all the AnimGroups created.
     */
    var animGroupArray = [];
	
    /**
     * a constructor for creating an AminGroup. This will create an AnimGroup Object
     * that will hold the an empty map of key:trigger_id value: array anims.
     * 
     * @param {Object} trigger_group - the group to listen to for a trigger event
     * @param {Object} trigger_event - the event listening too
     * 
     * This object also has a list (array) of Animation (anims) that will cycle through. 
     * this is so we can chain different effect on the same event. 
     * so
     * click to 'blind down'
     * click again to 'blind up'
     * and cycle through... if you need only 1 effect, it should work too. 
     */
    function AnimGroup(trigger_group, trigger_event) {
	   this.trigger_group = trigger_group;
	   this.trigger_event = trigger_event;
	   this.map = {};
	   addAnimGroup(this);
	   return this;	
	}

    /**
     * adds an anim to this AnimGroup's map keyed on the trigger_id 
     * 
     * @param {Object} trigger_id
     * @param {Object} anim
     */
    AnimGroup.prototype.addAnim = function (trigger_id, anim) {
		aList = this.map[trigger_id];
		if (!this.map[trigger_id])
		{
		      this.map[trigger_id] = [];
		}
		this.map[trigger_id].push(anim);
	}

    /**
     * 
     * @param {Object} animGroup
     */
    addAnimGroup = function (animGroup)
	{
		animGroupArray[animGroupArray.length] = animGroup;
	}
	
    /**
     * returns the AnimGroup of this key
     * 
     * @param {Object} trigger_group
     * @param {Object} trigger_event
     */        
	getAnimGroup = function (trigger_group, trigger_event)
	{
	    for (i=0,len=animGroupArray.length; i<len ; ++i) 
	    {
	        ag = animGroupArray[i];
	        if ((ag) &&
			    (ag.trigger_group == trigger_group) &&
	            (ag.trigger_event == trigger_event))
	        {
	          return ag;      
	        } 
	     }
	     return undefined;  
	};
	
    /**
     * The callback function that will look for the animation to animate and
     * also cycle through the list of animation... 
	 * 
	 * @param {Object} e - the event
	 * @param {Object} animGroup
	 */
    callback = function(e, animGroup) {
		
        trigger_id = YAHOO.util.Event.getTarget(e).id;
        animList = animGroup.map[trigger_id];
		
		// splice first element an animate it
		var anim = animList.splice(0,1);
		anim[0].animate();

		// add this element back to the list
		animList.push(anim[0]);
    };
	
    return {
		/**
		 * this function is used to add into the Animator an "anim". 
		 * Each group/event will (1) have a "addListener" (2) array of anims 
		 * 
		 * @param {Object} trigger_event - the event 'click' etc that will activate the anim
		 * @param {Object} trigger_group - the group id that the Animator is listening to
		 * @param {Object} trigger_id    - the id that of the on'event' that will trigger the anim
		 * @param {Object} anim
		 * 
		 */
		add : function (trigger_event, trigger_group, trigger_id, anim) {
		    var animGroup = getAnimGroup(trigger_group, trigger_event);
		 
		    if (animGroup) {
                animGroup.addAnim(trigger_id, anim);              						
		    }
		    else { // create a new group, event 
                animGroup = new AnimGroup(trigger_group, trigger_event);
				animGroup.addAnim(trigger_id,  anim);
		        YAHOO.util.Event.addListener(trigger_group, trigger_event, callback, animGroup, true);
		    }
		}
    };
}();