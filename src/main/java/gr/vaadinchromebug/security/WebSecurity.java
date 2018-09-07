package gr.vaadinchromebug.security;

import gr.vaadinchromebug.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.logging.Logger;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final static Logger logger =
            Logger.getLogger(WebSecurity.class.getName());


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/",
                        "/bug/",
                        "/vaadinServlet/**",
                        "/VAADIN/**", "/error**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll();
               // .and().addFilterBBasicAuthenticationFilter.class);
        ;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }




}