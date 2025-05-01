package swteam6.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import swteam6.backend.entity.Review;

@Getter
public class ReviewResponseDto {

    private Long id;
    private Long userId;
    private Long placeId;
    private String title;
    private String comment;
    private double score;

    @Builder
    public ReviewResponseDto(Long id, Long userId, Long placeId, String title, String comment, double score){
        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
        this.title = title;
        this.comment = comment;
        this.score = score;
    }

    public static ReviewResponseDto fromEntity(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .placeId(review.getPlace().getId())
                .title(review.getTitle())
                .comment(review.getComment())
                .score(review.getScore())
                .build();
    }
}
