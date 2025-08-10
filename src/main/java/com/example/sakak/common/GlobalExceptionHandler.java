package com.example.sakak.common;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    static class ProblemDetailResource extends RepresentationModel<ProblemDetailResource> {
        private final ProblemDetail problem;

        public ProblemDetailResource(ProblemDetail problem) {
            this.problem = problem;
        }

        public ProblemDetail getProblem() { return problem; }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Entity Not Found");
        pd.setProperty("timestamp", OffsetDateTime.now());
        pd.setProperty("errorCode", "entityNotFound");

        ProblemDetailResource body = new ProblemDetailResource(pd);
        body.add(Link.of("/docs/index.html").withRel("profile"));

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }
}
