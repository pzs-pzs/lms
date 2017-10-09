package com.lms.demo.service;

import com.lms.demo.domain.User;
import com.lms.demo.repository.RoleRepository;
import com.lms.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户 " + s + " 不存在");
        }
        System.out.println("s:"+s+"已经登录");
        System.out.println("username:"+user.getUsername());
        System.out.println("password:"+user.getPassword());
        return user;
    }
}
