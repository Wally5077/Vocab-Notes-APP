package com.home.englishnote.models.repositories;

import com.home.englishnote.exceptions.EmailDuplicatedException;
import com.home.englishnote.exceptions.InvalidCredentialsException;
import com.home.englishnote.exceptions.MemberIdInvalidException;
import com.home.englishnote.exceptions.MemberInfoNotFoundException;
import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class RetrofitMemberRepository implements MemberRepository {

    private MemberApi memberApi;

    public RetrofitMemberRepository(Retrofit retrofit) {
        memberApi = retrofit.create(MemberApi.class);
    }

    private interface MemberApi {
        @POST("/api/members")
        Call<Token> signUp(@Body Member member, @Body Credentials credentials);

        @POST("/api/members/tokens")
        Call<Token> signInToken(@Body Credentials credentials);

        @GET("/api/members/{id}")
        Call<Member> signInMemberInfo(@Path("id") int memberId);
    }

    @Override
    public Token signUp(Member member, Credentials credentials) {
        Call<Token> tokenCall = memberApi.signUp(member, credentials);
        Token token = null;
        try {
            Response<Token> tokenResponse = tokenCall.execute();
            if (tokenResponse.code() == 400) {
                throw new EmailDuplicatedException();
            }
            token = tokenResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public Token signInToken(Credentials credentials) {
        Call<Token> tokenCall = memberApi.signInToken(credentials);
        Token token = null;
        try {
            Response<Token> tokenResponse = tokenCall.execute();
            if (tokenResponse.code() == 401) {
                throw new InvalidCredentialsException();
            }
            token = tokenResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public Member getMember(int memberId) {
        Call<Member> memberCall = memberApi.signInMemberInfo(memberId);
        Member member = null;
        try {
            Response<Member> tokenResponse = memberCall.execute();
            switch (tokenResponse.code()) {
                case 400:
                    throw new MemberIdInvalidException();
                case 404:
                    throw new MemberInfoNotFoundException();
            }
            member = tokenResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return member;
    }

    @Override
    public void updateMemberInfo(String firstName, String lastName, int age) {

    }

    @Override
    public List<Dictionary> getOwnDictionaries(int memberId, int offset, int limit) {
        return null;
    }
}
