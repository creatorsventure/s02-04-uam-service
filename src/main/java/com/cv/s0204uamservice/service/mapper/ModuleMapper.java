package com.cv.s0204uamservice.service.mapper;

import com.cv.s01coreservice.service.mapper.generic.GenericMapper;
import com.cv.s0202uamservicepojo.dto.ModuleDto;
import com.cv.s0202uamservicepojo.entity.Module;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleMapper extends GenericMapper<ModuleDto, Module> {
}
