package com.cv.s0204uamservice.service.intrface;


import com.cv.s01coreservice.service.intrface.generic.GenericService;
import com.cv.s0202uamservicepojo.dto.MenuDto;
import com.cv.s0202uamservicepojo.dto.MenuTreeDto;

import java.util.List;

public interface MenuService extends GenericService<MenuDto> {

    List<MenuTreeDto> readMenuAsTree() throws Exception;
}
