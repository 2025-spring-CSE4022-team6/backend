package swteam6.backend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swteam6.backend.dto.response.PlaceDto;
import swteam6.backend.dto.response.SimpleReviewDto;
import swteam6.backend.entity.Place;
import swteam6.backend.entity.Review;
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

    //음식점 상세페이지 조회
    public PlaceDto getPlaceList(Long id) {
        Place place=placeRepository.findById(id).
                orElseThrow(()->new EntityNotFoundException("해당 ID의 장소를 찾을 수 없습니다."));


        List<Review> reviews = reviewRepository.findTop3ByPlaceOrderByIdDesc(place);

        List<SimpleReviewDto> reviewDtos = reviews.stream()
                .map(review -> SimpleReviewDto.of(review))
                .toList();

        return PlaceDto.builder()
                .name(place.getName())
                .score(place.getScore())
                .reviews(reviewDtos)
                .build();
    }

}
