package com.sathish.contract.service;

import com.sathish.contract.domain.Contract;
import com.sathish.contract.dao.jpa.ContractRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Service
public class ContractService {

    private static final Logger log = LoggerFactory.getLogger(ContractService.class);
    
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    CounterService counterService;

    public ContractService() {
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public Contract getContract(long id) {
        return contractRepository.findOne(id);
    }

    public void updateContract(Contract contract) {
    		contractRepository.save(contract);
    }

    public void deleteContract(Long id) {
    		contractRepository.delete(id);
    }

    public Page<Contract> getAllContracts(Integer page, Integer size) {
		Page pageOfContracts  = contractRepository.findAll(new PageRequest(page, size));
        if (size > 50) {
            counterService.increment("sathish.contractService.getAll.largePayload");
        }
        return pageOfContracts;
    }
}
