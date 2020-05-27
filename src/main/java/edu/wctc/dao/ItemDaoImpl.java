package edu.wctc.dao;

        import edu.wctc.entity.Item;
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.query.Query;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Item> getItems() {
        // Get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // Get list of donuts from query
        List<Item> itemList = session.createQuery("from Item", Item.class).getResultList();

        // Return results
        return itemList;
    }

    @Override
    public void saveItem(Item theItem) {
        // Get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // save/update the donut
        session.saveOrUpdate(theItem);
    }

    @Override
    public Item getItem(int theId) {
        // Get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        return session.get(Item.class, theId);
    }

    @Override
    public void deleteItem(int theId) {
        // Get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // Select the object to get a persistent copy
        Item doomedItem = session.get(Item.class, theId);

        // Only delete if ID was valid
        if (doomedItem != null) {
            // Deleting this way will properly cascade deletes
            // and associated objects (like DonutReviews) will
            // also be deleted
            session.delete(doomedItem);
        }
    }

    @Override
    public List<Item> getItemsByName(String theSearchTerm) {
        // Get current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // Add wildcards and make search term lowercase (for case insensitivity)
        theSearchTerm = "%" + theSearchTerm.toLowerCase() + "%";

        Query query = session.createQuery("from ALL_ITEMS where lower(ITEM_NAME) like :nameToSearch");
        query.setParameter("nameToSearch", theSearchTerm);

        return query.getResultList();
    }
}