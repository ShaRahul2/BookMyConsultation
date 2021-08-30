package org.upgrad.ratingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.ratingservice.model.dto.RatingDto;
import org.upgrad.ratingservice.model.entity.RatingInfoEntity;
import org.upgrad.ratingservice.service.rating.RatingService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class RatingController {

    RatingService service;

    @Autowired
    public RatingController(RatingService service) {
        this.service = service;
    }

    /**
     * This endpoint is used by the users to submit the ratings of their experience with the doctor with whom they had an appointment.
     * URI: /ratings
     * HTTP method: POST
     * Request body: doctorId, rating
     * @param ratingDto
     * @return
     */
    @PostMapping("/ratings")
    public ResponseEntity<String> doctorRegistration(@Valid @RequestBody RatingDto ratingDto) {
        RatingInfoEntity doctorInfo = this.service.updateRating(ratingDto);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}