package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.RecommentRequestDto;
import com.facebook.facebookclone.model.Recomment;
import com.facebook.facebookclone.repository.RecommentLikeItRepository;
import com.facebook.facebookclone.repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Recomment recomment = recommentRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        recomment.recommentUpdate(requestDto);
    }

    public List<Recomment> recommentLikeItCounter(List<Recomment> recommentList, String username) {
        for (Recomment value : recommentList) {
            Long recommentId = value.getId();
            Long likesCount = recommentLikeItRepository.countByRecommentId(recommentId);

            value.addRecommentLikeItCount(likesCount);

            value.changeLikeItChecker(recommentLikeItRepository.findByUsernameAndRecommentId(username, recommentId).isPresent());
        }
        return recommentList;
    }

    @Transactional
    public void deleteRecomment(String username, Long commentId) {
        recommentRepository.deleteByUsernameAndCommentId(username, commentId);
    }
}
