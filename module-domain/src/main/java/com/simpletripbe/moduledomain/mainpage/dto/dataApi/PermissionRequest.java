package com.simpletripbe.moduledomain.mainpage.dto.dataApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {

    private String serviceKey;
    private String returnType;
    private String numOfRows;
    private String pageNo;
    private String country_nm;
    private String country_iso_alp2;


}