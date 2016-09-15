package test.improve;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import test.improve.shared.CatEntity;
import test.improve.shared.ProdEntity;

import java.util.ArrayList;
import java.util.List;

class SearchService {

    private List<ProdEntity> productsList = new ArrayList<>();
    private List<CatEntity> categories = new ArrayList<>();
    private SessionFactory sessionFactory;
    private Session session;

    /**
     * Конструктор с созданием сессии Hibernate
     */
    SearchService() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }

    List<ProdEntity> getProductsList() {

        //checkInput();
        getProducts();

        return productsList;
    }

    private void checkInput() {
        // TODO: 14.09.2016 валидировать запрос
    }

    private void getProducts() {
        // TODO: 14.09.2016 получить результат из БД (исопльзовать JPA)
        Query query;

        query = session.createQuery("from CatEntity");
        categories = query.list();

        query = session.createQuery("from ProdEntity");
        productsList = query.list();

        replaceCat();
    }

    private void replaceCat() {
        for (CatEntity category : categories) {
            for (ProdEntity product : productsList)
                if (product.getCatId() == category.getId())
                    product.setCatName(category.getName());
        }
    }
}
