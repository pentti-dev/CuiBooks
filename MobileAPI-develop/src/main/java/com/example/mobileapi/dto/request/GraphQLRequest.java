package com.example.mobileapi.dto.request;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GraphQLRequest {
    String query;

    Map<String, Object> variables;


}
