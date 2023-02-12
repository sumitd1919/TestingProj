package com.example.demo.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.example.demo.Users;
import com.example.demo.entity.UsersEntity;
import com.example.demo.helper.Helper;
import com.example.demo.producer.KafkaProducer;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.AwsService;
import com.example.demo.service.UserDetailsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserDetailsController {

	@Autowired
	private AwsService awsService;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private KafkaProducer producer;

	@Autowired
	private RestTemplate restTemplate;

	int retryCount=1;
	@GetMapping("/test/details/{name}")
	//@CircuitBreaker(name = "serviceCircuitBreaker",fallbackMethod = "serviceFallback")
	//@Retry(name = "retryTesting", fallbackMethod = "serviceFallback")
	@RateLimiter(name = "limiterTesting", fallbackMethod = "serviceFallback")
	public String getDockerDetails(@PathVariable String name){
		log.info("Retry count : {}", retryCount);
		retryCount++;
		String resp = this.restTemplate.getForObject("http://serviceone/details/"+name, String.class);
		return resp;

	}

	public String serviceFallback(String name, Exception ex){
		return "this is fallback response......";
	}


	@GetMapping("/new/details/{name}")
	public String getDockerDetails1(@PathVariable String name){
		String resp = this.restTemplate.getForObject("http://serviceone/details/"+name, String.class);
		return resp;

	}
	
	@GetMapping("/users/all")
	public List<UsersEntity> getUsersDetails(){
		return userRepo.findAll();
		
	}

	@GetMapping("/users/{id}")
	public Optional<UsersEntity> getUsersDetailsById(@PathVariable Long id){
		return userRepo.findById(id);
	}

	@PostMapping("/users/post")
	public List<UsersEntity> postUsers(@RequestBody Users users){
		System.out.println(users.toString());
		UsersEntity usersEntity = new UsersEntity();
		usersEntity.setId(users.getId());
		usersEntity.setName(users.getName());
		usersEntity.setMobile(users.getMobile());
		usersEntity.setEmail(users.getEmail());
		System.out.println(usersEntity.toString());
		userRepo.save(usersEntity);
		return userRepo.findAll();
	}

	@PostMapping("/publish")
	public void publishMessage(@RequestBody Users user){

		producer.publishMessage(String.valueOf(user),"testing");
	}

	@GetMapping("/showBuckets")
	public List<Bucket> showBuckets(){
		return awsService.getAllBuckets();
	}

	@GetMapping("/download")
	public void downloadFile() throws IOException {
		awsService.readDataFromBucket();
	}

	@PostMapping("/upload")
	public void uploadFile() throws IOException {
		awsService.uploadFile();
	}

	@PostMapping("/save/users")
	public ResponseEntity<?> saveUsers(@RequestParam("file") MultipartFile excelFile) throws IOException {
		if(Helper.isValidExcel(excelFile)) {
			userDetailsService.saveUsersList(excelFile);
			return ResponseEntity.ok("file is valid and data is saved in db");
		}
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Excel is not valid");
	}

	@PostMapping("/zip")
	public ResponseEntity<?> unzipFile(@RequestParam("zipfile") MultipartFile zipFile) throws IOException, ZipException {
			String response = userDetailsService.zipFileUnzip(zipFile);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

	}


}
