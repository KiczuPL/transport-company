package com.example.transportcompany.security;

import com.example.transportcompany.security.filter.CustomAuthenticationFilter;
import com.example.transportcompany.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.transportcompany.security.RoleEnum.ROLE_ADMIN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity//(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtConfig jwtConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(),jwtConfig);
        customAuthenticationFilter.setFilterProcessesUrl("/login");


        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/auth**").permitAll();
        http.authorizeRequests().antMatchers("/api/auth**").anonymous();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/login").anonymous();
        http.authorizeRequests().antMatchers("/error").anonymous();
        http.authorizeRequests().antMatchers("/**").hasAuthority(ROLE_ADMIN.toString());
        http.authorizeRequests().anyRequest().authenticated()
        .and()
        .formLogin()
                .defaultSuccessUrl("/api/users", true)
        .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic();


        /*
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/login/**").anonymous()
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


         */
        //http.authorizeRequests().antMatchers("/login/**").permitAll();
        //http.authorizeRequests().antMatchers(GET, "/api/v1/user/**").hasAnyAuthority("USER");
        //http.authorizeRequests().antMatchers(GET, "/api/v1/users/**").hasAnyAuthority("ADMIN");

        //http.authorizeRequests();

        http.addFilter(customAuthenticationFilter);
        http.addFilterAfter(new CustomAuthorizationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class);

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
