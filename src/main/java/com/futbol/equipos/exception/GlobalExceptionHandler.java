package com.futbol.equipos.exception;

import com.futbol.equipos.entity.ResponseObject;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.SignatureException;
import java.util.NoSuchElementException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleValidationException(MethodArgumentNotValidException ex) {
        ResponseObject response = new ResponseObject("La solicitud es inválida", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseObject> handleNoElementException(NoSuchElementException ex) {
        ResponseObject response = new ResponseObject("Equipo no encontrado", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseObject> handleExpiredJwtException(ExpiredJwtException ex) {
        ResponseObject response = new ResponseObject("Token JWT malformado", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ResponseObject> handleMalformedJwtException(MalformedJwtException ex) {
        ResponseObject response = new ResponseObject("Token JWT malformado", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseObject> handleSignatureException(SignatureException ex) {
        ResponseObject response = new ResponseObject("Error de firma del token JWT", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseObject> handleJwtException(JwtException ex) {
        ResponseObject response = new ResponseObject("Error JWT: " + ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseObject> handleAuthenticationException(AuthenticationException ex) {
        ResponseObject response = new ResponseObject("Falló la autenticación", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
