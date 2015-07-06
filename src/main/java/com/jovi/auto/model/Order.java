package com.jovi.auto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
@CsvRecord(separator = ",")
public class Order
{
	@XmlAttribute(name = "name")
	@DataField(pos = 1)
	private String name;
	
	@XmlAttribute(name = "amount")
	@DataField(pos = 2)
	private String amount;
	 
	@XmlAttribute(name = "customer")
	@DataField(pos = 3)
	private String customer;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public String getCustomer()
	{
		return customer;
	}

	public void setCustomer(String customer)
	{
		this.customer = customer;
	}
}
