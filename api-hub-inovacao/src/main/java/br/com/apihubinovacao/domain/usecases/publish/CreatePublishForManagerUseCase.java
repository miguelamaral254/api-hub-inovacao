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
import org.springframework.transaction.annotation.Transactional;
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

    // 🔍 Enhanced DTO validation
    private void validateCreatePublish(PublishCreateDTO publishCreateDTO) {
        if (publishCreateDTO == null) {
            throw new BusinessException(ErrorCodeEnum. INVALID_PUBLISH_DATA);
        }
        if (publishCreateDTO.title() == null || publishCreateDTO.title().trim().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_PUBLISH_TITLE);
        }
        if (publishCreateDTO.description() == null || publishCreateDTO.description().trim().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_PUBLISH_DESCRIPTION);
        }
        if (publishCreateDTO.acessLink() == null || publishCreateDTO.acessLink().trim().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_PUBLISH_ACCESS_LINK);
        }
        if (publishCreateDTO.initialDate() == null) {
            throw new BusinessException(ErrorCodeEnum.INVALID_PUBLISH_INITIAL_DATE);
        }
        if (publishCreateDTO.finalDate() == null) {
            throw new BusinessException(ErrorCodeEnum.INVALID_PUBLISH_FINAL_DATE);
        }
        if (publishCreateDTO.finalDate().isBefore(publishCreateDTO.initialDate())) {
            throw new BusinessException(ErrorCodeEnum.INVALID_PUBLISH_DATE_RANGE);
        }
    }
@Transactional
    public PublishResponseDTO execute(PublishCreateDTO publishCreateDTO, MultipartFile imageFile, HttpServletRequest request) {
        // ✅ Validação do DTO
        validateCreatePublish(publishCreateDTO);

        // ✅ Validação do Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCodeEnum.INVALID_TOKEN);
        }

        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);

        // ✅ Verificar se o usuário é Admin ou Manager
        Manager manager = managerRepository.findByEmail(email).orElse(null);
        Admin admin = adminRepository.findByEmail(email).orElse(null);

        if (manager == null && admin == null) {
            throw new BusinessException(ErrorCodeEnum.INVALID_ROLE);
        }

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 5 * 1024 * 1024) {
                throw new BusinessException(ErrorCodeEnum.IMAGE_SIZE_EXCEEDED);
            }
            try {
                imageUrl = imageService.saveImage(imageFile, request);
            } catch (IOException e) {
                throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_FAILED);
            }
        }

        // ✅ Criar nova publicação
        Publish publish = new Publish();
        publish.setTitle(publishCreateDTO.title());
        publish.setDescription(publishCreateDTO.description());
        publish.setAcessLink(publishCreateDTO.acessLink());
        publish.setPhotoLink(imageUrl);
        publish.setInitialDate(publishCreateDTO.initialDate());
        publish.setFinalDate(publishCreateDTO.finalDate());
        publish.setPublishedDate(LocalDate.now());

        // ✅ Definir quem criou a publicação
        if (manager != null) {
            publish.setManager(manager);
        } else {
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
    }
}