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
        store.setListStoreBoba(new ArrayList<>());
        store.getListStoreBoba().add(storeBoba);

        List<BobaTeaModel> listBobaTea = bobaTeaService.getBobaTeaList();

        model.addAttribute("store", store);
        model.addAttribute("storeBoba", storeBoba);
        model.addAttribute("listBobaTea", listBobaTea);

        return "form-assign-bobatea";
    }

    @PostMapping("/store/{id}/assign-boba")
    public String assignBobaTeaSubmit(@PathVariable Long id, @ModelAttribute StoreBobaTeaModel storeBoba, Model model) {
        StoreModel store = storeService.getStoreByIdStore(id);
        BobaTeaModel bobaTea = storeBoba.getBobaTea();

        storeBoba.setStore(store);

        List<StoreBobaTeaModel> listStoreBoba = store.getListStoreBoba();

        for (int i = 0; i < listStoreBoba.size(); i++) {
            System.out.println(listStoreBoba.get(i).getBobaTea().getName());
        }

        String fstChar = "PC";

        String scdChar = "";
        if(store.getId() < 10){
            scdChar = "00" + Long.toString(store.getId());
        }
        else if(store.getId() >= 10 && store.getId() < 100){
            scdChar = "0" + Long.toString(store.getId());
        }

        String thdChar = "";
        if(bobaTea.getTopping() == null) thdChar = "0";
        else thdChar = "1";

        String lstChar = "";
        if(bobaTea.getId() < 10){
            lstChar = "00" + Long.toString(bobaTea.getId());
        }
        else if(bobaTea.getId() >= 10 && bobaTea.getId() < 100){
            lstChar = "0" + Long.toString(bobaTea.getId());
        }

        String code = fstChar + scdChar + thdChar + lstChar;

        storeBoba.setProductionCode(code);
        storeBobaService.addStoreBoba(storeBoba);

        model.addAttribute("storeName", store.getName());
        model.addAttribute("listStoreBoba", listStoreBoba);

        return "assign-bobatea";
    }

}
