package com.spring.backend.security;

import com.spring.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

    private static final Logger logger = LoggerFactory.getLogger(CustomPasswordEncoder.class);

    @Autowired
    public UserRepository userRepository;

    @Override
    public String encode(CharSequence rawPassword) {
        // Oracle의 aaa_encoder 함수를 호출하여 비밀번호를 암호화
        // rawPassword를 사용하여 암호화 로직을 구현
        // 암호화된 비밀번호를 반환
        return userRepository.encodePassword( (String) rawPassword );
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // matches() 메서드에서는 필요한 경우 암호화된 비밀번호의 유효성을 검증
        // rawPassword와 encodedPassword를 비교하여 일치 여부 반환
        if (encodedPassword != null) {
            String inputPw = userRepository.encodePassword( (String) rawPassword );
            return inputPw.equals(encodedPassword);
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }
    }
}
