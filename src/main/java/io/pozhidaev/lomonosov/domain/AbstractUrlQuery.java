package io.pozhidaev.lomonosov.domain;

import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

abstract class AbstractUrlQuery {

    String path;
    transient List<UrlQueryNode> nodes;

    public URI generateUrl(final UriBuilder uriBuilder) {

        uriBuilder.path(Optional.ofNullable(path).orElseThrow(() -> new RuntimeException("Path cannot be null.")));
        Optional.ofNullable(nodes).orElseThrow(() -> new RuntimeException("Nodes cannot be null"))
                .forEach(n -> uriBuilder.queryParam(n.name, n.getValue()));
        return uriBuilder.build();

    }

    class UrlQueryNode{

        private final Boolean isOptional;
        private final String name;
        private final Object value;

        UrlQueryNode(Boolean isOptional, String name, Object value) {
            this.isOptional = Optional.ofNullable(isOptional).orElse(false);
            this.name = name;
            this.value = value;
        }

        String getValue(){
            if ("0".equals(value)) {
                return "";
            }
            return Optional.ofNullable(value)
                    .orElse("")
                    .toString();
        }
    }
}
