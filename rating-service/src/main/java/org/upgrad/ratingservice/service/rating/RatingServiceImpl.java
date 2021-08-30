package org.upgrad.ratingservice.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upgrad.ratingservice.dao.RatingDao;
import org.upgrad.ratingservice.model.dto.RatingDto;
import org.upgrad.ratingservice.model.entity.RatingInfoEntity;
import org.upgrad.ratingservice.model.mapper.RatingMapper;

import java.io.IOException;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    RatingDao ratingDao;
    RestTemplate restTemplate;

    @Autowired
    public RatingServiceImpl(RatingDao ratingDao, RestTemplate restTemplate) {
        this.ratingDao = ratingDao;
        this.restTemplate = restTemplate;
    }

    /**
     * Updating the rating for the Doctor id  and putting the comment as well
     * @param ratingDto: doctor id, comment
     * @return
     */
    public RatingInfoEntity updateRating(RatingDto ratingDto) {
        var info = RatingMapper.convertDTOToEntity(ratingDto);
        UUID uuid = UUID.randomUUID();
        info.setId(uuid.toString());

        return ratingDao.save(info);
    }
}
