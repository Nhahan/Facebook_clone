package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.SignupRequestDto;
import com.facebook.facebookclone.model.User;
import com.facebook.facebookclone.model.UserProfile;
import com.facebook.facebookclone.model.UserRole;
import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordChecker = requestDto.getPasswordChecker();
        String emailAddress = requestDto.getEmailAddress();

        Optional<User> found = userRepository.findByEmailAddress(emailAddress);
        if (password.length() < 4) {
            throw new IllegalArgumentException("password는 최소 4글자입니다.");
        } else if (!password.equals(passwordChecker)) {
            throw new IllegalArgumentException("password와 passwordChecker가 다릅니다.");
        } else if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 EmailAddress가 존재합니다.");
        }
        password = passwordEncoder.encode(requestDto.getPassword());
        UserRole role = UserRole.USER;
        String userIdentifier = String.valueOf(userRepository.countAllByUsernameContaining(username));

        username = String.format("%s%s", username, userIdentifier); // username에 Id값 추가하기

        User user = new User(username, password, emailAddress, role);
        userRepository.save(user);
        userProfileRepository.save(new UserProfile(username)); // 유저프로필 생성
    }
}
