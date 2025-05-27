package swteam6.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import swteam6.backend.entity.Review;

@Getter @Setter
@Builder
public class SimpleReviewDto {
    private Long id;
    private String title;
    private double score;

    public static SimpleReviewDto of(Review review) {
        return SimpleReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .score(review.getScore())
                .build();
    }
}
