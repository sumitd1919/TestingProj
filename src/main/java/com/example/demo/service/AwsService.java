package com.example.demo.service;

import com.amazonaws.services.s3.model.Bucket;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface AwsService {

     List<Bucket> getAllBuckets();
     void readDataFromBucket() throws IOException;

     void uploadFile();
}
