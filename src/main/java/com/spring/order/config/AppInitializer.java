package com.spring.order.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Hello world!
 *
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
	@Override
	 protected Class < ? > [] getRootConfigClasses() {
	  return new Class[] {
	   ApplicationConfiguration.class, HibernateConfig.class
	  };
	 }
	 @Override
	 protected Class < ? > [] getServletConfigClasses() {
		 return new Class[] { WebMvcConfig.class };
	 }
	 @Override
	 protected String[] getServletMappings() {
	  return new String[] {
	   "/*"
	  };
	 }
}
