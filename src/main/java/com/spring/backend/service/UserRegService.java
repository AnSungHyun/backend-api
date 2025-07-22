package com.spring.backend.service;

import com.spring.backend.models.User;
import com.spring.backend.security.WebSecurityConfig;

import com.spring.backend.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserRegService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    WebSecurityConfig webSecurityConfig;

    public Page<User> getUserReg(String userId, String userGb, String useYn, int currentPage, int pageSize){
        Sort sortSearchDic = Sort.by(
                Sort.Order.asc("userId")
        );
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sortSearchDic); // 페이지 번호, 페이지 크기, 정렬 기준 필드

        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if(userId != null && !"".equals(userId) && !userId.isEmpty()) {
            	predicates.add(cb.like(cb.upper(root.get("userId")), "%" + userId + "%"));
            }
            if(userGb != null && !"".equals(userGb) && !userGb.isEmpty()) {
            	predicates.add(cb.equal(root.get("userGb"), userGb));
            }
            if(useYn != null && !"".equals(useYn) && !useYn.isEmpty()) {
            	predicates.add(cb.equal(root.get("useYn"), useYn));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return userRepository.findAll(spec,pageable);

    }
    
    
    public User saveUserReg(User user, Boolean isNew) throws Exception{
    	
    	String validatePassword;
    	if(isNew) {
    		//insert
    		
    		//1.패스워드 체크 
            validatePassword = validatePassword(user);
            if(!validatePassword.equals("0000")){
                throw new Exception(validatePassword);
            }
    		//2.id 중복 체크
            Optional<User> chkDupUserReg = userRepository.findByUsername(user.getUsername());
        	
            if(chkDupUserReg.isPresent()) {
    			throw new DataIntegrityViolationException("Duplicate Key");
    		}
            
            user.setPassword(webSecurityConfig.customPasswordEncoder().encode(user.getPassword()));
            
    		//3.유저 정보 저장
            
    	} else {
    		
            if(!user.getPassword().equals(user.getPasswdOrg())) { // 비밀번호가 같지 않은 경우 검증 후 변경 되도록
            	//새로운 비밀번호 인코딩
            	String pwEc = webSecurityConfig.customPasswordEncoder().encode(user.getPassword());
            	if(!user.getPasswdOrg().equals(pwEc)) { // 새로운 비밀 번호는 인코딩 되어 있지 않아서 인코딩 후 값 비교 
            		validatePassword = validatePassword(user);
            		if(!validatePassword.equals("0000")){
            			throw new Exception(validatePassword);
            		}
            		user.setPassword(pwEc);
            	}else {
            		  throw new Exception("Password cannot be the same as the current password.");
            	}
            }
    	}
    	
    	return userRepository.save(user);
    }
    
    /**
     * 비밀번호 유효성 검사
     *
     * @return String 유효성 메시지 (유효하면 0000)
     */

    public String validatePassword(User user) {
    	String username = user.getUsername();
    	String password = user.getPassword();
    	
        if (password.contains(username)) {
            return "The UserID cannot be included in the password.";
        }

        if (password.length() < 8) {
            return "The password must be a minimum of 8 characters in length.";
        }

        if (password.length() > 10) {
            return "The password must not exceed 10 characters in length.";
        }

        if (password.matches(".*[^A-Za-z0-9].*")) {
            return "Special characters are not allowed in the password.";
        }

        int charTypes = 0;
        if (password.matches(".*[A-Z].*")) {
            charTypes++;
        }
        if (password.matches(".*[a-z].*")) {
            charTypes++;
        }
        if (password.matches(".*[0-9].*")) {
            charTypes++;
        }

        if (charTypes < 3) {
            return "The password must include a combination of uppercase letters, lowercase letters, and numbers (at least three types).";
        }

        for (char c : password.toCharArray()) {
            if (countChars(c, password) >= 3) {
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
