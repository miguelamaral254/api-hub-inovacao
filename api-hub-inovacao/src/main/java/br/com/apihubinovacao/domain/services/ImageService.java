package br.com.apihubinovacao.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "uploads/";

    public String saveImage(MultipartFile file) throws IOException {
        // Cria o diretório 'uploads' caso não exista
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Gerar um nome de arquivo único usando UUID
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Cria o arquivo na pasta uploads
        File imageFile = new File(UPLOAD_DIR + uniqueFileName);

        // Salva o arquivo no diretório 'uploads'
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(file.getBytes());
        }

        // Retorna o caminho relativo da imagem
        return "/uploads/" + uniqueFileName;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return ""; // Retorna string vazia caso não tenha extensão
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}