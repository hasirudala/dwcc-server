package in.hasirudala.dwcc.server.config;

import in.hasirudala.dwcc.server.auth.GoogleIdAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${google.identityClientId}")
    private String googleIdentityClientId;

    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        GoogleIdAuthenticationFilter
            filter = new GoogleIdAuthenticationFilter(userDetailsService, getGoogleIdentityClientId());

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

    @Bean
    public String getGoogleIdentityClientId() { return googleIdentityClientId; }
}
