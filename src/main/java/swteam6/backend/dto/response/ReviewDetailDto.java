package swteam6.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import swteam6.backend.entity.Review;
import swteam6.backend.entity.Place;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.yaml.snakeyaml.tokens.Token.ID.Tag;

@Builder
@Getter
public class ReviewDetailDto {
    private final Long id;
    private final String title;
    private final String comment;
    private final double score;
    private final String writer;
    private final String placeName;
    private final List<String> tagList;

    public static ReviewDetailDto of(Review review) {
        return ReviewDetailDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .comment(review.getComment())
                .score(review.getScore())
                .writer(review.getUser().getNickname())
                .placeName(review.getPlace().getName())
                .tagList(review.getTags().stream()
                        .map(e->e.getDescription())
                        .collect(Collectors.toList()))
                .build();
    }
}
