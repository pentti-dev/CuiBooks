package com.example.mobileapi.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof AppException appException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(appException.getErrorCode().getMessage())
                    .extensions(Map.of(
                            "code", appException.getErrorCode().getCode(),
                            "httpStatus", appException.getErrorCode().getHttpStatus().value()
                    ))
                    .build();
        }

        // Log lỗi không xác định để debug
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);

        // Nếu không phải AppException, trả về lỗi mặc định
        return GraphqlErrorBuilder.newError(env)
                .message("Internal server error")
                .build();
    }
}
