package swteam6.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swteam6.backend.dto.request.ReviewCreateDto;
import swteam6.backend.dto.response.ReviewResponseDto;
import swteam6.backend.entity.Place;
import swteam6.backend.entity.Review;
import swteam6.backend.entity.ReviewTag;
import swteam6.backend.entity.User;
import swteam6.backend.enums.Tag;
import swteam6.backend.exception.PlaceNotFoundException;
import swteam6.backend.exception.ReviewNotFoundException;
import swteam6.backend.exception.UserNotFoundException;
import swteam6.backend.repository.PlaceRepository;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    //리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long id, String email,ReviewCreateDto requestDto) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new PlaceNotFoundException("해당 장소가 존재하지 않습니다."));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 존재하지 않습니다."));

        Review review = new Review(
                null,
                user,
                place,
                requestDto.getTitle(),
                requestDto.getComment(),
                requestDto.getScore(),
                new ArrayList<>()
        );

        // 태그가 있으면 ReviewTag 엔티티로 변환해서 연결
        if (requestDto.getTags() != null) {
            for (Tag tagEnum : requestDto.getTags()) {
                review.getReviewTags().add(new ReviewTag(review, tagEnum));
            }
        }
        Review savedReview = reviewRepository.save(review);
        place.updateTotalReviews(1);
        placeRepository.save(place);
        return ReviewResponseDto.fromEntity(savedReview);
    }
    //리뷰 상세 조회
    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("해당 ID의 리뷰를 찾을 수 없습니다"));
    }
}
