package nextstep.utils;

import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

public class PathPatternUtils {
    public static final PathPatternParser PATH_PATTERN_PARSER = new PathPatternParser();

    private PathPatternUtils() {
    }

    public static PathPattern parse(String path) {
        PATH_PATTERN_PARSER.setMatchOptionalTrailingSeparator(true);
        return PATH_PATTERN_PARSER.parse(path);
    }

    public static PathContainer toPathContainer(String path) {
        if (path == null) {
            return null;
        }
        return PathContainer.parsePath(path);
    }
}
