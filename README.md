# Directed Ego Network Filter

This plugin is an extension of the Ego Network filter, that allows to select only downstream nodes or both directions.
In the future it will also support only upstream nodes.

### Requirements

This Gephi plugin requires JDK and [Maven](http://maven.apache.org/).
It was built and tested with JDK 17, and Apache Maven 3.9.9
```txt
java version "17.0.12" 2024-07-16 LTS
Java(TM) SE Runtime Environment (build 17.0.12+8-LTS-286)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.12+8-LTS-286, mixed mode, sharing)
```

### How to use this plugin
Make sure you are in the root folder.

To compile:
```sh
 mvn clean package
```

To run:
```sh
 mvn org.gephi:gephi-maven-plugin:run
```