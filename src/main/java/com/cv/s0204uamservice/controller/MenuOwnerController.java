package com.cv.s0204uamservice.controller;

import com.cv.s01coreservice.constant.ApplicationConstant;
import com.cv.s01coreservice.controller.generic.GenericController;
import com.cv.s01coreservice.dto.PaginationDto;
import com.cv.s01coreservice.enumeration.APIResponseType;
import com.cv.s0202uamservicepojo.dto.MenuOwnerDto;
import com.cv.s0204uamservice.constant.UAMConstant;
import com.cv.s0204uamservice.service.intrface.MenuOwnerService;
import com.cv.s0204uamservice.util.StaticUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UAMConstant.APP_NAVIGATION_API_MENU_OWNER)
@AllArgsConstructor
@Slf4j
public class MenuOwnerController implements GenericController<MenuOwnerDto> {
    private MenuOwnerService service;

    @PostMapping
    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid MenuOwnerDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("MenuOwnerController.create {}", result.getAllErrors());
                return StaticUtil.getFailureResponse(result);
            }
            return StaticUtil.getSuccessResponse(service.create(dto), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("MenuOwnerController.create {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @PutMapping
    @Override
    public ResponseEntity<Object> update(@RequestBody @Valid MenuOwnerDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("MenuOwnerController.update {}", result.getAllErrors());
                return StaticUtil.getFailureResponse(result);
            }
            return StaticUtil.getSuccessResponse(service.update(dto), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("MenuOwnerController.update {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @GetMapping(ApplicationConstant.APP_NAVIGATION_METHOD_UPDATE_STATUS)
    @Override
    public ResponseEntity<Object> updateStatus(@RequestParam String id, @RequestParam boolean status) {
        try {
            return StaticUtil.getSuccessResponse(service.updateStatus(id, status), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("MenuOwnerController.updateStatus {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<Object> readOne(@RequestParam String id) {
        try {
            log.info("MenuOwnerController.readOne {}", id);
            return StaticUtil.getSuccessResponse(service.readOne(id), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("MenuOwnerController.readOne {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @PostMapping(ApplicationConstant.APP_NAVIGATION_METHOD_READ_PAGE)
    @Override
    public ResponseEntity<Object> readPage(@RequestBody PaginationDto dto) {
        try {
            return StaticUtil.getSuccessResponse(service.readAll(dto), APIResponseType.OBJECT_LIST);
        } catch (Exception e) {
            log.error("MenuOwnerController.readPage {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @GetMapping(ApplicationConstant.APP_NAVIGATION_METHOD_READ_ID_NAME_MAP)
    @Override
    public ResponseEntity<Object> readIdNameMapping() {
        try {
            return StaticUtil.getSuccessResponse(service.readIdAndNameMap(), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("MenuOwnerController.readIdNameMapping {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }

    @DeleteMapping
    @Override
    public ResponseEntity<Object> delete(@RequestParam String id) {
        try {
            return StaticUtil.getSuccessResponse(service.delete(id), APIResponseType.OBJECT_ONE);
        } catch (Exception e) {
            log.error("MenuOwnerController.delete {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(e);
        }
    }
}
