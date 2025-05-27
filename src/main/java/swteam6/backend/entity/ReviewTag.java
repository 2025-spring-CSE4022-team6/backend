package swteam6.backend.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swteam6.backend.enums.*;

@Entity
@Getter
@NoArgsConstructor
public class ReviewTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    // Review 와 Tag를 연결할 때 편리한 생성자 정의
    public ReviewTag(Review review, Tag tag) {
        this.review = review;
        this.tag = tag;
    }
}
