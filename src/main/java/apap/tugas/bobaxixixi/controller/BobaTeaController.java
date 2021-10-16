package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.*;
import apap.tugas.bobaxixixi.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import java.lang.*;

import java.util.Random;

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
        bobaTea.setListStoreBoba(new ArrayList<>());
        bobaTea.getListStoreBoba().add(storeBoba);

        List<StoreModel> listStore = storeService.getStoreList();

        model.addAttribute("bobaTea", bobaTea);
        model.addAttribute("storeBoba", storeBoba);
        model.addAttribute("listStore", listStore);

        return "form-assign-store";
    }

    @PostMapping("/boba/{id}/assign-store")
    public String assignStoreSubmit(@PathVariable Long id, @ModelAttribute StoreBobaTeaModel storeBoba, Model model) {
        BobaTeaModel bobaTea = bobaTeaService.getBobaTeaByIdBobaTea(id);

        StoreModel store = storeBoba.getStore();

        storeBoba.setBobaTea(bobaTea);

        List<StoreBobaTeaModel> listStoreBoba = bobaTea.getListStoreBoba();

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

        System.out.println(code);

        storeBoba.setProductionCode(code);
        storeBobaService.addStoreBoba(storeBoba);

        model.addAttribute("bobaName", bobaTea.getName());
        model.addAttribute("listStoreBoba", listStoreBoba);

        return "assign-store";
    }
    @GetMapping("/search")
    public String viewCariBobaTeaByTopping(
            @ModelAttribute BobaTeaModel boba,
            @RequestParam(value= "bobaTea", required = false) BobaTeaModel bobaTea,
            @RequestParam(value = "topping",required = false) ToppingModel topping,
            Model model

    ){
        List<BobaTeaModel> listBobaTea = bobaTeaService.getBobaTeaList();
        List<ToppingModel> listTopping = toppingService.getToppingList();

        model.addAttribute("listBobaTea", listBobaTea);
        model.addAttribute("listTopping", listTopping);
        model.addAttribute("boba", boba);

        boolean hasSelect = ((bobaTea!=null) || (topping!=null));
        model.addAttribute("hasSelect", hasSelect);
        if(hasSelect) {
            if ((bobaTea != null) && (topping != null)) {
                List<BobaTeaModel> bobaTeaList = bobaTeaService.getBobaByNameAndTopping(bobaTea, topping);
                model.addAttribute("bobaTeaList", bobaTeaList);
                boolean hasBoba = bobaTeaList.size() > 0;
                model.addAttribute("hasBoba", hasBoba);

            } else if ((bobaTea != null) && (topping == null)) {
                List<BobaTeaModel> bobaTeaList = bobaTeaService.getBobaByName(bobaTea);
                model.addAttribute("bobaTeaList", bobaTeaList);
                boolean hasBoba = bobaTeaList.size() > 0;
                model.addAttribute("hasBoba", hasBoba);

            } else if ((bobaTea == null) && (topping != null)) {
                List<BobaTeaModel> bobaTeaList = bobaTeaService.getBobaByTopping(topping);
                model.addAttribute("bobaTeaList", bobaTeaList);
                boolean hasBoba = bobaTeaList.size() > 0;
                model.addAttribute("hasBoba", hasBoba);
            }
        }
        return "find-boba";
    }

}
