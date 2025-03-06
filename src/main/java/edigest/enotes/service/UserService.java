package edigest.enotes.service;

import edigest.enotes.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean checkUserByEmail(String email);
    User findUserByEmail(String email);

    User saveUser(User user);
    void removeSessionMassage();


}
