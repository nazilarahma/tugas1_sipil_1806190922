package apap.tugas.sipil.repository;

import apap.tugas.sipil.model.AkademiModel;
import apap.tugas.sipil.model.PilotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PilotDb extends JpaRepository<PilotModel,Long> {
    Optional<PilotModel> findById(Long Id);
    Optional<PilotModel> findByNip(String nipPilot);
    Optional<PilotModel> findByNik(String nikPilot);
    List<PilotModel> findAllByMaskapaiModel_Kode(String kode);
    List<PilotModel> findAllByAkademiModel_Id(Long id);
    List<PilotModel> findAllByMaskapaiModel_KodeAndAkademiModel_Id(String kode, Long id);
    List<PilotModel> findByOrderByCounterDesc();

}
