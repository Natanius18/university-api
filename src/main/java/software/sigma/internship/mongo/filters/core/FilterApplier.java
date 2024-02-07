package software.sigma.internship.mongo.filters.core;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import software.sigma.internship.mongo.filters.exceptions.WrongQueryParamException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static software.sigma.internship.mongo.filters.domain.Filter.AVERAGE;
import static software.sigma.internship.mongo.filters.domain.Filter.PAGE;
import static software.sigma.internship.mongo.filters.domain.Filter.PAGE_SIZE;
import static software.sigma.internship.mongo.filters.domain.Filter.SEARCH;
import static software.sigma.internship.mongo.filters.domain.Filter.SELECT;
import static software.sigma.internship.mongo.filters.domain.Filter.SORT;

public final class FilterApplier {

    private FilterApplier() {
    }

    public static final String FIELD_DECIMETER = ",";

    public static List<AggregationOperation> applyRestApiQueries(Map<String, String> restApiQueries) {
        List<AggregationOperation> operations = new ArrayList<>();
        applyGroupOperation(operations, restApiQueries);
        applySortOperation(operations, restApiQueries);
        applySelectOperation(operations, restApiQueries);
        applySearchQueryParam(operations, restApiQueries);
        applyPageQueryParam(operations, restApiQueries);
        applyPageSizeQueryParam(operations, restApiQueries);
        if (operations.isEmpty()) {
            operations.add(sort(ASC, "testName"));
        }
        return operations;
    }

    private static void applyGroupOperation(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        if (restApiQueries.get(AVERAGE.getCode()) != null) {
            String averageField = restApiQueries.get(AVERAGE.getCode());
            if (averageField.equals("numberOfTry") || averageField.equals("result")) {
                operations.add(group("testName")
                        .avg("numberOfTry").as("averageNumberOfTry")
                        .avg("result").as("averageResult"));
            }
            else {
                throw new WrongQueryParamException("Invalid query parameter for average field");
            }
        }
    }

    private static void applyPageQueryParam(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        try {
            if (restApiQueries.get(PAGE.getCode()) != null) {
                long skip = parseLong(restApiQueries.get(PAGE.getCode()));
                if (skip < 0) {
                    throw new NumberFormatException();
                }
                operations.add(skip(skip));
            }
        } catch (NumberFormatException pageParamExc) {
            throw new WrongQueryParamException("Page param must be greater than or equal to 0");
        }
    }

    private static void applyPageSizeQueryParam(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        try {
            if (restApiQueries.get(PAGE_SIZE.getCode()) != null) {
                int limit = parseInt(restApiQueries.get(PAGE_SIZE.getCode()));
                if (limit < 0) {
                    throw new NumberFormatException();
                }
                operations.add(limit(limit));
            }
        } catch (NumberFormatException pageParamExc) {
            throw new WrongQueryParamException("PageSize param must be greater than or equal to 0");
        }
    }

    private static void applySearchQueryParam(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        try {
            if (restApiQueries.get(SEARCH.getCode()) != null) {
                var jsonCriteria = new JSONObject(restApiQueries.get(SEARCH.getCode()));
                var keys = jsonCriteria.names();
                for (int i = 0; i < keys.length(); ++i) {
                    String field = keys.getString(i);
                    String value = jsonCriteria.getString(field);
                    operations.add(match(where(field).is(value)));
                }
            }
        } catch (JSONException e) {
            throw new WrongQueryParamException("Your JSON format is wrong");
        }
    }

    private static void applySelectOperation(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        if (restApiQueries.get(SELECT.getCode()) != null) {
            var selectedFields = restApiQueries.get(SELECT.getCode()).split(FIELD_DECIMETER);
            operations.add(project(selectedFields));
        }
    }

    private static void applySortOperation(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        try {
            if (restApiQueries.get(SORT.getCode()) != null) {
                var sortQueryParam = restApiQueries.get(SORT.getCode());
                var sortDir = sortQueryParam.charAt(0) == '-' ? DESC : ASC;
                var sortBy = sortDir.equals(DESC)
                    ? sortQueryParam.substring(1)
                    : sortQueryParam;
                operations.add(sort(sortDir, sortBy));
            }
        } catch (IndexOutOfBoundsException subStringException) {
            throw new WrongQueryParamException();
        }
    }
}
