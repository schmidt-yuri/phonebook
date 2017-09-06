package yuri.schmidt;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import yuri.schmidt.config.RootConfig;
import yuri.schmidt.config.WebConfig;

public class DispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {RootConfig.class};
	}
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	@Override
	protected void customizeRegistration(Dynamic registration) {
		
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	@Override
	protected Filter[] getServletFilters() {

	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    characterEncodingFilter.setForceEncoding(true);
	    return new Filter[] { characterEncodingFilter};
	}
		
	

}
