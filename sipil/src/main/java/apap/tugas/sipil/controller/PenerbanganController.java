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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.MissingFormatArgumentException;

@Controller
public class PenerbanganController {
    @Qualifier("penerbanganServiceImpl")
    @Autowired
    private PenerbanganService penerbanganService;
    @Autowired
    private PilotPenerbanganService pilotPenerbanganService;
    @Autowired
    private PilotService pilotService;

    @RequestMapping("/penerbangan")
    public String listPenerbangan(Model model){
        List<PenerbanganModel> listPenerbangan = penerbanganService.getPenerbanganList();
        model.addAttribute("listPenerbangan", listPenerbangan);
        return "viewall-penerbangan";
    }

    @GetMapping("/penerbangan/tambah")
    public String addPenerbanganFormPage(Model model){
        model.addAttribute("penerbangan", new PenerbanganModel());

        return "form-add-penerbangan";
    }

    @PostMapping("/penerbangan/tambah")
    public String addPilotSubmit(
            @ModelAttribute PenerbanganModel penerbangan, Model model){
        penerbanganService.addPenerbangan(penerbangan);
        model.addAttribute("kode", penerbangan.getKode());

        return "add-penerbangan";
    }

    @GetMapping("/penerbangan/ubah/{idPenerbangan}")
    public String changePenerbanganFormPage(
            @PathVariable Long idPenerbangan,
            Model model
    ) {
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        model.addAttribute("penerbangan", penerbangan);
        return "form-update-penerbangan";
    }

    @PostMapping("penerbangan/ubah")
    public String changePenerbanganSubmit(
            @ModelAttribute PenerbanganModel penerbangan,
            Model model
    ) {
        PenerbanganModel updatePenerbangan = penerbanganService.updatePenerbangan(penerbangan);
        model.addAttribute("id", penerbangan.getId());
        return "update-penerbangan";
    }

    @PostMapping("penerbangan/hapus")
    public String deletePenerbangan(
            @RequestParam("idPenerbangan") Long idPenerbangan,
            Model model
    ) {
        List<PilotPenerbanganModel> pilotPenerbanganModelList = pilotPenerbanganService.getPilotPenerbanganList();
        List longId = new ArrayList();
        for(PilotPenerbanganModel i: pilotPenerbanganModelList){
            Long penerbanganId = i.getPenerbanganModel().getId();
            longId.add(penerbanganId);
        }
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        model.addAttribute("kode", penerbangan.getKode());
        if(longId.contains(idPenerbangan)){
            return"gagal-delete-penerbangan";
        } else{
            model.addAttribute("id",idPenerbangan);
            System.out.println(idPenerbangan);
            penerbanganService.deletePenerbangan(idPenerbangan);
//            model.addAttribute("kode", penerbangan.getKode());
            return "delete-penerbangan";
        }

    }


}
