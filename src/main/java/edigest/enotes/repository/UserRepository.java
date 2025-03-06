package edigest.enotes.repository;

import edigest.enotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    public boolean existsByEmail(String username);
    public User findByEmail(String email);

}
