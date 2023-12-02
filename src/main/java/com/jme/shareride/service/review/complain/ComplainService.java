package com.jme.shareride.service.review.complain;

import com.jme.shareride.requests.ComplainRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ComplainService {

    ResponseEntity fileAComplain(HttpServletRequest servletRequest,ComplainRequest complainRequest);
}
