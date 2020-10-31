package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.repository.PilotDb;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import apap.tugas.sipil.service.PilotService;


import java.util.List;
import java.util.Random;
import java.time.*;


@Service
@Transactional
public class PilotServiceImpl implements PilotService {
    String jk;
    String tl;
    String np;
    int gl;
    int gb;
    String lastnama;
    String glc;
    String gbc;
    double thl;
    int tahun;
    String thstr;
    String random;
    String hasil;
    String hasildua;
    String nip;
    String hasiltiga;
    String hasilempat;



    @Autowired
    PilotDb pilotDb;
//    @Autowired
//    private PilotService pilotService;

    @Override
    public void addPilot (PilotModel pilot) {
        pilotDb.save(pilot);
    }

    @Override
    public List<PilotModel> getPilotList(){
        return pilotDb.findAll();
    }

//    @Override
//    public List<PilotModel> getPilotListDesc(Sort.by(Sort.Direction.ASC,)
    @Override
    public List<PilotModel> getPilotListDescCounter(){
        return pilotDb.findByOrderByCounterDesc();
    }
    @Override
    public String getNip (PilotModel pilot){
        jk = Integer.toString(pilot.getJenisKelamin());
        tl = pilot.getTempatLahir().substring(0,2).toUpperCase();
        np = pilot.getNama();
        lastnama = String.valueOf(np.charAt(np.length()-1)).toUpperCase();
        LocalDate localDate = pilot.getTanggalLahir().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        gl = localDate.getDayOfMonth();
//        gl = pilot.getTanggalLahir().getDay();
        glc = Integer.toString(gl);
//        gb =pilot.getTanggalLahir().getMonth();
        gb = localDate.getMonthValue();
        gbc = Integer.toString(gb);
        thl = localDate.getYear()/10;
        tahun = (int) Math.floor(thl);
        thstr = Integer.toString(tahun);
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rd = new Random();
        char letter = abc.charAt(rd.nextInt(abc.length()));
        Random r = new Random();
        String huruf = Character.toString(abc.charAt(r.nextInt(abc.length())));
        random = Character.toString(letter);
//        random = RandomString.make(2).toUpperCase();
        hasil = jk+tl+lastnama+glc+gbc+thstr+random+huruf;
        hasildua = jk+tl+lastnama+glc+"0"+gbc+thstr+random+huruf;
        hasiltiga = jk+tl+lastnama+"0"+glc+"0"+gbc+thstr+random+huruf;
        hasilempat = jk+tl+lastnama+"0"+glc+gbc+thstr+random+huruf;

        if (gb<10 && gl<10){
            return hasiltiga;
        } else if(gb<10 && gl>9){
            return hasildua;
        }
        else if(gb>9 && gl<10){
            return hasilempat;
        }
        else {
            return hasil;
        }
    }

    @Override
    public PilotModel getPilotByNip(String nipPilot){
        return pilotDb.findByNip(nipPilot).get();
    }
    @Override
    public PilotModel getPilotByNik(String nikPilot){
        return pilotDb.findByNik(nikPilot).get();
    }
    @Override
    public PilotModel updatePilot(PilotModel pilot){
        pilotDb.save(pilot);
        return pilot;
    }
    @Override
    public PilotModel getPilotById(Long id){
        return pilotDb.findById(id).get();
    }

    @Override
    public List<PilotModel> getPilotListByMaskapai(String kode){
        return pilotDb.findAllByMaskapaiModel_Kode(kode);
    }

    @Override
    public List<PilotModel> getPilotListByAkademi(Long id){
        return pilotDb.findAllByAkademiModel_Id(id);
    }

    @Override
    public List<PilotModel> getPilotListByMaskapaiandAkademi(String kode, Long id){
        return pilotDb.findAllByMaskapaiModel_KodeAndAkademiModel_Id(kode, id);
    }

//    @Override
//    public List<PilotModel> getPilotListDescCounterLimit(String kode,int count){
//        return pilotDb.findAllByOrderByCounterDescLimitToAndMaskapaiModel_Kode(count, kode);
//    }


}