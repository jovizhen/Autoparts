package com.jovi.auto.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class JsonOrderProcessor implements Processor
{
	public void process(Exchange exchange) throws Exception
	{
		exchange.getIn().setHeader("order_status", "normal");
	}
}
