package com.whytowait.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class CloudflareR2Config {

    @Value("${cloudflare.account_id}")
    private String ACCOUNT_ID;

    @Value("${cloudflare.access_key}")
    private String ACCESS_KEY;

    @Value("${cloudflare.secret_key}")
    private String SECRET_KEY;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of("auto")) // Cloudflare R2 doesn't use specific AWS regions
                .endpointOverride(URI.create("https://" + ACCOUNT_ID + ".r2.cloudflarestorage.com"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                ACCESS_KEY,  // Replace with your R2 Access Key
                                SECRET_KEY   // Replace with your R2 Secret Key
                        )
                ))
                .build();
    }
}
