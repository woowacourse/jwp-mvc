package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface ModelToJsonParser<T, R> {

    R parse(T t) throws JsonProcessingException;
}
