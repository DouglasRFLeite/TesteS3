package com.douglas.testes3.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    private static final String bucketName = "bucket-de-teste-para-armazenamento-de-imagens-douglas";

    public String uploadImage(MultipartFile file) throws IOException {
        File tempFile = convertMultiPartFileToFile(file);
        amazonS3.putObject(bucketName, file.getOriginalFilename(), tempFile);
        tempFile.delete();
        return "Imagem carregada com sucesso: " + file.getOriginalFilename();
    }

    public InputStream downloadImage(String fileName) {
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        return s3Object.getObjectContent();
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File tempFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }

    public byte[] getImage(String fileName) throws IOException {
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        try (S3ObjectInputStream objectInputStream = s3Object.getObjectContent()) {
            return IOUtils.toByteArray(objectInputStream);
        }
    }
}
