package com.cv.s0204uamservice.controller;

import com.cv.s01coreservice.enumeration.APIResponseType;
import com.cv.s0202uamservicepojo.dto.PasswordDto;
import com.cv.s0202uamservicepojo.dto.groups.password.ChangeGroup;
import com.cv.s0202uamservicepojo.dto.groups.password.ForgotGroup;
import com.cv.s0202uamservicepojo.dto.groups.password.ResetAdminGroup;
import com.cv.s0202uamservicepojo.dto.groups.password.ResetGroup;
import com.cv.s0204uamservice.constant.UAMConstant;
import com.cv.s0204uamservice.service.intrface.PasswordService;
import com.cv.s0204uamservice.util.StaticUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UAMConstant.APP_NAVIGATION_API_PASSWORD)
@AllArgsConstructor
@Slf4j
public class PasswordController {

    private PasswordService service;

    @PutMapping(UAMConstant.APP_NAVIGATION_API_CHANGE)
    public ResponseEntity<Object> change(@Validated(ChangeGroup.class) @RequestBody @Valid PasswordDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("PasswordController.change {}", result.getAllErrors());
                return StaticUtil.getFailureResponse(result);
            }
            return StaticUtil.getSuccessResponse(service.change(dto), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("PasswordController.change {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @PostMapping(UAMConstant.APP_NAVIGATION_API_FORGOT)
    public ResponseEntity<Object> forgot(@Validated(ForgotGroup.class) @RequestBody @Valid PasswordDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("PasswordController.forgot {}", result.getAllErrors());
                return StaticUtil.getFailureResponse(result);
            }
            return StaticUtil.getSuccessResponse(service.forgot(dto), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("PasswordController.forgot {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @PostMapping(UAMConstant.APP_NAVIGATION_API_RESET_ADMIN)
    public ResponseEntity<Object> resetByAdmin(@Validated(ResetAdminGroup.class) @RequestBody @Valid PasswordDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("PasswordController.resetByAdmin {}", result.getAllErrors());
                return StaticUtil.getFailureResponse(result);
            }
            return StaticUtil.getSuccessResponse(service.resetByAdmin(dto), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("PasswordController.resetByAdmin {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @PostMapping(UAMConstant.APP_NAVIGATION_API_RESET)
    public ResponseEntity<Object> reset(@Validated(ResetGroup.class) @RequestBody @Valid PasswordDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("PasswordController.reset {}", result.getAllErrors());
                return StaticUtil.getFailureResponse(result);
            }
            return StaticUtil.getSuccessResponse(service.reset(dto), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("PasswordController.reset {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }
}