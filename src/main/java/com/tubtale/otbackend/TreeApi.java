package com.tubtale.otbackend;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.glassfish.jersey.server.JSONP;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Path("trees")
@Produces(MediaType.APPLICATION_JSON)
public class TreeApi {

    public static final int numbersOfTreesPerGridCell = 5;

    @GET
    @JSONP(queryParam = "callback")
    public String getAllTrees(@QueryParam("x") float x,
                              @QueryParam("y") float y,
                              @QueryParam("dontInclude") String dontIncludeStrArg) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("dontinclude....." + dontIncludeStrArg);
        try {
            ArrayList<Integer> dontInclude = mapper.readValue(dontIncludeStrArg, mapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
            int numberOfTrees = 7 + dontInclude.size();
            System.out.println("---------------DONT INCLUDE SIZE---------------------------->" + dontInclude.size());
            List<Tree> trees = TreeDao.getInstance().getAllTrees(x, y, numberOfTrees);
            List<Tree> ansList = new ArrayList<Tree>();
            for (Tree t : trees) {
                if (!dontInclude.contains(t.getId())) {
                    ansList.add(t);
                }
            }
            int emptyTrees = numbersOfTreesPerGridCell - TreeDao.getInstance().countTotalTreesInGridPoint(x, y);
            return "{ \"treeContent\":" + mapper.writeValueAsString(ansList) + ", \"emptyTrees\":" + emptyTrees + " }";
        }catch (Exception e){
            e.printStackTrace();
            return "{ \"treeContent\":null,\"emptyTrees\":"+0+"}" ;
        }
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
                          @QueryParam("x") float x,
                          @QueryParam("y") float y) throws Exception {
        Tree tree = TreeDao.getInstance().getTree(id);
        int emptyTrees = numbersOfTreesPerGridCell - TreeDao.getInstance().countTotalTreesInGridPoint(x,y);
        return "{ \"treeContent\":" + new ObjectMapper().writeValueAsString(tree) +",\"emptyTrees\":"+emptyTrees+"}" ;
    }

    @PUT
    @JSONP(queryParam = "callback")
    public String putTree(String treeJson,
                          @Context HttpServletRequest request) throws Exception {
        Tree tree = new ObjectMapper().readValue(treeJson, Tree.class);
        float x = (float)tree.getX();
        float y = (float)tree.getY();

        int emptyTrees = numbersOfTreesPerGridCell - TreeDao.getInstance().countTotalTreesInGridPoint(x,y);
        if( emptyTrees == 0 ){
            return "{ \"treeContent\":null,\"emptyTrees\":"+0+"}" ;
        }
        if(request != null){
            String address = request.getRemoteAddr();
            tree.setIp(address);
        }
        tree.setTimestamp(new Timestamp(System.currentTimeMillis()));
        TreeDao.getInstance().save(tree);
        return "{ \"treeContent\":" + new ObjectMapper().writeValueAsString(tree) +",\"emptyTrees\":"+emptyTrees+"}" ;
    }

    @DELETE
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public void deleteTree(@PathParam("id") int id) throws Exception {
        TreeDao.getInstance().deleteTree(id);
    }

}
