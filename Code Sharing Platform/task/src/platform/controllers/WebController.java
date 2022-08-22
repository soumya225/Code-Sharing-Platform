package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.models.Code;
import platform.services.CodeService;

import java.util.Optional;

@Controller
public class WebController {

    private final CodeService codeService;

    @Autowired
    public WebController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code/{N}")
    public String getCode(Model model, @PathVariable int N) {

        Optional<Code> codeOptional = codeService.findById(N);
        Code code = codeOptional.orElseThrow();

        model.addAttribute("code", code.getCode());
        model.addAttribute("date", code.getDate());

        return "see_code";
    }

    @GetMapping("/code/new")
    public String getNewCode() {
        return "create_code";
    }

   @GetMapping("/code/latest")
    public String getLatestCodes(Model model) {

        model.addAttribute("codes",
                codeService.getNLatestCodes(10));

        return "see_all_codes";
    }
}
