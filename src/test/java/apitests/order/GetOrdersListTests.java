package apitests.order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import resthandlers.apihandlers.OrdersAPIHandler;
import org.junit.Test;

public class GetOrdersListTests extends OrdersAPIHandler {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка API получения списка заказов. Ожидаемый результат - возвращается список заказов")
    public void getOrderListWithoutParamsIsSuccess() {
        Response response = getOrdersList();
        checkStatusCode(response, 200);
        checkOrdersInResponse(response);
    }
}
