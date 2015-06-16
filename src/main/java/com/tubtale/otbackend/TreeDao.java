package com.tubtale.otbackend;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class TreeDao {


    private static volatile TreeDao instance = null;
    private TreeDao() { }

    public static synchronized TreeDao getInstance() {
        if (instance == null) {
            instance = new TreeDao();
        }
        return instance;
    }

    public void deleteAllTrees() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.createQuery("delete from Tree").executeUpdate();
        session.close();
    }

    public List<Tree> getAllTrees() {
        return getAllTrees(0, 0);
    }
    public List<Tree> getAllTrees(int firstResult, int maxResult) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Tree> trees = (List<Tree>)session.createSQLQuery("SELECT * FROM Tree").addEntity(Tree.class).list();
        return trees;
    }

    public Tree getTree(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tree tree = (Tree) session.get(Tree.class, id);
        session.close();
        return tree;
    }

    public void save(Tree tree) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("pre:"+tree.getId());
        session.saveOrUpdate(tree);
        System.out.println("post:" + tree.getId());
        session.flush();
        session.close();
    }

    public Tree deleteTree(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tree tree = getTree(id);
        if (tree != null) {
            session.delete(tree);
            session.flush();
        }
        session.close();
        return tree;
    }


}
