package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.models.FileUploadResponse;
import nl.pin.paardenstal.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //zorgt ervoor dat een bestand kan worden ge-upload.
    @PostMapping("/upload")
    FileUploadResponse singleFileUpload(@RequestParam ("file")MultipartFile file) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects
                .requireNonNull(file.getOriginalFilename())).toUriString();
        String contentType = file.getContentType();

        String fileName = fileService.storeFile(file, url);

        return new FileUploadResponse(fileName, contentType, url);

    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downloadSingleFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.downloadFile(fileName);

        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

}
