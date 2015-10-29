package de.prosiebensat1digital.helium.apiinterface;

import java.util.List;

import de.prosiebensat1digital.helium.model.DeviceInfo;
import de.prosiebensat1digital.helium.model.DeviceToken;
import de.prosiebensat1digital.helium.model.MiddlewareResult;
import de.prosiebensat1digital.helium.model.Service;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ConfigStaticApi {
    @GET("/getServices")
    MiddlewareResult<List<Service>> getServices();
    
    @POST("/devices")
    MiddlewareResult<DeviceToken> registerDevice(@Body DeviceInfo deviceInfo);
}
