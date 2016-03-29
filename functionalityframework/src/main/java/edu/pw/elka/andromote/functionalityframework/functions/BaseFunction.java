package edu.pw.elka.andromote.functionalityframework.functions;

import android.content.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import edu.pw.elka.andromote.functionalityframework.datatypes.FunctionParam;

public abstract class BaseFunction implements Function {
	protected final Context context;
	protected Logger logger = LoggerFactory.getLogger(BaseDeviceFunction.class);
	protected Map<FunctionParam, String> params = new HashMap<FunctionParam, String>();
		
	public BaseFunction(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseFunction other = (BaseFunction) obj;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}
}
