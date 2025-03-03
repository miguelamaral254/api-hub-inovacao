package br.com.apihubinovacao.domain.projects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "uploads/";
    private static final String PROD_BASE_URL = "https://missaonrf25.pe.senac.br/appevento/uploads/";

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

        String baseUrl = request.getScheme() + "://" + request.getServerName() +
                (request.getServerPort() != 80 && request.getServerPort() != 443 ? ":" + request.getServerPort() : "");

        if (baseUrl.contains("missaonrf25.pe.senac.br")) {
            return PROD_BASE_URL + fileName;
        }

        return baseUrl + "/uploads/" + fileName;
    }
}