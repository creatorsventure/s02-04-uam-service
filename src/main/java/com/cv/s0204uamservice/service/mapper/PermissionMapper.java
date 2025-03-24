package com.cv.s0204uamservice.service.mapper;

import com.cv.s01coreservice.service.mapper.generic.GenericMapper;
import com.cv.s0202uamservicepojo.dto.PermissionDto;
import com.cv.s0202uamservicepojo.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends GenericMapper<PermissionDto, Permission> {
}
