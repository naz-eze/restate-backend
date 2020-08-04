package com.restateai.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restateai.model.lead.Lead;
import java.util.List;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class LeadsDTO {
    List<Lead> leads;
}
