package com.beerbuddy.web.controller.rest.api;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;

public class MappingInfo {
	protected String description;
	protected Set<RequestMethod> methods;
	protected Set<String> patterns;
	protected Set<NameValueExpression<String>> headers;
	protected Set<NameValueExpression<String>> params;
	protected Set<MediaType> produces;
	protected String beanSimpleName;
	protected String beanMethodName;
	protected String returnType;
	
	public Set<RequestMethod> getMethods() {
		return methods;
	}
	public Set<String> getPatterns() {
		return patterns;
	}
	public String getBeanMethodName() {
		return beanMethodName;
	}
	public String getBeanSimpleName() {
		return beanSimpleName;
	}
	public Set<NameValueExpression<String>> getHeaders() {
		return headers;
	}
	public Set<NameValueExpression<String>> getParams() {
		return params;
	}
	public Set<MediaType> getProduces() {
		return produces;
	}
	public String getReturnType() {
		return returnType;
	}
	public String getDescription() {
		return description;
	}
	public void setBeanMethodName(String beanMethodName) {
		this.beanMethodName = beanMethodName;
	}
	public void setBeanSimpleName(String beanSimpleName) {
		this.beanSimpleName = beanSimpleName;
	}public void setDescription(String description) {
		this.description = description;
	}public void setHeaders(Set<NameValueExpression<String>> headers) {
		this.headers = headers;
	}public void setParams(Set<NameValueExpression<String>> params) {
		this.params = params;
	}public void setMethods(Set<RequestMethod> methods) {
		this.methods = methods;
	}public void setPatterns(Set<String> patterns) {
		this.patterns = patterns;
	}public void setProduces(Set<MediaType> produces) {
		this.produces = produces;
	}public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}