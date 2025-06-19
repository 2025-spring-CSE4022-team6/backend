package swteam6.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import swteam6.backend.dto.response.PlaceResponse;
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

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
    @Mock PlaceRepository placeRepository;
    @Mock ReviewRepository reviewRepository;
    @Mock UserRepository userRepository;
    @InjectMocks PlaceService placeService;

    @Test
    void getPlaces_noPlaces_returnsEmptyList() {
        when(placeRepository.findAll()).thenReturn(List.of());

        List<PlaceResponse> responses = placeService.getPlaces();
        assertNotNull(responses);
        assertTrue(responses.isEmpty());

        verify(placeRepository).findAll();
        verifyNoInteractions(reviewRepository);
    }

    @Test
    void getPlaces_singlePlaceNoReviews_returnsResponseWithEmptyTagList() {
        Place place = new Place();
        place.setId(1L);
        place.setName("A");
        place.setLocation("Seoul");
        place.setCuisine(Cuisine.한식);
        place.setLatitude(37.5);
        place.setLongitude(127.0);
        place.setScore(4.0);
        place.setTotalReviews(0);

        when(placeRepository.findAll()).thenReturn(List.of(place));
        when(reviewRepository.findAllByPlace(place)).thenReturn(List.of());

        List<PlaceResponse> responses = placeService.getPlaces();
        assertEquals(1, responses.size());
        PlaceResponse resp = responses.get(0);
        assertEquals(1L, resp.getId());
        assertEquals("A", resp.getName());
        assertEquals(Cuisine.한식, resp.getCuisine());
        assertEquals("Seoul", resp.getLocation());
        assertEquals(4.0, resp.getScore());
        assertEquals(0, resp.getTotalReviews());
        assertNotNull(resp.getTagList());
        assertTrue(resp.getTagList().isEmpty());

        verify(reviewRepository).findAllByPlace(place);
    }

    @Test
    void getPlaces_withReviews_returnsTopTags() {
        // prepare place
        Place place = new Place();
        place.setId(2L);

        // mutable list로 변경
        Review r1 = new Review(1L, new User(), place, "T1", "C1", 3.0, new ArrayList<>());
        r1.getReviewTags().add(new ReviewTag(r1, Tag.안주맛집));
        r1.getReviewTags().add(new ReviewTag(r1, Tag.화장실굿));

        Review r2 = new Review(2L, new User(), place, "T2", "C2", 4.0, new ArrayList<>());
        r2.getReviewTags().add(new ReviewTag(r2, Tag.안주맛집));

        when(placeRepository.findAll()).thenReturn(List.of(place));
        when(reviewRepository.findAllByPlace(place)).thenReturn(List.of(r1, r2));

        List<PlaceResponse> responses = placeService.getPlaces();
        List<String> tags = responses.get(0).getTagList();

        assertEquals(2, tags.size());
        assertEquals(Tag.안주맛집.getDescription(), tags.get(0));
        // 화장실굿 appears once, should follow 안주맛집
        assertEquals(Tag.화장실굿.getDescription(), tags.get(1));
    }

    @Test
    void getPlaceList_existingId_returnsTagListOnly() {
        Long id = 3L;
        Place place = new Place();
        place.setId(id);
        place.setTotalReviews(5);
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        // mutable list로 변경
        Review r = new Review(1L, new User(), place, "T", "C", 5.0, new ArrayList<>());
        r.getReviewTags().add(new ReviewTag(r, Tag.분위기));
        when(reviewRepository.findAllByPlace(place)).thenReturn(List.of(r));

        PlaceResponse resp = placeService.getPlaceList(id);
        assertEquals(id, resp.getId());
        assertNotNull(resp.getTagList());
        assertEquals(1, resp.getTagList().size());
        assertEquals(Tag.분위기.getDescription(), resp.getTagList().get(0));
    }

    @Test
    void getPlaceList_nonExistingId_throws() {
        when(placeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PlaceNotFoundException.class, () -> placeService.getPlaceList(99L));
        verify(placeRepository).findById(99L);
    }
}