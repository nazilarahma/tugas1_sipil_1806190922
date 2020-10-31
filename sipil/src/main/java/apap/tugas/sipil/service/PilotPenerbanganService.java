package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;

import java.util.Date;
import java.util.List;

public interface PilotPenerbanganService {
    PilotPenerbanganModel getPilotPenerbanganByIdPenerbangan(Long id);
    void addPilotPenerbangan(PilotPenerbanganModel pilotPenerbangan);
    List<PilotPenerbanganModel> getListPilotByIdPenerbangan(Long id);
    List<PilotPenerbanganModel> getListPenerbanganByIdPilot(Long id);
    List<PilotPenerbanganModel> getPilotPenerbanganList();
    Integer countByIdPilot(Long id);
    List<PilotPenerbanganModel> getByTanggalPenerbanganBetween(Date d1, Date d2);

}
