package software.sigma.internship.mongo.filters.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import software.sigma.internship.mongo.filters.exceptions.WrongQueryParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            operations.add(Aggregation.sort(Direction.ASC, "testName"));
        }
        return operations;
    }

    private static void applyGroupOperation(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        if (restApiQueries.get(AVERAGE.getCode()) != null) {
            String average = restApiQueries.get(AVERAGE.getCode());
            if (average.equals("true")) {
                operations.add(Aggregation.group("testName").avg("result").as("averageResult"));
            }
        }
    }

    private static void applyPageQueryParam(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        try {
            if (restApiQueries.get(PAGE.getCode()) != null) {
                String pageQueryParam = restApiQueries.get(PAGE.getCode());
                long skip = Long.parseLong(pageQueryParam);
                if (skip < 0) {
                    throw new NumberFormatException();
                }
                operations.add(Aggregation.skip(skip));
            }
        } catch (NumberFormatException pageParamExc) {
            throw new WrongQueryParam("Page param must be greater than or equal to 0");
        }
    }

    private static void applyPageSizeQueryParam(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        try {
            if (restApiQueries.get(PAGE_SIZE.getCode()) != null) {
                String pageSizeQueryParam = restApiQueries.get(PAGE_SIZE.getCode());
                int limit = Integer.parseInt(pageSizeQueryParam);
                if (limit < 0) {
                    throw new NumberFormatException();
                }
                operations.add(Aggregation.limit(limit));
            }
        } catch (NumberFormatException pageParamExc) {
            throw new WrongQueryParam("PageSize param must be greater than or equal to 0");
        }
    }

    private static void applySearchQueryParam(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        try {
            if (restApiQueries.get(SEARCH.getCode()) != null) {
                JSONObject jsonCriteria = new JSONObject(restApiQueries.get(SEARCH.getCode()));
                JSONArray keys = jsonCriteria.names();
                for (int i = 0; i < keys.length(); ++i) {
                    String field = keys.getString(i);
                    String value = jsonCriteria.getString(field);
                    operations.add(Aggregation.match(Criteria.where(field).is(value)));
                }
            }
        } catch (JSONException e) {
            throw new WrongQueryParam("Your JSON format is wrong");
        }
    }

    private static void applySelectOperation(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        if (restApiQueries.get(SELECT.getCode()) != null) {
            String[] selectedFields = restApiQueries.get(SELECT.getCode()).split(FIELD_DECIMETER);
            operations.add(Aggregation.project(selectedFields));
        }
    }

    private static void applySortOperation(List<AggregationOperation> operations, Map<String, String> restApiQueries) {
        try {
            if (restApiQueries.get(SORT.getCode()) != null) {
                String sortQueryParam = restApiQueries.get(SORT.getCode());
                Direction sortDir =
                        sortQueryParam.charAt(0) == '-' ? Direction.DESC : Direction.ASC;
                String sortBy = sortDir.equals(Direction.DESC) ? sortQueryParam.substring(1)
                        : sortQueryParam;
                operations.add(Aggregation.sort(sortDir, sortBy));
            }
        } catch (IndexOutOfBoundsException subStringException) {
            throw new WrongQueryParam();
        }
    }
}
