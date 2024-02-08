package software.sigma.internship.mongo.filters.core;

import lombok.NoArgsConstructor;
import software.sigma.internship.mongo.filters.domain.Filter;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class FilterCollector {

    public static Map<String, String> collectRestApiParams(Map<String, String> filters) {

        return Arrays.stream(Filter.values())
            .map(Filter::getCode)
            .filter(filters::containsKey)
            .collect(toMap(code -> code, filters::get));
    }

}
