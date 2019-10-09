package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.ForwardView;
import nextstep.mvc.tobe.view.RedirectView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LegacyHandlerAdapterTest {
    public static final String REDIRECT_PREFIX = "redirect:";
    LegacyHandlerAdapter legacyHandlerAdapter;
    @BeforeEach
    public void setup() {
        legacyHandlerAdapter = new LegacyHandlerAdapter();
    }

    @Test
    public void canAdapt_controller_true() {
        Controller controller = getTestController();
        assertThat(legacyHandlerAdapter.canAdapt(controller)).isTrue();
    }

    @DisplayName("view가 redirect prefix를 포함 시 알맞은 뷰를 반환하는지")
    @Test
    public void createView_redirectPrefix_redirectView(){
        assertThat(legacyHandlerAdapter.createView(REDIRECT_PREFIX)).isInstanceOf(RedirectView.class);
    }

    @DisplayName("view가 redirect prefix가 없을 시 알맞은 뷰 반환")
    @Test
    public void createView_nonRedirectPrefix_forwardView(){
        assertThat(legacyHandlerAdapter.createView("abc")).isInstanceOf(ForwardView.class);
    }


    public Controller getTestController() {
        return (req, resp) -> null;
    }
}