package swteam6.backend.dto.response;

import lombok.Getter;
import swteam6.backend.entity.Review;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewDetailResponse {
    private Long id;
    private String title;
    private String comment;
    private Long score;
    private String writer;
    private String placeName;
    private List<String> tags;

    public ReviewDetailResponse(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.comment = review.getComment();
        this.score = review.getScore();
        this.writer = review.getUser().getNickname();         // User 엔티티에 getUsername()이 있다고 가정
        this.placeName = review.getPlace().getName();         // Place 엔티티에 getName()이 있다고 가정
        this.tags = review.getTags().stream()
                .map(tag -> tag.getTag().toString()) // enum -> 문자열
                .collect(Collectors.toList());
    }
}
