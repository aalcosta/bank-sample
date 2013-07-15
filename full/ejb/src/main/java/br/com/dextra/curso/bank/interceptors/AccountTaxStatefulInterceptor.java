package br.com.dextra.curso.bank.interceptors;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import br.com.dextra.curso.bank.domain.Account;
import br.com.dextra.curso.bank.service.AccountStatefulServiceBean;
import br.com.dextra.curso.bank.util.JMSSenderUtil;

public class AccountTaxStatefulInterceptor {

	@Resource
	private SessionContext ctx;

	@AroundInvoke
	public Object taxInterceptor(InvocationContext invCtx) throws Exception {

		Object result = invCtx.proceed();

		Object[] params = invCtx.getParameters();
		BigDecimal movimentValue = (BigDecimal) params[0];

		AccountStatefulServiceBean bean = (AccountStatefulServiceBean) invCtx
				.getTarget();
		Account account = bean.getAccount(); 

		JMSSenderUtil.sendJMSMessage(ctx, "topic/A", new InterceptorMessage(
				account.getId(), movimentValue));

		return result;
	}

}
