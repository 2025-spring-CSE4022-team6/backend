package swteam6.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swteam6.backend.entity.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
