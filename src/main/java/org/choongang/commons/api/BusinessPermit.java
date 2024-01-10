package org.choongang.commons.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class BusinessPermit {
    private Integer request_cnt;
    private Integer match_cnt;
    private String status_code;
    private List<BusinessPermitData> data;
}
