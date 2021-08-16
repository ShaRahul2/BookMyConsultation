package org.upgrad.userservice.service.aws;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

    void uploadFile(String userId, MultipartFile multipartFile);
}
