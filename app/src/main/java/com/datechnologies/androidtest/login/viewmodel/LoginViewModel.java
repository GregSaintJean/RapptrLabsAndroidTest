package com.datechnologies.androidtest.login.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.datechnologies.androidtest.App;
import com.datechnologies.androidtest.network.Api;
import com.datechnologies.androidtest.network.rapptrlabs.result.ErrorBody;
import com.datechnologies.androidtest.network.rapptrlabs.RapptrlabsService;
import com.datechnologies.androidtest.network.rapptrlabs.result.ErrorResult;
import com.datechnologies.androidtest.network.rapptrlabs.result.Result;
import com.datechnologies.androidtest.network.rapptrlabs.result.SuccessBody;
import com.datechnologies.androidtest.network.rapptrlabs.result.SuccessResult;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class LoginViewModel  extends AndroidViewModel {

    private static Converter<ResponseBody, ErrorBody> sErrorConverter;

    public LoginViewModel(Application application){
        super(application);
    }

    public MutableLiveData<LoginResult> authenticate(String email,
                                                     String password){
        MutableLiveData<LoginResult> result = new MutableLiveData<>();
        App.getNetworkIOExecutor().execute(() -> {
            RapptrlabsService service = Api.getService();
            LoginResult loginResult = new LoginResult();
            long start, end, time;
            start = System.currentTimeMillis();
            try {
                start = System.currentTimeMillis();
                Response<SuccessBody> response = service.login(email, password).execute();
                end = System.currentTimeMillis();
                time = end - start;

                if(response.isSuccessful()){
                    SuccessBody messages = response.body();
                    loginResult.result = new SuccessResult(
                            messages.getCode(),
                            messages.getMessage()
                    );
                    loginResult.time = time;

                    result.postValue(loginResult);

                } else {
                    ErrorBody error = getErrorBody(response);
                    loginResult.result = new ErrorResult(
                            error.getCode(),
                            error.getMessage()
                    );
                    loginResult.time = time;

                    result.postValue(loginResult);
                }

            } catch (IOException e) {
                end = System.currentTimeMillis();
                loginResult.result = new ErrorResult(
                        "Exception",
                        e.getMessage()
                );
                time = end - start;
                loginResult.time = time;

                result.postValue(loginResult);
            }

        });

        return result;
    }

    private static ErrorBody getErrorBody(Response<SuccessBody> response)
            throws IOException {
        if(response == null){
            throw new IllegalArgumentException("response is null");
        }
        if(sErrorConverter == null){
            sErrorConverter
                    = Api.getRetroFit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
        }

        assert response.errorBody() != null;
        return sErrorConverter.convert(response.errorBody());
    }

    public static class LoginResult{
        public Result result;
        public long time;
    }
}
