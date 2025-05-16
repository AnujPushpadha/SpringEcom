package com.anuj.repository;

import com.anuj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//user is entity class long is primaty key type
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String username);
}
