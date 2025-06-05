package swteam6.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swteam6.backend.dto.request.ReviewCreateDto;
import swteam6.backend.dto.response.ReviewResponseDto;
import swteam6.backend.entity.Place;
import swteam6.backend.entity.Review;
import swteam6.backend.entity.ReviewTag;
import swteam6.backend.entity.User;
import swteam6.backend.enums.Tag;
import swteam6.backend.exception.PlaceNotFoundException;
import swteam6.backend.exception.UserNotFoundException;
import swteam6.backend.exception.ReviewNotFoundException;
import swteam6.backend.repository.PlaceRepository;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private PlaceRepository placeRepository;
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        userRepository = mock(UserRepository.class);
        placeRepository = mock(PlaceRepository.class);
        reviewService = new ReviewService(placeRepository, reviewRepository, userRepository);
    }

    @Test
    void createReview_shouldReturnReviewResponseDto_whenDataIsValid() {
        // given
        Long placeId = 10L;
        String userEmail = "tester@example.com";

        Place place = new Place();
        place.setId(placeId);
        place.setName("Sample Place");
        place.setLocation("Incheon");
        place.setCuisine(null);
        place.setLatitude(0.0);
        place.setLongitude(0.0);
        place.setScore(0.0);
        place.setTotalReviews(0);
        place.setImagePath(null);

        User user = new User();
        user.setId(5L);
        user.setEmail(userEmail);

        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        ReviewCreateDto requestDto = new ReviewCreateDto();
        requestDto.setTitle("Great!");
        requestDto.setComment("Really enjoyed the food.");
        requestDto.setScore(4.5);
        requestDto.setTags(List.of(Tag.화장실굿, Tag.안주맛집));

        // Prepare a Review instance that the mock repository will return
        List<ReviewTag> reviewTags = new ArrayList<>();
        Review savedReview = new Review(
                123L,
                user,
                place,
                requestDto.getTitle(),
                requestDto.getComment(),
                requestDto.getScore(),
                reviewTags
        );
        // Manually add ReviewTag entries
        reviewTags.add(new ReviewTag(savedReview, Tag.화장실굿));
        reviewTags.add(new ReviewTag(savedReview, Tag.안주맛집));

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        // when
        ReviewResponseDto responseDto = reviewService.createReview(placeId, userEmail, requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals(123L, responseDto.getId());
        assertEquals(5L, responseDto.getUserId());
        assertEquals(placeId, responseDto.getPlaceId());
        assertEquals("Great!", responseDto.getTitle());
        assertEquals("Really enjoyed the food.", responseDto.getComment());
        assertEquals(4.5, responseDto.getScore());
        assertEquals(2, responseDto.getTags().size());
        assertTrue(responseDto.getTags().contains(Tag.화장실굿));
        assertTrue(responseDto.getTags().contains(Tag.안주맛집));

        verify(placeRepository, times(1)).findById(placeId);
        verify(userRepository, times(1)).findByEmail(userEmail);
        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(placeRepository, times(1)).save(place);
    }

    @Test
    void createReview_shouldThrowPlaceNotFoundException_whenPlaceDoesNotExist() {
        Long missingPlaceId = 999L;
        String userEmail = "tester@example.com";
        when(placeRepository.findById(missingPlaceId)).thenReturn(Optional.empty());

        ReviewCreateDto dummyDto = new ReviewCreateDto();
        dummyDto.setTitle("X");
        dummyDto.setComment("Y");
        dummyDto.setScore(1.0);
        dummyDto.setTags(List.of());

        PlaceNotFoundException ex = assertThrows(PlaceNotFoundException.class,
                () -> reviewService.createReview(missingPlaceId, userEmail, dummyDto)
        );
        assertEquals("해당 장소가 존재하지 않습니다.", ex.getMessage());

        verify(placeRepository, times(1)).findById(missingPlaceId);
        verify(userRepository, never()).findByEmail(any());
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void createReview_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        Long placeId = 20L;
        String missingEmail = "noone@example.com";

        Place place = new Place();
        place.setId(placeId);
        place.setName("Another Place");
        place.setLocation("Gwangju");
        place.setCuisine(null);
        place.setLatitude(0.0);
        place.setLongitude(0.0);
        place.setScore(0.0);
        place.setTotalReviews(0);
        place.setImagePath(null);

        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(userRepository.findByEmail(missingEmail)).thenReturn(Optional.empty());

        ReviewCreateDto dummyDto = new ReviewCreateDto();
        dummyDto.setTitle("X");
        dummyDto.setComment("Y");
        dummyDto.setScore(2.0);
        dummyDto.setTags(List.of());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> reviewService.createReview(placeId, missingEmail, dummyDto)
        );
        assertEquals("해당 사용자가 존재하지 않습니다.", ex.getMessage());

        verify(placeRepository, times(1)).findById(placeId);
        verify(userRepository, times(1)).findByEmail(missingEmail);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void findById_shouldReturnReview_whenReviewExists() {
        Long reviewId = 55L;

        User user = new User();
        user.setId(7L);
        user.setEmail("abc@xyz.com");

        Place place = new Place();
        place.setId(30L);
        place.setName("Some Place");
        place.setLocation("Daejeon");
        place.setCuisine(null);
        place.setLatitude(0.0);
        place.setLongitude(0.0);
        place.setScore(0.0);
        place.setTotalReviews(0);
        place.setImagePath(null);

        List<ReviewTag> tags = new ArrayList<>();
        Review existing = new Review(reviewId, user, place, "Hello", "Nice!", 5.0, tags);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existing));

        Review returned = reviewService.findById(reviewId);

        assertNotNull(returned);
        assertEquals(reviewId, returned.getId());
        assertEquals("Hello", returned.getTitle());
        assertEquals("Nice!", returned.getComment());

        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void findById_shouldThrowReviewNotFoundException_whenReviewDoesNotExist() {
        Long missingReviewId = 12345L;
        when(reviewRepository.findById(missingReviewId)).thenReturn(Optional.empty());

        ReviewNotFoundException ex = assertThrows(ReviewNotFoundException.class,
                () -> reviewService.findById(missingReviewId)
        );
        assertEquals("해당 ID의 리뷰를 찾을 수 없습니다", ex.getMessage());

        verify(reviewRepository, times(1)).findById(missingReviewId);
    }
}
