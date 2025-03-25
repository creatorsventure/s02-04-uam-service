package com.cv.s0204uamservice.service.intrface;


import com.cv.s01coreservice.service.intrface.generic.GenericService;
import com.cv.s0202uamservicepojo.dto.PasswordDto;

public interface PasswordService {

    boolean change(PasswordDto dto);

    boolean forgot(PasswordDto dto);

    boolean resetByAdmin(PasswordDto dto);

    boolean reset(PasswordDto passwordDto);

}
