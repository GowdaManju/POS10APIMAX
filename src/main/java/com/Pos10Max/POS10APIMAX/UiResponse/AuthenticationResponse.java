package com.Pos10Max.POS10APIMAX.UiResponse;

public class AuthenticationResponse {

        private String jwt;
        private String userId;

        public AuthenticationResponse(String jwt,String userId)
        {
            this.jwt=jwt;
            this.userId=userId;
        }
        public String getJwt()
        {
            return jwt;
        }

    public String getUserId()
    {
        return userId;
    }

}
