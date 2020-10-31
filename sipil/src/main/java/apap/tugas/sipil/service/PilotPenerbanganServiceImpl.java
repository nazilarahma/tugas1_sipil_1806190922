package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.repository.PilotPenerbanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class PilotPenerbanganServiceImpl implements PilotPenerbanganService {
    @Autowired
    PilotPenerbanganDb pilotPenerbanganDb;

    @Override
    public PilotPenerbanganModel getPilotPenerbanganByIdPenerbangan(Long id){
        return pilotPenerbanganDb.findByPenerbanganModel_Id(id).get();
    }

    @Override
    public void addPilotPenerbangan(PilotPenerbanganModel pilotPenerbangan ){
        pilotPenerbanganDb.save(pilotPenerbangan);
    }

    @Override
    public List<PilotPenerbanganModel> getListPilotByIdPenerbangan(Long id){
        return pilotPenerbanganDb.findAllByPenerbanganModel_Id(id);
    }
    @Override
    public List<PilotPenerbanganModel> getListPenerbanganByIdPilot(Long id){
        return pilotPenerbanganDb.findAllByPilotModel_Id(id);
    }
    @Override
    public List<PilotPenerbanganModel> getPilotPenerbanganList(){
        return pilotPenerbanganDb.findAll();
    }
    public Integer countByIdPilot(Long id){
        return pilotPenerbanganDb.countPilotPenerbanganModelByPilotModel_Id(id);
    }

    @Override
    public List<PilotPenerbanganModel> getByTanggalPenerbanganBetween(Date d1, Date d2){
        return pilotPenerbanganDb.findAllByPenerbanganModel_WaktuBetween(d1,d2);
    }

}
