package com.Pos10Max.POS10APIMAX.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.Pos10Max.POS10APIMAX.ServiceImpl.UserServiceImpl;

@EnableWebSecurity
@Configuration
public class WebSecurityFilter extends WebSecurityConfigurerAdapter {

//    @Override
//    protected UserDetailsService userDetailsService() {
//        return super.userDetailsService();
//    }

    @Autowired
    private UserServiceImpl userDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().cors().disable();
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
//                .antMatchers(HttpMethod.POST,"/user/authentication").permitAll()
//                .antMatchers("/user/login").permitAll()
//                .antMatchers("/reset/**").permitAll()
//                .antMatchers("/user/images/**").permitAll()
//                .antMatchers("/resetpassword.html").permitAll()
//                .anyRequest().authenticated()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);



        http.csrf().disable().cors().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
                .antMatchers(HttpMethod.POST,"/user/authentication").permitAll()
                .antMatchers("/user/login").permitAll()
                .anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailService);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }

}