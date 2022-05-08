package com.datechnologies.androidtest.network.rapptrlabs.result;

import com.datechnologies.androidtest.api.ChatLogModel;

public class ResultList extends Result{
    private ChatLogModel list;
    public ResultList(ChatLogModel list){
        this.list = list;
    }

    public ChatLogModel getList() {
        return list;
    }
}
