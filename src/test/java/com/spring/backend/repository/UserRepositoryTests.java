//package com.elysium.backend.repository;
//
//import com.spring.backend.models.backend.User;
//
//import jakarta.persistence.criteria.Predicate;
//
//import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserRepositoryTests {
//    @Autowired
//    private UserRepository repository;
//
//    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTests.class);
//
//    @Test
//    void findByUserId(){
//        Optional<User> user = repository.findByUserId("ASH");
//        user.ifPresent(value -> logger.info(() -> "User: " + value.getUserName()));
//    }
//
//    @Test
//    void encodePassword(){
//        String encodePwd = repository.encodePassword("aa");
//        logger.info(() -> "Encoded Password: " + encodePwd);
//    }
//
//    @Test
//    void findAllTest(){
//
//    	String userId = "bb";
//    	String userGb = "31";
//    	String useYn = "1";
//
//        Sort sortSearchDic = Sort.by(
//                Sort.Order.asc("userId")
//        );
//        Pageable pageable = PageRequest.of(1 - 1, 30, sortSearchDic); // 페이지 번호, 페이지 크기, 정렬 기준 필드
//
//        Specification<User> spec = (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if(userId != null && !"".equals(userId) && !userId.isEmpty()) {
//            	predicates.add(cb.like(cb.upper(root.get("userId")), "%" + userId + "%"));
//            }
//            if(userGb != null && !"".equals(userGb) && !userGb.isEmpty()) {
//            	predicates.add(cb.equal(root.get("userGb"), userGb));
//            }
//            if(useYn != null && !"".equals(useYn) && !useYn.isEmpty()) {
//            	predicates.add(cb.equal(root.get("useYn"), useYn));
//            }
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//        Page<User> userReg= repository.findAll(spec,pageable);
//
//        assertThat(userReg.getContent().size()).isGreaterThan(0);
//        logger.info(() -> "userReg Size: " + userReg.getContent().size());
//    }
//
//    @Test
//    @Transactional
//    void saveBaseCodeTest() {
//
//    	User userReg = new User();
//
//    	userReg.setUserId("aa");
//    	userReg.setUserName("asdf");
//
//    	userReg.setPasswd("1234");
//    	userReg.setUserGb("31");
//    	userReg.setUseYn("1");
//    	userReg.setInsertId("aa");
//    	userReg.setModifyId("aa");
//
//    	User userRegSave = repository.save(userReg);
//
//    	assertThat(userRegSave).isNotNull();
//    	assertThat(userRegSave.getUserId()).isEqualTo("asdf");
//
//    }
//}
