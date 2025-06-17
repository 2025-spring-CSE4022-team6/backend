package swteam6.backend.dto.response;

import lombok.Getter;
import swteam6.backend.entity.Review;
import swteam6.backend.entity.Place;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewDetailDto {
    private final Long id;
    private final String title;
    private final String comment;
    private final double score;
    private final String location;
    private final String cuisine;
    private final String imagePath;
    private final double latitude;
    private final double longitude;
    private final int totalReviews;
    private final String writer;
    private final String placeName;
    private final List<String> tags;

    public ReviewDetailDto(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.comment = review.getComment();
        this.score = review.getScore();

        Place place = review.getPlace();
        this.location = place.getLocation();
        this.cuisine = place.getCuisine().name(); // Enum → 문자열
        this.imagePath = place.getImagePath();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
        this.totalReviews = place.getTotalReviews();

        this.writer = review.getUser().getNickname();
        this.placeName = review.getPlace().getName();
        this.tags = review.getTags().stream()
                .map(tag -> tag.getDescription()) // enum -> 문자열
                .collect(Collectors.toList());
    }
}
