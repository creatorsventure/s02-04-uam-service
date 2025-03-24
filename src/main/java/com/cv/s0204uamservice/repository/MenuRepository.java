package com.cv.s0204uamservice.repository;

import com.cv.s01coreservice.repository.generic.GenericRepository;
import com.cv.s01coreservice.repository.generic.GenericSpecification;
import com.cv.s0202uamservicepojo.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends GenericRepository, GenericSpecification<Menu>,
        JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu> {

    List<Menu> findAllByMenuTypeAndStatus(Integer menuType, Boolean status);

}
