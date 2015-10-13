package com.tubtale.otbackend;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TreeDaoTest extends CommonTest {

    @Before
    public void beforeTreeDaoTest() {
        treeDao.save(tree);
        treeDao.deleteAllTrees();
    }

    @After
    public void afterTreeDaoTest() {
        treeDao.deleteAllTrees();
    }

    @Test
    public void getOneTreesShouldReturnAllTrees() {
        assertThat(treeDao.getAllTrees().size(), is(equalTo(1)));
    }

    @Test
    public void deleteAllTreesShouldDeleteAllTrees() {
        treeDao.deleteAllTrees();
        assertThat(treeDao.getAllTrees().size(), is(equalTo(0)));
    }

    @Test
    public void getAllTreesShouldReturnsTreesWithText() {
        Tree actual = treeDao.getAllTrees().get(0);
        assertThat(actual.getText(), is(equalTo(text)));
    }

    @Test
    public void getAllTreesShouldReturnAllTrees() {
        int size = 7;
        treeDao.deleteAllTrees();
        insertTrees(size);
        assertThat(treeDao.getAllTrees().size(), is(equalTo(size)));
    }

    @Test
    public void getTreeShouldReturnTreeWithTheSpecifiedId() {
        int size = 12;
        List<Tree> list= insertTrees(size);
        Tree actual = treeDao.getTree(list.get(5).getId());
        assertThat(actual.getId(), is(equalTo(list.get(5).getId())));
        assertThat(actual.getText(), is(equalTo(tree.getText())));
    }

    @Test
    public void getUnstoredIdShouldReturnTreeWithTheSpecifiedId() {
        int size = 12;
        List<Tree> list= insertTrees(size);
        Tree actual = treeDao.getTree(list.get(5).getId());
        assertThat(actual.getId(), is(equalTo(list.get(5).getId())));
        assertThat(actual.getText(), is(equalTo(tree.getText())));
    }

    @Test
    public void getTreeShouldReturnNullIfIdDoesNotExist() {
        Tree actual = treeDao.getTree(123);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void saveTreeShouldSaveTheNewTree() throws Exception{
        Tree newTree = new Tree();
        newTree.setText("B");
        newTree.setIp("A");
        newTree.setTimestamp(new java.sql.Timestamp(0));
        newTree.setMetersToHide(20);
        newTree.setLocation(32,33);
        treeDao.save(newTree);
        Tree fetched = treeDao.getTree(newTree.getId());
        assertThat(fetched, is(equalTo(newTree)));
        assertThat(fetched.getX(), is(newTree.getX()));
        assertThat(fetched.getX(), is((double)32));
    }

    @Test
    public void deleteTreeShouldReturnDeletedTree() {
        Tree actual = treeDao.deleteTree(tree.getId());
        assertThat(actual, is(equalTo(tree)));
    }

    @Test
    public void deleteTreeShouldReturnNullIfTreeDoesNotExist() {
        Tree actual = treeDao.deleteTree(123);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void deleteTreeShouldDeleteSpecifiedTree() {
        treeDao.deleteTree(tree.getId());
        assertThat(treeDao.getAllTrees().size(), is(equalTo(0)));
    }

    @Test
    public void getTreesShouldReturnNumberOfEmptyTrees() throws Exception{
        //0.0001 ==> 10m
        double[] x = { 1.40015 ,    1.40009  ,      1.4   ,     1.40002  ,  1.40002 , 1.40005 };
        double[] y = { 45.00001 ,     45     ,      45    ,    45.00002  , 45.00001 , 45.00001 };
        String[] m = { "3"      ,     "1"    ,      "2"   ,      "0"     ,   "4"    ,    "5"   };
        insertTrees(6,x,y,m);
        assertThat(treeDao.countTotalTreesInGridPoint(1.40003f, 45.00001f), is(5));
        assertThat(treeDao.countTotalTreesInGridPoint(1.40013f,45.00001f), is(1));
        assertThat(treeDao.countTotalTreesInGridPoint(1.40019f,45.00002f), is(1));
        assertThat(treeDao.countTotalTreesInGridPoint(1.40019f,45.00012f), is(0));
    }
}
