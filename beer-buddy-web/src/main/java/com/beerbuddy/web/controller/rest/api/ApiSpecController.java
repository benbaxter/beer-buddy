package com.beerbuddy.web.controller.rest.api;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.google.common.collect.Maps;

@Controller
public class ApiSpecController {

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	Map<RequestMappingInfo, HandlerMethod> handlerMethods;
	
	@PostConstruct
	public void init() {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.requestMappingHandlerMapping
				.getHandlerMethods();

		for (Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods
				.entrySet()) {
			RequestMappingInfo mapping = item.getKey();
			HandlerMethod method = item.getValue();

			for (String urlPattern : mapping.getPatternsCondition()
					.getPatterns()) {
//				System.out.println(method.getBeanType().getName() + "#"
//						+ method.getMethod().getName() + " <-- " + urlPattern);

				if (urlPattern.equals("some specific url")) {
					// add to list of matching METHODS
				}
			}
		}
		
		this.handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();
	}
	
	@Description("An api for definging the api spec")
	@RequestMapping("/api-spec") 
	public @ResponseBody Map<String, Set<MappingInfo>> apiSpec() {
		
		Map<String, Set<MappingInfo>> api = Maps.newTreeMap();
		
		for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
			MappingInfo info = new MappingInfo();
			
			RequestMappingInfo mappingInfo = entry.getKey();
			info.setMethods(mappingInfo.getMethodsCondition().getMethods());
			info.setPatterns(mappingInfo.getPatternsCondition().getPatterns());
			info.setHeaders(mappingInfo.getHeadersCondition().getExpressions());
			info.setParams(mappingInfo.getParamsCondition().getExpressions());
			info.setProduces(mappingInfo.getProducesCondition().getProducibleMediaTypes());
			
			
			HandlerMethod handlerMethod = entry.getValue();
			Description description = handlerMethod.getMethodAnnotation(Description.class);
			if( description != null ) {
				info.setDescription(description.value()); 
			}
			info.setBeanSimpleName(handlerMethod.getBeanType().getSimpleName());
			info.setBeanMethodName(handlerMethod.getMethod().getName());
			info.setReturnType(handlerMethod.getReturnType()
					.getMethod().getGenericReturnType().getTypeName());
			
			if( api.get(info.getBeanSimpleName()) == null ) {
				api.put(info.getBeanSimpleName(), 
						new TreeSet<MappingInfo>((a, b) -> {
							String aUrls = a.getPatterns().stream().collect(Collectors.joining());
							aUrls += a.getMethods().stream().map(method -> method.name()).collect(Collectors.joining());
							String bUrls = b.getPatterns().stream().collect(Collectors.joining());
							bUrls += b.getMethods().stream().map(method -> method.name()).collect(Collectors.joining());
							return aUrls.compareTo(bUrls);
						}));
			}
			api.get(info.getBeanSimpleName()).add(info);
		}
		
		return api;
	}
	
	@Description("A page to display the api in a pretty manner")
	@RequestMapping("/api-spec.html")
	public String apiPage(Model model) {
		model.addAttribute("activeNav", "api-spec");
		model.addAttribute("api", apiSpec());
		return "api-spec/index";
	}
}