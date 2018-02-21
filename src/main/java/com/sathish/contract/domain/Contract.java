package com.sathish.contract.domain;

import java.util.Date;

import javax.persistence.*;

import com.sathish.contract.exception.DataFormatException;

@Entity
@Table(name = "Contract")
public class Contract {

    @Id
    @GeneratedValue()
    private long contractId;
    
    private String contractName;
    
    private long businessNumber;

    private double amountRequested;

	private Date contractActivationDate;
    
    private boolean contractStatus;
    
    public Contract() {
    }
    
	public Contract(String contractName, long businessNumber, Date contractActivationDate, double amountRequested, boolean contractStatus) {
        this.contractName = contractName;
        this.businessNumber = businessNumber;
        this.amountRequested = amountRequested;
    }
    

    public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public long getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(long businessNumber) {
		this.businessNumber = businessNumber;
	}

	public double getAmountRequested() {
		return amountRequested;
	}

	public void setAmountRequested(double amountRequested) {
		if (amountRequested > 50000.00 && this.contractName.equalsIgnoreCase("Express")) {
			throw new DataFormatException("Express Contract can be any amount less than $50,000");
		}
		this.amountRequested = amountRequested;
		setContractActivationDate(new Date());
		this.setContractStatus(true);
		
	}
	
    public Date getContractActivationDate() {
		return contractActivationDate;
	}

	public void setContractActivationDate(Date contractActivationDate) {
		this.contractActivationDate = contractActivationDate;
	}


	public boolean isContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(boolean contractStatus) {
		this.contractStatus = contractStatus;
	}

    @Override
    public String toString() {
        return "Contract {" +
                "contractId=" + contractId +
                ", contractName='" + contractName + '\'' +
                ", businessNumber='" + businessNumber + '\'' +
                ", contractActivationDate='" + contractActivationDate + '\'' +
                ", amountRequested=" + amountRequested +
                ", contractStatus=" + contractStatus +
                '}';
    }
}
