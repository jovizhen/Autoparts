package com.jovi.auto;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import com.jovi.auto.route.AutoshopRouteBuilder;

/**
 * Hello world!
 *
 */
public class CamelMain
{
	public static void main(String[] args) throws Exception
	{
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		context.addRoutes(new AutoshopRouteBuilder());

		context.start();
		Thread.sleep(10000);

		context.stop();
	}
}
