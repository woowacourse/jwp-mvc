package examples;

import examples.annotation.Controller;
import examples.annotation.Inject;

@Controller
public class QnaController {
    private MyQnaService qnaService;

    @Inject
    public QnaController(MyQnaService qnaService) {
        this.qnaService = qnaService;
    }
}
