package com.hohee.dbp.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SearchRequest {
    private String keyword;
    private String type;
}
