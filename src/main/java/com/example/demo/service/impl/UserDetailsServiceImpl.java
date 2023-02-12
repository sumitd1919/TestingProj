package com.example.demo.service.impl;

import com.example.demo.entity.UsersEntity;
import com.example.demo.helper.Helper;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserDetailsService;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
@Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	private KafkaProducer<String, String> producer;


	@Override
	public List<UsersEntity> getUserDetails(Long id) {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public void saveUsersList(MultipartFile excelFile) throws IOException {
		List<UsersEntity> usersEntityList = Helper.convertExceltoList(excelFile.getInputStream());
		userRepo.saveAll(usersEntityList);
	}

	@Override
	public String zipFileUnzip(MultipartFile zipFile) throws IOException, ZipException {
	try {
		Path path = Paths.get("/home/nagarro/Desktop/ziptest").toAbsolutePath().normalize();
		Files.copy(zipFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		String zipFilePath = new UrlResource(path.toUri()).getFile().getAbsolutePath();
		Path destPath = Paths.get("/home/nagarro/Desktop/zipdest").toAbsolutePath().normalize();
		String dest = new UrlResource(destPath.toUri()).getFile().getAbsolutePath();
		ZipFile zipFile1 = new ZipFile(zipFilePath);
		zipFile1.extractAll(dest);
	}catch (Exception e) {
			e.printStackTrace();
		}
return null;

	}

}
