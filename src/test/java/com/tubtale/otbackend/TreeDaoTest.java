package com.tubtale.otbackend;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TreeDaoTest extends CommonTest {

    @Before
    public void beforeTreeDaoTest() {
        treeDao.save(tree);
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
        int size = 12;
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
    public void getTreeShouldReturnNullIfIdDoesNotExist() {
        Tree actual = treeDao.getTree(123);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void saveTreeShouldSaveTheNewTree() {
        Tree newTree = new Tree();
        newTree.setText("B");
        newTree.setIp("A");
        newTree.setTimestamp(new java.sql.Timestamp(0));
        newTree.setMetersToHide(20);
        treeDao.save(newTree);
        /*List<Tree> list = treeDao.getAllTrees();
        for(Tree t: list){
            System.out.println(t.getId());
        }*/
        System.out.println("requestedAnswewrdId: "+ treeDao.getTree(newTree.getId()).getId());
        assertThat(0, is(1));
       // assertThat(treeDao.getAllTrees().size(), is(equalTo(2)));
       // assertThat(treeDao.getTree(newTree.getId()), is(equalTo(newTree)));
    }

    @Test
    public void shouldBeImpossibleToUpdate() {
        Tree newTree = new Tree();
        newTree.setText("bbb");
        treeDao.save(newTree);
        newTree.setText("aaa");
        treeDao.save(newTree);
        String dbtext = treeDao.getTree(newTree.getId()).getText();
        assertThat(dbtext, is(not(equalTo("aaa"))));
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
}
