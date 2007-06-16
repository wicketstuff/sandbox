Patches for dojo should have the following format because 
patches ar used to make a custom dojo release : 

<subject>.<trackNumber>.patch

subject 	: subject of the patch or patched file name
trackNumber	: trackNumber in the dojo tracker : http://trac.dojotoolkit.org/

#=============================================================================#
# Following patches have to be applied on a 0.4.1 dojo release even if for    #
# for the moment, dojo embedded in wicketstuff.dojo is Dojo 0.4.0             #
# See http://81.17.46.170:8090/jira/browse/DOJO-65                            #
#=============================================================================# 

-------------------------------------------------------------------------------


Add some comments here if it is necessary

calendarFr.2376.patch
********************* 
This patch has been applied but in our used dojo revision (0.4.1) we need it. 
Should keep waiting for dojo version upgrade.

DropDownDateTimePicker.2463.patch
*********************************
Allow DropDownDateTimePicker to be skinned. Patch not yet applied in dojo

DropDownDateTimePicker.2468.patch
*********************************
DropDownDateTimePicker.2463.patch should be applied before this one

FloatingPane.2623.patch
***********************
This patch contains both patches from 2623 issues (http://trac.dojotoolkit.org/ticket/2623)

FloatingPane.2697.patch
***********************
This patch should be applied after FloatingPane.2623.patch

HtmlDragManager.xxxx.patch
**************************
This patch is not referenced in the dojo tracker. It comes from a strange behavior
in some dnd case where the id should be null even if it is on the element.
TODO : test on next revision if it still usefull?

RichText.2492.patch
*******************
This patch fixes the following bug:
http://trac.dojotoolkit.org/ticket/2492
The change is applied to trunk, but not to the 0.4 branch. Therefore, we need to apply it again.
http://trac.dojotoolkit.org/changeset/7421

SplitContainer.2296.patch
*************************
-

TimeContainer.xxxx.patch
************************
This patch is used to give an id to the container. Don't remember where it is used
TODO : check if it is usefull