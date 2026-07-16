package cl.duoc.ETF.service;

import cl.duoc.ETF.config.RabbitMQConfig;
import cl.duoc.ETF.dto.MensajeCursoDTO;
import cl.duoc.ETF.model.MensajeConsumido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.duoc.ETF.repository.MensajeConsumidoRepository;

import java.time.LocalDateTime;

@Service
public class MensajeConsumidorService {

    private final RabbitTemplate rabbitTemplate;

    private final MensajeConsumidoRepository repository;

    public MensajeConsumidorService(
            RabbitTemplate rabbitTemplate,
            MensajeConsumidoRepository repository
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    @Transactional
    public MensajeConsumido consumirMensaje() {

        Object objetoRecibido =
                rabbitTemplate.receiveAndConvert(
                        RabbitMQConfig.QUEUE_MENSAJES,
                        5000
                );

        if (objetoRecibido == null) {
            throw new IllegalStateException(
                    "No existen mensajes pendientes "
                            + "en la cola"
            );
        }

        if (!(objetoRecibido
                instanceof MensajeCursoDTO dto)) {

            throw new IllegalStateException(
                    "El mensaje recibido no corresponde "
                            + "al formato MensajeCursoDTO"
            );
        }

        MensajeConsumido mensaje =
                convertirMensaje(dto);

        MensajeConsumido guardado =
                repository.save(mensaje);

        System.out.println(
                "Mensaje consumido y guardado."
        );

        System.out.println(
                "ID almacenado: "
                        + guardado.getId()
        );

        return guardado;
    }

    private MensajeConsumido convertirMensaje(
            MensajeCursoDTO dto
    ) {
        MensajeConsumido mensaje =
                new MensajeConsumido();

        mensaje.setTipoEvento(
                dto.getTipoEvento()
        );

        mensaje.setCursoId(
                dto.getCursoId()
        );

        mensaje.setEstudianteId(
                dto.getEstudianteId()
        );

        mensaje.setNombreEstudiante(
                dto.getNombreEstudiante()
        );

        mensaje.setDescripcion(
                dto.getDescripcion()
        );

        mensaje.setEstado(
                "CONSUMIDO"
        );

        mensaje.setFechaEvento(
                dto.getFechaEvento()
        );

        mensaje.setFechaConsumo(
                LocalDateTime.now()
        );

        return mensaje;
    }
}