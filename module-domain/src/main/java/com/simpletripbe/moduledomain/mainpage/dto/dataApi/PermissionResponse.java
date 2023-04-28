package com.simpletripbe.moduledomain.mainpage.dto.dataApi;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissionResponse {

    private Integer currentCount;
    private List<DataList> data = new ArrayList<>();
    private Integer numOfRows;
    private Integer pageNo;
    private Integer resultCode;
    private String resultMsg;
    private Integer totalCount;

    @Data
    public static class DataList {
        private String country_eng_nm;
        private String country_iso_alp2;
        private String country_nm;
        private String dplmt_pspt_visa_cn;
        private String dplmt_pspt_visa_yn;
        private String gnrl_pspt_visa_cn;
        private String gnrl_pspt_visa_yn;
        private String have_yn;
        private Integer id;
        private String nvisa_entry_evdc_cn;
        private String ofclpspt_visa_cn;
        private String ofclpspt_visa_yn;
        private String remark;
    }

}
