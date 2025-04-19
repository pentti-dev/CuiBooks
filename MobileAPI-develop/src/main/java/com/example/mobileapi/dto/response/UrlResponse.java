package com.example.mobileapi.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UrlResponse {
    String url;
}
