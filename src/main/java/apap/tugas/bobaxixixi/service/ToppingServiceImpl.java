package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ToppingModel;
import apap.tugas.bobaxixixi.repository.ToppingDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ToppingServiceImpl implements ToppingService {
    @Autowired
    ToppingDb toppingDb;

    @Override
    public void addTopping(ToppingModel topping){
        toppingDb.save(topping);
    }

    @Override
    public void updateTopping(ToppingModel topping){
        toppingDb.save(topping);
    }

    @Override
    public void removeTopping(ToppingModel topping){
        toppingDb.delete(topping);
    }

    @Override
    public List<ToppingModel> getToppingList(){
        return toppingDb.findAll();
    }

    @Override
    public ToppingModel getToppingByIdTopping(Long id){
        Optional<ToppingModel> x = toppingDb.findById(id);
        if (x.isPresent()) {
            return x.get();
        }
        return null;
    }
}
