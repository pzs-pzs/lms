package com.lms.demo.service;

import com.lms.demo.domain.User;
import com.lms.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return true;
        }
        return false;

    }

    public boolean createUser(String username, String password,
                              String email){
        User user = new User();
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(4);
        user.setPassword(encoder.encode(password));
        user.setName(username);
        user.setEnabled(true);
        user.setEmail(email);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        User result = userRepository.save(user);
        if(result!=null){
            return true;
        }else{
            return false;
        }
    }

}
