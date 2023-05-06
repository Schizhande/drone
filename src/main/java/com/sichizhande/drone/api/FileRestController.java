package com.sichizhande.drone.api;

import com.sichizhande.drone.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class FileRestController {

    private final FileStorageService fileStorageService;

    public FileRestController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/api/v1/files")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.downloadFile(fileName);
        String contentType = getContentType(request, resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private static String getContentType(HttpServletRequest httpServletRequest, Resource resource) {

        String contentType = null;
        try {
            contentType = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.error("### Could not determine file type. due to {}", ex.getMessage());
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

}

