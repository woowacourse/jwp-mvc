package examples;

import annotation.Controller;
import annotation.Inject;

@Controller
public class QnaControllerInTestPackage {
    private MyQnaService qnaService;

    @Inject
    public QnaControllerInTestPackage(MyQnaService qnaService) {
        this.qnaService = qnaService;
    }
}
