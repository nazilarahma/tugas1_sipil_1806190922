package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.AkademiModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.service.PilotPenerbanganService;
import apap.tugas.sipil.service.PilotService;
import apap.tugas.sipil.service.AkademiService;
import apap.tugas.sipil.service.MaskapaiService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Map.Entry;

@Controller
public class PilotController {
    @Qualifier("pilotServiceImpl")
    @Autowired
    private PilotService pilotService;
    @Autowired
    private AkademiService akademiService;
    @Autowired
    private MaskapaiService maskapaiService;
    @Autowired
    private PilotPenerbanganService pilotPenerbanganService;


    @GetMapping("/")
    private String home() {
        return "home";
    }

    @RequestMapping("/pilot")
    public String listBonus(Model model){
        List<PilotModel> listPilot = pilotService.getPilotList();
        model.addAttribute("listPilot", listPilot);
        return "viewall-pilot";
    }

    @RequestMapping("/bonus")
    public String listPilotBonus(Model model){
        List<PilotPenerbanganModel> listpp = pilotPenerbanganService.getPilotPenerbanganList();

        Date today = new Date();
        System.out.println(today);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date lastMonth = cal.getTime();
        List<PilotPenerbanganModel> listPilotPenerbangan = pilotPenerbanganService.getByTanggalPenerbanganBetween(lastMonth,today);
        List<PilotModel> listPilot = new ArrayList<>();
        for(PilotPenerbanganModel pilot: listPilotPenerbangan){
            PilotModel getPilot = pilot.getPilotModel();
            if(!listPilot.contains(getPilot)){
                listPilot.add(getPilot);
            }
        }
        model.addAttribute("listPilot", listPilot);
        return "bonus";
    }

    @GetMapping("/pilot/tambah")
    public String addPilotFormPage(Model model){
        model.addAttribute("pilot", new PilotModel());
        model.addAttribute("listAkademi", akademiService.getAkademiList());
        model.addAttribute("listMaskapai", maskapaiService.getMaskapaiList());

        return "form-add-pilot";
    }

    @PostMapping("/pilot/tambah")
    public String addPilotSubmit(
            @ModelAttribute PilotModel pilot, Model model){
        pilot.setNip(pilotService.getNip(pilot));
        pilotService.addPilot(pilot);
        model.addAttribute("nip", pilot.getNip());
        model.addAttribute("idAkademi", pilot.getAkademiModel().getId());
        model.addAttribute("idMaskapai", pilot.getMaskapaiModel().getId());


        return "add-pilot";
    }



    @GetMapping("/pilot/ubah/{nipPilot}")
    public String changePilotFormPage(
            @PathVariable String nipPilot,
            Model model
    ) {

        PilotModel pilot = pilotService.getPilotByNip(nipPilot);
        model.addAttribute("pilot", pilot);
        model.addAttribute("nip", pilot.getNip());
        model.addAttribute("listAkademi", akademiService.getAkademiList());
        model.addAttribute("listMaskapai", maskapaiService.getMaskapaiList());
        return "form-update-pilot";
    }

    @PostMapping("pilot/ubah")
    public String changePilotSubmit(
            @ModelAttribute PilotModel pilot,
            Model model
    ) {

        pilot.setNip(pilotService.getNip(pilot));
        PilotModel updatePilot = pilotService.updatePilot(pilot);
        model.addAttribute("Id", pilot.getId());
        model.addAttribute("pilot", updatePilot);
        return "update-pilot";
    }

    @GetMapping("/cari/pilot")
    public String cariPilotSubmit(
            @RequestParam(required = false) String kodeMaskapai,
            @RequestParam(required = false) Long idSekolah,
            Model model
    ) {
        model.addAttribute("listAkademi", akademiService.getAkademiList());
        model.addAttribute("listMaskapai", maskapaiService.getMaskapaiList());
        if(kodeMaskapai!=null && idSekolah==null){
            List<PilotModel> pilotMaskapai = pilotService.getPilotListByMaskapai(kodeMaskapai);
            model.addAttribute("listPilot", pilotMaskapai);
            return "list-pilot-filter";
        }
        else if(kodeMaskapai==null && idSekolah!=null){
            List<PilotModel> pilotAkademi = pilotService.getPilotListByAkademi(idSekolah);
            model.addAttribute("listPilot", pilotAkademi);
            return "list-pilot-filter";
        }
        else {
            List<PilotModel> pilotMaskapaiAkademi = pilotService.getPilotListByMaskapaiandAkademi(kodeMaskapai,idSekolah);
            model.addAttribute("listPilot", pilotMaskapaiAkademi);
            return "list-pilot-filter";
        }
    }

    @GetMapping("/cari/pilot/penerbangan-terbanyak")
    public String cariPilotTerbanyak(
            @RequestParam(required = false) String kodeMaskapai, Model model
    ) {
        model.addAttribute("listMaskapai", maskapaiService.getMaskapaiList());
        List<PilotModel> pilotMaskapai = pilotService.getPilotListByMaskapai(kodeMaskapai);
        List<Long> idPilotList = new ArrayList<Long>();
        for (PilotModel idPilotMaskapai : pilotMaskapai){
            idPilotList.add(idPilotMaskapai.getId());
        }
        List countPenerbangan = new ArrayList();
        List<PilotModel> listPilot = new ArrayList<>();
        for (Long id: idPilotList){

            List<PilotPenerbanganModel> pilotPenerbangan = pilotPenerbanganService.getListPenerbanganByIdPilot(id);
            PilotModel pilot = pilotService.getPilotById(id);

            for(PilotPenerbanganModel pilper: pilotPenerbangan){
                pilot= pilper.getPilotModel();
            }

            int banyakPenerbangan = pilotPenerbanganService.countByIdPilot(id);
            pilot.setCounter(banyakPenerbangan);
            listPilot.add(pilot);
        }
        List<PilotModel> listPilotnya = pilotService.getPilotListDescCounter();
        Collections.sort(listPilot, Collections.reverseOrder(Comparator.comparingInt(PilotModel::getCounter)));
        try{
            List<PilotModel> result = new ArrayList<>();
            if(listPilot.size()==1){
                if(listPilot.get(0).getCounter()!=0){
                    result.add(listPilot.get(0));
                }
            } else if(listPilot.size()==2){
                if(listPilot.get(0).getCounter()!=0){
                    result.add(listPilot.get(0));
                    result.add(listPilot.get(1));

                }
//                result.add(listPilot.get(0));
//                result.add(listPilot.get(1));
            }else{
                for(int i = 0; i<3;i++){
                    if(listPilot.get(0).getCounter()!=0){
                        result.add(listPilot.get(i));
                    }
                }
            }

            listPilot.sort(Comparator.comparingInt(PilotModel::getCounter));

            model.addAttribute("listPilot", result);

            return "penerbangan-terbanyak";
        } catch (Exception e){
            return "penerbangan-terbanyak";
        }
    }



}
