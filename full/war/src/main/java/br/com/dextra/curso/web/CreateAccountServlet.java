package br.com.dextra.curso.web;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dextra.curso.bank.domain.Account;
import br.com.dextra.curso.bank.service.AccountManagerServiceLocal;
import br.com.dextra.curso.bank.util.BankUtil;

public class CreateAccountServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB(name="AccountManagerServiceBean")
    private AccountManagerServiceLocal service;
        
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            Account account = new Account();
            account.setOwner(req.getParameter("owner"));
            account.setCash(new BigDecimal(0));
            account.generateAccountNumber();
                        
            account = service.createAccount(account);
                   
            writeResponse(resp, account);
    }

    private void writeResponse(HttpServletResponse resp, Account a) throws IOException {
        Writer out = resp.getWriter();
        
        out.append("<h1>Conta corrente criada com sucesso!</h1><br/><br/>");
        out.append("N&uacute;mero da conta: ").append(a.getAccountNumber()).append("<br/>");
        out.append("Titular: ").append(a.getOwner()).append("<br/>");
        out.append("Saldo atual: ").append(BankUtil.formatCurrency(a.getCash()));
    }

}
