package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ViewResolverTest {

    public static final List<ViewResolver> VIEW_RESOLVERS = Arrays.asList(new JspViewResolver(), new RedirectViewResolver());

    @Test
    void findJspView() throws Exception {
        String viewName = "abc.jsp";

        View view = VIEW_RESOLVERS.stream()
                .filter(viewResolver -> viewResolver.supports(viewName))
                .findAny()
                .map(viewResolver -> viewResolver.resolve(viewName))
                .orElseThrow(IllegalArgumentException::new);

        assertThat(view).isEqualTo(new JspView(viewName));
    }

    @Test
    void findRedirectView() throws Exception {
        String prefix = "redirect:";
        String url = "/users";
        String viewName = prefix + url;

        View view = VIEW_RESOLVERS.stream()
                .filter(viewResolver -> viewResolver.supports(viewName))
                .findAny()
                .map(viewResolver -> viewResolver.resolve(viewName))
                .orElseThrow(IllegalArgumentException::new);

        assertThat(view).isEqualTo(new RedirectView(url));
    }
}