    package br.com.apihubinovacao.api.controllers;

    import br.com.apihubinovacao.domain.dtos.projects.*;
    import br.com.apihubinovacao.domain.usecases.projects.create.CreateAcademicProjectForProfessorUseCase;
    import br.com.apihubinovacao.domain.usecases.projects.create.CreateAcademicProjectForStudentUseCase;
    import br.com.apihubinovacao.domain.usecases.projects.get.ListAcademicProjectsByUserEmailUseCase;
    import br.com.apihubinovacao.domain.usecases.projects.get.ListAcademicProjectsForProfessorUseCase;
    import br.com.apihubinovacao.domain.usecases.projects.get.ListAcademicProjectsForStudentUseCase;
    import br.com.apihubinovacao.domain.usecases.projects.get.ListAllAcademicProjectsUseCase; // Novo UseCase
    import br.com.apihubinovacao.domain.usecases.projects.update.UpdateAcademicProjectDetailsUseCase;
    import br.com.apihubinovacao.domain.usecases.projects.update.UpdateAcademicProjectStatusUseCase;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/projects")
    public class ProjectController {

        private final CreateAcademicProjectForProfessorUseCase createAcademicProjectForProfessorUseCase;
        private final CreateAcademicProjectForStudentUseCase createAcademicProjectForStudentUseCase;
        private final ListAcademicProjectsForProfessorUseCase listAcademicProjectsForProfessorUseCase;
        private final ListAcademicProjectsForStudentUseCase listAcademicProjectsForStudentUseCase;
        private final ListAcademicProjectsByUserEmailUseCase listAcademicProjectsByUserEmailUseCase;
        private final ListAllAcademicProjectsUseCase listAllAcademicProjectsUseCase; // Novo UseCase
        private final UpdateAcademicProjectStatusUseCase updateAcademicProjectStatusUseCase;
        private final UpdateAcademicProjectDetailsUseCase updateAcademicProjectDetailsUseCase;

        @Autowired
        public ProjectController(
                CreateAcademicProjectForProfessorUseCase createAcademicProjectForProfessorUseCase,
                CreateAcademicProjectForStudentUseCase createAcademicProjectForStudentUseCase,
                ListAcademicProjectsForProfessorUseCase listAcademicProjectsForProfessorUseCase,
                ListAcademicProjectsForStudentUseCase listAcademicProjectsForStudentUseCase,
                ListAcademicProjectsByUserEmailUseCase listAcademicProjectsByUserEmailUseCase,
                ListAllAcademicProjectsUseCase listAllAcademicProjectsUseCase, UpdateAcademicProjectStatusUseCase updateAcademicProjectStatusUseCase, UpdateAcademicProjectDetailsUseCase updateAcademicProjectDetailsUseCase) { // Novo UseCase
            this.createAcademicProjectForProfessorUseCase = createAcademicProjectForProfessorUseCase;
            this.createAcademicProjectForStudentUseCase = createAcademicProjectForStudentUseCase;
            this.listAcademicProjectsForProfessorUseCase = listAcademicProjectsForProfessorUseCase;
            this.listAcademicProjectsForStudentUseCase = listAcademicProjectsForStudentUseCase;
            this.listAcademicProjectsByUserEmailUseCase = listAcademicProjectsByUserEmailUseCase;
            this.listAllAcademicProjectsUseCase = listAllAcademicProjectsUseCase; // Injeção do novo UseCase
            this.updateAcademicProjectStatusUseCase = updateAcademicProjectStatusUseCase;
            this.updateAcademicProjectDetailsUseCase = updateAcademicProjectDetailsUseCase;
        }

        @PreAuthorize("hasAnyRole('USER')")
        @PostMapping("/professor/create")
        public ResponseEntity<AcademicProjectResponseProfessorDTO> createProjectForProfessor(
                @RequestBody AcademicProjectCreateProfessorDTO dto) {
            AcademicProjectResponseProfessorDTO createdProject = createAcademicProjectForProfessorUseCase.execute(dto);
            return ResponseEntity.ok(createdProject);
        }

        @PreAuthorize("hasAnyRole('USER')")
        @PostMapping("/student/create")
        public ResponseEntity<AcademicProjectResponseStudentDTO> createProjectForStudent(
                @RequestBody AcademicProjectCreateStudentDTO dto) {
            AcademicProjectResponseStudentDTO createdProject = createAcademicProjectForStudentUseCase.execute(dto);
            return ResponseEntity.ok(createdProject);
        }

        @PreAuthorize("hasAnyRole('PROFESSOR','ADMIN', 'MANAGERS')")
        @GetMapping("/all-professor")
        public ResponseEntity<List<AcademicProjectResponseProfessorDTO>> getAllProjectsForProfessor() {
            List<AcademicProjectResponseProfessorDTO> projects = listAcademicProjectsForProfessorUseCase.execute();
            return ResponseEntity.ok(projects);
        }

        @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN', 'MANAGERS')")
        @GetMapping("/all-student")
        public ResponseEntity<List<AcademicProjectResponseStudentDTO>> getAllProjectsForStudent() {
            List<AcademicProjectResponseStudentDTO> projects = listAcademicProjectsForStudentUseCase.execute();
            return ResponseEntity.ok(projects);
        }

        @GetMapping("/by-email")
        public ResponseEntity<List<?>> getProjectsByUserEmail(@RequestParam String email) {
            List<?> projects = listAcademicProjectsByUserEmailUseCase.execute(email);
            return ResponseEntity.ok(projects);
        }

        @GetMapping("/all")
        public ResponseEntity<List<?>> getAllProjects() {
            List<?> projects = listAllAcademicProjectsUseCase.execute();
            return ResponseEntity.ok(projects);
        }
        @PreAuthorize("hasAnyRole('MANAGER')")
        @PutMapping("/{projectId}/status")
        public ResponseEntity<Void> updateProjectStatus(
                @PathVariable Long projectId,
                @RequestBody UpdateAcademicProjectStatusDTO dto) {
            updateAcademicProjectStatusUseCase.execute(projectId, dto);
            return ResponseEntity.noContent().build();
        }

        @PreAuthorize("hasAnyRole('PROFESSOR','STUDENT')")
        @PutMapping("/{projectId}/details")
        public ResponseEntity<Void> updateProjectDetails(
                @PathVariable Long projectId,
                @RequestBody UpdateAcademicProjectDetailsDTO updateDTO) {
            updateAcademicProjectDetailsUseCase.execute(projectId, updateDTO);
            return ResponseEntity.noContent().build();
        }
    }