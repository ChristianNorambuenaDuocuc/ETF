package cl.duoc.ETF.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MENSAJES_CONSUMIDOS")
public class MensajeConsumido {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mensaje_consumido_seq"
    )
    @SequenceGenerator(
            name = "mensaje_consumido_seq",
            sequenceName = "MENSAJE_CONSUMIDO_SEQ",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "TIPO_EVENTO")
    private String tipoEvento;

    @Column(name = "CURSO_ID")
    private Long cursoId;

    @Column(name = "ESTUDIANTE_ID")
    private String estudianteId;

    @Column(name = "NOMBRE_ESTUDIANTE")
    private String nombreEstudiante;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "FECHA_EVENTO")
    private LocalDateTime fechaEvento;

    @Column(name = "FECHA_CONSUMO")
    private LocalDateTime fechaConsumo;

    public MensajeConsumido() {
    }

    public Long getId() {
        return id;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(
            String tipoEvento
    ) {
        this.tipoEvento = tipoEvento;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(
            Long cursoId
    ) {
        this.cursoId = cursoId;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(
            String estudianteId
    ) {
        this.estudianteId = estudianteId;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(
            String nombreEstudiante
    ) {
        this.nombreEstudiante =
                nombreEstudiante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(
            String descripcion
    ) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(
            String estado
    ) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(
            LocalDateTime fechaEvento
    ) {
        this.fechaEvento = fechaEvento;
    }

    public LocalDateTime getFechaConsumo() {
        return fechaConsumo;
    }

    public void setFechaConsumo(
            LocalDateTime fechaConsumo
    ) {
        this.fechaConsumo = fechaConsumo;
    }
}
