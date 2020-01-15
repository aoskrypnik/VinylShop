package com.vinyl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Salesman {

    private int tabNum;
    private String name;
    private String passportNum;
    private String addressCity;
    private String addressStr;
    private String addressHome;
    private Integer addressApt;
    private String phoneNum;
    private Date worksFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date worksTo;
    private Integer salary;
    private String login;

}
