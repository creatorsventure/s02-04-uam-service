package com.cv.s0204uamservice.service.mapper;

import com.cv.s01coreservice.service.mapper.generic.GenericMapper;
import com.cv.s0202uamservicepojo.dto.UserDetailDto;
import com.cv.s0202uamservicepojo.entity.UserDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDetailDto, UserDetail> {
}
