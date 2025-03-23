package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    @Transactional
    public Enterprise createEnterprise(Enterprise enterprise) {
        validateBusinessRules(enterprise);

        if (enterprise.getAddress() != null) {
            enterprise.getAddress().setEnterprise(enterprise);
        }

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

        if (enterprise.getNomeEmpresa() == null || enterprise.getNomeEmpresa().isEmpty()) {
            throw new BusinessException(EnterpriseExceptionCodeEnum.INVALID_COMPANY_NAME);
        }
    }
}