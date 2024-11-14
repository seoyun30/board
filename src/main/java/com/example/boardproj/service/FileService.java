package com.example.boardproj.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;

@Component
public class FileService {

    @Value("${imgLocation}")
    public String imgLocation;

    public void register(MultipartFile multipartFile, String newImgName){
        // 들어온 사진 이름

        System.out.println(imgLocation+"\\"+newImgName);

        // 사진 경로 및 이름
        File file = new File(imgLocation+"\\"+newImgName);

        // 사진 저장
        try {

            multipartFile.transferTo(file);

        }catch (IOException e){

        }
    }

    //물리적인 파일 삭제
    public void delFile(String filePath){

        File deleteFilekkk = new File("파일경로");

        //if(파일 객체변수명 deldteFilekkk가 존재한다면~)
        if (deleteFilekkk.exists()){
            deleteFilekkk.delete();
            System.out.println("파일삭제함");
            System.out.println("파일삭제함");
            System.out.println("파일삭제함");
        }else {
            System.out.println("파일 삭제못함");
            System.out.println("파일 삭제못함");
            System.out.println("파일 삭제못함");
        }
    }


}
