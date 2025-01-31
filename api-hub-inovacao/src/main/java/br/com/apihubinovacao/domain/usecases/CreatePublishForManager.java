package br.com.apihubinovacao.domain.usecases;

import br.com.apihubinovacao.domain.dtos.PublishCreateDTO;
import br.com.apihubinovacao.domain.dtos.PublishResponseDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.Publish;
import br.com.apihubinovacao.domain.models.users.Manager;
import br.com.apihubinovacao.domain.repositories.ManagerRepository;
import br.com.apihubinovacao.domain.repositories.PublishRepository;
import br.com.apihubinovacao.domain.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreatePublishForManager {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PublishRepository publishRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private HttpServletRequest request;

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

    public PublishResponseDTO execute (PublishCreateDTO publishCreateDTO) {


        // valida e extrai o token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCodeEnum.INVALID_TOKEN);
        }

        String token = authHeader.replace("Bearer ", "");

        // extrai email
        String email = jwtService.extractEmail(token);
        // busca pelo email o id de quem adicionou
        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCodeEnum.INVALID_TOKEN));



        Publish publish = new Publish();
        publish.setTitle(publishCreateDTO.title());
        publish.setDescription(publishCreateDTO.description());
        publish.setAcessLink(publishCreateDTO.acessLink());
        publish.setPhotoLink(publishCreateDTO.photoLink());
        publish.setInitialDate(publishCreateDTO.initialDate());
        publish.setFinalDate(publishCreateDTO.finalDate());
        publish.setManager(manager);



        Publish savedPublish = publishRepository.save(publish);

        return new PublishResponseDTO(
                savedPublish.getId(),
                savedPublish.getTitle(),
                savedPublish.getDescription(),
                savedPublish.getAcessLink(),
                savedPublish.getPhotoLink(),
                savedPublish.getInitialDate(),
                savedPublish.getFinalDate());

    }
}
