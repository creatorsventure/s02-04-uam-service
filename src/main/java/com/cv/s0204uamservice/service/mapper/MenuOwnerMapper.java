package com.cv.s0204uamservice.service.mapper;

import com.cv.s01coreservice.service.mapper.generic.GenericMapper;
import com.cv.s0202uamservicepojo.dto.MenuOwnerDto;
import com.cv.s0202uamservicepojo.entity.MenuOwner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuOwnerMapper extends GenericMapper<MenuOwnerDto, MenuOwner> {
}
