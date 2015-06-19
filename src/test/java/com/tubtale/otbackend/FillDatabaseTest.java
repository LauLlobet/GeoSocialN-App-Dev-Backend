package com.tubtale.otbackend;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class FillDatabaseTest extends CommonTest {

    @Before
    public void beforeTreeDaoTest() {
        treeDao.deleteAllTrees();
    }

    @After
    public void afterTreeDaoTest() {
    }

    @Test
    public void getTreesShouldReturnNumberOfEmptyTrees() throws Exception{
        //0.0001 ==> 10m
        double[] x = { 1.40015 ,    1.40009  ,      1.4   ,     1.40002  ,  1.40002 , 1.40005 };
        double[] y = { 45.00001 ,     45     ,      45    ,    45.00002  , 45.00001 , 45.00001 };
        String[] m = { "3"      ,     "1"    ,      "2"   ,      "0"     ,   "4"    ,    "5"   };
        insertTrees(6,x,y,m);
        assertThat(treeDao.getAllTrees().size(),is(6));
        int count = treeDao.countTotalTreesInGridPoint(1.40003f, 45.00001f);
        assertThat(treeDao.getAllTrees().size(),is(6));
        assertThat(count, is(5));
        /*assertThat(treeDao.countTotalTreesInGridPoint(1.40013f,45.00001f), is(1));
        assertThat(treeDao.countTotalTreesInGridPoint(1.40019f,45.00002f), is(1));
        assertThat(treeDao.countTotalTreesInGridPoint(1.40019f,45.00012f), is(0));*/
    }
}
