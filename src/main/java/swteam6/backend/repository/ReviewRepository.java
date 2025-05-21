package swteam6.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swteam6.backend.entity.Place;
import swteam6.backend.entity.Review;
import swteam6.backend.entity.User;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findTop3ByPlaceOrderByIdDesc(Place place);


    //특정 user + place 에 대한 모든 리뷰를 ID 내림차순(최신 순)으로 조회
    List<Review> findByUserAndPlaceOrderByIdDesc(User user, Place place);
}
