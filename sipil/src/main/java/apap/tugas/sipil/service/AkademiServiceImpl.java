package apap.tugas.sipil.service;

import apap.tugas.sipil.model.AkademiModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.repository.AkademiDb;
import apap.tugas.sipil.repository.PilotDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional

public class AkademiServiceImpl implements AkademiService{
    @Autowired
    AkademiDb akademiDb;

    @Override
    public List<AkademiModel> getAkademiList(){
        return akademiDb.findAll();
    }

}
