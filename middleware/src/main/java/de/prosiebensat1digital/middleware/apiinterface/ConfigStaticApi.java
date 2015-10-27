package de.prosiebensat1digital.middleware.apiinterface;

import java.util.List;

import de.prosiebensat1digital.middleware.model.DeviceInfo;
import de.prosiebensat1digital.middleware.model.DeviceToken;
import de.prosiebensat1digital.middleware.model.MiddlewareResult;
import de.prosiebensat1digital.middleware.model.Service;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ConfigStaticApi {
    @GET("/getServices")
    MiddlewareResult<List<Service>> getServices();
    
    @POST("/devices")
    MiddlewareResult<DeviceToken> registerDevice(@Body DeviceInfo deviceInfo);
}
