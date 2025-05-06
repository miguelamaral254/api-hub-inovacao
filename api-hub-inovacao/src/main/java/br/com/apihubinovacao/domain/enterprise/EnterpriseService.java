package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.BusinessException;
import br.com.apihubinovacao.domain.users.UserExceptionCodeEnum;
import br.com.apihubinovacao.domain.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public Enterprise createEnterprise(Enterprise enterprise) {
        validateBusinessRules(enterprise);

        if (enterprise.getAddress() != null) {
            enterprise.getAddress().setEnterprise(enterprise);
        }
        enterprise.setPassword(passwordEncoder.encode(enterprise.getPassword()));

        return enterpriseRepository.save(enterprise);
    }

    @Transactional(readOnly = true)
    public Page<Enterprise> searchEnterprises(
            Specification<Enterprise> specification,
            Pageable pageable
    ) {
        return enterpriseRepository.findAll(specification, pageable);
    }   

    @Transactional(readOnly = true)
    public Enterprise findById(Long id) {
        return enterpriseRepository.findById(id)
                .orElseThrow(() -> new BusinessException(EnterpriseExceptionCodeEnum.ENTERPRISE_NOT_FOUND));
    }

    private void validateBusinessRules(Enterprise enterprise) {
        if (enterpriseRepository.existsByCnpj((enterprise.getCnpj()))) {
            throw new BusinessException(EnterpriseExceptionCodeEnum.ENTERPRISE_CNPJ_ALREADY_EXISTS);
        }

        if(enterpriseRepository.existsByEmail((enterprise.getEmail()))) {
            throw new BusinessException(EnterpriseExceptionCodeEnum.ENTERPRISE_EMAIL_ALREADY_EXISTS);
        }

        if(userRepository.existsByEmail((enterprise.getEmail()))) {
            throw new BusinessException(EnterpriseExceptionCodeEnum.ENTERPRISE_EMAIL_ALREADY_EXISTS);
        }

        if (enterprise.getNomeEmpresa() == null || enterprise.getNomeEmpresa().isEmpty()) {
            throw new BusinessException(EnterpriseExceptionCodeEnum.INVALID_COMPANY_NAME);
        }
    }

    @Transactional
    public Enterprise updateEnterprise(Long id, Consumer<Enterprise> updateConsumer) {
        Enterprise existingEnterprise = findById(id);

        updateConsumer.accept(existingEnterprise);

        validateUpdateBusiness(id, existingEnterprise);

        return enterpriseRepository.save(existingEnterprise);
    }

    private void validateUpdateBusiness(Long id, Enterprise existingEnterprise) {
        if (existingEnterprise.getCnpj() != null) {
            Enterprise existingCnpjEnterprise = enterpriseRepository.findByCnpjAndIdNot(existingEnterprise.getCnpj(), id);

            if (existingCnpjEnterprise != null) {
                throw new BusinessException(EnterpriseExceptionCodeEnum.ENTERPRISE_CNPJ_ALREADY_EXISTS);
            }
        }

        if (existingEnterprise.getNomeEmpresa() == null || existingEnterprise.getNomeEmpresa().isEmpty()) {
            throw new BusinessException(EnterpriseExceptionCodeEnum.INVALID_COMPANY_NAME);
        }
    }

    @Transactional
    public Enterprise disableEnterprise(Long id, Boolean disable) {
        Enterprise enterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new BusinessException(EnterpriseExceptionCodeEnum.ENTERPRISE_NOT_FOUND));

        enterprise.setEnabled(disable);
        return enterpriseRepository.save(enterprise);
    }


    @Transactional
    public void deleteEnterprise(Long id) {
        Enterprise enterprise = findById(id);
        enterpriseRepository.delete(enterprise);
    }


    @Transactional()
    public Enterprise authenticateEnterprise(String email, String password) {
        Enterprise enterprise = enterpriseRepository.findByEmail((email))
                .orElseThrow(() -> new BusinessException(UserExceptionCodeEnum.EMAIL_DOES_NOT_MATCH));
        if (!passwordEncoder.matches(password, enterprise.getPassword())) {
            throw new BusinessException(UserExceptionCodeEnum.INVALID_PASSWORD);
        }
        return enterprise;
    }
}