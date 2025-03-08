package br.com.apihubinovacao.domain.editals;

import br.com.apihubinovacao.domain.errors.exceptions.BusinessException;
import br.com.apihubinovacao.domain.errors.exceptions.GeneralExceptionCodeEnum;
import br.com.apihubinovacao.domain.errors.exceptions.ProjectExceptionCodeEnum;
import br.com.apihubinovacao.domain.errors.exceptions.UserExceptionCodeEnum;
import br.com.apihubinovacao.domain.projects.Projects;
import br.com.apihubinovacao.domain.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditalService {
    private final UserRepository userRepository;
    private final EditalRepository editalRepository;

    @Transactional
    public Edital createEdital(Edital edital)  {

        validateBusinessRules(edital);

        if (edital.getIdUser() != null && edital.getIdUser().getId() == null) {
            edital.setIdUser(null);
        }

        return editalRepository.save(edital);
    }

    @Transactional(readOnly = true)
    public Page<Edital> searchEditals(
            Specification<Edital> specification,
            Pageable pageable
    ) {
        return editalRepository.findAll(specification, pageable);
    }

    //TODO: Criar e ajustar as exceptions de editais
    @Transactional(readOnly = true)
    public Edital findById(Long id) {
        return editalRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ProjectExceptionCodeEnum.PROJECT_NOT_FOUND));
    }

    private void validateBusinessRules(Edital edital) {
        if (edital.getTitle() == null || edital.getTitle().isEmpty()) {
            throw new BusinessException(GeneralExceptionCodeEnum.INVALID_REQUEST);
        }

        if (edital.getIdUser() == null || edital.getIdUser().getId() == null) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }

        if (!userRepository.existsById(edital.getIdUser().getId())) {
            throw new BusinessException(UserExceptionCodeEnum.USER_NOT_FOUND);
        }
    }



}
