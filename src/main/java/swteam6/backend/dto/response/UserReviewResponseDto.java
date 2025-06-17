package swteam6.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import swteam6.backend.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class UserReviewResponseDto {
    private Long id;
    private String title;
    private double score;
    private final List<String> tagList;
    private String placeName;

    public static UserReviewResponseDto of(Review review) {
        return UserReviewResponseDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .score(review.getScore())
                .tagList(review.getTags().stream()
                        .map(e->e.getDescription())
                        .collect(Collectors.toList()))
                .placeName(review.getPlace().getName())
                .build();
    }
}
