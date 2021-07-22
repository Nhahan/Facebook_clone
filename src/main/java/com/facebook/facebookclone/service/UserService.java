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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

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
    public Map<String, String> registerUser(SignupRequestDto requestDto) {
        Map<String, String> registerResultMap = new HashMap<>();

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordChecker = requestDto.getPasswordChecker();
        String emailAddress = requestDto.getEmailAddress();

        String pattern = ".*\\d.*";

        Optional<User> found = userRepository.findByEmailAddress(emailAddress);
        if (!password.equals(passwordChecker)) {
            registerResultMap.put("msg", "password와 passwordChecker가 다릅니다.");
            return registerResultMap;
//            throw new IllegalArgumentException("password와 passwordChecker가 다릅니다.");
        } else if (found.isPresent()) {
            registerResultMap.put("msg", "중복된 EmailAddress가 존재합니다.");
            return registerResultMap;
//            throw new IllegalArgumentException("중복된 EmailAddress가 존재합니다.");
        } else if (username.matches(pattern)) {
            registerResultMap.put("msg", "이름에 숫자가 들어갈 수 없습니다.");
            return registerResultMap;
//            throw new IllegalArgumentException("이름에 숫자가 들어갈 수 없습니다.");
        }
        password = passwordEncoder.encode(requestDto.getPassword());
        UserRole role = UserRole.USER;
        String userIdentifier = String.valueOf(userRepository.countAllByUsernameContaining(username));

        username = String.format("%s%s", username, userIdentifier); // username에 Id값 추가하기

        User user = new User(username, password, emailAddress, role);
        userRepository.save(user);
        userProfileRepository.save(new UserProfile(username)); // 유저프로필 생성

        registerResultMap.put("msg", "회원가입 완료");
        return registerResultMap;
    }
}
