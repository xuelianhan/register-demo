package com.github.register.infrastructure.configuration;

import com.github.register.domain.auth.provider.PreAuthenticatedAuthenticationProvider;
import com.github.register.domain.auth.provider.UsernamePasswordAuthenticationProvider;
import com.github.register.domain.auth.service.AuthenticAccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;

/**
 * Spring Security的用户认证服务器配置
 * <p>
 * 借用Spring Security作为认证服务器，告知服务器通过怎样的途径去查询用户、加密密码和验证用户真伪
 * 我们实际上并不使用Spring Security提供的认证表单，而是选择了前端通过OAuth2的密码模式，在授权过程中同时完成认证
 * 由于服务端整套安全机制（方法授权判断、OAuth2密码模式的用户认证、密码的加密算法）仍然是构建在Spring Security基础之上
 * 所以我们的认证服务、用户信息服务仍然继承着Spring Security提供的基类，并在这里注册到Spring Security当中
 *
 * @author
 * @date
 **/
@Configuration
@EnableWebSecurity
public class AuthenticationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticAccountDetailsService authenticAccountDetailsService;

    @Autowired
    private UsernamePasswordAuthenticationProvider userProvider;

    @Autowired
    private PreAuthenticatedAuthenticationProvider preProvider;

    @Autowired
    private OAuth2AuthenticationManager oAuth2AuthenticationManager;

    @Autowired
    private PasswordEncoder encoder;


    /**
     * 需要把AuthenticationManager主动暴漏出来
     * 以便在授权服务器{@link AuthorizationServerConfiguration}中可以使用它来完成用户名、密码的认证
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return oAuth2AuthenticationManager;
    }

    /**
     * 配置Spring Security的安全认证服务
     * Spring Security的Web安全设置，将在资源服务器配置{@link ResourceServerConfiguration}中完成
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticAccountDetailsService).passwordEncoder(encoder);
        auth.authenticationProvider(userProvider);
        auth.authenticationProvider(preProvider);
    }
}
