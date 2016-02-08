package de.sevenfactory.helium.apiinterface;

import java.util.List;

import de.sevenfactory.helium.model.DeviceInfo;
import de.sevenfactory.helium.model.DeviceToken;
import de.sevenfactory.helium.model.MiddlewareResult;
import de.sevenfactory.helium.model.Service;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ConfigStaticApi {
    @GET("/getServices")
    MiddlewareResult<List<Service>> getServices();
    
    @POST("/devices")
    MiddlewareResult<DeviceToken> registerDevice(@Body DeviceInfo deviceInfo);
}
