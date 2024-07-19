package apitests.order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import resthandlers.apihandlers.OrdersAPIHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTests extends OrdersAPIHandler {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private final List<String> scooterColor;
    private Integer trackId;

    public CreateOrderTests(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] initParamsForTest() {
        return new Object[][] {
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        this.firstName = "Ян";
        this.lastName = "Пак";
        this.address = "Москва, Тестовая ул., д. 123";
        this.phone = "+7 (910) 777-88-99";
        this.rentTime = "3";
        this.deliveryDate = "2024-08-02";
        this.comment = "Хочу кататься";
    }

    @After
    @Step("Очистка данных после теста")
    public void clearAfterTests() {
        if (trackId == null) return;

        deleteOrder(trackId);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка API создания заказа. Ожидаемый результат - заказ создан, возвращается его track-номер")
    public void createOrderIsSuccess() {
        Allure.parameter("Цвет самоката", scooterColor);

        Response response = createOrder(firstName, lastName, address, phone, rentTime, deliveryDate, comment, scooterColor);
        checkStatusCode(response, 201);
        checkResponseParamNotNull(response, "track");

        this.trackId = getTrack(response);
    }
}
