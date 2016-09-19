package test.improve;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import test.improve.shared.CatEntity;
import test.improve.shared.ProdEntity;

import java.util.*;

class SearchService {

    private Map<Integer, String> categories = new HashMap<>();
    private List<CatEntity> categoriesList = new ArrayList<>();
    private SessionFactory sessionFactory;
    private Session session;

    /**
     * Конструктор с созданием сессии Hibernate и
     * вызовом метода для инициализации мапы categories
     */
    SearchService() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();

        initCategoriesList();
    }

    /**
     * Инициализация мапы с названиями категорий товаров, где ключ = id категории в БД
     */
    private void initCategoriesList() {
        categoriesList = session.createQuery("from CatEntity ").list();

        for (CatEntity category : categoriesList)
            categories.put(category.getId(), category.getName());

        categoriesList.clear();
    }

    /**
     * Собирает запрос из введенных критериев и выполняет его
     *
     * @param search массив с введенными критериями
     * @return список товаров, соответствующих критериям
     */
    List<ProdEntity> getProductsList(String [] search) {

        String query ;
        String tempQuery = "from ProdEntity where ";

        // преобразовываем название категории в id_cat
        if (!search[0].equals("null")) tempQuery += "catId = " + getCategoryName(search[0]) + " and ";

        // формируем сторку запроса по введенным критериям
        for (int i = 1; i < search.length; i++) {
            if (!search[i].equals("null")) {
                    tempQuery += search[i] + " and ";
                }
        }

        // отрезаем лишнюю запятую с пробелом
        query = tempQuery.substring(0, tempQuery.length() - 5);
        System.out.println("query: " + query);

        Query result = session.createQuery(query);

        return result.list();
    }

    /**
     * Возвращает id категории по названию, которое ввел пользователь
     * @param category введенная категория
     * @return id этой категории или 0, если нет совпадений
     */
    private int getCategoryName(String category) {
        int catId = 0;
        String s;

        for (Map.Entry entry : categories.entrySet()) {
            s = (String) entry.getValue();
            if (s.toLowerCase().contains(category.toLowerCase()) ) catId = (int) entry.getKey();
        }

        return catId;
    }

    /**
     * @return возвращает мапу с именами категорий из БД
     */
    Map<Integer, String> getCategories() {
        return categories;
    }

}