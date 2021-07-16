package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.CommentRequestDto;
import com.facebook.facebookclone.dto.RecommentRequestDto;
import com.facebook.facebookclone.model.Comment;
import com.facebook.facebookclone.model.CommentLikeIt;
import com.facebook.facebookclone.model.Recomment;
import com.facebook.facebookclone.model.RecommentLikeIt;
import com.facebook.facebookclone.repository.CommentLikeItRepository;
import com.facebook.facebookclone.repository.CommentRepository;
import com.facebook.facebookclone.repository.RecommentLikeItRepository;
import com.facebook.facebookclone.repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecommentService {

    private final RecommentRepository recommentRepository;
    private final RecommentLikeItRepository recommentLikeItRepository;

    @Transactional
    public void createRecomment(RecommentRequestDto requestDto) {
        Recomment recomment = new Recomment(requestDto);
        recommentRepository.save(recomment);
    }

    @Transactional
    public void putRecomment(Long id, RecommentRequestDto requestDto) {
        Recomment recomment = recommentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        recomment.recommentUpdate(requestDto);
    }

    public List<Recomment> recommentLikeItCounter(List<Recomment> recommentList, String username) {
        for (Recomment value : recommentList) {
            Long recommentId = value.getId();
            Long likesCount = recommentLikeItRepository.countByRecommentId(recommentId);

            value.addRecommentLikeItCount(likesCount);

            Optional<RecommentLikeIt> didUsernameLikeIt = Optional.ofNullable(recommentLikeItRepository.findByUsernameAndRecommentId(username, recommentId));
            value.changeLikeItChecker(didUsernameLikeIt.isPresent());
        }
        return recommentList;
    }
}
