package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import app.core.filter.LoginFilter;
import app.core.sessions.SessionContext;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@CrossOrigin
public class Application {

	//http://localhost:8080/swagger-ui.html
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("app.core")).paths(PathSelectors.ant("/api/**")).build();
	}
	
	@Bean
	public FilterRegistrationBean<LoginFilter> filterRegistration(SessionContext sessionContext){
		FilterRegistrationBean<LoginFilter> frb = new FilterRegistrationBean<LoginFilter>();
		LoginFilter loginFilter = new LoginFilter(sessionContext);
		frb.setFilter(loginFilter);
		frb.addUrlPatterns("/api/admin/*", "/api/company/*", "/api/customer/*");
		return frb;
	}
	
}