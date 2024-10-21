package org.tak.todolistapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String field;
    private String filedValue;
    private Long fieldId;
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String resourceName, String field, String filedValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, field, filedValue));
        this.resourceName = resourceName;
        this.field = field;
        this.filedValue = filedValue;
    }
    public ResourceNotFoundException(String resourceName, Long fieldId) {
        super(String.format("%s not found with id : '%s'", resourceName, fieldId));
        this.resourceName = resourceName;
        this.fieldId = fieldId;
    }
}
