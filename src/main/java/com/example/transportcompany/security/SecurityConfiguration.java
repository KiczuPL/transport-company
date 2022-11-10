package com.example.transportcompany.security;

import com.example.transportcompany.security.filter.CustomAuthenticationFilter;
import com.example.transportcompany.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/login");


        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/login**").anonymous()
                .antMatchers("/api/v1/users/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/user/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/role/**").hasAuthority("ADMIN")
                //.anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/api/v1/users", true)
                    .and()
                .logout()
                .permitAll()
                ;//.anyRequest().authenticated();

        //http.authorizeRequests().antMatchers("/login/**").permitAll();
        //http.authorizeRequests().antMatchers(GET, "/api/v1/user/**").hasAnyAuthority("USER");
        //http.authorizeRequests().antMatchers(GET, "/api/v1/users/**").hasAnyAuthority("ADMIN");

        //http.authorizeRequests();

        http.addFilter(customAuthenticationFilter);
        http.addFilterAfter(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
