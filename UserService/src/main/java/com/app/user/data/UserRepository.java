package com.app.user.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
