package br.com.apihubinovacao.domain.services;

import br.com.apihubinovacao.domain.dtos.PhoneCreateDTO;
import br.com.apihubinovacao.domain.dtos.PhoneResponseDTO;
import br.com.apihubinovacao.domain.models.Phone;
import br.com.apihubinovacao.domain.models.User;
import br.com.apihubinovacao.domain.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public PhoneResponseDTO createPhone(PhoneCreateDTO dto, User user) {
        Phone phone = new Phone();
        phone.setNumber(dto.number());
        phone.setUser(user);

        Phone savedPhone = phoneRepository.save(phone);

        return new PhoneResponseDTO(savedPhone.getId(), savedPhone.getNumber());
    }

}