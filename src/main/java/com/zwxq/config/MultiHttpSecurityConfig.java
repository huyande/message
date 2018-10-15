package com.zwxq.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class MultiHttpSecurityConfig implements WebMvcConfigurer {
	
	
	
	@Value("${password}")
	private String password;
	
	@Bean                                                             
	public UserDetailsService userDetailsService() throws Exception {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode(password);

		// ensure the passwords are encoded properly
		UserDetails user = User.withUsername("admin")
							.password("{bcrypt}"+hashPass)
							.roles("ADMIN")
							.build();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(user);
		//manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
		return manager;
	}

	
	

	@Configuration
	@Order(1)                                                        
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/admin/**")
					.fullyAuthenticated().and().formLogin().loginPage("/index")
					.loginProcessingUrl("/admin/login")
					.successHandler(new AuthenticationSuccessHandler() {
			            @Override
			            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
			                httpServletResponse.setContentType("application/json;charset=utf-8");
			                PrintWriter out = httpServletResponse.getWriter();
			                out.write("{\"status\":\"ok\",\"msg\":\"登录成功\"}");
			                out.flush();
			                out.close();
			            }
			        })
					.failureHandler(new AuthenticationFailureHandler() {
	                    @Override
	                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
	                        httpServletResponse.setContentType("application/json;charset=utf-8");
	                        String msg = "";
	                        msg = e.getMessage();
	                        PrintWriter out = httpServletResponse.getWriter();
	                        out.write("{\"status\":\"error\",\"msg\":\""+msg+"\"}");
	                        out.flush();
	                        out.close();
	                    }
	                })
					.permitAll()
					.and()
					.headers().frameOptions().disable()
					.and()
					.logout().logoutUrl("/admin/logout").logoutSuccessUrl("/index").permitAll();
		}			
	}

	@Configuration                                                   
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.authorizeRequests()
					.anyRequest().permitAll()
					.and()
					.cors();
		}
	}
	
	
/*
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/ModelPics/**").addResourceLocations(env.getProperty("model.img.location"));
		registry.addResourceHandler("/OcrPics/**").addResourceLocations("file:" + OcrPics);
		registry.addResourceHandler("/FaceDetectPics/**").addResourceLocations("file:" + FaceDetectPics);
		registry.addResourceHandler("/FaceContrast/**").addResourceLocations("file:" + FaceContrast);
		registry.addResourceHandler("/PictureClassify/**").addResourceLocations("file:" + PictureClassify);
		registry.addResourceHandler("/PictureSearch/**").addResourceLocations("file:" + PictureSearch);
		registry.addResourceHandler("/FaceSearch/**").addResourceLocations("file:" +FaceSearch);
		//super.addResourceHandlers(registry);
	}*/
	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("https://www.baidu.com"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	
}
