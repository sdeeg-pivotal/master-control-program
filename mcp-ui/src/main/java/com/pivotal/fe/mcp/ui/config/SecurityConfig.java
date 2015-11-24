package com.pivotal.fe.mcp.ui.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
public class SecurityConfig {
	
	//Secure Configuration (the default)
	@Profile("!security-open")
	@Configuration
	//@EnableRedisHttpSession
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	public static class Secure extends WebSecurityConfigurerAdapter
	{
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().permitAll().defaultSuccessUrl("/mcp.html")
			.and().logout()
			.and().authorizeRequests()
				.antMatchers("/login.html", "/js/**", "/img/*", "/elements/*", "/uaa/token")
				.permitAll().anyRequest()
				.authenticated()
			.and().csrf().disable();
//				.authenticated()
//				.and()
//				.csrf().csrfTokenRepository(csrfTokenRepository()).and()
//				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		}

//		private Filter csrfHeaderFilter() {
//			return new OncePerRequestFilter() {
//				@Override
//				protected void doFilterInternal(HttpServletRequest request,
//						HttpServletResponse response, FilterChain filterChain)
//						throws ServletException, IOException {
//					CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//					if (csrf != null) {
//						Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//						String token = csrf.getToken();
//						if (cookie == null || token != null
//								&& !token.equals(cookie.getValue())) {
//							cookie = new Cookie("XSRF-TOKEN", token);
//							cookie.setPath("/");
//							response.addCookie(cookie);
//						}
//					}
//					filterChain.doFilter(request, response);
//				}
//			};
//		}
//
//		private CsrfTokenRepository csrfTokenRepository() {
//			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//			repository.setHeaderName("X-XSRF-TOKEN");
//			return repository;
//		}
	}

	//Configuration class to enable open security
	@Profile("security-open")
	@Configuration
	public static class Open
	{
		@Bean
		public GenericFilterBean insecureFilter()
		{
			return new InsecureFilterChain();
		}

		public class InsecureFilterChain extends GenericFilterBean {
			@Override
			public void doFilter(ServletRequest request, ServletResponse response,
					FilterChain filterChain) throws IOException, ServletException {
				filterChain.doFilter(request, response);
			}
		}
	}
}
