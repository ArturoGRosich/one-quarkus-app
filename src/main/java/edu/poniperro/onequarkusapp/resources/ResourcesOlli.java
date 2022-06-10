package edu.poniperro.onequarkusapp.resources;

import edu.poniperro.onequarkusapp.dominio.Item;
import edu.poniperro.onequarkusapp.dominio.Orden;
import edu.poniperro.onequarkusapp.dominio.Usuaria;
import edu.poniperro.onequarkusapp.services.ServiceOlli;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class ResourcesOlli {
    @Inject
    public ServiceOlli service;

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/wellcome")
    public String wellcome() {
        return "Wellcome Ollivanders!";
    }


    @GET
    @Path("/usuaria/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuaria(@PathParam("nombre") String nombre){
        Usuaria usuaria = service.cargaUsuaria(nombre);
        return  usuaria.getNombre().isEmpty()
                ?Response.status(Response.Status.NOT_FOUND).build()
                :Response.status(Response.Status.OK).entity(usuaria).build();
    }

    @POST
    @Path("/ordena")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postOrder(@Valid Orden orden){
        Orden pedido = service.comanda(orden.getUser().getNombre(), orden.getItem().getNombre());
        return pedido != null
                ?Response.status(Response.Status.CREATED).entity(pedido).build()
                :Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/pedidos/{usuaria}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOrders(@PathParam("usuaria") String nameUsuaria){
        List<Orden> ordenes = service.cargaOrden(nameUsuaria);
        return Response.status(Response.Status.OK).entity(ordenes).build();
    }

    @GET
    @Path("/item/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getItemQuality(@PathParam("nombre") String nombre){
        Item item = service.cargaItem(nombre);
        return item.getNombre().isEmpty() == false
                ?Response.status(Response.Status.OK).entity(item).build()
                :Response.status(Response.Status.NOT_FOUND).build();
    }
}

