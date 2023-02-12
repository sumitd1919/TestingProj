package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.UsersEntity;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.web.multipart.MultipartFile;

public interface UserDetailsService {

	List<UsersEntity> getUserDetails(Long id);

	void saveUsersList(MultipartFile excelFile) throws IOException;

	String zipFileUnzip(MultipartFile zipFile) throws IOException, ZipException;
}
