package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.*;
import apap.tugas.bobaxixixi.service.BobaTeaService;
import apap.tugas.bobaxixixi.service.ManagerService;
import apap.tugas.bobaxixixi.service.StoreBobaService;
import apap.tugas.bobaxixixi.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import java.lang.*;

import java.time.LocalTime;
import java.util.Random;

import org.springframework.validation.BindingResult;

@Controller
public class StoreController {
    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("storeBobaServiceImpl")
    @Autowired
    private StoreBobaService storeBobaService;

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    private BobaTeaService bobaTeaService;

    @GetMapping("/store")
    private String viewStore(Model model){
        List<StoreModel> listStore = storeService.getStoreList();
        model.addAttribute("listStore", listStore);
        return "view-all-store";
    }

    @GetMapping("/store/add")
    public String addStoreForm(Model model) {
        StoreModel store = new StoreModel();
        List<ManagerModel> listManager = managerService.getManagerList();

        model.addAttribute("store", store);
        model.addAttribute("listManager", listManager);
        return "form-add-store";
    }

    @PostMapping(value = "/store/add")
    public String addStoreSubmit(@ModelAttribute StoreModel store, Model model) {
        String fstChar = "SC";

        StringBuilder nameReversed = new StringBuilder(store.getName().toUpperCase().substring(0,3));
        String secChar = nameReversed.reverse().toString();

        LocalTime open = store.getOpenHour(), close = store.getCloseHour();
        String finalOpen = open.toString().substring(0,2);
        String finalClose = String.valueOf(Integer.parseInt(close.toString().substring(0,2)) / 10);

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        String lstChar = sb.toString();

        String code = fstChar + secChar + finalOpen + finalClose + lstChar;

        store.setStoreCode(code);
        storeService.addStore(store);

        model.addAttribute("name", store.getName());
        model.addAttribute("code", store.getStoreCode());
        return "add-store";
    }

    @GetMapping("/store/{id}")
    public String viewDetailStore(@PathVariable Long id, Model model){
        StoreModel store = storeService.getStoreByIdStore(id);

        List<StoreBobaTeaModel> listStoreBoba = store.getListStoreBoba();

        model.addAttribute("store", store);
        model.addAttribute("listStoreBoba", listStoreBoba);

        return "view-detail-store";
    }

    @GetMapping("/store/update/{id}")
    public String updateStoreForm(@PathVariable Long id, Model model){
        StoreModel store = storeService.getStoreByIdStore(id);
        List<ManagerModel> listManager = managerService.getManagerList();
        boolean canUpdate = storeService.timeHandler(store);

        if(canUpdate == true){
            model.addAttribute("store", store);
            model.addAttribute("listManager", listManager);

            return "form-update-store";
        }
        else{
            model.addAttribute("name", store.getName());
            return "error-update-store";
        }
    }

    @PostMapping("/store/update")
    public String updateStoreSubmit(@ModelAttribute StoreModel store, Model model){
        String fstChar = "SC";

        StringBuilder nameReversed = new StringBuilder(store.getName().toUpperCase().substring(0,3));
        String secChar = nameReversed.reverse().toString();

        LocalTime open = store.getOpenHour(), close = store.getCloseHour();
        String finalOpen = open.toString().substring(0,2);
        String finalClose = String.valueOf(Integer.parseInt(close.toString().substring(0,2)) / 10);

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        String lstChar = sb.toString();

        String code = fstChar + secChar + finalOpen + finalClose + lstChar;

        store.setStoreCode(code);
        storeService.updateStore(store);

        model.addAttribute("name", store.getName());
        model.addAttribute("code", store.getStoreCode());

        return "update-store";
    }

    @GetMapping("/store/delete/{id}")
    public String deleteStore(@PathVariable Long id, Model model) {
        StoreModel store = storeService.getStoreByIdStore(id);
        boolean canDelete = storeService.timeHandler(store);

        if(canDelete == true){
            if(store.getListStoreBoba().size() == 0){
                storeService.removeStore(store);
                model.addAttribute("name", store.getName());
                return "delete-store";
            }
            else{
                model.addAttribute("name", store.getName());
                return "error-delete-store";
            }
        }
        else{
            model.addAttribute("name", store.getName());
            return "error-delete-store";
        }
    }

    @GetMapping("/store/{id}/assign-boba")
    public String assignBobaTeaForm(@PathVariable Long id, Model model) {
        StoreModel store = storeService.getStoreByIdStore(id);

        StoreBobaTeaModel storeBoba = new StoreBobaTeaModel();

        List<BobaTeaModel> listBobaTea = bobaTeaService.getBobaTeaList();

        model.addAttribute("store", store);
        model.addAttribute("storeBoba", storeBoba);
        model.addAttribute("listBobaTea", listBobaTea);

        return "form-assign-bobatea";
    }

    @PostMapping("/store/{id}/assign-boba")
    public String assignBobaTeaSubmit(
            @PathVariable Long id,
            @ModelAttribute StoreBobaTeaModel storeBoba,
            @RequestParam(value = "bobaTea") List<Long> bobaTeaIdList,
            Model model) {

        StoreModel store = storeService.getStoreByIdStore(id);

        List<BobaTeaModel> bobaTeaList = new ArrayList<>();

        for (int i = 0; i < bobaTeaIdList.size(); i++) {
            StoreBobaTeaModel storeBobaTea = new StoreBobaTeaModel();
            storeBobaTea.setStore(store);
            storeBobaTea.setBobaTea(bobaTeaService.getBobaTeaByIdBobaTea(bobaTeaIdList.get(i)));

            String fstChar = "PC";

            String scdChar = "";
            if(storeBobaTea.getStore().getId() < 10){
                scdChar = "00" + Long.toString(storeBobaTea.getStore().getId());
            }
            else if(storeBobaTea.getStore().getId() >= 10 && storeBobaTea.getStore().getId() < 100){
                scdChar = "0" + Long.toString(storeBobaTea.getStore().getId());
            }

            String thdChar = "";
            if(storeBobaTea.getBobaTea().getTopping() == null) thdChar = "0";
            else thdChar = "1";

            String lstChar = "";
            if(storeBobaTea.getBobaTea().getId() < 10){
                lstChar = "00" + Long.toString(storeBobaTea.getBobaTea().getId());
            }
            else if(storeBobaTea.getBobaTea().getId() >= 10 && storeBobaTea.getBobaTea().getId() < 100){
                lstChar = "0" + Long.toString(storeBobaTea.getBobaTea().getId());
            }

            String code = fstChar + scdChar + thdChar + lstChar;

            storeBobaTea.setProductionCode(code);

            storeBobaService.addStoreBoba(storeBobaTea);
        }
        model.addAttribute("boba", bobaTeaList);
        model.addAttribute("store", store);
        model.addAttribute("listOfBoba", bobaTeaList);
        model.addAttribute("storeBobaTea", store.getListStoreBoba());

        return "assign-bobatea";

    }

}
