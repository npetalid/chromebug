package gr.vaadinchromebug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan
@PropertySources({
		// everything in the classpath takes precendence over anything else
		// if it was not in the classpath then second propertysource overrides first
		@PropertySource("classpath:application.yml"),
		@PropertySource(value="file:/logs/application-production.properties.example", ignoreResourceNotFound = true),

})
public class Application {
	@Autowired
	Environment environment;

	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ServletWebServerFactory servletContainer() {
		Integer runningPort = Integer.parseInt(environment.getProperty("server.port"));
		boolean sslRequired = Boolean.parseBoolean(environment.getProperty("security.require-ssl"));
		if (sslRequired == true) {
			TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
				@Override
				protected void postProcessContext(Context context) {
					SecurityConstraint securityConstraint = new SecurityConstraint();
					securityConstraint.setUserConstraint("CONFIDENTIAL");
					SecurityCollection collection = new SecurityCollection();
					collection.addPattern("/*");
					securityConstraint.addCollection(collection);
					context.addConstraint(securityConstraint);
				}
			};
			tomcat.addAdditionalTomcatConnectors(redirectConnector(runningPort));

			return tomcat;
		} else { //We need to return something in case we are in dev and we do not have https
			return new TomcatServletWebServerFactory();
		}

	}

	private Connector redirectConnector(int redirectTo) {

		final int redirectFrom = 80 ;

		Connector connector = new Connector(
				TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(redirectFrom);
		connector.setSecure(false);
		connector.setRedirectPort(redirectTo);

		return connector;
	}

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}
}

