package edu.poniperro.onequarkusapp.dominio;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "t_ordenes")
public class Orden extends PanacheEntityBase {
    public Orden() {}
    public Orden(Usuaria user, Item item) {
        this.user = user;
        this.item = item;
    }

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ord_id")
    private Long id;
    @ManyToOne(optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ord_user")
    private Usuaria user;
    @ManyToOne(optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ord_item")
    private Item item;



    public Usuaria getUser() {
        return this.user;
    }

    public Item getItem() {
        return this.item;
    }

    public Long getId() {
        return this.id;
    }
    public static List<Orden> listByUserName(String name) {
        List<Orden> ordenes = Orden.listAll();
        List<Orden> FilteredOrders = ordenes.stream().filter(o -> o.getUser().getNombre().equalsIgnoreCase(name)).collect(Collectors.toList());
        return FilteredOrders;
    }
}
