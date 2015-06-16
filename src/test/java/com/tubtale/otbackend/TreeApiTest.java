package com.tubtale.otbackend;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;
import org.skyscreamer.jsonassert.JSONAssert;

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
        itemsTarget = target.path("v1/items");
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

    @Test
    public void v1ItemsShouldReturnStatus200() {
        assertThat(itemsTarget.request().head().getStatus(), is(200));
    }

    @Test
    public void getV1ItemsShouldReturnTypeApplicationJson() {
        assertThat(itemsTarget.request().get().getMediaType().toString(), is("application/json"));
    }

    @Test
    public void getV1ItemsShouldReturnListOfTrees() throws Exception {
        int size = 3;
        insertTrees(size);
        String json = itemsTarget.request().get(String.class);
        List<Tree> actual = extractTreeListContentOfAGetOrPut(json);
        assertThat(actual.size(), is(size));
    }

    @Test
    public void deleteV1ItemsShouldDeleteAllTrees() {
        itemsTarget.request().delete();
        assertThat(treeDao.getAllTrees().size(), is(0));
    }

    @Test
    public void getV1ItemsIdShouldReturnSpecifiedTree() throws Exception {
        Tree expected = insertTrees(1).get(0);
        String json = itemsTarget.path("/" + expected.getId()).request().get(String.class);
        Tree actual = objectMapper.readValue(json, Tree.class);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void putV1ItemsIdShouldSaveNewTree() throws Exception {
        String json = new ObjectMapper().writeValueAsString(tree);
        Response r = itemsTarget.request().put(Entity.text(json));
        Tree putAnsTree = extractTreeContentOfAGetOrPut(r.readEntity(String.class));
        tree.setId(putAnsTree.getId());
        Tree actual = treeDao.getTree(putAnsTree.getId());
        assertThat(actual, is(not(nullValue())));
        assertThat(actual, is(equalTo(tree)));
        assertThat(treeDao.getAllTrees().size(), is(1));
    }

    @Test
    public void putV1ItemsShouldUpdateExistingTree() throws Exception {
        treeDao.saveOrUpdateTree(tree);
        tree.setText("This is new title");
        String json = new ObjectMapper().writeValueAsString(tree);
        Response r = itemsTarget.request().put(Entity.text(json));
        Tree putAnsTree = extractTreeContentOfAGetOrPut(r.readEntity(String.class));
        Tree actual = treeDao.getTree(putAnsTree.getId());
        assertThat(actual, is(equalTo(tree)));
        assertThat(treeDao.getAllTrees().size(), is(1));
    }

    @Test
    public void deleteV1ItemsIdShouldDeleteExistingTree() throws Exception {
        List<Tree> trees = insertTrees(3);
        treeDao.saveOrUpdateTree(trees.get(0));
        itemsTarget.path("/" + trees.get(0).getId()).request().delete();
        assertThat(treeDao.getAllTrees().size(), is(trees.size() - 1));
    }

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
        return new ObjectMapper().readValue(contentNode,  objectMapper.getTypeFactory().constructCollectionType(List.class, Tree.class));
    }
}
