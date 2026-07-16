package cl.duoc.ETF.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            IllegalStateException.class
    )
    public ResponseEntity<Map<String, Object>>
    manejarEstadoInvalido(
            IllegalStateException ex
    ) {
        Map<String, Object> respuesta =
                new LinkedHashMap<>();

        respuesta.put(
                "estado",
                HttpStatus.NOT_FOUND.value()
        );

        respuesta.put(
                "error",
                "No fue posible consumir el mensaje"
        );

        respuesta.put(
                "mensaje",
                ex.getMessage()
        );

        respuesta.put(
                "fecha",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>>
    manejarErrorGeneral(
            Exception ex
    ) {
        Map<String, Object> respuesta =
                new LinkedHashMap<>();

        respuesta.put(
                "estado",
                HttpStatus.INTERNAL_SERVER_ERROR
                        .value()
        );

        respuesta.put(
                "error",
                "Error interno"
        );

        respuesta.put(
                "mensaje",
                ex.getMessage()
        );

        respuesta.put(
                "fecha",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(respuesta);
    }
}
