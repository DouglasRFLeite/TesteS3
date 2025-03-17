package com.douglas.awss3test.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Service
public class S3Service {
    @Autowired
    private AmazonS3 amazonS3;

    private final String bucketName = "image-storage-and-retrieval-s3-test-douglas";

    public byte[] getImage(String fileName) throws IOException {
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        try (S3ObjectInputStream objectInputStream = s3Object.getObjectContent()) {
            return IOUtils.toByteArray(objectInputStream);
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        File tempFile = convertMultiPartFileToFile(file);
        amazonS3.putObject(bucketName, file.getOriginalFilename(), tempFile);
        tempFile.delete();
        return "Imagem carregada com sucesso: " + file.getOriginalFilename();
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File tempFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }
}
