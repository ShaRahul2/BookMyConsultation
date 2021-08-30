package org.upgrad.ratingservice.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rating")
public class RatingInfoEntity {
    private String id;
    private String doctorId;
    private String rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
