package org.rangiffler.controller;

import java.util.List;
import org.rangiffler.model.CountryJson;
import org.rangiffler.service.api.GrpcGeoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

  private static final Logger LOG = LoggerFactory.getLogger(CountryController.class);
  private final GrpcGeoClient grpcGeoClient;

  @Autowired
  public CountryController(GrpcGeoClient grpcGeoClient) {
    this.grpcGeoClient = grpcGeoClient;
  }

  @GetMapping("/countries")
  public List<CountryJson> getAllCountries(@AuthenticationPrincipal Jwt principal) {
    return grpcGeoClient.getAllCountries();
  }

}
