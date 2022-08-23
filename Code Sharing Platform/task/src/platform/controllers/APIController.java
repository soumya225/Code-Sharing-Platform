package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.NotFoundException;
import platform.models.Code;
import platform.services.CodeService;

import java.util.*;

@RestController
@RequestMapping("/api")
public class APIController {

    private final CodeService codeService;

    @Autowired
    public APIController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code/{uuid}")
    public ResponseEntity<Code> getCode(@PathVariable String uuid) {

        Optional<Code> codeOptional = codeService.findByUUIDIfUnrestricted(uuid);

        if(codeOptional.isEmpty()) {
            throw new NotFoundException();
        }

        Code code = codeOptional.get();

        return ResponseEntity.ok(code);
    }

    @PostMapping("/code/new")
    public ResponseEntity<Map<String, String>> addNewCode(@RequestBody Code input) {
        System.out.println(input);

        codeService.addNewCode(input);

        Map<String, String> response = new HashMap<>();
        response.put("id", input.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/latest")
    public ResponseEntity<List<Code>> getLatestCodes() {

        return new ResponseEntity<>(
                codeService.getNLatestUnrestrictedCodes(10),
                HttpStatus.OK
        );

    }
}
