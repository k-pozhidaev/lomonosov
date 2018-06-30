package io.pozhidaev.lomonosov.domain;

import java.util.List;
import java.util.Optional;

abstract class AbstractUrlQuery {

    String path;
    transient List<UrlQueryNode> nodes;

    class UrlQueryNode{

        private final Boolean isOptional;
        private final String name;
        private final String value;

        public UrlQueryNode(Boolean isOptional, String name, String value) {
            this.isOptional = Optional.ofNullable(isOptional).orElse(false);
            this.name = name;
            this.value = value;
        }

        public String getValue(){
            if ("0".equals(value)) {
                return "";
            }
            return Optional.ofNullable(value).orElse("");
        }
    }
}
