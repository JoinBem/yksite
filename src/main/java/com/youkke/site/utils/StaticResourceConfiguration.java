package com.youkke.site.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import com.youkke.site.YksiteApplication;



@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(StaticResourceConfiguration.class);

	@Value("${static.path:}")
	private String staticPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		if (staticPath != null) {
			String apppath = Path.getAppPath(YksiteApplication.class);
			LOG.info("11111111111Serving static content from " + "file://" + apppath + "/static/");
			LOG.info(Path.getAppPath(YksiteApplication.class));
			registry.addResourceHandler("/**").addResourceLocations("file://" + apppath + "/static/").addResourceLocations("file://" + "/C:/Users/leeeeee/Desktop/www.21zsjc.com/www.21zsjc.com/");
		}
	}

	// see
	// https://stackoverflow.com/questions/27381781/java-spring-boot-how-to-map-my-my-app-root-to-index-html
	/*@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/markdown.html").setViewName("markdown");
		registry.addViewController("/content.html").setViewName("content");
	}*/

	@Bean
	SpringResourceTemplateResolver xmlTemplateResolver(ApplicationContext appCtx) {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		String apppath = Path.getAppPath(YksiteApplication.class);
		templateResolver.setApplicationContext(appCtx);
		templateResolver.setPrefix("file://" + "/C:/Users/leeeeee/Desktop/www.21zsjc.com/www.21zsjc.com/template/zh_CN/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);
		LOG.info("Serving static content from " + "file://" + apppath + "/templates/");
		return templateResolver;
	}

	@Bean
	SpringTemplateEngine templateEngine(ApplicationContext appCtx) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(xmlTemplateResolver(appCtx));

		return templateEngine;
	}
	
	/*public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                //SerializerFeature.WriteClassName,
                SerializerFeature.WriteMapNullValue,
                //SerializerFeature.UseSingleQuotes, //支持单引号
                SerializerFeature.WriteDateUseDateFormat, //日期格式化 
                SerializerFeature.WriteMapNullValue, // 输出空置字段
                SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
                SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
                SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
                SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
                //https://github.com/alibaba/fastjson/wiki/%E5%BE%AA%E7%8E%AF%E5%BC%95%E7%94%A8
                //关闭循环引用
                //SerializerFeature.DisableCircularReferenceDetect
        );
        ValueFilter valueFilter = new ValueFilter() {
            //o 是class
            //s 是key值
            //o1 是value值
            public Object process(Object o, String s, Object o1) {
            	System.out.println(o1);
                if (null == o1){
                    o1 = "";
                }
                return o1;
            }
        };
        fastJsonConfig.setSerializeFilters(valueFilter);

        converter.setFastJsonConfig(fastJsonConfig);

        return converter;
    }
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//converters.add(jacksonMessageConverter());
		System.out.println("333333333:"+converters.size());
		for (HttpMessageConverter<?> converter : converters) {
	        if (converter instanceof MappingJackson2HttpMessageConverter) {
	        	MappingJackson2HttpMessageConverter jacksonMessageConverter = (MappingJackson2HttpMessageConverter) converter;
	        	System.out.println("99999999"+jacksonMessageConverter);
	        	break;
	        }
	        System.out.println("99999999"+converter);
	    }
		
		converters.add(fastJsonHttpMessageConverter());
		
		for (HttpMessageConverter<?> converter : converters) {
			System.out.println("55555555555"+converter);
		}
		super.configureMessageConverters(converters);
	}*/
	
	@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> messageConverter : converters) {
            System.out.println("222222222"+messageConverter); //2
        }
    }

}
