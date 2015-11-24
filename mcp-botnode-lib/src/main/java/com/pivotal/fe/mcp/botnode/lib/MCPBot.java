package com.pivotal.fe.mcp.botnode.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Component
//@Documented
@Qualifier
@Scope("prototype")
public @interface MCPBot {

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface MCPBotRun {
		
	}
}
