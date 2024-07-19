package resthandlers.httpclients;

import models.requestmodels.Courier;
import io.restassured.response.Response;
import models.ScooterUrls;

public class CourierHTTPClient extends BaseHTTPClient {
    public Response createCourier(Courier courier) {
        return doPostRequest(
                ScooterUrls.HOST_NAME + ScooterUrls.CREATE_COURIER,
                courier,
                "application/json"
        );
    }

    public Response loginCourier(Courier courier) {
        return doPostRequest(
                ScooterUrls.HOST_NAME + ScooterUrls.LOGIN_COURIER,
                courier,
                "application/json"
        );

    }

    public Response deleteCourier(Integer idCourier) {
        return doDeleteRequest(ScooterUrls.HOST_NAME + ScooterUrls.DELETE_COURIER + idCourier);
    }
}
