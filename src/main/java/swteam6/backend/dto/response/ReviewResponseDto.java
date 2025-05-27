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

    @Builder
    public ReviewResponseDto(Long id, Long userId, Long placeId, String title, String comment, double score, List<Tag> tags) {
        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
        this.title = title;
        this.comment = comment;
        this.score = score;
        this.tags = tags;
    }

    public static ReviewResponseDto fromEntity(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .placeId(review.getPlace().getId())
                .title(review.getTitle())
                .comment(review.getComment())
                .score(review.getScore())
                .tags(review.getTags())
                .build();
    }
}
