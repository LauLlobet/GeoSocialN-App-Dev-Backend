package com.tubtale.otbackend;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.glassfish.jersey.server.JSONP;
import com.tubtale.otbackend.Tree;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("v1/items")
@Produces(MediaType.APPLICATION_JSON)
public class TreeApi {

    private static final String ITEMS_URL = "/api/v1/items";

    @GET
    @JSONP(queryParam = "callback")
    public String getAllTrees(@QueryParam("x") float x,
                              @QueryParam("y") float y) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        List<Tree> trees = TreeDao.getInstance().getAllTrees(x,y);
        for (Tree tree : trees) {
            tree.setIp(ITEMS_URL + "/" + tree.getId());
        }
        return "{ \"treeContent\":" + mapper.writeValueAsString(trees) +", \"emptyTrees\":"+(x+y)+" }" ;
    }

    @DELETE
    @JSONP(queryParam = "callback")
    public void deleteAllTrees() throws Exception {
        System.out.println("deleting ALL trees");
        TreeDao.getInstance().deleteAllTrees();
    }

    @GET
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public String getTree(@PathParam("id") int id,
                          @QueryParam("x") int x,
                          @QueryParam("y") int y) throws Exception {
        Tree tree = TreeDao.getInstance().getTree(id);
        return "{ \"treeContent\":" + new ObjectMapper().writeValueAsString(tree) +",\"emptyTrees\":"+(x+y)+"}" ;
    }

    @PUT
    @JSONP(queryParam = "callback")
    public String putTree(String treeJson,
                          @QueryParam("x") int x,
                          @QueryParam("y") int y) throws Exception {
        Tree tree = new ObjectMapper().readValue(treeJson, Tree.class);
        TreeDao.getInstance().save(tree);
        return "{ \"treeContent\":" + new ObjectMapper().writeValueAsString(tree) +",\"emptyTrees\":"+(x+y)+"}" ;
    }

    @DELETE
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public void deleteTree(@PathParam("id") int id) throws Exception {
        TreeDao.getInstance().deleteTree(id);
    }

}
