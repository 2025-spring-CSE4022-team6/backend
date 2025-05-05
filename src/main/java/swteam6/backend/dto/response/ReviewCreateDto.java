package swteam6.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateDto {
    private Long userId;
    private Long placeId;
    private String title;
    private String comment;
    private double score;
}
