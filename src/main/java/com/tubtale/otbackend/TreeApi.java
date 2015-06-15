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
    public String getAllTrees(@QueryParam("offset") int offset,
                              @QueryParam("count") int count,
                              @QueryParam("callback") String callback) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        List<Tree> trees = TreeDao.getInstance().getAllTrees(offset, count);
        for (Tree tree : trees) {
            tree.setIp(ITEMS_URL + "/" + tree.getId());
        }
        return "[" + mapper.writeValueAsString(trees) +", emptyTrees:0]" ;
    }

    @DELETE
    @JSONP(queryParam = "callback")
    public void deleteAllTrees() throws Exception {
        TreeDao.getInstance().deleteAllTrees();
    }

    @GET
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public String getTree(@PathParam("id") int id) throws Exception {
        Tree tree = TreeDao.getInstance().getTree(id);
        return new ObjectMapper().writeValueAsString(tree);
    }

    @PUT
    @JSONP(queryParam = "callback")
    public String putTree(String treeJson) throws Exception {
        Tree tree = new ObjectMapper().readValue(treeJson, Tree.class);
        TreeDao.getInstance().saveOrUpdateTree(tree);
        return "[" + new ObjectMapper().writeValueAsString(tree) +",emptyTrees:0]" ;
    }

    @DELETE
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public void deleteTree(@PathParam("id") int id) throws Exception {
        TreeDao.getInstance().deleteTree(id);
    }

}
