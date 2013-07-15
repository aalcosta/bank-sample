package br.com.dextra.curso.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dextra.curso.bank.exceptions.AccountNotFoundException;
import br.com.dextra.curso.bank.exceptions.NoAvailableCashException;
import br.com.dextra.curso.bank.service.AccountStatelessServiceLocal;

public class AccountWithdrawServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.execute(req, resp);
	}

	private void execute(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		String accountNumber = req.getParameter("num");
		BigDecimal value = new BigDecimal(req.getParameter("val"));
		try {
			AccountStatelessServiceLocal service = (AccountStatelessServiceLocal) new InitialContext()
					.lookup("bank/AccountStatelessServiceBean/local");

			value = service.withdraw(accountNumber, value);
			value.round(new MathContext(2, RoundingMode.CEILING));
			out.append("Sucessful Deposit! Current situation: " + value);
		} catch (AccountNotFoundException e) {
			out
					.append("Withdraw Fail! Account " + accountNumber
							+ "not found!");
		} catch (NoAvailableCashException e) {
			out.append("Withdraw Fail! Insuficient funds on account "
					+ accountNumber);
		} catch (NamingException e) {
			out.append("Infrastructure ERROR!");
		}
	}
}
