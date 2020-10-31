package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.AkademiModel;
import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.service.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Map.Entry;

@Controller
public class PilotPenerbanganController {
    @Qualifier("pilotPenerbanganServiceImpl")
    @Autowired
    private PilotPenerbanganService pilotPenerbanganService;
    @Autowired
    private PenerbanganService penerbanganService;
    @Autowired
    private PilotService pilotService;

    @GetMapping("/pilot/{nipPilot}")
    public String viewPilot(
            @PathVariable String nipPilot,
            Model model
    ){
        PilotModel pilot = pilotService.getPilotByNip(nipPilot);

        List <PilotPenerbanganModel> listPP = pilotPenerbanganService.getListPenerbanganByIdPilot(pilot.getId());
        List <PenerbanganModel> listPenerbangan = new ArrayList<PenerbanganModel>();
        for(PilotPenerbanganModel i: listPP){
            PenerbanganModel penerbanganModel = i.getPenerbanganModel();
            listPenerbangan.add(penerbanganModel);
        }
        System.out.println(listPenerbangan);

        model.addAttribute("pilot", pilot);
        model.addAttribute("sekolah", pilot.getAkademiModel().getNama());
        model.addAttribute("maskapai", pilot.getMaskapaiModel().getNama());
        model.addAttribute("pilotPenerbangan", pilot.getListPilotPenerbangan());
        model.addAttribute("id", pilot.getId());
        model.addAttribute("listPenerbangan", listPenerbangan);
        return "view-pilot";
    }
    @GetMapping("/penerbangan/detail/{idPenerbangan}")
    public String viewPenerbangan(
            @PathVariable Long idPenerbangan,
            Model model
    ){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println(date);
        String link = "/pilot/tambah";
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        List <PilotPenerbanganModel> listPp = pilotPenerbanganService.getListPilotByIdPenerbangan(idPenerbangan);
        List <PilotModel> listPilotFilter = new ArrayList<PilotModel>();
        for(PilotPenerbanganModel i: listPp){
            PilotModel pilotModel = i.getPilotModel();
            listPilotFilter.add(pilotModel);
        }
        model.addAttribute("penerbangan", penerbangan);
        model.addAttribute("listPilot", pilotService.getPilotList());
        model.addAttribute("listPilotPenerbangan", penerbangan.getListPilotPenerbangan());
        model.addAttribute("pilotPenerbangan", new PilotPenerbanganModel());
        model.addAttribute("idPenerbangan", idPenerbangan);
        model.addAttribute("link", link );
        model.addAttribute("listPilotFilter", listPilotFilter);
        model.addAttribute("tanggal", date);


        return "view-penerbangan";
    }

    @PostMapping("/penerbangan/{idPenerbangan}/pilot/tambah")
    public String addPilotPenerbangan(
            @PathVariable Long idPenerbangan, PilotPenerbanganModel pilotPenerbangan, Model model) {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println(date);
        pilotPenerbangan.setTanggalPenugasan(date);
        PilotModel pilot = pilotService.getPilotById(pilotPenerbangan.getPilotModel().getId());
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(pilotPenerbangan.getPenerbanganModel().getId());
        pilotPenerbanganService.addPilotPenerbangan(pilotPenerbangan);

        model.addAttribute("nama", pilot.getNama());
        model.addAttribute("kode", penerbangan.getKode());
        return "add-pilot-penerbangan";
    }


}
