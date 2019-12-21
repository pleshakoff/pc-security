package com.parcom.security.auth;

public interface AuthService {
    TokenResource authenticate(UserAuthDto userAuthDTO);

    TokenResource setContext(ContextDto contextDto);
}
