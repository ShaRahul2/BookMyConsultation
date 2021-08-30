package org.upgrad.ratingservice.service.rating;

import org.upgrad.ratingservice.model.dto.RatingDto;
import org.upgrad.ratingservice.model.entity.RatingInfoEntity;

public interface RatingService {

    RatingInfoEntity updateRating(RatingDto ratingDto);
}
