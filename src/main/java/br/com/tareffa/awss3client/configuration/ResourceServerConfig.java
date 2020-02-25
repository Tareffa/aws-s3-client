package br.com.tareffa.awss3client.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer // @formatter:off
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final static String resourceId = "resources";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources){
        resources.resourceId(resourceId);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/api/**")
            .and()
                .authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/v1/arquivos/*/view").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/arquivos/*/download").permitAll()
                    .antMatchers("/**").permitAll()
                .anyRequest()
                    .authenticated();
    }
    
}
