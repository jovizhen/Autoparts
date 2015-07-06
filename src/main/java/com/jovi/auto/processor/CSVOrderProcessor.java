package com.jovi.auto.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class CSVOrderProcessor implements Processor
{

	public void process(Exchange exchange) throws Exception
	{
		Object order = exchange.getIn().getBody();
		System.out.print("here"+order.toString());
	}

}
