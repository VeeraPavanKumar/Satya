package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {
private static final long serialversionUID=1L;
	  public String getResourceName() {
	return resourceName;
}
public void setResourceName(String resourceName) {
	this.resourceName = resourceName;
}
public String getFieldName() {
	return fieldName;
}
public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
}
public Object getFieldValue() {
	return fieldValue;
}
public void setFieldValue(Object fieldValue) {
	this.fieldValue = fieldValue;
}
public static long getSerialversionuid() {
	return serialversionUID;
}
	private String resourceName;
	  private String fieldName;
	  private Object fieldValue;
	public ResourceNotFound(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s:'&s'",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	  
	  
	  }
