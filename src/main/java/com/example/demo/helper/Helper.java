package com.example.demo.helper;

import com.example.demo.Users;
import com.example.demo.entity.UsersEntity;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helper {

    public static boolean isValidExcel(MultipartFile file){
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    }

    public static List<UsersEntity> convertExceltoList(InputStream inputStream) throws IOException {
        List<UsersEntity> usersList = new ArrayList<UsersEntity>();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet("data");
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()){
            Row row = iterator.next();
            if(row.getRowNum()==0){
                continue;
            }else {
                Iterator<Cell> cells = row.iterator();
                UsersEntity usersEntity = new UsersEntity();
                int cid = 0;
                while (cells.hasNext()){

                    Cell cell = cells.next();
                    switch (cid){
                        case 0:
                            usersEntity.setId((long) cell.getNumericCellValue());
                            break;
                        case 1:
                            usersEntity.setName(cell.getStringCellValue());
                            break;
                        case 2:
                            double value = cell.getNumericCellValue();
                            String finalValue = value % 1 == 0 ? String.valueOf((long)value) : String.valueOf(value);
                            usersEntity.setMobile(finalValue);
                            break;
                        case 3:
                            usersEntity.setEmail(cell.getStringCellValue());
                            break;
                        default:
                            break;

                    }
                    cid++;
                }
                usersList.add(usersEntity);

            }

        }
        return usersList;

    }
}
