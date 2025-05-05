package swteam6.backend.dto.response;

import lombok.Getter;
import swteam6.backend.entity.Review;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewDetailDto {
    private final Long id;
    private final String title;
    private final String comment;
    private final double score;
    private final String writer;
    private final String placeName;
    private final List<String> tags;

    public ReviewDetailDto(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.comment = review.getComment();
        this.score = review.getScore();
        this.writer = review.getUser().getNickname();
        this.placeName = review.getPlace().getName();
        this.tags = review.getTags().stream()
                .map(tag -> tag.getTag().toString()) // enum -> 문자열
                .collect(Collectors.toList());
    }
}
