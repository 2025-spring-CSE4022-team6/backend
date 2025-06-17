package swteam6.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import swteam6.backend.entity.User;

import java.util.List;

@Getter
@Builder
public class ProfileResponse {
    private Long id;
    private String email;
    private String nickname;
    private String profilePath;
    private List<UserReviewResponseDto> reviews;

    public static ProfileResponse of(User user, List<UserReviewResponseDto> simpleReviewDtoList) {
        return ProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profilePath(user.getProfilePath())
                .reviews(simpleReviewDtoList)
                .build();
    }
}
