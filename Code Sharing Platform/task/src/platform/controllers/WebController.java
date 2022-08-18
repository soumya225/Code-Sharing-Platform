package platform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import platform.models.Code;
import platform.repositories.CodeRepository;

import java.util.Optional;

@Controller
public class WebController {

    private final CodeRepository codeRepository;

    public WebController(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @GetMapping("/code")
    public String getCode(Model model) {

        Optional<Code> codeOptional = codeRepository.findById(1);
        Code code = codeOptional.orElse(new Code());

        model.addAttribute("code", code.getCode());
        model.addAttribute("date", code.getDate());

        return "see_code";
    }

    @GetMapping("/code/new")
    public String getNewCode() {
        return "create_code";
    }
}
