package com.cv.s0204uamservice.service.implementation;

import com.cv.s01coreservice.constant.ApplicationConstant;
import com.cv.s01coreservice.dto.PaginationDto;
import com.cv.s01coreservice.exception.ExceptionComponent;
import com.cv.s01coreservice.service.function.StaticFunction;
import com.cv.s01coreservice.util.StaticUtil;
import com.cv.s0202uamservicepojo.dto.PermissionDto;
import com.cv.s0202uamservicepojo.entity.Permission;
import com.cv.s0204uamservice.constant.UAMConstant;
import com.cv.s0204uamservice.repository.PermissionRepository;
import com.cv.s0204uamservice.service.intrface.PermissionService;
import com.cv.s0204uamservice.service.mapper.PermissionMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = UAMConstant.APP_NAVIGATION_API_PERMISSION)
@Transactional(rollbackOn = Exception.class)
public class PermissionServiceImplementation implements PermissionService {
    private final PermissionRepository repository;
    private final PermissionMapper mapper;
    private final ExceptionComponent exceptionComponent;

    @CacheEvict(keyGenerator = "cacheKeyGenerator", allEntries = true)
    @Override
    public PermissionDto create(PermissionDto dto) throws Exception {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @CacheEvict(keyGenerator = "cacheKeyGenerator", allEntries = true)
    @Override
    public PermissionDto update(PermissionDto dto) throws Exception {
        return mapper.toDto(repository.findById(dto.getId()).map(entity -> {
            BeanUtils.copyProperties(dto, entity);
            repository.save(entity);
            return entity;
        }).orElseThrow(() -> exceptionComponent.expose("app.code.004", true)));
    }

    @CacheEvict(keyGenerator = "cacheKeyGenerator", allEntries = true)
    @Override
    public Boolean updateStatus(String id, boolean status) throws Exception {
        return repository.findById(id).map(entity -> {
            entity.setStatus(status);
            repository.save(entity);
            return true;
        }).orElseThrow(() -> exceptionComponent.expose("app.code.004", true));
    }

    @Cacheable(keyGenerator = "cacheKeyGenerator")
    @Override
    public PermissionDto readOne(String id) throws Exception {
        return mapper.toDto(repository.findByIdAndStatus(id, ApplicationConstant.APPLICATION_STATUS_ACTIVE, Permission.class).orElseThrow(() -> exceptionComponent.expose("app.code.004", true)));
    }

    @CacheEvict(keyGenerator = "cacheKeyGenerator", allEntries = true)
    @Override
    public Boolean delete(String id) throws Exception {
        repository.deleteById(id);
        return true;
    }

    @Cacheable(keyGenerator = "cacheKeyGenerator")
    @Override
    public PaginationDto readAll(PaginationDto dto) throws Exception {
        Page<Permission> page;
        if (StaticUtil.isSearchRequest(dto.getSearchField(), dto.getSearchValue())) {
            page = repository.findAll(repository.searchSpec(dto.getSearchField(), dto.getSearchValue()), StaticFunction.generatePageRequest.apply(dto));
        } else {
            page = repository.findAll(StaticFunction.generatePageRequest.apply(dto));
        }
        dto.setTotal(page.getTotalElements());
        dto.setResult(page.stream().map(mapper::toDto).collect(Collectors.toList()));
        return dto;
    }

    @Cacheable(keyGenerator = "cacheKeyGenerator")
    @Override
    public Map<String, String> readIdAndNameMap() throws Exception {
        return repository.findAllByStatus(ApplicationConstant.APPLICATION_STATUS_ACTIVE, Permission.class).stream().collect(Collectors.toMap(Permission::getId, Permission::getName));
    }
}
