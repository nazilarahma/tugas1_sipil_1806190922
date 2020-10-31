package apap.tugas.sipil.repository;

import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PilotPenerbanganDb extends JpaRepository<PilotPenerbanganModel,Long> {
    Optional<PilotPenerbanganModel> findByPenerbanganModel_Id(Long id);
    List<PilotPenerbanganModel> findAllByPenerbanganModel_Id(Long id);
    List<PilotPenerbanganModel> findAllByPilotModel_Id(Long id);
    Integer countPilotPenerbanganModelByPilotModel_Id(Long id);
    List<PilotPenerbanganModel> findAllByPenerbanganModel_WaktuBetween(Date date1, Date date2);
    List<PilotModel> findByTanggalPenugasanBetween(Date d1, Date d2);
}

