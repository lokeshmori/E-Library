package edigest.enotes.service;

import edigest.enotes.entity.User;
import edigest.enotes.repository.UserRepository;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder  passwordEncoder;

    @Override
    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        return userRepository.save(user);
    }

    @Override
    public boolean checkUserByEmail(String email) {

         return userRepository.existsByEmail(email);

    }

    @Override
    public void removeSessionMassage() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        //session.invalidate();
        session.removeAttribute("msg");

    }

    @Override
    public User findUserByEmail(String email) {


        User user = userRepository.findByEmail(email);

        return user;
    }
}
