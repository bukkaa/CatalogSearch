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
        if (    (!req.getParameter("category").isEmpty()) ||
                (!req.getParameter("name").isEmpty()) ||
                (!req.getParameter("price_min").isEmpty()) ||
                (!req.getParameter("price_max").isEmpty())
                ) {

            // TODO передать запрос поисковому сервису
            productsList = searchService.getProductsList("5000");
            categories = searchService.getCategories();

            // возвращаем результат запроса
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }
    }
}

