# MCP BotNode Library
Library to create BotNode apps.  Includes all the basic functionality for a multi-threaded
bot that gathers and returns stats.  Implementation of bot logic is left to the app using
the lib.

##Usage

Create a bot node application by using the annotation `@BotNode` on a class:

```
@BotNode
public class MyBot {
...
}
```

Set Spring Profiles: cloud (config server and registry), cli (turns off web)

##Distributed Control System Node

Implements a general control api

/initialize /init
/start
/stop
/reset
/status

## CLI

Enable command line interface by using profile `botnode-cli`.


