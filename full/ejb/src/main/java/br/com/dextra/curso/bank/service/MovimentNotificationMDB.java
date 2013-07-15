package br.com.dextra.curso.bank.service;

import java.text.SimpleDateFormat;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.dextra.curso.bank.domain.Moviment;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/B") })
public class MovimentNotificationMDB implements MessageListener {

	/**
	 * Logger
	 */
	private static final Log LOGGER = LogFactory
			.getLog(AccountStatefulServiceBean.class);

	public void onMessage(Message message) {

		try {
			ObjectMessage msg = (ObjectMessage) message;
			Moviment moviment = (Moviment) msg.getObject();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			StringBuilder sb = new StringBuilder("\n ############## \n   ");
			sb.append(moviment.getDesc()).append(" de R$ ");
			sb.append(moviment.getValue()).append(" realizado na conta '");
			sb.append(moviment.getTarget().getAccountNumber()).append("' em ");
			sb.append(sdf.format(moviment.getDate()));
			sb.append("\n ############## ");

			LOGGER.info(sb.toString());
		} catch (JMSException e) {
			LOGGER.error("JMS Unexpected exception", e);
			throw new EJBException();
		}
	}
}
