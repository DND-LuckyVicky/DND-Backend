package com.luckyvicky.dndbackend.hn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "OdsayOpenFeign", url = "ODSAY_API_URI")
public interface OdsayOpenFeign {

    @GetMapping
    OdsaySearchResponse searchPublicTransportation(
        URI uri,
        @SpringQueryMap OdsaySearchRequest odsaySearchRequest
    );
}
