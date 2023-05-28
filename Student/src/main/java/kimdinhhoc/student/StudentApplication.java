package kimdinhhoc.student;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import kimdinhhoc.student.interceptor.LogInterceptor;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling  //quartz --- job
@EnableCaching //redis key-value 
public class StudentApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	// org.springframework.web.servlet.LocaleResolver
	@Bean
	LocaleResolver localeResolver() {
		// org.springframework.web.servlet.i18n.SessionLocaleResolver
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(new Locale("vi"));
		return resolver;
	}

	// giong filter
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");// input name, url param name

		return lci;
	}
	
	@Autowired
	LogInterceptor logInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(logInterceptor);
	}

}
