package com.cv.s0204uamservice.service.implementation;

import com.cv.s01coreservice.constant.ApplicationConstant;
import com.cv.s01coreservice.dto.PaginationDto;
import com.cv.s01coreservice.exception.ExceptionComponent;
import com.cv.s01coreservice.service.function.StaticFunction;
import com.cv.s01coreservice.util.StaticUtil;
import com.cv.s0202uamservicepojo.dto.MenuOwnerDto;
import com.cv.s0202uamservicepojo.entity.MenuOwner;
import com.cv.s0204uamservice.constant.UAMConstant;
import com.cv.s0204uamservice.repository.MenuOwnerRepository;
import com.cv.s0204uamservice.service.intrface.MenuOwnerService;
import com.cv.s0204uamservice.service.mapper.MenuOwnerMapper;
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
@CacheConfig(cacheNames = UAMConstant.APP_NAVIGATION_API_MENU_OWNER)
@Transactional(rollbackOn = Exception.class)
public class MenuOwnerServiceImplementation implements MenuOwnerService {
    private final MenuOwnerRepository repository;
    private final MenuOwnerMapper mapper;
    private final ExceptionComponent exceptionComponent;

    @CacheEvict(keyGenerator = "cacheKeyGenerator", allEntries = true)
    @Override
    public MenuOwnerDto create(MenuOwnerDto dto) throws Exception {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @CacheEvict(keyGenerator = "cacheKeyGenerator", allEntries = true)
    @Override
    public MenuOwnerDto update(MenuOwnerDto dto) throws Exception {
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
    public MenuOwnerDto readOne(String id) throws Exception {
        return mapper.toDto(repository.findByIdAndStatus(id, ApplicationConstant.APPLICATION_STATUS_ACTIVE, MenuOwner.class).orElseThrow(() -> exceptionComponent.expose("app.code.004", true)));
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
        Page<MenuOwner> page;
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
        return repository.findAllByStatus(ApplicationConstant.APPLICATION_STATUS_ACTIVE, MenuOwner.class).stream().collect(Collectors.toMap(MenuOwner::getId, MenuOwner::getName));
    }
}
