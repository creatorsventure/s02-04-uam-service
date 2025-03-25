package com.cv.s0204uamservice.service.implementation;

import com.cv.s01coreservice.exception.ExceptionComponent;
import com.cv.s0202uamservicepojo.dto.PasswordDto;
import com.cv.s0202uamservicepojo.entity.Password;
import com.cv.s0202uamservicepojo.entity.UserDetail;
import com.cv.s0204uamservice.constant.UAMConstant;
import com.cv.s0204uamservice.repository.UserRepository;
import com.cv.s0204uamservice.service.intrface.PasswordService;
import com.cv.s0204uamservice.service.intrface.TokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = UAMConstant.APP_NAVIGATION_API_PASSWORD)
@Transactional(rollbackOn = Exception.class)
public class PasswordServiceImplementation implements PasswordService {

    private final ExceptionComponent exceptionComponent;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @CacheEvict(keyGenerator = "cacheKeyGenerator", allEntries = true)
    @Override
    public boolean change(PasswordDto passwordDto) {

        UserDetail userDetail = userRepository.findByUserId(passwordDto.getUserId())
                .orElseThrow(() -> exceptionComponent.expose("app.code.003", true));

        if (!userDetail.isStatus()) {
            throw exceptionComponent.expose("app.code.003", true);
        }

        Password password = userDetail.getPassword();
        if (password == null || !password.getHashPassword().equals(passwordDto.getOldPassword())) {
            throw exceptionComponent.expose("app.code.003", true);
        }

        password.setEncryptedPassword(passwordDto.getPassword());
        password.setHashPassword(passwordDto.getPassword());

        userRepository.save(userDetail);

        return true;
    }

    @Override
    public boolean forgot(PasswordDto passwordDto) {

        UserDetail userDetail = userRepository.findByEmail(passwordDto.getEmail())
                .orElseThrow(() -> exceptionComponent.expose("app.code.003", true));

        String resetToken = tokenService.generateToken(userDetail.getUserId());
        String resetLink = "http://frontend-url/reset-password?token=" + resetToken;

        sendPasswordResetEmail(userDetail.getEmail(), resetLink);

        return true;
    }

    @Override
    public boolean resetByAdmin(PasswordDto passwordDto) {

        UserDetail userDetail = userRepository.findByUserId(passwordDto.getUserId())
                .orElseThrow(() -> exceptionComponent.expose("app.code.003", true));

        String resetToken = tokenService.generateToken(userDetail.getUserId());
        String resetLink = "http://frontend-url/reset-password?token=" + resetToken;

        sendPasswordResetEmail(userDetail.getEmail(), resetLink);

        return true;
    }

    @CacheEvict(keyGenerator = "cacheKeyGenerator", allEntries = true)
    @Override
    public boolean reset(PasswordDto passwordDto) {

        String userId = tokenService.validateToken(passwordDto.getToken());
        if (userId == null) {
            throw exceptionComponent.expose("app.code.005", true);
        }

        UserDetail userDetail = userRepository.findByUserId(userId)
                .orElseThrow(() -> exceptionComponent.expose("app.code.003", true));

        Password password = userDetail.getPassword();
        if (password == null) {
            password = new Password();
            userDetail.setPassword(password);
        }

        password.setEncryptedPassword(passwordDto.getPassword());
        password.setHashPassword(passwordDto.getPassword());

        userRepository.save(userDetail);

        return true;
    }

    //Mocked Functions: Starts
    public void sendPasswordResetEmail(String email, String message) {
        System.out.println("Mail sent successfully" + email + ": " + message);
    }
    //Mocked Functions: Ends

}
