<%@ page import="test.improve.shared.ProdEntity" %>
<%@ page import="static test.improve.SearchTerminal.productsList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Improve Test Web Application</title>
  </head>
  <body>
  <p>Прайс-лист</p>

<!-- Поля для ввода запроса -->
  <table id="searchTable">
      <tr>
          <td>
              Категория:
          </td>
          <td>
              Наименование:
          </td>
          <td>
              Цена от:
          </td>
          <td>
              Цена до:
          </td>
      </tr>
      <tr>
          <form method="post" action="/search">
              <td>
                  <input name="category">
              </td>
              <td>
                  <input name="name">
              </td>
              <td>
                  <input name="price_min">
              </td>
              <td>
                  <input name="price_max">
              </td>
              <td>
                  <input type="submit" value="Найти">
              </td>
          </form>
      </tr>
  </table>

  <br>
  <br>

<!-- доделать таблицу вывода -->
  <table>
      <tr>
          <td>
              Категория
          </td>
          <td>
              Наименование
          </td>
          <td>
              Цена
          </td>
      </tr>
      <%for (ProdEntity product : productsList){%>
      <tr>
          <td>
              <% out.print(product.getCatName()); %>
          </td>
          <td>
              <% out.print(product.getName()); %>
          </td>
          <td>
              <% out.print(product.getPrice());} %>
          </td>
      </tr>

  </table>

  </body>
</html>
