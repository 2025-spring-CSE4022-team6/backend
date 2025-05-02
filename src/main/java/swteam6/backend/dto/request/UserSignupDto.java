package swteam6.backend.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupDto {
    private String email;
    private String nickname;
    private String profilePath;
}
