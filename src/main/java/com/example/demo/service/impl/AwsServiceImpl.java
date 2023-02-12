package com.example.demo.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.aws.AwsClient;
import com.example.demo.service.AwsService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.List;

@Service
public class AwsServiceImpl  implements AwsService {
    @Autowired
    private AwsClient awsClient;

    @Override
    public List<Bucket> getAllBuckets() {
        return awsClient.getAwsClient().listBuckets();
    }

    @Override
    public void readDataFromBucket() throws IOException {
        AmazonS3 amazonS3 = awsClient.getAwsClient();
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest("test-shirsh","0/images/case/1000/image 1.png"));
        InputStream inputStream = s3Object.getObjectContent();
        FileCopyUtils.copy(inputStream, new FileOutputStream(new File("/home/nagarro/Desktop/downloadAws/img.png")));
        inputStream.close();
    }

    @Override
    public void uploadFile() {
        AmazonS3 amazonS3 = awsClient.getAwsClient();
        //amazonS3.deleteObject("test-shirsh","notes.txt");
        //amazonS3.createBucket("testsumit");
        amazonS3.deleteObject("test-shirsh","notess.txt");
        //amazonS3.putObject("test-shirsh","notess.txt", new File("/home/nagarro/Desktop/downloadAws/keycloaknotes"));
    }
}
