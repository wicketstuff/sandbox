Wicket Assistant

The reason for the two Idea project is that the testcase framework is not in the 5.x api.
Therefore the tests must be run and created from a 6.x version.
But the code still has to be checked against a 5.x version and is best developed with a 5.x version
to be sure that it works under 5.x versions.

Getting started:
Look here: http://www.jetbrains.com/idea/plugins/plugin_developers.html

When you have set it up you have to add the idea.jar file from your Idea installation to the idea_jdk when added.
Then you should be able to run the junit test from within the idea 6 project files. See the setting_up_idea.jpg screenshot showing
how the Intellij Idea SDK is used as the jdk (comes with the plugins package) and how the idea.jar file from your idea installation
is added to the Idea SDK. It is within this jar file the needed files for running the test cases are.