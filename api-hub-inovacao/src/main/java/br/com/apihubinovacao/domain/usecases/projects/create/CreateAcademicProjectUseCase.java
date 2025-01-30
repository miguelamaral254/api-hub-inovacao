package br.com.apihubinovacao.domain.usecases.projects.create;

import br.com.apihubinovacao.domain.dtos.AcademicProjectCreateDTO;
import br.com.apihubinovacao.domain.dtos.AcademicProjectResponseDTO;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.repositories.AcademicProjectRepository;
import br.com.apihubinovacao.domain.repositories.ProfessorRepository;  // Importando o repositório de professores
import br.com.apihubinovacao.domain.repositories.StudentRepository;   // Importando o repositório de estudantes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreateAcademicProjectUseCase {

    @Autowired
    private AcademicProjectRepository academicProjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    public AcademicProjectResponseDTO execute(AcademicProjectCreateDTO createDTO) {

        // Validação simples de campos obrigatórios
        if (createDTO.title() == null || createDTO.title().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        if (createDTO.description() == null || createDTO.description().isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.INVALID_REQUEST);
        }

        // Verificar se o e-mail pertence a um professor ou a um estudante
        boolean professorExists = professorRepository.findByEmail(createDTO.userEmail()).isPresent();
        boolean studentExists = studentRepository.findByEmail(createDTO.userEmail()).isPresent();

        if (!professorExists && !studentExists) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND); // Exceção se o e-mail não estiver cadastrado
        }

        // Verificar se o projeto com o mesmo título já existe
        boolean projectExists = academicProjectRepository.existsByTitle(createDTO.title());
        if (projectExists) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_USER); // Usar erro apropriado
        }

        // Criação do modelo do projeto acadêmico
        AcademicProject project = new AcademicProject();
        project.setTitle(createDTO.title());
        project.setDescription(createDTO.description());
        project.setUrlPhoto(createDTO.urlPhoto());
        project.setPdfLink(createDTO.pdfLink());
        project.setSiteLink(createDTO.siteLink());
        project.setTypeAP(createDTO.typeAP());
        project.setAuthorEmail(createDTO.userEmail());
        project.setCreationDate(LocalDate.now());

        // Salvar o projeto na base de dados
        AcademicProject savedProject = academicProjectRepository.save(project);

        // Mapear para o DTO de resposta
        return new AcademicProjectResponseDTO(
                savedProject.getId(),
                savedProject.getTitle(),
                savedProject.getDescription(),
                savedProject.getUrlPhoto(),
                savedProject.getPdfLink(),
                savedProject.getSiteLink(),
                savedProject.getTypeAP(),
                savedProject.getAuthorEmail(),
                savedProject.getCreationDate().toString()
        );
    }
}