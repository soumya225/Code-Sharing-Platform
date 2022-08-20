package platform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.models.Code;
import platform.repositories.CodeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    private final CodeRepository codeRepository;

    public WebController(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @GetMapping("/code/{N}")
    public String getCode(Model model, @PathVariable int N) {

        Optional<Code> codeOptional = codeRepository.findById(N);
        Code code = codeOptional.orElse(new Code());

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
       Iterable<Code> codes = codeRepository.findAll();

       List<Code> latestCodes = new ArrayList<>();

       codes.forEach(latestCodes::add);

       latestCodes.sort(Collections.reverseOrder());

       int numLatestCodes = latestCodes.size();

        model.addAttribute("codes",
                latestCodes.subList(0, Math.min(10, numLatestCodes)));

        return "see_all_codes";
    }
}
