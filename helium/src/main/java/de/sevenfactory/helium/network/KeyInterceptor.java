package de.sevenfactory.helium.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import de.sevenfactory.helium.TokenRepository;

public class KeyInterceptor implements Interceptor {
    private final TokenRepository mTokenRepository;
    private       RequestSigner   mRequestSigner;

    public KeyInterceptor(RequestSigner inRequestSigner, TokenRepository inTokenRepository) {
        mRequestSigner = inRequestSigner;
        mTokenRepository = inTokenRepository;
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {
        Request request       = chain.request();
        Request signedRequest = mRequestSigner.signRequest(request, mTokenRepository.getDeviceToken());
        return chain.proceed(signedRequest);
    }
}


