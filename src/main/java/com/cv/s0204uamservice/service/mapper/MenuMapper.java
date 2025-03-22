package com.cv.s0204uamservice.service.mapper;

import com.cv.s01coreservice.service.mapper.generic.GenericMapper;
import com.cv.s0202uamservicepojo.dto.MenuDto;
import com.cv.s0202uamservicepojo.entity.Menu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper extends GenericMapper<MenuDto, Menu> {
}
