package swteam6.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import swteam6.backend.dto.request.ReviewCreateDto;
import swteam6.backend.dto.response.ReviewDetailDto;
import swteam6.backend.dto.response.ReviewResponseDto;
import swteam6.backend.entity.Place;
import swteam6.backend.entity.Review;
import swteam6.backend.entity.ReviewTag;
import swteam6.backend.entity.User;
import swteam6.backend.enums.Tag;
import swteam6.backend.enums.Cuisine;
import swteam6.backend.exception.PlaceNotFoundException;
import swteam6.backend.exception.ReviewNotFoundException;
import swteam6.backend.exception.UserNotFoundException;
import swteam6.backend.repository.PlaceRepository;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock PlaceRepository placeRepository;
    @Mock ReviewRepository reviewRepository;
    @Mock UserRepository userRepository;
    @InjectMocks ReviewService reviewService;

    @Test
    void createReview_success_returnsDtoAndIncrementsTotalReviews() {
        Long placeId = 10L;
        String email = "test@example.com";

        Place place = new Place();
        place.setId(placeId);
        place.setLocation("Seoul");
        place.setCuisine(Cuisine.한식);
        place.setImagePath("img");
        place.setLatitude(0.0);
        place.setLongitude(0.0);
        place.setTotalReviews(0);

        User user = new User();
        user.setId(5L);

        ReviewCreateDto dtoReq = new ReviewCreateDto();
        dtoReq.setTitle("Title");
        dtoReq.setComment("Comment");
        dtoReq.setScore(4.5);
        dtoReq.setTags(List.of(Tag.화장실굿, Tag.안주맛집));

        // stub savedReview and pre-populate tags on the returned instance
        Review savedReview = new Review(100L, user, place, "Title", "Comment", 4.5, new ArrayList<>());
        savedReview.getReviewTags().add(new ReviewTag(savedReview, Tag.화장실굿));
        savedReview.getReviewTags().add(new ReviewTag(savedReview, Tag.안주맛집));

        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        ReviewResponseDto resp = reviewService.createReview(placeId, email, dtoReq);

        assertNotNull(resp);
        assertEquals(100L, resp.getId());
        assertEquals(5L, resp.getUserId());
        assertEquals(placeId, resp.getPlaceId());
        assertEquals("Title", resp.getTitle());
        assertEquals("Comment", resp.getComment());
        assertEquals(4.5, resp.getScore());
        assertEquals(List.of(Tag.화장실굿, Tag.안주맛집), resp.getTags());
        assertEquals(1, place.getTotalReviews());
        verify(placeRepository).save(place);
    }


    @Test
    void createReview_nullTags_returnsEmptyTagList() {
        Long placeId = 11L;
        String email = "u@example.com";

        Place place = new Place();
        place.setId(placeId);
        place.setLocation("Seoul");
        place.setCuisine(Cuisine.한식);           // ← null 대신 설정
        place.setImagePath("img");
        place.setLatitude(0.0);
        place.setLongitude(0.0);
        place.setTotalReviews(0);

        User user = new User();
        user.setId(2L);

        ReviewCreateDto dtoReq = new ReviewCreateDto();
        dtoReq.setTitle("T");
        dtoReq.setComment("C");
        dtoReq.setScore(3.0);
        // dtoReq.setTags(null) 그대로 두면 됩니다.

        Review savedReview = new Review(101L, user, place, "T", "C", 3.0, new ArrayList<>());
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        ReviewResponseDto resp = reviewService.createReview(placeId, email, dtoReq);

        assertNotNull(resp);
        assertTrue(resp.getTags().isEmpty());
    }

    @Test
    void createReview_placeNotFound_throws() {
        when(placeRepository.findById(99L)).thenReturn(Optional.empty());
        ReviewCreateDto dtoReq = new ReviewCreateDto();
        dtoReq.setTitle("T");
        dtoReq.setComment("C");
        dtoReq.setScore(1.0);
        dtoReq.setTags(List.of(Tag.분위기));

        assertThrows(PlaceNotFoundException.class, () -> reviewService.createReview(99L, "e@e.com", dtoReq));
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void createReview_userNotFound_throws() {
        Long placeId = 12L;
        String email = "no@user.com";
        Place place = new Place();
        place.setId(placeId);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ReviewCreateDto dtoReq = new ReviewCreateDto();
        dtoReq.setTitle("T");
        dtoReq.setComment("C");
        dtoReq.setScore(1.0);
        dtoReq.setTags(List.of(Tag.분위기));

        assertThrows(UserNotFoundException.class, () -> reviewService.createReview(placeId, email, dtoReq));
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void getReviewDetail_success_returnsDto() {
        // given
        User user = new User();
        user.setNickname("tester");
        Place place = new Place();
        place.setName("MyPlace");

        Review review = new Review(
                55L,           // id
                user,
                place,
                "Hello",       // title
                "Nice!",       // comment
                5.0,           // score
                List.of(
                        new ReviewTag(null, Tag.분위기),
                        new ReviewTag(null, Tag.안주맛집)
                )
        );
        // 태그가 비어있거나 있으면 추가해도 됩니다.
        when(reviewRepository.findById(55L))
                .thenReturn(Optional.of(review));

        // when
        ReviewDetailDto dto = reviewService.getReviewDetail(55L);

        // then
        assertNotNull(dto);
        assertEquals(55L, dto.getId());
        assertEquals("Hello", dto.getTitle());
        assertEquals("Nice!", dto.getComment());
        assertEquals(5.0, dto.getScore());
        assertEquals("tester", dto.getWriter());
        assertEquals("MyPlace", dto.getPlaceName());
        assertEquals(List.of(
                Tag.분위기.getDescription(),
                Tag.안주맛집.getDescription()
        ), dto.getTagList());
    }

    @Test
    void getReviewDetail_notFound_throws() {
        when(reviewRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class,
                () -> reviewService.getReviewDetail(999L));
    }
}
