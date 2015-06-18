package com.tubtale.otbackend;

import org.hibernate.Session;

import java.util.List;
import javax.persistence.EntityManager;


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
        Session session = HibernateUtil.getSession();
        session.createQuery("delete from Tree").executeUpdate();
        session.close();
    }

    public List<Tree> getAllTrees() {
        return getAllTrees(0, 0);
    }
    public List<Tree> getAllTrees(int firstResult, int maxResult) {
        Session session = HibernateUtil.getSession();
        List<Tree> trees = (List<Tree>)session.createSQLQuery("SELECT * FROM Tree").addEntity(Tree.class).list();
        return trees;
    }

    public Tree getTree(int id) {
        Session session = HibernateUtil.getSession();
        Tree tree = (Tree) session.get(Tree.class, id);
        session.close();
        return tree;
    }

    public void save(Tree tree) {
        Session session = HibernateUtil.getSession();
        if(tree.getId() != null)
            return;
        session.saveOrUpdate(tree);
        session.flush();
        session.close();
    }

    public Tree deleteTree(int id) {
        EntityManager em = HibernateUtil.createEntityManager();
        Tree tree = getTree(id);
        try {
            em.remove(em.contains(tree) ? tree : em.merge(tree));
            return tree;
        }catch (Exception e){
            return null;
        }
    }


}
