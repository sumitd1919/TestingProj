package com.example.demo.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.demo.config.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsClient {

    @Autowired
    private AwsConfig awsConfig;

    public AmazonS3 getAwsClient(){
        AmazonS3 amazonS3 = null;
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsConfig.getAccessKey(),awsConfig.getSecretKey());
        amazonS3 = new AmazonS3Client(awsCredentials);
        return amazonS3;
    }

}
