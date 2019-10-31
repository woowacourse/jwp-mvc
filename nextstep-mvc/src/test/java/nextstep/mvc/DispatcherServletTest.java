package nextstep.mvc;

import nextstep.mvc.tobe.AnnotationHandlerAdapter;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherServletTest {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private DispatcherServlet dispatcherServlet;

    @BeforeEach
    void setUp() throws ServletException {
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("slipp.controller");
        List<HandlerMapping> handlerMappings = Arrays.asList(annotationHandlerMapping);

        AnnotationHandlerAdapter annotationHandlerAdapter = new AnnotationHandlerAdapter();
        List<HandlerAdapter> handlerAdapters = Arrays.asList(annotationHandlerAdapter);

        dispatcherServlet = new DispatcherServlet(handlerMappings, handlerAdapters);
        dispatcherServlet.init();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void 홈() throws ServletException, IOException {
        request.setMethod("GET");
        request.setRequestURI("/");

        dispatcherServlet.service(request, response);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void 회원가입() throws ServletException, IOException {
        request.setMethod("POST");
        request.setRequestURI("/users/create");

        request.addParameter("userId", "coogiegood");
        request.addParameter("password", "password");
        request.addParameter("name", "coogine");
        request.addParameter("email", "coogie@gmail.com");

        dispatcherServlet.service(request, response);

        assertThat(response.getStatus()).isEqualTo(302);
    }

    @Test
    void 비로그인_유저리스트_조회() throws ServletException, IOException {
        request.setMethod("GET");
        request.setRequestURI("/users");

        dispatcherServlet.service(request, response);

        assertThat(response.getStatus()).isEqualTo(302);
    }

    @Test
    void 로그인_유저리스트_조회() throws ServletException, IOException {
        request.setMethod("GET");
        request.setRequestURI("/users");

        User user = new User("coogie123", "password", "coogie", "coogie@gmail.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        request.setSession(session);

        dispatcherServlet.service(request, response);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void 로그인_성공() throws ServletException, IOException {
        request.setMethod("POST");
        request.setRequestURI("/users/login");

        User user = new User("coogie123", "password", "coogie", "coogie@gmail.com");
        DataBase.addUser(user);

        request.addParameter("userId", user.getUserId());
        request.addParameter("password", user.getPassword());

        dispatcherServlet.service(request, response);

        assertThat(response.getStatus()).isEqualTo(302);
    }

    @Test
    void 로그인_실패_아이디_없음() throws ServletException, IOException {
        request.setMethod("POST");
        request.setRequestURI("/users/login");

        User user = new User("coogie123", "password", "coogie", "coogie@gmail.com");
        DataBase.addUser(user);

        request.addParameter("userId", user.getUserId() + "a");
        request.addParameter("password", user.getPassword());

        dispatcherServlet.service(request, response);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void 로그인_실패_비밀번호_틀림() throws ServletException, IOException {
        request.setMethod("POST");
        request.setRequestURI("/users/login");

        User user = new User("coogie123", "password", "coogie", "coogie@gmail.com");
        DataBase.addUser(user);

        request.addParameter("userId", user.getUserId() + "a");
        request.addParameter("password", user.getPassword());

        dispatcherServlet.service(request, response);

        assertThat(response.getStatus()).isEqualTo(200);
    }
}