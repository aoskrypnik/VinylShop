package com.vinyl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Salesman {
    private Integer tabNum;
    private String salesmanName;
    private String passportNum;
    private String addressCity;
    private String addressStr;
    private String addressHome;
    private Integer addressApt;
    private String salesmanPhoneNum;
    private Date worksFrom;
    private Date worksTo;
    private Integer salary;
    private String salesmanLogin;

}
