package com.github.register.infrastructure.utility;

import javax.inject.Named;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

@Named
public class Encryption {

    /**
     * Configure the password encryption algorithm used for authentication: BCrypt
     * Since the encryption of {@link PasswordEncoder} is used in many Spring Security authenticators,
     * add the @Bean annotation here to publish out PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 使用默认加密算法进行编码
     */
    public String encode(CharSequence rawPassword) {
        return passwordEncoder().encode(Optional.ofNullable(rawPassword).orElse(""));
    }

}
