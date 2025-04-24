// UrlResponse.java
package com.example.mobileapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UrlResponse {
    @JsonProperty("url")  // Đảm bảo rằng trường 'url' sẽ được ánh xạ chính xác trong JSON
    String url;
}
