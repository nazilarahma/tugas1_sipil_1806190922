package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;

import java.util.List;

public interface PilotService {
    void addPilot(PilotModel pilot);
    List<PilotModel> getPilotList();
    String getNip(PilotModel pilot);
    PilotModel getPilotByNip(String nipPilot);
    PilotModel getPilotById(Long id);
    PilotModel getPilotByNik(String nikPilot);
    PilotModel updatePilot(PilotModel pilot);
    List<PilotModel> getPilotListByMaskapai(String kode);
    List<PilotModel> getPilotListByAkademi(Long id);
    List<PilotModel> getPilotListByMaskapaiandAkademi(String kode, Long id);
    List<PilotModel> getPilotListDescCounter();
//    List<PilotModel> getPilotListDescCounterLimit(String kode, Integer count);
//    List<PilotModel> getPilotListDescCounterLimit(String kode,int count);




}
