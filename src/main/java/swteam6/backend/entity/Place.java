package swteam6.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import swteam6.backend.enums.Cuisine;

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
    private Cuisine cuisine;
}
