package swteam6.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import swteam6.backend.entity.User;

@Getter
public class UserResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private String profilePath;

    @Builder
    public UserResponseDto(Long id, String email, String nickname, String profilePath) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profilePath = profilePath;
    }

    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profilePath(user.getProfilePath())
                .build();
    }
}