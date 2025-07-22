package com.spring.backend.service;

import com.spring.backend.dto.UserDto;
import com.spring.backend.models.User;
import com.spring.backend.repository.UserRepository;
import com.spring.backend.security.WebSecurityConfig;
import com.spring.backend.security.jwt.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    public User getUserById(String userId){
        Optional<User> user = userRepository.findByUsername(userId);
        System.out.println(user.get().getUsername());
        return user.get();
    }

    public UserDto getUserById() {
        Optional<String> userId = SecurityUtils.getCurrentUsername();

        return UserDto.from(userRepository.findByUsername(userId.get()).get());
    }

    // password change method, with parameter is current password and new password
    public User changePassword(String currentPassword, String newPassword) throws Exception {

        String validatePassword = validatePassword(currentPassword, newPassword);
        if(!validatePassword.equals("0000")){
            throw new Exception(validatePassword);
        }

        Optional<String> userId = SecurityUtils.getCurrentUsername();
        Optional<User> user = userRepository.findByUsername(userId.get());

        if (webSecurityConfig.customPasswordEncoder().matches(currentPassword, user.get().getPassword())) {
            user.get().setPassword(webSecurityConfig.customPasswordEncoder().encode(newPassword));
            userRepository.save(user.get());
        } else{
            throw new Exception("current password is not matched");
        }
        return user.get();
    }

    /**
     * 비밀번호 유효성 검사
     *
     * @param newPassword 비밀번호
     * @return String 유효성 메시지 (유효하면 0000)
     */

    public String validatePassword(String currentPassword, String newPassword) {
        Optional<String> userId = SecurityUtils.getCurrentUsername();

        if (newPassword.equals(currentPassword)) {
            return "Password cannot be the same as the current password.";
        }

        if (userId.isPresent() && newPassword.contains(userId.get())) {
            return "The UserID cannot be included in the password.";
        }

        if (newPassword.length() < 8) {
            return "The password must be a minimum of 8 characters in length.";
        }

        if (newPassword.length() > 10) {
            return "The password must not exceed 10 characters in length.";
        }

        if (newPassword.matches(".*[^A-Za-z0-9].*")) {
            return "Special characters are not allowed in the password.";
        }

        int charTypes = 0;
        if (newPassword.matches(".*[A-Z].*")) {
            charTypes++;
        }
        if (newPassword.matches(".*[a-z].*")) {
            charTypes++;
        }
        if (newPassword.matches(".*[0-9].*")) {
            charTypes++;
        }

        if (charTypes < 3) {
            return "The password must include a combination of uppercase letters, lowercase letters, and numbers (at least three types).";
        }

        for (char c : newPassword.toCharArray()) {
            if (countChars(c, newPassword) >= 3) {
                return "The password cannot use the same character consecutively three or more times";
            }
        }

        return "0000"; // 유효한 비밀번호
    }

    /**
     * 문자열 내에서 특정 문자의 출현 횟수를 계산합니다.
     *
     * @param c 문자
     * @param str 문자열
     * @return 문자의 출현 횟수
     */
    private int countChars(char c, String str) {
        int count = 0;
        for (char character : str.toCharArray()) {
            if (character == c) {
                count++;
            }
        }
        return count;
    }

}
