package in.hasirudala.dwcc.server.framework;

import in.hasirudala.dwcc.server.auth.GoogleIdAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private GoogleIdAuthenticationFilter filter;

    @Autowired
    public SecurityConfiguration(GoogleIdAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(filter, AbstractPreAuthenticatedProcessingFilter.class)
            .addFilterBefore(
                new CustomExceptionTranslationFilter(new CustomAuthenticationEntryPoint()),
                filter.getClass()
            )
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable();
    }
}
