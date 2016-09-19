package test.improve;

import test.improve.shared.ProdEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/search")
public class SearchTerminal extends HttpServlet{
    private SearchService searchService = new SearchService();
    public static List<ProdEntity> productsList = new ArrayList<>();
    public static Map<Integer, String> categories = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (    (!req.getParameter("category").isEmpty()) || //порверка, что хотя бы один из критериев поиска введен
                (!req.getParameter("name").isEmpty()) ||
                (!req.getParameter("price_min").isEmpty()) ||
                (!req.getParameter("price_max").isEmpty())
                )
        {

            // заполняем мапу с названиями категорий товаров
            categories = searchService.getCategories();

            String[] search = new String[4]; // массив с фрагментами запроса или null, если критерий не был определен пользователем
            search[0] = req.getParameter("category").isEmpty() ? "null" : req.getParameter("category"); // преобразование названия категории в id_cat сделано в SearchService
            search[1] = req.getParameter("name").isEmpty() ? "null" : "name = '" + req.getParameter("name") + "'";
            search[2] = req.getParameter("price_min").isEmpty() ? "null" : "price >= " + req.getParameter("price_min");
            search[3] = req.getParameter("price_max").isEmpty() ? "null" : "price <= " + req.getParameter("price_max");

            productsList = searchService.getProductsList(search);

            // возвращаем результат запроса в браузер
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        else
            // это на случай, если ничего введено не было
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}

