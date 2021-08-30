package org.upgrad.ratingservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.upgrad.ratingservice.model.entity.RatingInfoEntity;

public interface RatingDao  extends MongoRepository<RatingInfoEntity, String> {
}
