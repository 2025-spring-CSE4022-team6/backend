package swteam6.backend.dto.response;

import lombok.*;
import swteam6.backend.entity.Place;
import swteam6.backend.enums.Cuisine;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceResponse {
    private Long id;
    private String name;
    private double score;
    private String location;
    private Cuisine cuisine;
    private String imagePath;
    private List<SimpleReviewDto> reviews;


    public static PlaceResponse of(Place place) {
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .location(place.getLocation())
                .score(place.getScore())
                .cuisine(place.getCuisine())
                .imagePath(place.getImagePath())
                .build();
    }
}
