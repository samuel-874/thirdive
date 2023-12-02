package com.jme.shareride.service.review;

import com.jme.shareride.dto.ReviewRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    ResponseEntity reviewDriver(HttpServletRequest request, ReviewRequest reviewRequest);
}
