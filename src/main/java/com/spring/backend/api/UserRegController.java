package com.spring.backend.api;


import com.spring.backend.models.User;
import com.spring.backend.payload.response.ApiResponse;
import com.spring.backend.payload.response.UserRegResponse;
import com.spring.backend.service.UserRegService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userReg")
public class UserRegController {


    @Autowired
    public UserRegService userRegService;

    @GetMapping("/userRetrieve")
    public ResponseEntity<?> getUserRetrieve(@RequestParam String userId,
    								  @RequestParam String userGb,
    								  @RequestParam String useYn,
						    		  @RequestParam int currentPage,
						              @RequestParam int pageSize) {

        Page<User> userRegPage = userRegService.getUserReg(userId, userGb, useYn,currentPage, pageSize);
        
        List<User> content = userRegPage.getContent();
        UserRegResponse userRegResponse = new UserRegResponse();
        userRegResponse.setUserReg(content);
        userRegResponse.setTotalCount(userRegPage.getTotalElements());
        userRegResponse.setCurrentPage(currentPage);
        userRegResponse.setPageSize(pageSize);
        
        return new ResponseEntity<>(new ApiResponse<>(0, userRegResponse), HttpStatus.OK);
    }
    
    @PutMapping("/saveUserReg")
    public  ResponseEntity<?> putUserReg(@Valid @RequestBody User user) throws Exception{
    	
    	User userRegResult =userRegService.saveUserReg(user, false) ;
   
    	return new ResponseEntity<>(new ApiResponse<>(0, userRegResult), HttpStatus.OK);
    }
    
    @PostMapping("/saveUserReg")
    public  ResponseEntity<?> postUserReg(@Valid @RequestBody User user) throws Exception{
    	
    	User userRegResult =userRegService.saveUserReg(user, true) ;
   
    	return new ResponseEntity<>(new ApiResponse<>(0, userRegResult), HttpStatus.OK);
    }

}
