package swteam6.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swteam6.backend.dto.response.PlaceResponse;
import swteam6.backend.dto.response.SimpleReviewDto;
import swteam6.backend.entity.Place;
import swteam6.backend.entity.Review;
import swteam6.backend.entity.ReviewTag;
import swteam6.backend.entity.User;
import swteam6.backend.enums.Cuisine;
import swteam6.backend.enums.Tag;
import swteam6.backend.exception.PlaceNotFoundException;
import swteam6.backend.repository.PlaceRepository;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaceServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PlaceServiceTest.class);
    private PlaceRepository placeRepository;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private PlaceService placeService;

    @BeforeEach
    void setUp() {
        placeRepository = mock(PlaceRepository.class);
        reviewRepository = mock(ReviewRepository.class);
        userRepository = mock(UserRepository.class);
        placeService = new PlaceService(placeRepository, reviewRepository, userRepository);
    }

    @Test
    void getPlaces_shouldReturnEmptyList_whenNoPlacesExist() {
        when(placeRepository.findAll()).thenReturn(List.of());

        List<PlaceResponse> responses = placeService.getPlaces();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(placeRepository, times(1)).findAll();
    }

    @Test
    void getPlaces_shouldReturnListOfPlaceResponses_whenPlacesExist() {
        // given
        Place place1 = new Place();
        place1.setId(1L);
        place1.setName("Place A");
        place1.setLocation("Seoul");
        place1.setCuisine(Cuisine.한식);
        place1.setLatitude(37.5);
        place1.setLongitude(127.0);
        place1.setScore(4.2);
        place1.setTotalReviews(0);
        place1.setImagePath("pathA");

        Place place2 = new Place();
        place2.setId(2L);
        place2.setName("Place B");
        place2.setLocation("Busan");
        place2.setCuisine(Cuisine.이자카야);
        place2.setLatitude(35.1);
        place2.setLongitude(129.0);
        place2.setScore(3.8);
        place2.setTotalReviews(0);
        place2.setImagePath("pathB");

        when(placeRepository.findAll()).thenReturn(List.of(place1, place2));
        when(reviewRepository.findAllByPlace(place1)).thenReturn(List.of());
        when(reviewRepository.findAllByPlace(place2)).thenReturn(List.of());

        // when
        List<PlaceResponse> responses = placeService.getPlaces();

        // then
        assertEquals(2, responses.size());

        PlaceResponse resp1 = responses.get(0);
        assertEquals(place1.getId(), resp1.getId());
        assertEquals("Place A", resp1.getName());
        assertEquals("Seoul", resp1.getLocation());
        assertEquals(Cuisine.한식, resp1.getCuisine());
        assertEquals(4.2, resp1.getScore());
        assertEquals(0, resp1.getTotalReviews());
        assertTrue(resp1.getTagList().isEmpty());

        PlaceResponse resp2 = responses.get(1);
        assertEquals(place2.getId(), resp2.getId());
        assertEquals("Place B", resp2.getName());
        assertEquals("Busan", resp2.getLocation());
        assertEquals(Cuisine.이자카야, resp2.getCuisine());
        assertEquals(3.8, resp2.getScore());
        assertEquals(0, resp2.getTotalReviews());
        assertTrue(resp2.getTagList().isEmpty());

        verify(placeRepository, times(1)).findAll();
        verify(reviewRepository, times(1)).findAllByPlace(place1);
        verify(reviewRepository, times(1)).findAllByPlace(place2);
    }

    @Test
    void getPlaces_shouldComputeTopTags_whenReviewsHaveTags() {
        // given: set up one place with two tagged reviews
        Place place = new Place();
        place.setId(10L);
        place.setName("Tagged Place");
        place.setLocation("Jeju");
        place.setCuisine(Cuisine.칵테일바);
        place.setLatitude(33.5);
        place.setLongitude(126.5);
        place.setScore(5.0);
        place.setTotalReviews(2);
        place.setImagePath("img");

        // create two reviews each with some tags
        Review rev1 = new Review(
                101L,
                new User(),    // User 필드는 ID만 비교하지 않으므로 간단히 new User()를 써도 됨
                place,
                "Good",
                "Really good place",
                4.0,
                new ArrayList<>()   // 빈 태그 리스트
        );
        rev1.getReviewTags().add(new ReviewTag(rev1, Tag.화장실굿));
        rev1.getReviewTags().add(new ReviewTag(rev1, Tag.안주맛집));

        Review rev2 = new Review(
                102L,
                new User(),
                place,
                "Excellent",
                "Amazing vibe",
                5.0,
                new ArrayList<>()
        );
        rev2.getReviewTags().add(new ReviewTag(rev2, Tag.안주맛집));
        rev2.getReviewTags().add(new ReviewTag(rev2, Tag.분위기));

        when(placeRepository.findAll()).thenReturn(List.of(place));
        when(reviewRepository.findAllByPlace(place)).thenReturn(List.of(rev1, rev2));

        // when
        List<PlaceResponse> responses = placeService.getPlaces();

        // then
        assertEquals(1, responses.size());
        PlaceResponse resp = responses.get(0);
        // TASTE appears twice, CLEAN and AMBIENT once each → top 3 order: TASTE, CLEAN, AMBIENT
        List<String> topTags = resp.getTagList();
        assertEquals(3, topTags.size());
        assertEquals(Tag.안주맛집.getDescription(), topTags.get(0));
        assertEquals(Tag.화장실굿.getDescription(), topTags.get(1));
        assertEquals(Tag.분위기.getDescription(), topTags.get(2));
    }

    @Test
    void getPlaceList_shouldReturnPlaceResponse_whenPlaceExists() {
        // given
        Long placeId = 100L;
        Place place = new Place();
        place.setId(placeId);
        place.setName("Test Place");
        place.setLocation("Daegu");
        place.setCuisine(Cuisine.치킨);
        place.setLatitude(35.87);
        place.setLongitude(128.6);
        place.setScore(4.5);
        place.setTotalReviews(2);
        place.setImagePath("img");

        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));

        User dummyUser = new User();
        dummyUser.setId(50L);

        Review r1 = new Review(1L, dummyUser, place, "Title1", "Comment1", 4.0, new ArrayList<>());
        Review r2 = new Review(2L, dummyUser, place, "Title2", "Comment2", 5.0, new ArrayList<>());
        Review r3 = new Review(3L, dummyUser, place, "Title3", "Comment3", 3.0, new ArrayList<>());

        when(reviewRepository.findTop3ByPlaceOrderByIdDesc(place)).thenReturn(List.of(r3, r2, r1));

        // when
        PlaceResponse response = placeService.getPlaceList(placeId);

        // then
        assertNotNull(response);
        assertEquals(placeId, response.getId());
        assertEquals("Test Place", response.getName());
        assertEquals("Daegu", response.getLocation());
        assertEquals(Cuisine.치킨, response.getCuisine());
        assertEquals(4.5, response.getScore());
        assertEquals(2, response.getTotalReviews());

        List<SimpleReviewDto> dtoList = response.getReviews();
        assertEquals(3, dtoList.size());
        assertEquals(r3.getId(), dtoList.get(0).getId());
        assertEquals(r2.getId(), dtoList.get(1).getId());
        assertEquals(r1.getId(), dtoList.get(2).getId());

        verify(placeRepository, times(1)).findById(placeId);
        verify(reviewRepository, times(1)).findTop3ByPlaceOrderByIdDesc(place);
    }

    @Test
    void getPlaceList_shouldThrowPlaceNotFoundException_whenPlaceDoesNotExist() {
        Long placeId = 999L;
        when(placeRepository.findById(placeId)).thenReturn(Optional.empty());

        PlaceNotFoundException ex = assertThrows(PlaceNotFoundException.class,
                () -> placeService.getPlaceList(placeId)
        );
        assertEquals("해당 ID의 장소를 찾을 수 없습니다.", ex.getMessage());

        verify(placeRepository, times(1)).findById(placeId);
        verify(reviewRepository, never()).findTop3ByPlaceOrderByIdDesc(any());
    }

    // -------------------------
    // DTO 객체들에 대한 단위 테스트
    // -------------------------

    @Test
    void placeResponse_of_shouldMapAllFieldsCorrectly() {
        // given: Place 엔티티 세팅
        Place place = new Place();
        place.setId(55L);
        place.setName("DTO Place");
        place.setLocation("Busan");
        place.setCuisine(Cuisine.맥주호프);
        place.setLatitude(35.1);
        place.setLongitude(129.0);
        place.setScore(3.3);
        place.setTotalReviews(5);
        place.setImagePath("image/path");

        // 태그 목록 (String) 준비
        List<String> sampleTags = List.of("TagA", "TagB");

        // when
        PlaceResponse dto = PlaceResponse.of(place, sampleTags);

        // then: 모든 필드가 Place 엔티티와 리스트 값을 따라야 함
        assertEquals(55L, dto.getId());
        assertEquals("DTO Place", dto.getName());
        assertEquals("Busan", dto.getLocation());
        assertEquals(Cuisine.맥주호프, dto.getCuisine());
        assertEquals(3.3, dto.getScore());
        assertEquals(5, dto.getTotalReviews());
        assertEquals("image/path", dto.getImagePath());
        // 위도/경도 필드 확인 (DTO 필드명은 langitude, longitude)
        assertEquals(129.0, dto.getLangitude());
        assertEquals(129.0, dto.getLongitude());
        // 태그 리스트
        assertEquals(2, dto.getTagList().size());
        assertTrue(dto.getTagList().contains("TagA"));
        assertTrue(dto.getTagList().contains("TagB"));
    }
    @Test
    void simpleReviewDto_of_shouldMapReviewFieldsCorrectly() {
        // given: Review 엔티티에 필요한 필드 세팅
        Place place = new Place();
        place.setId(7L);
        User user = new User();
        user.setId(99L);

        Review review = new Review(
            77L,
            new User(),
            place,
            "Sample Title",
            "Really good place",
            4.8,
            new ArrayList<>()
        );
        review.getReviewTags().add(new ReviewTag(review, Tag.안주맛집));
        review.getReviewTags().add(new ReviewTag(review, Tag.분위기));

        // when
        SimpleReviewDto dto = SimpleReviewDto.of(review);

        // then
        assertEquals(77L, dto.getId());
        assertEquals("Sample Title", dto.getTitle());
        assertEquals(4.8, dto.getScore());
    }
}

