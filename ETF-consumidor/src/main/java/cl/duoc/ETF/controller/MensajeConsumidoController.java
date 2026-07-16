package cl.duoc.ETF.controller;

import cl.duoc.ETF.model.MensajeConsumido;
import cl.duoc.ETF.repository.MensajeConsumidoRepository;
import cl.duoc.ETF.service.MensajeConsumidorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeConsumidoController {

    private final MensajeConsumidorService service;

    private final MensajeConsumidoRepository repository;

    public MensajeConsumidoController(
            MensajeConsumidorService service,
            MensajeConsumidoRepository repository
    ) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/consumir")
    public ResponseEntity<MensajeConsumido>
    consumirMensaje() {

        MensajeConsumido mensaje =
                service.consumirMensaje();

        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/consumidos")
    public ResponseEntity<
            List<MensajeConsumido>
    > listarMensajesConsumidos() {

        return ResponseEntity.ok(
                repository.findAll()
        );
    }

    @GetMapping(
            "/consumidos/estudiante/{estudianteId}"
    )
    public ResponseEntity<
            List<MensajeConsumido>
    > buscarPorEstudiante(
            @PathVariable String estudianteId
    ) {
        return ResponseEntity.ok(
                repository.findByEstudianteId(
                        estudianteId
                )
        );
    }

    @GetMapping("/estado")
    public ResponseEntity<Map<String, String>>
    obtenerEstado() {

        return ResponseEntity.ok(
                Map.of(
                        "aplicacion",
                        "ETF-CONSUMIDOR",
                        "estado",
                        "ACTIVO"
                )
        );
    }
}