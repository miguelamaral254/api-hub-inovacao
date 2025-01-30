package br.com.apihubinovacao.domain.services;

import br.com.apihubinovacao.domain.dtos.PhoneCreateDTO;
import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.models.users.*;
import br.com.apihubinovacao.domain.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public PhoneResponseDTO createPhone(PhoneCreateDTO dto, UserBase user) {
        Phone phone = new Phone();
        phone.setNumber(dto.number());

        // Verifica qual tipo de usuário está sendo passado e define corretamente no `Phone`
        if (user instanceof Admin) {
            phone.setAdmin((Admin) user);
        } else if (user instanceof Manager) {
            phone.setManager((Manager) user);
        } else if (user instanceof Student) {
            phone.setStudent((Student) user);
        } else if (user instanceof Professor) {
            phone.setProfessor((Professor) user);
        } else if (user instanceof PartnerCompany) {
            phone.setPartnerCompany((PartnerCompany) user);
        } else {
            throw new IllegalArgumentException("Tipo de usuário desconhecido ao criar telefone.");
        }

        Phone savedPhone = phoneRepository.save(phone);

        return new PhoneResponseDTO(savedPhone.getId(), savedPhone.getNumber());
    }
}