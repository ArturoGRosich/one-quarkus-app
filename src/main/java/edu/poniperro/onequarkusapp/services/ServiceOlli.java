package edu.poniperro.onequarkusapp.services;

import edu.poniperro.onequarkusapp.dominio.Item;
import edu.poniperro.onequarkusapp.dominio.Orden;
import edu.poniperro.onequarkusapp.dominio.Usuaria;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServiceOlli {
    public Usuaria cargaUsuaria(String nombre) {
        Optional<Usuaria> usuaria = Usuaria.findByIdOptional(nombre);
        return usuaria.isPresent() ?usuaria.get(): new Usuaria();
    }

    public Item cargaItem(String nombre) {
        Optional<Item> item = Item.findByIdOptional(nombre);
        return item.isPresent() ?item.get(): new Item();
    }

    public List<Orden> cargaOrden(String id) {
        List<Orden> ordenes = Orden.listByUserName(id);
        return ordenes;
    }
    @Transactional
    public Orden comanda(String userName, String itemName) {
        Orden orden = null;
        Optional<Usuaria> user = Usuaria.findByIdOptional(userName);
        Optional<Item> item = Item.findByIdOptional(itemName);

        if (user.isPresent() && item.isPresent() && user.get().getDestreza() >= item.get().getQuality()) {
            orden = new Orden(user.get(), item.get());
            orden.persist();
        }

        return orden;
    }

    public List<Orden> comandaMultiple(String name, List<String> items) {
        Orden ordenActual = null;
        List<Orden> comanda = new ArrayList<Orden>();
        for (String item: items) {
            ordenActual = comanda(name, item);
            if (ordenActual != null) {
                comanda.add(ordenActual);
            }
        }
        return comanda;
    }
}
