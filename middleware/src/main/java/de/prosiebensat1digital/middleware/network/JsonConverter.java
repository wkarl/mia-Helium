package de.prosiebensat1digital.middleware.network;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.JacksonConverter;
import retrofit.mime.TypedInput;

public class JsonConverter extends JacksonConverter {
    private ObjectMapper mObjectMapper;
    
    public JsonConverter() {
        mObjectMapper = new ObjectMapper();
    }
    
    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        if (body.length() == 0) {
            try {
                JavaType javaType = mObjectMapper.getTypeFactory().constructType(type);
                return mObjectMapper.readValue("{}", javaType);
            } catch (IOException e) {
                throw new ConversionException(e);
            }
        }
        
        return super.fromBody(body, type);
    }
}
