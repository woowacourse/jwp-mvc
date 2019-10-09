package nextstep.utils;

import org.springframework.http.server.PathContainer;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

public class PathPatternUtils {
    private static final PathPatternParser PATH_PATTERN_PARSER = new PathPatternParser();
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    private PathPatternUtils() {
    }

    public static PathPattern parse(final String path) {
        PATH_PATTERN_PARSER.setMatchOptionalTrailingSeparator(true);
        return PATH_PATTERN_PARSER.parse(path);
    }

    public static PathContainer toPathContainer(final String path) {
        if (path == null) {
            return null;
        }
        return PathContainer.parsePath(path);
    }

    public static boolean match(String pattern, String path){
        final boolean match = PATH_MATCHER.match(pattern, path);
        return match;
    }
}
