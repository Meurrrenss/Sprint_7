package apitests.courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import resthandlers.apihandlers.CourierAPIHandler;
import java.util.UUID;

public class CreateCourierTests extends CourierAPIHandler {
    private String login;
    private String password;
    private String firstName;
    public CreateCourierTests() {}

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @After
    @Step("Очистка данных после теста")
    public void cleanAfterTests() {
        if (!isCourierCreated()) return;

        Integer idCourier = getIdCourier(loginCourier(login, password));

        if (idCourier != null) {
            deleteCourier(idCourier);
        }

        setIsCreated(false);
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверка API создания нового курьера. Ожидаемый результат - новый курьер создан")
    public void createNewCourierIsSuccess() {
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }
    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка возможности создания двух одинаковых курьеров. Ожидаемый результат - одинаковых курьеров создать нельзя")
    public void createSameCouriersIsFailed(){
        // Создание первого курьера
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        // Создание второго курьера
        response = createCourier(login, password, firstName);

        checkStatusCode(response, 409);
        checkMessage(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }
    @Test
    @DisplayName("Создание нового курьера без логина")
    @Description("Проверка API создания нового курьера без логина. Ожидаемый результат - новый курьер НЕ создан")
    public void createCourierMissingLoginParamIsFailed() {
        Response response = createCourier("", password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без пароля")
    @Description("Проверка API создания нового курьера без пароля. Ожидаемый результат - новый курьер НЕ создан")
    public void createCourierMissingPasswordParamIsFailed() {
        Response response = createCourier(login, "", firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }
}
