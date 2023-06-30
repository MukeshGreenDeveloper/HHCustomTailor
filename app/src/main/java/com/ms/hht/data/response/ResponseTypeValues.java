package com.ms.hht.data.response;

public class ResponseTypeValues {
    /**
     * For requesting an authorization code.
     *
     * @see "The OAuth 2.0 Authorization Framework (RFC 6749), Section 3.1.1
     * <https://tools.ietf.org/html/rfc6749#section-3.1.1>"
     */
    public static final String CODE = "code";

    /**
     * For requesting an access token via an implicit grant.
     *
     * @see "The OAuth 2.0 Authorization Framework (RFC 6749), Section 3.1.1
     * <https://tools.ietf.org/html/rfc6749#section-3.1.1>"
     */
    public static final String TOKEN = "token";

    /**
     * For requesting an OpenID Conenct ID Token.
     *
     * @see "The OAuth 2.0 Authorization Framework (RFC 6749), Section 3.1.1
     * <https://tools.ietf.org/html/rfc6749#section-3.1.1>"
     */
    public static final String ID_TOKEN = "id_token";
}
