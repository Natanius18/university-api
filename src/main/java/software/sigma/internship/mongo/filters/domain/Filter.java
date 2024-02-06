package software.sigma.internship.mongo.filters.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Filter {
    PAGE_SIZE("pageSize"),
    PAGE("page"),
    SEARCH("q"),
    SELECT("select"),
    SORT("sort"),
    AVERAGE("average");

    private final String code;

}
