package com.sathish.contract.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sathish.contract.Application;
import com.sathish.contract.api.rest.ContractController;
import com.sathish.contract.domain.Contract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ContractControllerTest {


    @InjectMocks
    ContractController controller;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //@Test
    public void shouldHaveEmptyDB() throws Exception {
        mvc.perform(get("/contract-management/contracts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldCreateRetrieveUpdateDelete() throws Exception {
    		Contract r1 = mockContract("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);
        
        Contract r2 = mockContract("shouldCreateAndUpdate");
        r2.setContractName("Sales");
        byte[] r2Json = toJson(r2);

        //CREATE
        MvcResult result = mvc.perform(post("/contract-management/contracts/")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());
        
      //RETRIEVE
        mvc.perform(get("/contract-management/contracts/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractId", is((int) id)))
                .andExpect(jsonPath("$.contractName", is(r1.getContractName())))
                .andExpect(jsonPath("$.amountRequested", is(r1.getAmountRequested())));
        
        //UPDATE
        result = mvc.perform(put("/contract-management/contracts/" + id)
                .content(r2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        //RETRIEVE updated
        mvc.perform(get("/contract-management/contracts/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractId", is((int) id)))
                .andExpect(jsonPath("$.contractName", is(r2.getContractName())))
                .andExpect(jsonPath("$.amountRequested", is(r1.getAmountRequested())));

        //DELETE
        mvc.perform(delete("/contract-management/contracts/" + id))
                .andExpect(status().isNoContent());

        mvc.perform(get("http://localhost:8090/contract-management/contracts" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }


    /*
    ******************************
     */

    private long getResourceIdFromUrl(String locationUrl) {
    	if (locationUrl == null) {
    		return 1l;
    	} else {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);    		
    	}

    }


    private Contract mockContract(String prefix) {
    		Contract c = new Contract();

        c.setContractName(prefix + "_contractName");
        c.setBusinessNumber(123);
        c.setAmountRequested(100.00);
        c.setContractId(1);

        return c;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }


}
