package com.cv.s0204uamservice.service.implementation;

import com.cv.s01coreservice.constant.ApplicationConstant;
import com.cv.s01coreservice.dto.PaginationDto;
import com.cv.s01coreservice.exception.ExceptionComponent;
import com.cv.s01coreservice.service.function.StaticFunction;
import com.cv.s01coreservice.util.StaticUtil;
import com.cv.s0202uamservicepojo.dto.UserDetailDto;
import com.cv.s0202uamservicepojo.entity.UserDetail;
import com.cv.s0204uamservice.repository.UserRepository;
import com.cv.s0204uamservice.service.intrface.UserService;
import com.cv.s0204uamservice.service.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UserServiceImplementation implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final ExceptionComponent exceptionComponent;

    @Override
    public UserDetailDto create(UserDetailDto dto) throws Exception {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public UserDetailDto update(UserDetailDto dto) throws Exception {
        return mapper.toDto(repository.findById(dto.getId()).map(entity -> {
            BeanUtils.copyProperties(dto, entity);
            repository.save(entity);
            return entity;
        }).orElseThrow(() -> exceptionComponent.expose("app.message.failure.003", true)));
    }

    @Override
    public Boolean updateStatus(String id, boolean status) throws Exception {
        return repository.findById(id).map(entity -> {
            entity.setStatus(status);
            repository.save(entity);
            return true;
        }).orElseThrow(() -> exceptionComponent.expose("app.message.failure.003", true));
    }

    @Override
    public UserDetailDto readOne(String id) throws Exception {
        return mapper.toDto(repository
                .findByIdAndStatus(id, ApplicationConstant.APPLICATION_STATUS_ACTIVE, UserDetail.class)
                .orElseThrow(() -> exceptionComponent.expose("app.message.failure.003", true)));

    }

    @Override
    public Boolean delete(String id) throws Exception {
        repository.deleteById(id);
        return true;
    }

    @Override
    public PaginationDto readAll(PaginationDto dto) throws Exception {
        Page<UserDetail> page;
        if (StaticUtil.isSearchRequest(dto.getSearchField(), dto.getSearchValue())) {
            page = repository.findAll(repository.searchSpec(dto.getSearchField(), dto.getSearchValue()),
                    StaticFunction.generatePageRequest.apply(dto));
        } else {
            page = repository.findAll(StaticFunction.generatePageRequest.apply(dto));
        }
        dto.setTotal(page.getTotalElements());
        dto.setResult(page.stream().map(entity -> mapper.toDto(entity)).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public Map<String, String> readIdAndNameMap() throws Exception {
        return repository.findAllByStatus(ApplicationConstant.APPLICATION_STATUS_ACTIVE, UserDetail.class).stream()
                .collect(Collectors.toMap(UserDetail::getId, UserDetail::getName));
    }

}
