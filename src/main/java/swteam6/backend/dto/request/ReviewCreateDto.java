package swteam6.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swteam6.backend.enums.Tag;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewCreateDto {
    private String title;
    private String comment;
    private double score;
    private List<Tag> tags;
}
