package com.sichizhande.drone.configs;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FileStorageProperties {

    @Value("${file.uploadDir}")
    private String uploadRoot;

    @Value("${file.fileTypes}")
    private String fileTypes;

    public String getUploadRoot() {
        return uploadRoot;
    }

    public List<String> getFileTypes() {
        val temp = Arrays.asList(fileTypes.split(","));
        val types = new ArrayList<>(temp);
        types.addAll(temp.stream()
                .map(String::toUpperCase)
                .toList());
        return types;
    }
}
