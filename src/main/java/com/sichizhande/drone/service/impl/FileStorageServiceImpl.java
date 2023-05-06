package com.sichizhande.drone.service.impl;

import com.sichizhande.drone.configs.FileStorageProperties;
import com.sichizhande.drone.exceptions.FileNotFoundException;
import com.sichizhande.drone.exceptions.InvalidRequestException;
import com.sichizhande.drone.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageProperties fileStorageProperties;

    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    @Override
    public String storeFile(final MultipartFile file) {

        requireNonNull(file);

        validateFileType(file);

        String fileName = StringUtils.cleanPath(requireNonNull(file.getOriginalFilename()));

        this.checkIfFileNameContainsInvalidCharacters(fileName);

        fileName = this.generateUniqueFileName(fileName);

        Path targetLocation = getFileStorageLocation().resolve(fileName);

        this.createDirectoriesIfThereDoNotExist(targetLocation);

        this.persistFile(file, targetLocation);

        return fileName;

    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            Path filePath = this.getFileStorageLocation().resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    private void validateFileType(MultipartFile file) {

        if (isNull(file)) {
            throw new InvalidRequestException("File  cannot be null");
        }

        val originalFileName = file.getOriginalFilename();

        if (isNull(originalFileName)) {
            throw new InvalidRequestException("Client not supported in uploading files");
        }

        final String fileName = StringUtils.cleanPath(originalFileName);

        for (String fileNameExtension : fileStorageProperties.getFileTypes()) {
            if (fileName.endsWith(fileNameExtension)) {
                return;
            }
        }

        throw new InvalidRequestException("File type is not accepted");
    }

    private void persistFile(MultipartFile file, Path path) {

        try {

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not store file " + file.getName() + ". Please try again!");
        }

    }

    private String generateUniqueFileName(String fileName) {
        String fileExtension = "";
        try {
            fileExtension = fileName.substring(fileName.lastIndexOf("."));
        } catch (Exception ignore) {

        }
        return UUID.randomUUID() + fileExtension;
    }


    private void checkIfFileNameContainsInvalidCharacters(String fileName) {
        if (fileName.contains("..")) {
            throw new IllegalStateException("Filename contains invalid path sequence " + fileName);
        }
    }


    private Path getFileStorageLocation() {
        return Paths.get(this.fileStorageProperties.getUploadRoot())
                .toAbsolutePath().normalize();
    }

    private void createDirectoriesIfThereDoNotExist(Path fileStorageLocation) {
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not create the directory where the uploaded files will be stored." + ex);
        }

    }


}
