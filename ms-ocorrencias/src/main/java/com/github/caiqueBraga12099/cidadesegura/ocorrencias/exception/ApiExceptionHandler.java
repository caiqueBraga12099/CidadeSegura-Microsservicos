package com.github.caiqueBraga12099.cidadesegura.ocorrencias.exception;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ProblemDetail> negocio(RegraNegocioException exception) {
        return resposta(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> validacao(MethodArgumentNotValidException exception) {
        String mensagem = exception.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .sorted().collect(Collectors.joining("; "));
        return resposta(400, mensagem);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> formato(Exception exception) {
        return resposta(400, "JSON, enum, data ou identificador em formato invalido.");
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ObjectOptimisticLockingFailureException.class})
    public ResponseEntity<ProblemDetail> conflito(Exception exception) {
        return resposta(409, "Conflito de dados. Consulte o recurso e tente novamente.");
    }

    private ResponseEntity<ProblemDetail> resposta(int status, String mensagem) {
        ProblemDetail detalhe = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status), mensagem);
        return ResponseEntity.status(status).body(detalhe);
    }
}
