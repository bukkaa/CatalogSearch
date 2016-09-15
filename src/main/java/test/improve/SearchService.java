package test.improve;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import test.improve.shared.CatEntity;
import test.improve.shared.ProdEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SearchService {

    private List<ProdEntity> productsList = new ArrayList<>();
    private Map<Integer, String> categories = new HashMap<>();
    private List<CatEntity> categoriesList = new ArrayList<>();
    private SessionFactory sessionFactory;
    private Session session;

    /**
     * Конструктор с созданием сессии Hibernate
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
     * Запускает проверку введенных критериев. Если они верны,
     * вызывает метод для получения запрашиваемых товаров из БД
     * @param inputQuery введенные критерии
     * @return список товаров, соответствующих критериям
     */
    List<ProdEntity> getProductsList(String inputQuery) {

        //if (checkInput(inputQuery))
            getProductsFromDB(inputQuery);
        //else return null;

        return productsList;
    }

    /**
     * @return возвращает мапу с именами категорий из БД
     */
    Map<Integer, String> getCategories() {
        return categories;
    }

    /**
     * порверяет критерии на правильность ввода
     * @param inputQuery введенный запрос
     * @return true - если запрос верен,
     *          false - если нет.
     */
    private boolean checkInput(String inputQuery) {

        // inputQuery must be shorter than 255 chars and consist of numbers and letters
        if ( !inputQuery.matches("^[0-9a-zA-Zа-яА-Я]{1,255}$") )
            return false;
        return true;
    }

    /**
     * Проводит поиск товаров по БД в соответствии с критериями
     * @param inputQuery критерии поиска
     */
    private void getProductsFromDB(String inputQuery) {
        // TODO: 14.09.2016 получить результат из БД (исопльзовать JPA)
        Query query;

        query = session.createQuery("from ProdEntity");
        productsList = query.list();
    }
}