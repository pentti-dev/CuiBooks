package vn.edu.hcmuaf.fit.fahabook.dto.request;

import java.util.Map;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GraphQLRequest {
    String query;

    Map<String, Object> variables;
}
