package nextstep.mvc.tobe.view;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (model.size() == 0) {
            return;
        }
        if (model.size() == 1) {
            model.keySet()
                    .forEach(key -> out.print(gson.toJson(model.get(key))));
            return;
        }
        out.print(gson.toJson(model));
    }
}
