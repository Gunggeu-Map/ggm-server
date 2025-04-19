package com.gunggeumap.ggm.question.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gunggeumap.ggm.question.service.FileUploadService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

  private final AmazonS3 amazonS3;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${spring.s3.directory}")
  private String directory;

  @Override
  public String uploadFile(MultipartFile image) throws IOException {
    if (image == null || image.isEmpty()) {
      throw new IllegalArgumentException("파일이 비어 있습니다.");
    }

    String fileName = directory + "/" + UUID.randomUUID() + "_" + image.getOriginalFilename();

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(image.getContentType());
    metadata.setContentLength(image.getSize());

    amazonS3.putObject(new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata));

    return amazonS3.getUrl(bucket, fileName).toString();
  }
}
