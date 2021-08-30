package org.upgrad.ratingservice.model.mapper;


import org.upgrad.ratingservice.model.dto.RatingDto;
import org.upgrad.ratingservice.model.entity.RatingInfoEntity;

public class RatingMapper {

    public static RatingInfoEntity convertDTOToEntity(RatingDto dto) {
        RatingInfoEntity info = new RatingInfoEntity();
        info.setRating(dto.getRating());
        info.setDoctorId(dto.getDoctorId());

        return info;
    }
}
