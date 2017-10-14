package com.lms.demo.bootstrap;

import com.lms.demo.domain.Role;
import com.lms.demo.domain.User;
import com.lms.demo.repository.RoleRepository;
import com.lms.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(value=1)
public class StartupRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... arg0) throws Exception {
        // TODO Auto-generated method stub

        Role role=roleRepository.findOneByName("ROLE_ADMIN");
        if(role==null){
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_NORMAL_USER"));
        }

        User user=userRepository.findByName("admin");
        if(user==null){
            user=new User();
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder(4).encode("1234"));
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            userRepository.save(user);
        }


    }

}
