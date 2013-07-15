package br.com.dextra.curso.bank.service;

import java.math.BigDecimal;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.dextra.curso.bank.interceptors.InterceptorMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/A") })
public class CPMFTaxCalculatorMDB implements MessageListener {

	/**
	 * Logger
	 */
	private static final Log LOGGER = LogFactory
			.getLog(CPMFTaxCalculatorMDB.class);

	@EJB//(name = "ejb/AccountMananger", mappedName = "bank/AccountManagerLocal")
	private AccountManagerServiceLocal accountManager;

	public void onMessage(Message message) {

		try {
			ObjectMessage msg = (ObjectMessage) message;
			InterceptorMessage moviment = (InterceptorMessage) msg.getObject();

			BigDecimal movimentValue = moviment.getMoviment().multiply(
					new BigDecimal(0.0003));
			accountManager.applyTax(moviment.getAccountId(), "CPMF",
					movimentValue);

		} catch (JMSException e) {
			LOGGER.error("JMS Unexpected exception", e);
			throw new EJBException();
		}
	}
}
