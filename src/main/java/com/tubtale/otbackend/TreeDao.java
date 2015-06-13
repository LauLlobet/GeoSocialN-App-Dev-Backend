package com.tubtale.otbackend;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;


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
        List<Tree> trees = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select id, title from Tree");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        @SuppressWarnings("unchecked")
        List allUsers = query.list();
        for (Iterator it = allUsers.iterator(); it.hasNext(); ) {
            Object[] treeObject = (Object[]) it.next();
            Tree tree = new Tree((Integer) treeObject[0]);
            tree.setTitle((String) treeObject[1]);
            trees.add(tree);
        }
        session.close();
        return trees;
    }

    public Tree getTree(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tree tree = (Tree) session.get(Tree.class, id);
        session.close();
        return tree;
    }

    public void saveOrUpdateTree(Tree tree) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.saveOrUpdate(tree);
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
