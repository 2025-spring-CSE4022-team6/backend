package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swteam6.backend.dto.response.ApiResponse;
import swteam6.backend.dto.response.PlaceResponse;
import swteam6.backend.service.PlaceService;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://13.124.170.215:3000"}, allowCredentials = "true")
@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    //[GET] 메인화면 조회
    @GetMapping()
    public ResponseEntity<ApiResponse> getPlaces(){
        List<PlaceResponse> response=placeService.getPlaces();
        return ResponseEntity.ok(new ApiResponse(true,200,"메인화면 음식점 목록 조회 성공",response));

    }


    
    //[GET]음식점 상세페이지 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlaceResponse>> getPlaceList(@PathVariable("id") Long id){
        PlaceResponse response=placeService.getPlaceList(id);
        return ResponseEntity.ok(new ApiResponse<>(true,200,"음식점 상세 리뷰 조회 성공",response));
    }
}
