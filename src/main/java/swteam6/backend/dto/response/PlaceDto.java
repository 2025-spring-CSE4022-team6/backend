package swteam6.backend.dto.response;

import lombok.*;
import swteam6.backend.entity.Place;
import swteam6.backend.entity.Review;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {
    private String name;
    private double score;
    private List<SimpleReviewDto> reviews;
}
