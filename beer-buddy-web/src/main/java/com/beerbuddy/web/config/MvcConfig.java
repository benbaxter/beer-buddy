package com.beerbuddy.web.config;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.beerbuddy.web.controller.rest.BeerController;
import com.beerbuddy.web.controller.ui.HomePageController;

@Configuration
@ComponentScan(basePackageClasses={HomePageController.class, BeerController.class})
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	@Bean
	public FilterRegistrationBean siteMeshFilterRegistrationBean() {
		FilterRegistrationBean registry = new FilterRegistrationBean();
		registry.setFilter(new ConfigurableSiteMeshFilter());
		registry.addUrlPatterns("/*");
		registry.addServletNames("siteMeshFilter");
		return registry;
	}
	
	//does the same thing as the bean above but lets spring have more control/
//	@Bean
//	public Filter sitemeshFilter() {
//		return new ConfigurableSiteMeshFilter();
//	}
}
