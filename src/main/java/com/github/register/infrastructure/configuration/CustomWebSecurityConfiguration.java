package com.github.register.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author sniper
 * @date 14 Sep 2023
 */
@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfiguration {


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Spring Security should completely ignore URLs starting with /resources/
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/js/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 鉴权相关配置
        // "/admin"要求有“ROLE_ADMIN"角色权限
        http.csrf((csrf -> csrf.disable()))
                //.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                (requests) -> requests.requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/hello").hasRole("USER") // "/hello"要求有"ROLE_USER"角色权限
                        .anyRequest().authenticated()); // 其它只需要身份认证通过即可，不需要其它特殊权限

        // 登录相关配置
        http.formLogin(formLogin -> formLogin
                .loginPage("/authentication") // 自定义登录页面，不再使用内置的自动生成页面
                .permitAll() // 允许自定义页面的匿名访问，不需要认证和鉴权
        );

        return http.build(); // 返回构建的SecurityFilterChain实例
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
