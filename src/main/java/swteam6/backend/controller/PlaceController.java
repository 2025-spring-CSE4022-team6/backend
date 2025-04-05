package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swteam6.backend.entity.Place;
import swteam6.backend.service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    //[GET] 메인화면 조회
    @GetMapping
    public ResponseEntity<List<Place>> getPlaceList() {
        return ResponseEntity.ok(placeService.getPlaceList());
    }

    //[GET] 음식점 상세페이지 조회
    public ResponseEntity<Place> getPlaceDetail(@PathVariable Long id) {
        return ResponseEntity.ok(placeService.getPlaceDetail(id));
    }
}
