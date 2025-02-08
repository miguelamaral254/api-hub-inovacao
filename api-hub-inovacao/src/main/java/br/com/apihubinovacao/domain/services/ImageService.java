package br.com.apihubinovacao.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "/home/miguel-amaral/api-hub-inovacao/api-hub-inovacao/uploads/";

    public String saveImage(MultipartFile file, HttpServletRequest request) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new IOException("Nome de arquivo inválido.");
        }

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        File imageFile = new File(UPLOAD_DIR, fileName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(file.getBytes());
        }

        return "https://missaonrf25.pe.senac.br/appevento/uploads/" + fileName;
    }
}

