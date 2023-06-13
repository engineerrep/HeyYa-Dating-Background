package com.heyya.tools.utils;

import com.heyya.exception.SvcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class S3Utils {

    @Autowired
    private S3Client s3Client;

    public static final String BUCKET_NAME = "your bucket";
    public static final String URL = "your server";

    public String uploadByFile(MultipartFile multipartFile) {

        String tmpPath = StringUtils.join(System.getProperties().getProperty("java.io.tmpdir"), "/");
        String fileSuf = null;
        int index = multipartFile.getOriginalFilename().lastIndexOf(".");
        if (index > -1) {
            fileSuf = multipartFile.getOriginalFilename().substring(index);
        }
        File file = new File(tmpPath + UUIDUtils.lowerCaseNoSeparatorUUID() + fileSuf);
        try {
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            throw new SvcException(e.getMessage());
        }
        return uploadByFile(file);
    }

    public String uploadByFile(File file) {
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(file.getName())
                .build();
        s3Client.putObject(putOb, RequestBody.fromFile(file));
        return StringUtils.join(URL, file.getName());
    }

    public List<String> batchUpload(List<MultipartFile> files) {
        List<CompletableFuture<String>> futures = files.parallelStream()
                .map(file -> CompletableFuture.supplyAsync(() -> uploadByFile(file))).collect(Collectors.toList());
        List<String> urls = futures.parallelStream().map(e -> {
            try {
                return e.get();
            } catch (Exception exception) {
                log.error("上传文件失败", e);
            }
            return "";
        }).collect(Collectors.toList());
        return urls;
    }

}
