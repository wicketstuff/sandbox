package org.wicketstuff.jmx.markup.html.tree;

import java.util.Map;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;
import org.wicketstuff.jmx.util.JmxUtil;

public class OperationExecuteButton extends AjaxButton
{

	private static final long serialVersionUID = 1L;

	private JmxMBeanWrapper mbean;
	private MBeanOperationInfo operation;
	private Map parameters;


	public OperationExecuteButton(String id, Form form, JmxMBeanWrapper bean,
			MBeanOperationInfo operation, Map parameters)
	{
		super(id, form);
		this.mbean = bean;
		this.operation = operation;
		this.parameters = parameters;
	}

	@Override
	protected final void onSubmit(AjaxRequestTarget target, Form form)
	{
		MBeanParameterInfo[] params = operation.getSignature();
		Object[] values = new Object[params.length];
		String[] signature = new String[params.length];
		Object result = null;
		for (int i = 0; i < params.length; i++)
		{
			values[i] = parameters.get(params[i].getName());
			signature[i] = JmxUtil.getType(params[i].getType()).getName();
		}
		try
		{
			result = mbean.invoke(operation, values, signature);
		}
		catch (Exception e)
		{
			result = e;
		}
		onSubmit(target, result);
	}

	protected void onSubmit(AjaxRequestTarget target, Object result)
	{

	}

	@Override
	protected void onError(AjaxRequestTarget target, Form form)
	{
		target.addComponent(form);
		super.onError(target, form);
	}
}
