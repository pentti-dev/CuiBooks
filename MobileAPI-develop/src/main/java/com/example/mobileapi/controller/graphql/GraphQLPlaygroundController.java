package com.example.mobileapi.controller.graphql;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class GraphQLPlaygroundController {

    @GetMapping("/graphql-ui")
    public void playground(HttpServletResponse response) throws IOException {
        String playgroundHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8"/>
                    <title>GraphQL Playground</title>
                    <link rel="stylesheet" href="/webjars/graphql-playground-react/build/static/css/index.css"/>
                    <link rel="shortcut icon" href="/webjars/graphql-playground-react/build/favicon.png"/>
                    <script src="/webjars/graphql-playground-react/build/static/js/middleware.js"></script>
                </head>
                <body>
                <div id="root"></div>
                <script>
                    window.addEventListener('load', function () {
                        GraphQLPlayground.init(document.getElementById('root'), {
                            endpoint: '/graphql',
                            settings: {
                                'request.credentials': 'same-origin'
                            }
                        });
                    });
                </script>
                </body>
                </html>
                """;
        response.setContentType("text/html");
        response.getWriter().write(playgroundHtml);
    }
}
