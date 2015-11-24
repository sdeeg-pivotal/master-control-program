# Master Control Program
Application to drive a distributed set of worker objects.  It includes:

* mcp-ui:  The Master Control Program.  This is the UI server and proxy to the backend services.
* mcp-botnode-lib: Base library for creating Bots 
* mcp-botnodes: These are the Bots.  The come up and wait for control messages.  They are the backend services.

Initially built to send GET/POST traffic to a web based application as part of a test harness, it's being generalized
so that it's easy to write a Bot as a "micro-services", but also have it present a custom UI through an interface.

##Architecture

mcp-ui
JavaScript/Polymer client
UI Server
Service Proxy (co-located with UI)

mcp-botnode-lib
Base library that implements a standard set of services that allow the bot to work as a multi-threaded node
as well as a participatant in the larger distributed command system.  This library has the annotations for:

* MCPBotNode
* MCPBot
* MCPBotRun

mcp-bots
As set of Bot implementations.  Currently all broken except for the sample.