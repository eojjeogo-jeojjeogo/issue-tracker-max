package com.codesquad.issuetracker.api.aws;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class AwsController {

    private final S3Service s3Service;

    @PostMapping("/api/images")
    public ResponseEntity<String> uploads3(@RequestParam("file") MultipartFile file) throws IOException {
        String url = s3Service.save(file);
        return ResponseEntity.ok(url);
    }
}

