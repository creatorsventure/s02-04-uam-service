package com.cv.s0204uamservice.service.implementation;

import com.cv.s01coreservice.exception.ExceptionComponent;
import com.cv.s0202uamservicepojo.entity.Token;
import com.cv.s0204uamservice.repository.TokenRepository;
import com.cv.s0204uamservice.service.intrface.TokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TokenServiceImplementation implements TokenService {

    private final TokenRepository repository;
    private final ExceptionComponent exceptionComponent;

    @Override
    public String generateToken(String userId) {
        String token = UUID.randomUUID().toString();
        Token tokenEntity = Token.builder().userId(userId).token(token).expiryDate(LocalDateTime.now().plusMinutes(30)).build();
        repository.save(tokenEntity);
        return token;
    }

    @Override
    public String validateToken(String token) {

        Token tokenEntity = repository.findByToken(token)
                .orElseThrow(() -> exceptionComponent.expose("app.code.005", true));

        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            repository.delete(tokenEntity);
            throw exceptionComponent.expose("app.code.006", true);
        }

        return tokenEntity.getUserId();

    }

    @Scheduled(fixedRate = 3600000)
    @Override
    public void cleanExpiredTokens() {

        repository.deleteByExpiryDateBefore(LocalDateTime.now());

    }

}
