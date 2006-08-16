wicket-archetypes
=================

This is the readme file for the wicket-archetypes folder of the wicket-stuff project

Wicket-archetypes is a collection of maven project archetypes designed for wicket.
Currently this collection consists of one archetype.

Contents
--------
 - Requirements
 - Getting started
 - Usage

Requirements
------------
To install and use these archetype Maven2 needs to be present.


Getting started
---------------
Installation:
 - cd <archetype-folder>
 - mvn install
 
Usage
-----
Crating a maven project using one of the archetypes:
 - mvn archetype:create -DarchetypeGroupId=<archetypeGroupId> -DarchetypeArtifactId=<archetypeArtifactId> \
                        -DarchetypeVersion=<archetypeVersion> -DgroupId=<groupId> -DartifactId=<artifactId>
e.g.:
 - mvn archetype:create -DarchetypeGroupId=wicket -DarchetypeArtifactId=wicket-archetype-template \
                        -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=de.mywicket.app -DartifactId=wicket-template