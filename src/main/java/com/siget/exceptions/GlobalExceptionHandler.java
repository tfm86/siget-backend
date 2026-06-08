package com.siget.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class GlobalExceptionHandler {

    public ResponseEntity<Map<String,Object>> handleEmailJaCadastrado(EmailJaCadastradoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", 409,
                        "erro", ex.getMessage()
                )
        );
    }
}
