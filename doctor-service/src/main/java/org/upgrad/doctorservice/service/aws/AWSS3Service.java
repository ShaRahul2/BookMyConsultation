package org.upgrad.doctorservice.service.aws;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

    void uploadFile(MultipartFile multipartFile);
}
