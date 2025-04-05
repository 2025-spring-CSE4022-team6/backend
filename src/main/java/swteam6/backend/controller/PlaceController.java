package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swteam6.backend.dto.response.ApiResponse;
import swteam6.backend.service.PlaceService;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    //[GET] 메인화면 조회
    
    //[GET] 음식점 상세페이지 조회
}
