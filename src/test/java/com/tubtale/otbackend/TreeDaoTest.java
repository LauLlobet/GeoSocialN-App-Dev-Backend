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
        treeDao.saveOrUpdateTree(tree);
    }

    @After
    public void afterTreeDaoTest() {
        treeDao.deleteAllTrees();
    }

    @Test
    public void getAllTreesShouldReturnAllTrees() {
        assertThat(treeDao.getAllTrees().size(), is(equalTo(1)));
    }

    @Test
    public void deleteAllTreesShouldDeleteAllTrees() {
        treeDao.deleteAllTrees();
        assertThat(treeDao.getAllTrees().size(), is(equalTo(0)));
    }

    @Test
    public void getAllTreesShouldReturnsTreesWithId() {
        Tree actual = treeDao.getAllTrees().get(0);
        assertThat(actual.getId(), is(equalTo(id)));
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
        Tree actual = treeDao.getTree(id);
        assertThat(actual, is(equalTo(actual)));
    }

    @Test
    public void getTreeShouldReturnNullIfIdDoesNotExist() {
        Tree actual = treeDao.getTree(123);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void saveOrUpdateTreeShouldSaveTheNewTree() {
        int actualId = 123;
        tree.setId(actualId);
        tree.setText("saving tree");
        treeDao.saveOrUpdateTree(tree);
        assertThat(treeDao.getAllTrees().size(), is(equalTo(2)));
        assertThat(treeDao.getTree(actualId), is(equalTo(tree)));
    }

    @Test
    public void deleteTreeShouldReturnDeletedTree() {
        Tree actual = treeDao.deleteTree(id);
        assertThat(actual, is(equalTo(tree)));
    }

    @Test
    public void deleteTreeShouldReturnNullIfTreeDoesNotExist() {
        Tree actual = treeDao.deleteTree(123);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void deleteTreeShouldDeleteSpecifiedTree() {
        treeDao.deleteTree(id);
        assertThat(treeDao.getAllTrees().size(), is(equalTo(0)));
    }

}
