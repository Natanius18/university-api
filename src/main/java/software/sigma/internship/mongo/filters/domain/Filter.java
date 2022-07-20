package software.sigma.internship.mongo.filters.domain;

public enum Filter {
    PAGE_SIZE("pageSize"),
    PAGE("page"),
    SEARCH("q"),
    SELECT("select"),
    SORT("sort"),
    AVERAGE("average");

    private final String code;

    Filter(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
