package com.example.shopping.service;

import com.example.shopping.common.error.MemberErrorCode;
import com.example.shopping.common.exception.ApiException;
import com.example.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        log.debug("사용자 로드 시도: {}", id);


        return userRepository.findByMemberId(id)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없습니다: {}", id);
                    return new ApiException(MemberErrorCode.MEMBER_NOT_FOUND);
                });
    }
}
