package apap.tugas.sipil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pilot_penerbangan")
public class PilotPenerbanganModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pilot", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PilotModel pilotModel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_penerbangan", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PenerbanganModel penerbanganModel;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @NotNull
    @Column(name = "tanggal_penugasan", nullable = false)
    private Date tanggalPenugasan;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PilotModel getPilotModel() {
        return pilotModel;
    }

    public void setPilotModel(PilotModel pilotModel) {
        this.pilotModel = pilotModel;
    }

    public PenerbanganModel getPenerbanganModel() {
        return penerbanganModel;
    }

    public void setPenerbanganModel(PenerbanganModel penerbanganModel) {
        this.penerbanganModel = penerbanganModel;
    }

    public Date getTanggalPenugasan() {
        return tanggalPenugasan;
    }

    public void setTanggalPenugasan(Date tanggalPenugasan) {
        this.tanggalPenugasan = tanggalPenugasan;
    }
}
