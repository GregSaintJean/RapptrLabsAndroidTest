package com.datechnologies.androidtest.chat.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.datechnologies.androidtest.App;
import com.datechnologies.androidtest.api.ChatLogModel;
import com.datechnologies.androidtest.network.Api;
import com.datechnologies.androidtest.network.rapptrlabs.result.ErrorBody;
import com.datechnologies.androidtest.network.rapptrlabs.RapptrlabsService;
import com.datechnologies.androidtest.network.rapptrlabs.result.ErrorResult;
import com.datechnologies.androidtest.network.rapptrlabs.result.Result;
import com.datechnologies.androidtest.network.rapptrlabs.result.ResultList;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ChatActivityViewModel extends AndroidViewModel {

    private static Converter<ResponseBody, ErrorBody> sErrorConverter;

    public ChatActivityViewModel(Application application){
        super(application);
    }

    public MutableLiveData<Result> getChatLog(){
        MutableLiveData<Result> result = new MutableLiveData<>();
        App.getNetworkIOExecutor().execute(() -> {
            RapptrlabsService service = Api.getService();

            try {
                Response<ChatLogModel> response = service.getChatLog().execute();

                if(response.isSuccessful()){
                    ChatLogModel messages = response.body();
                    result.postValue(new ResultList(messages));
                } else {
                    ErrorBody error = getErrorBody(response);
                    result.postValue(new ErrorResult(
                            error.getCode(),
                            error.getMessage()
                    ));
                }

            } catch (IOException e) {
                result.postValue(new ErrorResult(
                        "Exception",
                        e.getMessage()
                ));
            }

        });

        return result;
    }

    private static ErrorBody getErrorBody(Response<ChatLogModel> response)
            throws IOException {
        if(response == null){
            throw new IllegalArgumentException("response is null");
        }
        if(sErrorConverter == null){
            sErrorConverter
                    = Api.getRetroFit().responseBodyConverter(ErrorBody.class, new Annotation[0]);
        }

        return sErrorConverter.convert(response.errorBody());
    }

}
