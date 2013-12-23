package <%= packageName %>.resources;

import <%= packageName %>.model.<%= _.capitalize(name) %>;
import <%= packageName %>.store.<%= _.capitalize(name) %>DAO;
import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import com.yammer.dropwizard.hibernate.UnitOfWork;
import com.yammer.dropwizard.jersey.params.BooleanParam;
import com.yammer.dropwizard.jersey.params.DateTimeParam;
import com.yammer.dropwizard.jersey.params.IntParam;
import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/<%= pluralize(name) %>")
public class <%= _.capitalize(name) %>Resource {

    private static final Logger LOG = LoggerFactory.getLogger(<%= _.capitalize(name) %>Resource.class);

    private final <%= _.capitalize(name) %>DAO dao;

    public <%= _.capitalize(name) %>Resource(<%= _.capitalize(name) %>DAO dao) {
        this.dao = dao;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed 
    @UnitOfWork
    public <%= _.capitalize(name) %> create(<%= _.capitalize(name) %> entity) {
        return dao.save(entity);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @UnitOfWork
    public List<<%= _.capitalize(name) %>> getAll() {
        return dao.findAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @UnitOfWork
    public <%= _.capitalize(name) %> get(@PathParam("id") LongParam id) {
        Optional<<%= _.capitalize(name) %>> entity = dao.find(id.get());
        if (!entity.isPresent()) {
            throw new NotFoundException("<%= _.capitalize(name) %> " + id.get() + " not found");
        }
        return entity.get();
    }

    @DELETE
    @Path("{id}")
    @Timed
    @UnitOfWork
    public void delete(@PathParam("id") LongParam id) {
        Optional<<%= _.capitalize(name) %>> entity = dao.find(id.get());
        if (!entity.isPresent()) {
            throw new NotFoundException("<%= _.capitalize(name) %> " + id.get() + " not found");
        }
        dao.delete(entity.get());
    }
}