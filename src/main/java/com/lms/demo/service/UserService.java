package com.lms.demo.service;

import com.lms.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    public Long getUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String uname = userDetails.getUsername();
        long id = userRepository.findByName(uname).getId();
        return id;
    }

    /**
     * 判断是否登录
     * @return
     */
    public boolean isLogin() {
        /*if(getUserId()==null){
            return false;
        }*/
        return false;

    }

}
