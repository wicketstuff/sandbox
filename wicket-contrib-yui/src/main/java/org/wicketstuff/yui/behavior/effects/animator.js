if (typeof(Wicket) == "undefined") Wicket = { };
if (typeof(Wicket.yui) == "undefined") Wicket.yui = { };
/**
 * Animation is a Singleton. It keeps a list of AnimGroup objects. 
 * Each AnimGroup has a unique trigger_id and trigger_event.
 * Each AnimGroup also has a map, key on the animation_id of the animation, containing an array 
 * of anims (animation)
 * 
 * upon an event(trigger_event) on an id(trigger_id), the AnimGroup object is passed into the 
 * the callback function.
 * This function will call the animate() on the first anim in each array in the map. (mulitple triggers) 
 * upon completion of each animation, the anim object is cycled to the end of the array. (chaining)
 * 
 */
Wicket.yui.Animator = function () {

    /**
     * array of AnimGroups. to keep track of all the AnimGroups created.
     */
    var animGroupArray = [];
	
	/**
	 * constructor for an AnimGroup. Every trigger_id and trigger_event is a unique key.
	 * it contains a map of array of animations keyed on it's animation_id.
	 * 
	 * @param {Object} trigger_id
	 * @param {Object} trigger_event
	 */
    function AnimGroup(trigger_id, trigger_event) {
	   this.trigger_id = trigger_id;
	   this.trigger_event = trigger_event;
	   this.map = {};
	   addAnimGroup(this);
	   return this;	
	}

    /**
     * adds an anim to this AnimGroup's map keyed on the animation_id 
     * 
     * @param {Object} animation_id
     * @param {Object} anim
     */
    AnimGroup.prototype.addAnim = function (animation_id, anim) {
		if (!this.map[animation_id])
		{
		      this.map[animation_id] = [];
		}
		this.map[animation_id].push(anim);
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
     * @param {Object} trigger_id
     * @param {Object} trigger_event
     */        
	getAnimGroup = function (trigger_id, trigger_event)
	{
	    for (i=0,len=animGroupArray.length; i<len ; ++i) 
	    {
	        ag = animGroupArray[i];
	        if ((ag) &&
			    (ag.trigger_id == trigger_id) &&
	            (ag.trigger_event == trigger_event))
	        {
	          return ag;      
	        } 
	     }
	     return undefined;  
	};
	
    /**
	 * the callback function upon an event that was triggered.
	 * this animator will take the animGroup and animation 
	 * all the first anim of each animation_id arrays
	 * 
	 * @param {Object} e - the event
	 * @param {Object} animGroup
	 */
    callback = function(e, animGroup) 
	{
		for (a_anim_id in animGroup.map) 
		{
			var animList = animGroup.map[a_anim_id];
			if (animList)
			{
		        // splice first element an animate it
		        var anim = animList.splice(0,1);
		        anim[0].animate();
		        
		        // add this element back to the list
		        animList.push(anim[0]);
		   }
		}
    };
    return {

		/**
		 * Adds an animation (anim) into this Animator. A Listener is created for each new AnimGroup
		 * created. 
		 * 
		 * @param {Object} trigger_ids : the list of trigger_ids that will activate this animatione
		 * @param {Object} trigger_event : the trigger event
		 * @param {Object} animation_id : the id of the animation - need this as key of the map
		 * @param {Object} anim : the actual animation object that will be called "animate()"
		 */
		add : function (trigger_ids, trigger_event, animation_id, anim) {
		
			for (i=0, len=trigger_ids.length; i<len ; ++i) 
			{
				var trigger_id = trigger_ids[i];
		    	var animGroup = getAnimGroup(trigger_id, trigger_event);
		 
			    if (animGroup) 
			    {
	                animGroup.addAnim(animation_id, anim);              						
			    }
			    else 
			    { 	// create a new group, event 
	                animGroup = new AnimGroup(trigger_id, trigger_event);
					animGroup.addAnim(animation_id, anim);
			        YAHOO.util.Event.addListener(trigger_id, trigger_event, callback, animGroup, true);
			    }
			}
		}
    };
}();