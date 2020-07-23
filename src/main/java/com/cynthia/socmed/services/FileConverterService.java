package com.cynthia.socmed.services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileConverterService {

    public File convertFile (MultipartFile file, String username) throws IOException {

        File convertFile = new File("C:\\Users\\CynthiaM\\Desktop\\socmed\\src\\main\\resources\\static\\profilePictures\\"+username+".jpg");
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(String.valueOf(convertFile));
        fout.write(file.getBytes());
        fout.close();
        return convertFile;
    }
}
