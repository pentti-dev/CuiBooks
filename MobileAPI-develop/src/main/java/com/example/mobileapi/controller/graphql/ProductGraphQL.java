package com.example.mobileapi.controller.graphql;

import com.example.mobileapi.dto.request.GraphQLRequest;
import com.example.mobileapi.dto.response.ApiResponse;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.ExecutionGraphQlRequest;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.GraphQlRequest;
import org.springframework.graphql.GraphQlResponse;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Product GraphQL")
@RequestMapping("/api/graphql/product")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductGraphQL {
    GraphQlSource graphQlSource;

    @PostMapping
    public ResponseEntity<Object> executeGraphQL(@RequestBody GraphQLRequest request) {
        GraphQL graphQL = graphQlSource.graphQl();
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(request.getQuery())
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        return ResponseEntity.ok(executionResult.toSpecification());
    }

}
