package org.innobuilt.wicket.rest.jsonrpc;

import java.io.Serializable;
import com.google.gson.annotations.Expose;

public class Response implements Serializable{
	@Expose
	private Object result;
	@Expose
	private String id;
	@Expose
	private String jsonrpc;
	@Expose
	private org.innobuilt.wicket.rest.jsonrpc.Error error;
	
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	public String getJsonrpc() {
		return jsonrpc;
	}
	public void setError(org.innobuilt.wicket.rest.jsonrpc.Error error) {
		this.error = error;
	}
	public org.innobuilt.wicket.rest.jsonrpc.Error getError() {
		return error;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Object getResult() {
		return result;
	}
}
