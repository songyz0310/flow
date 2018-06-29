package org.flow.boot.ticket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {

	@Value("${spring.thymeleaf.mode}")
	private String mode;
	@Value("${spring.thymeleaf.cache}")
	private String cache;
	@Value("${spring.mvc.view.prefix.boot}")
	private String prefix;
	@Value("${spring.mvc.view.suffix}")
	private String suffix;

	@Bean("flowTemplateEngine")
	public TemplateEngine templateEngine() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode(mode);
		templateResolver.setPrefix(prefix);
		templateResolver.setSuffix(suffix);
		templateResolver.setCacheable(Boolean.parseBoolean(cache));

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver); // 设置模板解析器
		return templateEngine;

	}
}
