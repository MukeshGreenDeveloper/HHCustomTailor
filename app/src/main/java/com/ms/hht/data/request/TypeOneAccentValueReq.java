package com.ms.hht.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeOneAccentValueReq {


    @SerializedName("id")
    private Integer id;

    @SerializedName("choice_id")
    private Integer choice_id;

    @SerializedName("additional_infos")
    List<AdditionalInfo> additional_infos;




    public TypeOneAccentValueReq(Integer id, Integer choice_id, List<AdditionalInfo> additional_infos) {
        this.id = id;
        this.choice_id = choice_id;
        this.additional_infos = additional_infos;
    }

}
class AdditionalInfo{

    @SerializedName("info_id")
    private Integer info_id;

    @SerializedName("value")
    private String value;

    public AdditionalInfo(Integer info_id, String value) {
        this.info_id = info_id;
        this.value = value;
    }
}