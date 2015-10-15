package de.prosiebensat1digital.middleware.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class KeyInterceptor implements Interceptor {
    private RequestSigner mRequestSigner;
    
    public KeyInterceptor(final RequestSigner inRequestSigner) {
        mRequestSigner = inRequestSigner;
    }
    
    @Override
    public Response intercept(final Chain chain) throws IOException {
        Request request       = chain.request();
        Request signedRequest = mRequestSigner.signRequest(request);
        return chain.proceed(signedRequest);
    }
}
