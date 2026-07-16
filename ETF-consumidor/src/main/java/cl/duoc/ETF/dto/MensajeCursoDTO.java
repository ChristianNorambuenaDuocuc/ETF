package cl.duoc.ETF.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeCursoDTO implements Serializable {

    private String tipoEvento;

    private Long cursoId;

    private String estudianteId;

    private String nombreEstudiante;

    private String descripcion;

    private String estado;

    private LocalDateTime fechaEvento;
}