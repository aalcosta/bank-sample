package br.com.dextra.curso.bank.util;

import java.io.Serializable;

import javax.ejb.EJBContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe utilizatara para publicacoo de mensagens JMS por EJBs
 */
public class JMSSenderUtil {

	/**
	 * Logger
	 */
	private static final Log LOGGER = LogFactory.getLog(JMSSenderUtil.class);

	public static void sendJMSMessage(EJBContext ctx,
			String destinationJNDI, Serializable message) {

		ConnectionFactory connFactory = null;
		Connection conn = null;
		Session session = null;
		MessageProducer publisher = null;
			
		try {
			Destination destiny = (Destination) ctx.lookup(destinationJNDI);

			connFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
			conn = connFactory.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			publisher = session.createProducer(destiny);

			ObjectMessage msg = session.createObjectMessage(message);
			publisher.send(msg);
		} catch (JMSException e) {
			LOGGER.error("Error sending message to e-mail notification", e);
		} finally {
			try {
				if (publisher != null)
					publisher.close();
				if (session != null)
					session.close();
				if (conn != null)
					conn.close();
			} catch (JMSException e) {
				LOGGER.error("Error closing JMS publisher|session|connection",
						e);
			}
		}
	}

}
