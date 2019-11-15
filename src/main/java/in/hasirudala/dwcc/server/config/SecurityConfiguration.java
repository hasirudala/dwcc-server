package in.hasirudala.dwcc.server.config;

import in.hasirudala.dwcc.server.auth.GoogleIdAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private GoogleIdAuthenticationFilter filter;

    @Autowired
    public SecurityConfiguration(GoogleIdAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(filter, AbstractPreAuthenticatedProcessingFilter.class)
            .addFilterBefore(
                new ExceptionTranslationFilter(new Http403ForbiddenEntryPoint()),
                filter.getClass()
            )
            .authorizeRequests()
            .antMatchers("/observability/**").permitAll()
            //.antMatchers("/api/**").authenticated();
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable();
    }
}
