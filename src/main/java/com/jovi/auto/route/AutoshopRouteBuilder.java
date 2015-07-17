package com.jovi.auto.route;


import javax.xml.bind.JAXBContext;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import com.jovi.auto.model.Order;
import com.jovi.auto.processor.XMLOrderProcessor;

public class AutoshopRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
		JaxbDataFormat jaxb = new JaxbDataFormat(jaxbContext);
		
		JacksonDataFormat jackson = new JacksonDataFormat();
		jackson.setUnmarshalType(Order.class);
		
		BindyCsvDataFormat bindy = new BindyCsvDataFormat(); 
		bindy.setClassType(Order.class);
		
		//use JMS example
		from("file:src/main/data?noop=true").to("jms:incomingOrders");
		
		//CBR example
		//wireTap example
		//TODO for each type of order, add a processor to parse a pojo order and 
		//TODO forward to continueProcessing
		
		from("jms:incomingOrders").wireTap("jms:auditOrders")
			.choice()
				.when(header("camelFileName").endsWith(".json"))
					.to("jms:jsonOrders").unmarshal(jackson).setHeader("order_status", constant("normal"))
				.when(header("camelFileName").endsWith(".xml"))
					.to("jms:xmlOrders").unmarshal(jaxb).process(new XMLOrderProcessor())
				.when(header("camelFileName").regex("^.*(csv|csl)$"))
					.to("jms:csvOrders").unmarshal(bindy).setHeader("order_status", constant("normal"))
				.otherwise()
					.to("jms:badOrders").end()
					.filter(simple("${header.order_status} == 'normal'")).to("jms:continuedProcessing");
		//TODO creat another CBR, for each type parse order by using Data Formats
		//TODO forward to continueProcessing
		
		//reception list example
		//TODO add fast pass for top tier company, instead hard code top customer do it thru SQL
		//TODO regular customer should go thru account first and then production
		//TODO use pullEnricher for production / enricher for accounting
		from("jms:xmlOrder").setHeader("customer", xpath("order/@customer")).process(new Processor()
		{
			public void process(Exchange exchange) throws Exception
			{
				String reciptionList = "jms:accouting";
				if(header("customer").equals("honda"))
				{
					reciptionList+=", jms:production";
				}
				exchange.getIn().setHeader("reciptionList", reciptionList);
			}
		}).recipientList(header("reciptionList"));
	}

}
