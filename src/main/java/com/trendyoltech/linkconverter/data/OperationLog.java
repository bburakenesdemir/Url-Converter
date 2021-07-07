package com.trendyoltech.linkconverter.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "operation_log")
public class OperationLog {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String requestUrl;

    private String request;

    private String response;

    private Date requestTime;

    private Date responseTime;
}
