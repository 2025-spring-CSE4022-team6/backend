package swteam6.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import swteam6.backend.entity.Review;
import swteam6.backend.enums.Tag;

import java.util.List;

@Getter
public class ReviewResponseDto {

    private final Long id;
    private final Long userId;
    private final Long placeId;
    private final String title;
    private final String comment;
    private final double score;
    private final List<Tag> tags;
    private String location;
    private String cuisine;
    private String imagePath;
    private double latitude;
    private double longitude;
    private int totalReviews;

    @Builder
    public ReviewResponseDto(Long id, Long userId, Long placeId, String title, String comment, double score, String location, String cuisine, String imagePath,
                             double latitude, double longitude, int totalReviews, List<Tag> tags) {
        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
        this.title = title;
        this.comment = comment;
        this.score = score;
        this.location = location;
        this.cuisine = cuisine;
        this.imagePath = imagePath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalReviews = totalReviews;
        this.tags = tags;
    }

    public static ReviewResponseDto fromEntity(Review review) {
        var place = review.getPlace();

        return ReviewResponseDto.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .placeId(review.getPlace().getId())
                .title(review.getTitle())
                .comment(review.getComment())
                .score(review.getScore())
                .location(place.getLocation())
                .cuisine(place.getCuisine().name())
                .imagePath(place.getImagePath())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .totalReviews(place.getTotalReviews())
                .tags(review.getTags())
                .build();
    }
}
