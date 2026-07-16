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
            IllegalArgumentException.class
    )
    public ResponseEntity<Map<String, Object>>
    manejarDatosInvalidos(
            IllegalArgumentException ex
    ) {
        Map<String, Object> respuesta =
                new LinkedHashMap<>();

        respuesta.put(
                "estado",
                HttpStatus.BAD_REQUEST.value()
        );

        respuesta.put(
                "error",
                "Solicitud inválida"
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
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }
}
