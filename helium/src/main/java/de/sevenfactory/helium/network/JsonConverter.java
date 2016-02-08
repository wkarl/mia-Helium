package de.sevenfactory.helium.network;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;

public class JsonConverter extends GsonConverter {
    private Gson mGson;
    

    public JsonConverter(Gson gson) {
        super(gson);
        mGson = gson;
    }

    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        if (body.length() == 0) {
            try {
                return mGson.fromJson("{}", type);
            } catch (JsonIOException e) {
                throw new ConversionException(e);
            }
        }
        
        return super.fromBody(body, type);
    }
}
