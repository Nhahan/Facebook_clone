package com.facebook.facebookclone.security;

import com.facebook.facebookclone.model.User;
import com.facebook.facebookclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new UsernameNotFoundException(emailAddress + " 찾을 수 없습니다."));

        return new UserDetailsImpl(user);
    }
}