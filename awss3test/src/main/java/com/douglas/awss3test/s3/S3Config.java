package com.douglas.awss3test.s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials( //
                System.getenv("AWS_ACCESS_KEY_ID"), //
                System.getenv("AWS_SECRET_ACCESS_KEY"));

        return AmazonS3ClientBuilder //
                .standard() //
                .withRegion(System.getenv("AWS_REGION")) //
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)) //
                .build();
    }
}
