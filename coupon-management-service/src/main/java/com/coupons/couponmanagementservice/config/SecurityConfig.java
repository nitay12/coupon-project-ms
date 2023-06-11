package com.coupons.couponmanagementservice.config;

import com.coupons.couponmanagementservice.security.JwtTokenVerifier;
import com.coupons.couponmanagementservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private static final String[] AUTH_WHITE_LIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(new JwtTokenVerifier(jwtService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITE_LIST).permitAll()
                .antMatchers(HttpMethod.POST, "/api/coupons").hasRole("COMPANY")
                .antMatchers(HttpMethod.PUT, "/api/coupons").hasRole("COMPANY")
                .antMatchers(HttpMethod.DELETE, "/api/coupons").hasRole("COMPANY")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
