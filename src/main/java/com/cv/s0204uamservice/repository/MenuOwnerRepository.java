package com.cv.s0204uamservice.repository;

import com.cv.s01coreservice.repository.generic.GenericRepository;
import com.cv.s01coreservice.repository.generic.GenericSpecification;
import com.cv.s0202uamservicepojo.entity.MenuOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOwnerRepository extends GenericRepository, GenericSpecification<MenuOwner>,
        JpaRepository<MenuOwner, String>, JpaSpecificationExecutor<MenuOwner> {
}
