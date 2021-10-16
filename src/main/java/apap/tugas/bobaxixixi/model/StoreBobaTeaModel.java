package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "store_bobatea")

public class StoreBobaTeaModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 12)
    @Column(name = "production_code", nullable = false)
    private String productionCode;

    //Relation with StoreModel
//    @ManyToMany
//    @JoinTable(
//            name = "store_storeboba",
//            joinColumns = @JoinColumn(name = "id_store_boba"),
//            inverseJoinColumns = @JoinColumn(name = "id_store"))
//    List<StoreModel> listStore;

    //Relation with BobaTeaModel
//    @ManyToMany
//    @JoinTable(
//            name = "store_bobatea",
//            joinColumns = @JoinColumn(name = "id_store_boba"),
//            inverseJoinColumns = @JoinColumn(name = "id_boba"))
//    List<BobaTeaModel> listBobaTea;


    @ManyToOne
    @JoinColumn(name = "id_store")
    StoreModel store;

    @ManyToOne
    @JoinColumn(name = "id_boba")
    BobaTeaModel bobaTea;



}
