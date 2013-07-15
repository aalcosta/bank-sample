package br.com.dextra.curso.bank.interceptors;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import br.com.dextra.curso.bank.domain.Account;
import br.com.dextra.curso.bank.service.AccountStatelessServiceBean;
import br.com.dextra.curso.bank.util.JMSSenderUtil;

public class AccountTaxStatelessInterceptor {

	@Resource
	private SessionContext ctx;

	@AroundInvoke
	public Object taxInterceptor(InvocationContext invCtx) throws Exception {

		Object result = invCtx.proceed();

		Object[] params = invCtx.getParameters();
		String accountNumber = (String) params[0];
		BigDecimal movimentValue = (BigDecimal) params[1];

		AccountStatelessServiceBean bean = (AccountStatelessServiceBean) invCtx
				.getTarget();
		Account account = bean.findAccountByNumber(accountNumber); 

		JMSSenderUtil.sendJMSMessage(ctx, "topic/A", new InterceptorMessage(
				account.getId(), movimentValue));

		return result;
	}

}
