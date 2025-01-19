package com.whytowait.api.v1.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Path;
import java.util.Objects;

@Service
public class CloudflareR2Service {
    private final S3Client s3Client;

    @Value("${cloudflare.bucket_name}")
    private String BUCKET;

    public CloudflareR2Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String UploadFile(String path, MultipartFile file) {

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(path)
                        .contentType(file.getContentType())
                        .build(),
                Path.of(Objects.requireNonNull(file.getOriginalFilename()))
        );
        return "https://" + BUCKET + ".r2.cloudflarestorage.com/" + path;
    }
}
