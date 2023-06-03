package com.ms.hht.data.service;

public interface DisposableData {

    public void onSuccess(String url_type, Object o) throws Exception;
    public void onError(String message) throws Exception;

}
