package br.com.tareffa.awss3client.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    //
    public static String getContentType(Resource resource, HttpServletRequest request) throws Exception {
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        return (contentType == null) ? "application/octet-stream" : contentType;
    }

    public static String getContentDisposition(Resource resource, String contentDisposition) throws Exception {
        return String.format("%s;filename=\"%s\"", contentDisposition, resource.getFilename());
    }

    public static String getContentDisposition(Resource resource) throws Exception {
        return getContentDisposition(resource, "inline");
    }

    public static Resource loadFileAsResource(String filename) throws Exception {
        try {
            Resource resource = new UrlResource(Paths.get(filename).normalize().toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + filename, ex);
        }
    }

    public static Resource loadFileAsResource(File file) throws Exception {
        try {
            Resource resource = new UrlResource(Paths.get(file.getAbsolutePath()).normalize().toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + file.getAbsolutePath());
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + file.getAbsolutePath(), ex);
        }
    }

    public static File createTemporaryFile(String filename, InputStream inputStream) throws IOException {
        File tmp = File.createTempFile(UUID.randomUUID().toString(), getFileExtension(filename));
        tmp.deleteOnExit();
        Files.copy(inputStream, Paths.get(tmp.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        return tmp;
    }

    public static File createTemporaryFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        File tmp = File.createTempFile(UUID.randomUUID().toString(), getFileExtension(originalFilename));
        tmp.deleteOnExit();
        Files.copy(file.getInputStream(), Paths.get(tmp.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        return tmp;
    }

    public static String getFileExtension(String filename) {
        return filename.contains(".") ? filename.substring(filename.lastIndexOf(".") + 1) : ".tmp";
    }

}