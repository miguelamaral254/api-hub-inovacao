package br.com.apihubinovacao.domain.usecases.phone.create;

import br.com.apihubinovacao.domain.dtos.phone.PhoneCreateDTO;
import br.com.apihubinovacao.domain.dtos.phone.PhoneResponseDTO;
import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import br.com.apihubinovacao.domain.exceptions.BusinessException;
import br.com.apihubinovacao.domain.models.users.*;
import br.com.apihubinovacao.domain.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePhoneUseCase {

    @Autowired
    private PhoneRepository phoneRepository;

    public PhoneResponseDTO execute(PhoneCreateDTO dto, UserBase user) {
        Phone phone = new Phone();
        phone.setNumber(dto.number());

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
            throw new BusinessException(ErrorCodeEnum.PHONE_CREATION_FAILED);
        }

        Phone savedPhone = phoneRepository.save(phone);

        return new PhoneResponseDTO(savedPhone.getId(), savedPhone.getNumber());
    }
}