package com.cv.s0204uamservice.repository;

import com.cv.s01coreservice.repository.generic.GenericRepository;
import com.cv.s01coreservice.repository.generic.GenericSpecification;
import com.cv.s0202uamservicepojo.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository, GenericSpecification<UserDetail>,
        JpaRepository<UserDetail, String>, JpaSpecificationExecutor<UserDetail> {

    Optional<UserDetail> findByUserId(String userId);

    Optional<UserDetail> findByEmail(String email);

}
