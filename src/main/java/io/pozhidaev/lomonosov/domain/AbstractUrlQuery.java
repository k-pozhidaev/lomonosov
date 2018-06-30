package io.pozhidaev.lomonosov.domain;

import lombok.Data;

import java.util.List;

abstract class AbstractUrlQuery {

    protected List<UrlQueryNode> nodes;

    @Data
    class UrlQueryNode{
        private Boolean optional;
        private String name;
        private Object value;
    }
}
