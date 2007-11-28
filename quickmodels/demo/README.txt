This project is a demo of the QuickModels library, a wicket web-app deployable
to Tomcat.

It is set up as a NetBeans project, so the easiest way to work with it is to
open it in the NetBeans IDE.  Even there, some configuration will be necessary,
since some properties for library paths are missing.  If you are using NetBeans,
open the project, then right-click it and choose Resolve Missing Resources.
Note that if you have the Wicket modules from http://nbwicketsupport.dev.java.net
then Wicket should resolve correctly.

What you will need to do is create a file $DEMO_HOME/nbproject/private/private.properties
and populate it with versions of the following, correcting paths to your local
paths for Wicket and db4o. 

deploy.ant.properties.file=/Users/tim/.netbeans/6.0rc2/tomcat60.properties
file.reference.bloat-1.0.jar=/Users/tim/space/db4o-6.1/lib/bloat-1.0.jar
file.reference.db4o-6.1-java5.jar=/Users/tim/space/db4o-6.1/lib/db4o-6.1-java5.jar
file.reference.db4o-6.1-nqopt.jar=/Users/tim/space/db4o-6.1/lib/db4o-6.1-nqopt.jar
file.reference.db4o-persistence-0.1.jar=/Users/tim/space/wicketstuff/quickmodels/db4o-persistence/target/db4o-persistence-0.1.jar
j2ee.platform.classpath=/Users/tim/apache-tomcat-6.0.14/lib/annotations-api.jar:/Users/tim/apache-tomcat-6.0.14/lib/catalina-ant.jar:/Users/tim/apache-tomcat-6.0.14/lib/catalina-ha.jar:/Users/tim/apache-tomcat-6.0.14/lib/catalina-tribes.jar:/Users/tim/apache-tomcat-6.0.14/lib/catalina.jar:/Users/tim/apache-tomcat-6.0.14/lib/el-api.jar:/Users/tim/apache-tomcat-6.0.14/lib/jasper-el.jar:/Users/tim/apache-tomcat-6.0.14/lib/jasper.jar:/Users/tim/apache-tomcat-6.0.14/lib/jsp-api.jar:/Users/tim/apache-tomcat-6.0.14/lib/servlet-api.jar:/Users/tim/apache-tomcat-6.0.14/lib/tomcat-coyote.jar:/Users/tim/apache-tomcat-6.0.14/lib/tomcat-dbcp.jar:/Users/tim/apache-tomcat-6.0.14/lib/tomcat-i18n-es.jar:/Users/tim/apache-tomcat-6.0.14/lib/tomcat-i18n-fr.jar:/Users/tim/apache-tomcat-6.0.14/lib/tomcat-i18n-ja.jar:/Users/tim/apache-tomcat-6.0.14/bin/tomcat-juli.jar
j2ee.server.instance=tomcat60:home=/Users/tim/apache-tomcat-6.0.14
libs.Wicket.classpath.libfile.1=/Users/tim/space/persistence/TopicAuction/lib/wicket/wicket-1.3.0-SNAPSHOT.jar
libs.Wicket.classpath.libfile.2=/Users/tim/space/persistence/TopicAuction/lib/wicket/wicket-auth-roles-1.3.0-SNAPSHOT.jar
libs.Wicket.classpath.libfile.3=/Users/tim/space/persistence/TopicAuction/lib/wicket/wicket-extensions-1.3.0-SNAPSHOT.jar
libs.Wicket.classpath.libfile.4=/Users/tim/space/persistence/TopicAuction/lib/wicket/slf4j-simple-1.4.2.jar
libs.Wicket.classpath.libfile.5=/Users/tim/space/persistence/TopicAuction/lib/wicket/slf4j-api-1.4.2.jar
user.properties.file=/Users/tim/.netbeans/6.0rc2/build.properties
libs.Wicket.classpath=../../../persistence/TopicAuction/lib/wicket/wicket-1.3.0-SNAPSHOT.jar:../../../persistence/TopicAuction/lib/wicket/wicket-auth-roles-1.3.0-SNAPSHOT.jar:../../../persistence/TopicAuction/lib/wicket/wicket-extensions-1.3.0-SNAPSHOT.jar:../../../persistence/TopicAuction/lib/wicket/slf4j-simple-1.4.2.jar:../../../persistence/TopicAuction/lib/wicket/slf4j-api-1.4.2.jar

If the versions of persistence and quickmodels have been updated, you may need
to change the following in nbproject/project.properties to update the version
number appended to the JAR file name:
file.reference.persistence-0.1.jar=../quickmodels/persistence/target/persistence-0.1.jar
file.reference.quickmodels-0.1.jar=../quickmodels/quickmodels/target/quickmodels-0.1.jar
