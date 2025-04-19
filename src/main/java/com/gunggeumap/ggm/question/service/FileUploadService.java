package com.gunggeumap.ggm.question.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
  String uploadFile(MultipartFile image) throws IOException;
}
