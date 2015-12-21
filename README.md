# Master Control Program

The Master Control Program project is a set of Applications and a framework to create and a operate a distributed set of worker objects.  It includes:

* mcp-ui:  The Master Control Program user interface.  Also the proxy to the backend services.
* mcp-botnode-lib: Library for creating Nodes with Bots in the system. 
* mcp-bots: These are some Bots.  The come up and wait for control messages.

The goal is to make it easy to write a Bot that deploys as a "micro-service", but have it run in a multi-threaded environment.  This allows us to create a large number of workers with a smaller number of application instances.  The common library enables a common set of control services as well as built in statistics tracking.

##Architecture

###mcp-ui
JavaScript/Polymer client
UI Server
Service Proxy (co-located with UI)

###mcp-botnode-lib
Base library that provides a framework for building BotNodes (a collection of Bots).  Using the library provides a standard set of services that allow the the control program to interact with it.  Additionally, the BotNode framework provides statistic tracking for the bots and general reporting of performance data.  The goal is to make as easy to define the Bot logic without having to worry about the surrounding context.  The library provides 3 annotations:

* MCPBotNode
* MCPBot
* MCPBotRun

###mcp-bots
As set of Bot implementations.  Currently all broken except for the sample.  These are Spring Boot apps that utilize the botnode-lib framework to define bots.  Since the parent of these apps is Boot, the botnode-lib library needs to be installed in the local maven repository.

##Building

mvn clean install
cd <to bot implementation>
mvn package

The first call to maven builds the mcp applications and library.  Bot implementations are Boot apps that have the library as a dependency.  The second call to maven then builds and packages the app for deployment.

##Modes of operation

This is a Cloud Native application, and is best run on a cloud application platform such as Cloud Foundry.

A secondary mode of operation is local deploy.  This requires the starting of 3 base applications (mcp-ui, config server, eureka server) as well as the BotNode apps.
