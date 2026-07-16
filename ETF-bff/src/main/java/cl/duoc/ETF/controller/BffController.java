package cl.duoc.ETF.controller;


import cl.duoc.ETF.dto.MensajeCursoDTO;
import cl.duoc.ETF.service.MensajeProductorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bff")
public class BffController {

    private final MensajeProductorService productorService;

    public BffController(
            MensajeProductorService productorService
    ) {
        this.productorService = productorService;
    }

    @PostMapping("/mensajes")
    public ResponseEntity<Map<String, Object>>
    producirMensaje(
            @RequestBody MensajeCursoDTO mensaje
    ) {
        validarMensaje(mensaje);

        if (mensaje.getFechaEvento() == null) {
            mensaje.setFechaEvento(
                    LocalDateTime.now()
            );
        }

        if (mensaje.getEstado() == null
                || mensaje.getEstado().isBlank()) {

            mensaje.setEstado("PENDIENTE");
        }

        productorService.enviarMensaje(mensaje);

        Map<String, Object> respuesta =
                new LinkedHashMap<>();

        respuesta.put(
                "mensaje",
                "Mensaje enviado correctamente a RabbitMQ"
        );

        respuesta.put(
                "cola",
                "etf.cursos.queue"
        );

        respuesta.put(
                "tipoEvento",
                mensaje.getTipoEvento()
        );

        respuesta.put(
                "fechaEnvio",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(respuesta);
    }

    @GetMapping("/estado")
    public ResponseEntity<Map<String, String>>
    obtenerEstado() {

        Map<String, String> respuesta =
                new LinkedHashMap<>();

        respuesta.put(
                "aplicacion",
                "ETF-BFF"
        );

        respuesta.put(
                "estado",
                "ACTIVO"
        );

        return ResponseEntity.ok(respuesta);
    }

    private void validarMensaje(
            MensajeCursoDTO mensaje
    ) {
        if (mensaje.getTipoEvento() == null
                || mensaje.getTipoEvento().isBlank()) {

            throw new IllegalArgumentException(
                    "El tipo de evento es obligatorio"
            );
        }

        if (mensaje.getCursoId() == null) {
            throw new IllegalArgumentException(
                    "El ID del curso es obligatorio"
            );
        }

        if (mensaje.getEstudianteId() == null
                || mensaje.getEstudianteId().isBlank()) {

            throw new IllegalArgumentException(
                    "El ID del estudiante es obligatorio"
            );
        }
    }
}
