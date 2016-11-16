package filters;

import play.filters.cors.CORSFilter;
import play.filters.headers.SecurityHeadersFilter;
import play.http.DefaultHttpFilters;
import play.mvc.EssentialFilter;
import play.filters.gzip.GzipFilter;
import play.http.HttpFilters;

import javax.inject.Inject;

public class Filters extends DefaultHttpFilters {

    @Inject
    public Filters(GzipFilter gzipFilter, SecurityHeadersFilter securityHeadersFilter, CORSFilter corsFilter) {
        super(gzipFilter, securityHeadersFilter, corsFilter);
    }
}
