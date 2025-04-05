package swteam6.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swteam6.backend.entity.Place;
import swteam6.backend.repository.PlaceRepository;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    //메인화면 조회
    public List<Place> getPlaceList() {
        return placeRepository.findAll();
    }

    //음식점 상세페이지 조회
    public Place getPlaceDetail(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 술집이 없습니다"));
    }
}
