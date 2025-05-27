package swteam6.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import swteam6.backend.enums.Tag;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Place place;

    @Column(nullable = false)
    private String title;

    private String comment;

    @Column(nullable = false)
    private double score;

    //태그 목록
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewTag> reviewTags = new ArrayList<>();

    public Review(User user, Place place, String title, String comment, double score) {
        this.user = user;
        this.place = place;
        this.title = title;
        this.comment = comment;
        this.score = score;
    }

    public List<Tag> getTags() {
        return reviewTags.stream()
                .map(ReviewTag::getTag)
                .collect(Collectors.toList());
    }


}
