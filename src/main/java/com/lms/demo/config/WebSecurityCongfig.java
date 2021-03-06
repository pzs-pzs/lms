package com.lms.demo.config;

import com.lms.demo.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityCongfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder(4));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/web/**").permitAll()
                .antMatchers("/image/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/bootstrap/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/jquery/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/less/**").permitAll()
//                .antMatchers().hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/loginJudgement")
                .failureUrl("/web/login").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/web/getBookList")
                .permitAll();
        http.csrf().disable();
    }

}
