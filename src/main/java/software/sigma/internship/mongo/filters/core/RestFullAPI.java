package software.sigma.internship.mongo.filters.core;

import java.util.Map;

public class RestFullAPI {

    private RestFullAPI() {
    }

    public static Map<String, String> collectRestApiParams(Map<String, String> filters) {
        return FilterCollector.collectRestApiParams(filters);
    }

}
