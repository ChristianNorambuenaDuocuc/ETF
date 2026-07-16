package cl.duoc.ETF.repository;


import cl.duoc.ETF.model.MensajeConsumido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeConsumidoRepository
        extends JpaRepository<
                MensajeConsumido,
                Long
        > {

    List<MensajeConsumido>
    findByEstudianteId(
            String estudianteId
    );

    List<MensajeConsumido>
    findByCursoId(
            Long cursoId
    );
}
