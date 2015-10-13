package com.tubtale.otbackend;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.io.StringReader;
import java.util.List;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TreeApiTest  extends CommonTest {

    private static HttpServer server;
    private WebTarget itemsTarget;
    private ObjectMapper objectMapper;

    @BeforeClass
    public static void beforeTreeApiTestClass() {
        server = new Server().startServer();
    }

    @Before
    public void beforeTreeApiTest() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/api/");
        itemsTarget = target.path("trees");
        objectMapper = new ObjectMapper();
    }

    @After
    public void afterUserResourceTest() throws Exception {
        treeDao.deleteAllTrees();
    }

    @AfterClass
    public static void afterUserResourceTestClass() {
        server.shutdown();
    }

/*    @Test
    public void v1ItemsShouldReturnStatus200() {
        assertThat(itemsTarget.request().head().getStatus(), is(200));
    }

    @Test
    public void getV1ItemsShouldReturnTypeApplicationJson() {
        assertThat(itemsTarget.request().get().getMediaType().toString(), is("application/json"));
    }

    @Test
    public void getV1ItemsShouldReturnListOfTrees() throws Exception {
        double[] x = { 1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9};
        double[] y = { 0,0,0,0,0,0,0,0,0};
        String[] m = { "3","1","2","6","4","5","7","8","9"};
        insertTrees(9,x,y,m);
        itemsTarget = itemsTarget.queryParam("dontInclude","[]");
        String json = itemsTarget.request().get(String.class);
        List<Tree> actual = extractTreeListContentOfAGetOrPut(json);
        assertThat(actual.size(), is(7));
    }


    @Test
    public void getV1ItemsShouldReturnListOfTreesWithoutExceptionsFromTheList() throws Exception {
        double[] x = { 1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9};
        double[] y = { 0,0,0,0,0,0,0,0,0};
        String[] m = { "0","1","2","3","4","5","6","7","8"};
        List<Tree> list = insertTrees(9,x,y,m);

        String exceptions = "["+list.get(1).getId()+","+list.get(3).getId()+","+list.get(5).getId()+"]";
        itemsTarget = itemsTarget.queryParam("dontInclude",exceptions);
        itemsTarget = itemsTarget.queryParam("x",1.1);
        itemsTarget = itemsTarget.queryParam("y",0);
        String json = itemsTarget.request().get(String.class);
        List<Tree> actual = extractTreeListContentOfAGetOrPut(json);
        String receivedOrder ="";
        for(Tree t: actual){
            receivedOrder+=t.getText();
        }
        String expectedOrder = "024678";

        assertThat(receivedOrder, is(equalTo(expectedOrder)));

    }

    @Test
    public void deleteV1ItemsShouldDeleteAllTrees() {
        itemsTarget.request().delete();
        assertThat(treeDao.getAllTrees().size(), is(0));
    }

    @Test
    public void getV1ItemsIdShouldReturnSpecifiedTree() throws Exception {
        Tree expected = insertTrees(1).get(0);
        itemsTarget = itemsTarget.queryParam("x", 2);
        itemsTarget = itemsTarget.queryParam("y",2);
        String json = itemsTarget.path("/" + expected.getId()).request().get(String.class);
        Tree actual = extractTreeContentOfAGetOrPut(json);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void putV1ItemsIdShouldSaveNewTree() throws Exception {
        String json = new ObjectMapper().writeValueAsString(tree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r = itemsTarget.request().put(Entity.text(json));
        String jsonans = r.readEntity(String.class);
        Tree putAnsTree = extractTreeContentOfAGetOrPut(jsonans);
        tree.setId(putAnsTree.getId());
        tree.setTimestamp(putAnsTree.getTimestamp());
        Tree actual = treeDao.getTree(putAnsTree.getId());
        assertThat(actual, is(not(nullValue())));
        assertThat(actual, is(equalTo(tree)));
        assertThat(treeDao.getAllTrees().size(), is(1));
    }
*/
    @Test
    public void putV1ItemsWithAnIdIfATreeThatDoesentExistShouldReturnNull() throws Exception {
        String json = new ObjectMapper().writeValueAsString(tree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r = itemsTarget.request().put(Entity.text(json));
        String jsonans = r.readEntity(String.class);
        Tree putAnsTree = extractTreeContentOfAGetOrPut(jsonans);
        tree.setId(putAnsTree.getId());
        tree.setTimestamp(putAnsTree.getTimestamp());
        Tree actual = treeDao.getTree(putAnsTree.getId());

        actual.setId(123);
        String json2 = new ObjectMapper().writeValueAsString(actual);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        r = itemsTarget.request().put(Entity.text(json2));
        String jsonans2 = r.readEntity(String.class);

        assertThat(extractTreeListContentOfAGetOrPut(jsonans2), is(nullValue()));
    }

    @Test
    public void putV1ItemsTheSameAsAStoredOneShouldReturnTheStoredOne() throws Exception {
        String json = new ObjectMapper().writeValueAsString(tree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r = itemsTarget.request().put(Entity.text(json));
        String jsonans = r.readEntity(String.class);
        Tree putAnsTree = extractTreeContentOfAGetOrPut(jsonans);
        String json2 = new ObjectMapper().writeValueAsString(putAnsTree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r2 = itemsTarget.request().put(Entity.text(json2));
        String jsonans2 = r2.readEntity(String.class);
        Tree putAnsTree2 = extractTreeContentOfAGetOrPut(jsonans2);

        assertThat(putAnsTree2, is(not(nullValue())));
        assertThat(putAnsTree2, is(equalTo(putAnsTree)));
        assertThat(treeDao.getAllTrees().size(), is(1));
    }


    @Test
    public void putV1ItemsTheSameAsAStoredWithMetersToHideIncreasedBy3ShouldreturnNull() throws Exception {
        int inc = 2;
        String json = new ObjectMapper().writeValueAsString(tree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r = itemsTarget.request().put(Entity.text(json));
        String jsonans = r.readEntity(String.class);
        Tree putAnsTree = extractTreeContentOfAGetOrPut(jsonans);
        putAnsTree.setMetersToHide(putAnsTree.getMetersToHide() + inc);
        String json2 = new ObjectMapper().writeValueAsString(putAnsTree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r2 = itemsTarget.request().put(Entity.text(json2));
        String jsonans2 = r2.readEntity(String.class);
        Tree putAnsTree2 = extractTreeContentOfAGetOrPut(jsonans2);

        assertThat(putAnsTree2, is(nullValue()));
    }

    @Test
    public void putV1ItemsTheSameAsAStoredWithMetersToHideIncreasedBy1ShouldreturnNotNull() throws Exception {
        int inc = 1;
        String json = new ObjectMapper().writeValueAsString(tree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r = itemsTarget.request().put(Entity.text(json));
        String jsonans = r.readEntity(String.class);
        Tree putAnsTree = extractTreeContentOfAGetOrPut(jsonans);
        Tree expected = putAnsTree.obtainDuplicate();

        putAnsTree.setMetersToHide(putAnsTree.getMetersToHide() + inc);
        putAnsTree.setText("this text shouldnt be updated");

        String json2 = new ObjectMapper().writeValueAsString(putAnsTree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r2 = itemsTarget.request().put(Entity.text(json2));
        String jsonans2 = r2.readEntity(String.class);
        Tree updateAnsTree = extractTreeContentOfAGetOrPut(jsonans2);

        expected.setMetersToHide(expected.getMetersToHide()+inc);
        assertThat(updateAnsTree, is(not(nullValue())));
        assertThat(updateAnsTree, is(equalTo(expected)));
    }
/*

@Test
    public void putV1ItemsWithAnAlreadySavedTreeWithSameMetersToHideShouldReturnNull() throws Exception {
        String json = new ObjectMapper().writeValueAsString(tree);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        Response r = itemsTarget.request().put(Entity.text(json));
        String jsonans = r.readEntity(String.class);
        Tree putAnsTree = extractTreeContentOfAGetOrPut(jsonans);
        tree.setId(putAnsTree.getId());
        tree.setTimestamp(putAnsTree.getTimestamp());
        Tree actual = treeDao.getTree(putAnsTree.getId());


        String json2 = new ObjectMapper().writeValueAsString(actual);
        itemsTarget = itemsTarget.queryParam("x",2);
        itemsTarget = itemsTarget.queryParam("y",2);
        r = itemsTarget.request().put(Entity.text(json2));
        String jsonans2 = r.readEntity(String.class);

        assertThat(extractEmptyTreesCountContentOfAGetOrPut(jsonans2), is(0));
        assertThat(extractTreeListContentOfAGetOrPut(jsonans2), is(nullValue()));
    }
    @Test
    public void putV1ItemsShouldBePostableAndGettable() throws Exception {
        tree.setText("This is new title");
        tree.setLocation(123,123);
        String json = new ObjectMapper().writeValueAsString(tree);

        Response r = itemsTarget.request().put(Entity.text(json));




        String ans = r.readEntity(String.class);
        System.out.println("ANS:"+ans);
        Tree putAnsTree = extractTreeContentOfAGetOrPut(ans);
        Tree actual = treeDao.getTree(putAnsTree.getId());
        tree.setId(putAnsTree.getId());
        tree.setTimestamp(putAnsTree.getTimestamp());

        assertThat(actual, is(equalTo(tree)));
        assertThat(putAnsTree, is(equalTo(tree)));
        assertThat(treeDao.getAllTrees().size(), is(1));
    }

    @Test
    public void deleteV1ItemsIdShouldDeleteExistingTree() throws Exception {
        List<Tree> trees = insertTrees(3);
        itemsTarget.path("/" + trees.get(0).getId()).request().delete();
        assertThat(treeDao.getAllTrees().size(), is(trees.size() - 1));
    }

    @Test
    public void getTreesShouldReturnOrderedTrees() throws Exception{
        double[] x = {1.5 , 1.40625 ,    1.40625   ,      1.206   ,  50  };
        double[] y = { 45 , 38.6718 ,         45   ,      38.671  ,  50  };
        String[] m = { "3" ,    "1" ,        "2"   ,        "0"   ,  "4" };
        insertTrees(5,x,y,m);

        itemsTarget = itemsTarget.queryParam("x", 1.205);
        itemsTarget = itemsTarget.queryParam("y", 38.67);
        itemsTarget = itemsTarget.queryParam("dontInclude","[]");
        String json = itemsTarget.request().get(String.class);

        List<Tree> list = extractTreeListContentOfAGetOrPut(json);
        assertThat(list.size(), is(5));
        assertThat(extractTreeListContentOfAGetOrPut(json).size(), is(list.size()));
        String ordered = list.get(0).getText() +
                        list.get(1).getText() +
                        list.get(2).getText() +
                        list.get(3).getText() +
                        list.get(4).getText();
        assertThat(ordered,is(equalTo("01234")));
    }

    @Test
    public void getTreesShouldReturnNumberOfEmptyTrees() throws Exception{
        //0.0001 ==> 10m
        double[] x = { 1.400005 ,    1.40009  ,      1.4   ,     1.40002  ,  1.40002  };
        double[] y = { 45.00001 ,     45     ,      45    ,    45.00002  , 45.00001  };
        String[] m = { "3"      ,     "1"    ,      "2"   ,      "0"     ,   "4"     };
        insertTrees(5,x,y,m);

        itemsTarget = itemsTarget.queryParam("x", 1.4000);
        itemsTarget = itemsTarget.queryParam("y", 45.00001);
        String json = itemsTarget.request().get(String.class);

        assertThat(extractEmptyTreesCountContentOfAGetOrPut(json), is(TreeApi.numbersOfTreesPerGridCell-5));
    }

    @Test
    public void puttingTreeInAFullCellShouldReturnNull() throws Exception{
        //0.0001 ==> 10m
        double[] x = { 1.40095 ,    1.40009  ,      1.4   ,     1.40002  ,  1.40002 , 1.40002  ,  1.40092  };
        double[] y = { 45.00001 ,     45     ,      45    ,    45.00002  , 45.00001,  45.00001 , 45.00001  };
        String[] m = { "3"      ,     "1"    ,      "2"   ,      "0"     ,   "4"   ,   "4"     ,   "4"       };
        insertTrees(7,x,y,m);
        itemsTarget = itemsTarget.queryParam("x", 1.40005);
        itemsTarget = itemsTarget.queryParam("y", 45.000067);
        assertThat(extractEmptyTreesCountContentOfAGetOrPut(itemsTarget.request().get(String.class)), is(0));


        tree.setLocation(1.40005,45.000067);
        String json = new ObjectMapper().writeValueAsString(tree);
        Response r = itemsTarget.request().put(Entity.text(json));
        String jsonAnswer = r.readEntity(String.class);
        assertThat(extractEmptyTreesCountContentOfAGetOrPut(jsonAnswer), is(0));
        assertThat(extractTreeListContentOfAGetOrPut(jsonAnswer), is(nullValue()));

    }
*/
    public Tree extractTreeContentOfAGetOrPut(String restAns) throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new StringReader(restAns));
        JsonNode contentNode = rootNode.get("treeContent");
        return new ObjectMapper().readValue(contentNode, Tree.class);
    }

    public List<Tree> extractTreeListContentOfAGetOrPut(String restAns) throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new StringReader(restAns));
        JsonNode contentNode = rootNode.get("treeContent");
        if(contentNode.isNull()){
            return null;
        }
        return new ObjectMapper().readValue(contentNode,  objectMapper.getTypeFactory().constructCollectionType(List.class, Tree.class));
    }

    public Integer extractEmptyTreesCountContentOfAGetOrPut(String restAns) throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new StringReader(restAns));
        JsonNode contentNode = rootNode.get("emptyTrees");
        return new ObjectMapper().readValue(contentNode,Integer.class);
    }
}
