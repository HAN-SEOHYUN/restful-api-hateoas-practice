package com.example.sakak.common;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.Errors;

import java.util.List;

public class ErrorsResource extends RepresentationModel<ErrorsResource> {

    public static class ApiError {
        private final String code;
        private final String message;
        private final String objectName;

        public ApiError(String code, String message, String objectName) {
            this.code = code;
            this.message = message;
            this.objectName = objectName;
        }
        public String getCode() { return code; }
        public String getMessage() { return message; }
        public String getObjectName() { return objectName; }
    }

    private final List<ApiError> errors;

    public ErrorsResource(List<ApiError> errors) {
        this.errors = errors;
    }
    public List<ApiError> getErrors() { return errors; }

    public static ErrorsResource from(Errors bindingErrors) {
        List<ApiError> list = bindingErrors.getAllErrors().stream()
                .map(e -> new ApiError(
                        e.getCode(),
                        e.getDefaultMessage(),
                        e.getObjectName()
                ))
                .toList();
        return new ErrorsResource(list);
    }
}
