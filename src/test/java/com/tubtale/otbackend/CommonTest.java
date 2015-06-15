package com.tubtale.otbackend;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class CommonTest {

    protected final int id = 42;
    protected final String text = "i'm the first tree in all forests";
    protected Tree tree;
    protected TreeDao treeDao = TreeDao.getInstance();

    @Before
    public void beforeCommonTest() {
        tree = new Tree();
        tree.setText(text);
    }

    protected List<Tree> insertTrees(int count) {
        List<Tree> trees = new ArrayList<Tree>();
        for (int index = 1; index <= count; index++) {
            Tree tree = new Tree();
            tree.setText(text);
            treeDao.saveOrUpdateTree(tree);
            trees.add(tree);
        }
        return trees;
    }

}
