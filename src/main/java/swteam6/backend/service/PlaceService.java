package swteam6.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swteam6.backend.repository.PlaceRepository;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    //메인화면 조회

    //음식점 상세페이지 조회
}
