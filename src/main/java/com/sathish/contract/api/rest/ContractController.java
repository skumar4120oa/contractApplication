package com.sathish.contract.api.rest;

import com.sathish.contract.domain.Contract;
import com.sathish.contract.exception.DataFormatException;
import com.sathish.contract.service.ContractService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/contract-management/contracts")
public class ContractController extends AbstractEventPublisher {

    @Autowired
    private ContractService contractService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createContract(@RequestBody Contract contract,
                                 HttpServletRequest request, HttpServletResponse response) {
    		Contract createdContract = this.contractService.createContract(contract);
        response.setHeader("Contract", request.getRequestURL().append("/").append(createdContract.getContractId()).toString());
    }
    
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Page<Contract> getAllContract(@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
    		return this.contractService.getAllContracts(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Contract getContract(@PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
    		Contract contract = this.contractService.getContract(id);
        checkResourceFound(contract);
        return contract;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateContract(@PathVariable("id") Long id, @RequestBody Contract contract,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.contractService.getContract(id));
        if (id != contract.getContractId()) throw new DataFormatException("ID doesn't match!");
        this.contractService.updateContract(contract);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContract(@PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.contractService.getContract(id));
        this.contractService.deleteContract(id);
    }
    
}
