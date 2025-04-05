package swteam6.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swteam6.backend.repository.PlaceRepository;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    //리뷰 작성

    //리뷰 상세 조회
}
