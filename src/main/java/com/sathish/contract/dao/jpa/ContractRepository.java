package com.sathish.contract.dao.jpa;

import com.sathish.contract.domain.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source
 */
public interface ContractRepository extends PagingAndSortingRepository<Contract, Long> {
    Page findAll(Pageable pageable);
}
