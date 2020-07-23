package com.cynthia.socmed.comp;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileConverter {

    public File convertFile (MultipartFile file, String username) throws IOException {

        File convertFile = new java.io.File("C:\\Users\\CynthiaM\\Desktop\\socmed\\src\\main\\resources\\static\\profilePictures\\"+username+".jpg");
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(String.valueOf(convertFile));
        fout.write(file.getBytes());
        fout.close();
        return convertFile;
    }
}
