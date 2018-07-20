package io.pozhidaev.lomonosov.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
abstract class AbstractUrlQuery {

    String path;
    transient List<UrlQueryNode> nodes;

    public URI generateUrl(final UriBuilder uriBuilder) {

        uriBuilder.path(Optional.ofNullable(path).orElseThrow(() -> new RuntimeException("Path cannot be null.")));
        Optional.ofNullable(nodes).orElseThrow(() -> new RuntimeException("Nodes cannot be null"))
                .stream()
                .filter(v -> !v.getOptional() || "".equals(v.getValue()) )
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

        Boolean getOptional() {
            return isOptional;
        }

        String getValue(){
            return Optional.ofNullable(value)
                    .map(Object::toString)
                    .filter(v -> !"0.0".equals(v))
                    .filter(v -> !"0".equals(v))
                    .orElse("");
        }
    }
}
