package com.cv.s0204uamservice.repository;

import com.cv.s0202uamservicepojo.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, String> {

}
