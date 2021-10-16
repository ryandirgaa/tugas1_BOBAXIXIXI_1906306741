package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.*;
import apap.tugas.bobaxixixi.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.lang.*;


import org.springframework.validation.BindingResult;

@Controller
public class BobaTeaController {
    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    private BobaTeaService bobaTeaService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    @Qualifier("storeBobaServiceImpl")
    @Autowired
    private StoreBobaService storeBobaService;

    @GetMapping("/boba")
    private String viewBobaTea(Model model){
        List<BobaTeaModel> listBobaTea = bobaTeaService.getBobaTeaList();
        model.addAttribute("listBobaTea", listBobaTea);
        return "view-all-bobatea";
    }

    @GetMapping("/boba/add")
    public String addBobaTeaForm(Model model) {
        BobaTeaModel bobaTea = new BobaTeaModel();
        List<ToppingModel> listTopping = toppingService.getToppingList();

        model.addAttribute("bobaTea", bobaTea);
        model.addAttribute("listTopping", listTopping);

        return "form-add-bobatea";
    }

    @PostMapping(value = "/boba/add")
    public String addStoreSubmit(@ModelAttribute BobaTeaModel bobaTea, Model model) {
        bobaTeaService.addBobaTea(bobaTea);

        model.addAttribute("name", bobaTea.getName());

        return "add-bobatea";
    }

    @GetMapping("/boba/update/{id}")
    public String updateStoreForm(@PathVariable Long id, Model model){
        BobaTeaModel bobaTea = bobaTeaService.getBobaTeaByIdBobaTea(id);
        List<ToppingModel> listTopping = toppingService.getToppingList();

        model.addAttribute("bobaTea", bobaTea);
        model.addAttribute("listTopping", listTopping);

        return "form-update-bobatea";
    }

    @PostMapping("/boba/update")
    public String updateStoreSubmit(@ModelAttribute BobaTeaModel bobaTea, Model model){
        bobaTeaService.updateBobaTea(bobaTea);

        model.addAttribute("name", bobaTea.getName());

        return "update-bobatea";
    }

    @GetMapping("/boba/delete/{id}")
    public String deleteStore(@PathVariable Long id, Model model) {
        BobaTeaModel bobaTea = bobaTeaService.getBobaTeaByIdBobaTea(id);

        if(bobaTea.getListStoreBoba().size() == 0){
            bobaTeaService.removeBobaTea(bobaTea);
            model.addAttribute("name", bobaTea.getName());
            return "delete-bobatea";
        }
        else{
            model.addAttribute("name", bobaTea.getName());
            return "error-delete-bobatea";
        }
    }

    @GetMapping("/boba/{id}/assign-store")
    public String assignStoreForm(@PathVariable Long id, Model model) {
        BobaTeaModel bobaTea = bobaTeaService.getBobaTeaByIdBobaTea(id);

        StoreBobaTeaModel storeBoba = new StoreBobaTeaModel();

        List<StoreModel> listStore = storeService.getStoreList();

        model.addAttribute("bobaTea", bobaTea);
        model.addAttribute("storeBoba", storeBoba);
        model.addAttribute("listStore", listStore);

        return "form-assign-store";
    }

    @PostMapping("/boba/{id}/assign-store")
    public String assignStoreSubmit(
            @PathVariable Long id,
            @ModelAttribute StoreBobaTeaModel storeBoba,
            @RequestParam(value = "store") List<Long> storeIdList,
            Model model) {

        BobaTeaModel bobaTea = bobaTeaService.getBobaTeaByIdBobaTea(id);

        List<StoreModel> storeList = new ArrayList<>();

        for (int i = 0; i < storeIdList.size(); i++) {
            StoreBobaTeaModel storeBobaTea = new StoreBobaTeaModel();
            storeBobaTea.setBobaTea(bobaTea);

            storeBobaTea.setStore(storeService.getStoreByIdStore(storeIdList.get(i)));

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
        model.addAttribute("bobaTea", bobaTea);
        model.addAttribute("storeList", storeList);
        model.addAttribute("storeBobaTea", bobaTea.getListStoreBoba());

//        BobaTeaModel bobaTea = bobaTeaService.getBobaTeaByIdBobaTea(id);
//
//        StoreModel store = storeBoba.getStore();
//
//        storeBoba.setBobaTea(bobaTea);
//
//        List<StoreBobaTeaModel> listStoreBoba = bobaTea.getListStoreBoba();
//
//        String fstChar = "PC";
//
//        String scdChar = "";
//        if(store.getId() < 10){
//            scdChar = "00" + Long.toString(store.getId());
//        }
//        else if(store.getId() >= 10 && store.getId() < 100){
//            scdChar = "0" + Long.toString(store.getId());
//        }
//
//        String thdChar = "";
//        if(bobaTea.getTopping() == null) thdChar = "0";
//        else thdChar = "1";
//
//        String lstChar = "";
//        if(bobaTea.getId() < 10){
//            lstChar = "00" + Long.toString(bobaTea.getId());
//        }
//        else if(bobaTea.getId() >= 10 && bobaTea.getId() < 100){
//            lstChar = "0" + Long.toString(bobaTea.getId());
//        }
//
//        String code = fstChar + scdChar + thdChar + lstChar;
//
//        System.out.println(code);
//
//        storeBoba.setProductionCode(code);
//        storeBobaService.addStoreBoba(storeBoba);
//
//        model.addAttribute("bobaName", bobaTea.getName());
//        model.addAttribute("listStoreBoba", listStoreBoba);

        return "assign-store";
    }

    @GetMapping("/search")
    public String findBobaTeaByTopping(
            @RequestParam(value="idBobaTea") Optional<Long> idBobaTea,
            @RequestParam(value="idTopping") Optional<Long> idTopping,
            Model model
    ){
        List<BobaTeaModel> listBobaTea = bobaTeaService.getBobaTeaList();
        List<ToppingModel> listTopping = toppingService.getToppingList();

        model.addAttribute("listBobaTea", listBobaTea);
        model.addAttribute("listTopping", listTopping);

        Long toppingId = Long.valueOf(0);
        Long bobaTeaId = Long.valueOf(0);

        try { toppingId = idTopping.get(); }
        catch(Exception e){}

        try { bobaTeaId = idBobaTea.get(); }
        catch(Exception e){}

        List<BobaTeaModel> bobaResultList = new ArrayList<BobaTeaModel>();

        for(BobaTeaModel x : listBobaTea){
            if(x.getId() == bobaTeaId && x.getTopping().getId() == toppingId){
                bobaResultList.add(x);
            }
        }

        model.addAttribute("bobaResultList", bobaResultList);

        return "find-boba";
    }

    @GetMapping("/filter/manager")
    public String filterManagerByBobaTea(
            @RequestParam(value="idBobaTea") Optional<Long> idBobaTea,
            Model model
    ){
        List<ManagerModel> listManager = managerService.getManagerList();
        List<StoreBobaTeaModel> listStoreBoba = storeBobaService.getStoreBobaList();
        List<BobaTeaModel> listBobaTea = bobaTeaService.getBobaTeaList();

        Long bobaTeaId = Long.valueOf(0);

        try { bobaTeaId = idBobaTea.get(); }
        catch(Exception e){}

        model.addAttribute("listManager", listManager);
        model.addAttribute("listBobaTea", listBobaTea);

        List<ManagerModel> managerResultList = new ArrayList<ManagerModel>();


        for(BobaTeaModel x : listBobaTea){
            if(x.getId() == bobaTeaId){
                for (StoreBobaTeaModel y : x.getListStoreBoba()) {
                    managerResultList.add(y.getStore().getManager());
                }
            }
        }
        model.addAttribute("managerResultList", managerResultList);

        return "find-manager";
    }
}
