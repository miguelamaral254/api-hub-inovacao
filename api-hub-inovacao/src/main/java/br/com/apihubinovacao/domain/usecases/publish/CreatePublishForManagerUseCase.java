package br.com.apihubinovacao.domain.usecases.publish;

import br.com.apihubinovacao.domain.dtos.publish.PublishCreateDTO;
import br.com.apihubinovacao.domain.dtos.publish.PublishResponseDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.Publish;
import br.com.apihubinovacao.domain.models.users.Admin;
import br.com.apihubinovacao.domain.models.users.Manager;
import br.com.apihubinovacao.domain.repositories.AdminRepository;
import br.com.apihubinovacao.domain.repositories.ManagerRepository;
import br.com.apihubinovacao.domain.repositories.PublishRepository;
import br.com.apihubinovacao.domain.services.ImageService;
import br.com.apihubinovacao.domain.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class CreatePublishForManagerUseCase {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PublishRepository publishRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ImageService imageService;
    @Autowired
    private AdminRepository adminRepository;

    //Validação do DTO
    public void validationCreatePublish(PublishCreateDTO publishCreateDTO) {
        if (publishCreateDTO.title() == null || publishCreateDTO.title().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        if (publishCreateDTO.acessLink() == null || publishCreateDTO.acessLink().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        if (publishCreateDTO.description() == null || publishCreateDTO.description().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }
    }

    public PublishResponseDTO execute(PublishCreateDTO publishCreateDTO, MultipartFile imageFile, HttpServletRequest request) {
        validationCreatePublish(publishCreateDTO);

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCodeEnum.INVALID_TOKEN);
        }

        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);

        // 🔍 Buscar o usuário tanto na tabela `Manager` quanto na `Admin`
        Manager manager = managerRepository.findByEmail(email).orElse(null);
        Admin admin = adminRepository.findByEmail(email).orElse(null);

        if (manager == null && admin == null) {
            System.out.println("❌ Nenhum Manager ou Admin encontrado para o e-mail: " + email);
            throw new BusinessException(ErrorCodeEnum.INVALID_ROLE);
        }

        System.out.println("✅ Usuário autorizado: " + (manager != null ? "Manager: " + manager.getName() : "Admin: " + admin.getName()));

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageUrl = imageService.saveImage(imageFile, request);
            } catch (IOException e) {
                throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_FAILED);
            }
        }

        Publish publish = new Publish();
        publish.setTitle(publishCreateDTO.title());
        publish.setDescription(publishCreateDTO.description());
        publish.setAcessLink(publishCreateDTO.acessLink());
        publish.setPhotoLink(imageUrl);
        publish.setInitialDate(publishCreateDTO.initialDate());
        publish.setFinalDate(publishCreateDTO.finalDate());
        publish.setPublishedDate(LocalDate.now());

        // ✅ Definir o Manager ou Admin como responsável
        if (manager != null) {
            publish.setManager(manager);
        } else if (admin != null) {
            publish.setAdmin(admin);
        }

        Publish savedPublish = publishRepository.save(publish);

        return new PublishResponseDTO(
                savedPublish.getId(),
                savedPublish.getTitle(),
                savedPublish.getDescription(),
                savedPublish.getAcessLink(),
                savedPublish.getPhotoLink(),
                savedPublish.getInitialDate(),
                savedPublish.getFinalDate(),
                savedPublish.getPublishedDate()
        );
    }}