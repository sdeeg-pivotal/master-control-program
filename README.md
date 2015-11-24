# cloud-driver
Application to drive activities.  It includes:

* mcp-ui:  The Master Control Program.  This is the UI server and proxy to the backend services.
* mcp-botnode:  These are the workers.  The come up and wait for control messages.
* reporter:  Services that deliver high level reporting data. (FUTURE)

Initially built to send GET/POST traffic to a web based application, it's being generalized
to act as a simulator for source data.  It also is an implementation of "micro-services"
architecture with a SPA (single page application) front end.

##Architecture

mcp-ui
JavaScript/Polymer client
UI Server
Service Proxy (co-located with UI)

mcp-botnode
RESTful service implementing status/start/stop for the cluster

mcp-reporter
Reporter

