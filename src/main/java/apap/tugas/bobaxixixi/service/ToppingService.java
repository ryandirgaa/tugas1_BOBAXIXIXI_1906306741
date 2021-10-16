package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ToppingModel;
import java.util.List;

public interface ToppingService {
    void addTopping(ToppingModel topping);
    void updateTopping(ToppingModel topping);
    void removeTopping(ToppingModel topping);
    List<ToppingModel> getToppingList();
    ToppingModel getToppingByIdTopping(Long id);
}
