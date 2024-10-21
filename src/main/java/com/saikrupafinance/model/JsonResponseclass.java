package com.saikrupafinance.model;

public class JsonResponseclass {
	
	private String status;
	private String result;
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "JsonResponse [status=" + status + ", result=" + result + ", message=" + message + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

	private String message;
	

}
