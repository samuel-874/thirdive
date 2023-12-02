package com.jme.shareride.service.devileryinfoservices;

import com.jme.shareride.requests.transport.others.DeliveryInfoRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface DeliveryInfoService {

    ResponseEntity saveInfo(
            DeliveryInfoRequest infoRequest,
            HttpServletRequest request);

}
