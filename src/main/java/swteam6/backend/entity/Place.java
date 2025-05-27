package swteam6.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import swteam6.backend.enums.Cuisine;

import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="place_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable=false)
    private String location;

    @Column(nullable=false)
    private double latitude;

    @Column(nullable=false)
    private double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Cuisine cuisine;

    @Column(nullable = true)
    private String imagePath;
    
    private double score; //평균별점

    @Column(nullable=false)
    private int totalReviews;


    public void updateTotalReviews(int change){
        this.totalReviews+=change;
    }


}
