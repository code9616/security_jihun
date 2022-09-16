package com.study.security_jihun.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.security_jihun.domain.user.User;
import com.study.security_jihun.domain.user.UserRepository;
import com.study.security_jihun.handler.aop.annotation.Log;
import com.study.security_jihun.web.dto.auth.SignupReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Log
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = null;
		
		try {
			userEntity = userRepository.findUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username);
		}
		
		if(userEntity == null) {
			throw new UsernameNotFoundException(username + "사용자이름은 사용 할 수 없습니다.");
		}
		
		return new PrincipalDetails(userEntity);
	}
	
	@Log
	public boolean addUser(SignupReqDto signupReqDto) throws Exception {
		
		return userRepository.save(signupReqDto.toEntity()) > 0;
	}

}
