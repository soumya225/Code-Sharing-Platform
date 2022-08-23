package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.NotFoundException;
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

    @GetMapping("/code/{uuid}")
    public String getCode(Model model, @PathVariable String uuid) {

        Optional<Code> codeOptional = codeService.findByUUIDIfUnrestricted(uuid);

        if(codeOptional.isEmpty()) {
            throw new NotFoundException();
        }

        Code code = codeOptional.get();

        model.addAttribute("code", code.getCode());
        model.addAttribute("date", code.getDate());

        model.addAttribute("hasTimeRestriction", code.hasTimeRestriction());
        model.addAttribute("time", code.getTime());

        model.addAttribute("hasViewsRestriction", code.hasViewsRestriction());
        model.addAttribute("views", code.getViews());

        return "see_code";
    }

    @GetMapping("/code/new")
    public String getNewCode() {
        return "create_code";
    }

    @GetMapping("/code/latest")
    public String getLatestCodes(Model model) {

        model.addAttribute("codes",
                codeService.getNLatestUnrestrictedCodes(10));

        return "see_all_codes";
    }
}
