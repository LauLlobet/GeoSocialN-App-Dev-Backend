package com.tubtale.otbackend;


import com.tubtale.otbackend.twitterpublisher.TreeToTwitterPublisher;
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
    TreeToTwitterPublisher treeToTweeterPublisher;
    public TreeApi(){
        treeToTweeterPublisher = new TreeToTwitterPublisher();
    }

    @GET
    @JSONP(queryParam = "callback")
    public String getAllTrees(@QueryParam("x") float x,
                              @QueryParam("y") float y,
                              @QueryParam("dontInclude") String dontIncludeStrArg) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<Integer> dontInclude = mapper.readValue(dontIncludeStrArg, mapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
            //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
            int numberOfTrees = 7 + dontInclude.size();
            List<Tree> trees = TreeDao.getInstance().getAllTrees(x, y, numberOfTrees);
            List<Tree> ansList = new ArrayList<Tree>();
            for (Tree t : trees) {
                if (!dontInclude.contains(t.getId())) {
                    t.anonimize();
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
        if(tree != null){
            tree.anonimize();
        }
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
        if( isAnAlreadySavedTree(tree) ){
            return handleUpdateTree(tree,emptyTrees);
        }
        if( emptyTrees == 0 ){
            return "{ \"treeContent\":null,\"emptyTrees\":"+0+"}" ;
        }
        if(request != null){
            String address = request.getRemoteAddr();
            tree.setIp(address);
        }
        tree.setMetersToHide(0);
        tree.setTimestamp(new Timestamp(System.currentTimeMillis()));
        TreeDao.getInstance().save(tree);
        treeToTweeterPublisher.publishTreeInTwitterUsersMentioned(tree.getText(),x,y,tree.getId());
        return "{ \"treeContent\":" + new ObjectMapper().writeValueAsString(tree) +",\"emptyTrees\":"+emptyTrees+"}" ;
    }

    private String handleUpdateTree(Tree tree, int emptyTrees) {
        Tree previouslySavedOne = TreeDao.getInstance().getTree(tree.getId());
        if( previouslySavedOne == null){
            return "{ \"treeContent\":null,\"emptyTrees\":"+emptyTrees+"}";
        }
        try {
            System.out.println("tree.getId() ########################"+ tree.getId());
            if( previouslySavedOne.getMetersToHide()+1 == tree.getMetersToHide() ||
                    previouslySavedOne.getMetersToHide()-1 == tree.getMetersToHide() ||
                    previouslySavedOne.getMetersToHide() == tree.getMetersToHide() ) {
                previouslySavedOne.setMetersToHide(tree.getMetersToHide());
                TreeDao.getInstance().save(previouslySavedOne);
                return "{ \"treeContent\":" + new ObjectMapper().writeValueAsString(previouslySavedOne) +",\"emptyTrees\":"+emptyTrees+"}" ;
            } else {
                return "{ \"treeContent\":null,\"emptyTrees\":"+emptyTrees+"}";
            }
        } catch (Exception e) {
            return "{ \"treeContent\":null,\"emptyTrees\":"+emptyTrees+"}";
        }
    }

    private boolean isAnAlreadySavedTree(Tree tree) {
        if (tree.getId() == null) {
            return false;
        }else{
            return true;
        }

    }

    @DELETE
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public void deleteTree(@PathParam("id") int id) throws Exception {
        TreeDao.getInstance().deleteTree(id);
    }

}
