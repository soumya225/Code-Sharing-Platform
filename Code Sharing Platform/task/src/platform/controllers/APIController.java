package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.models.Code;
import platform.services.CodeService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class APIController {

    private final CodeService codeService;

    @Autowired
    public APIController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code/{N}")
    public ResponseEntity<Map<String, String>> getCode(@PathVariable int N) {

        Optional<Code> codeOptional = codeService.findById(N);

        Map<String, String> response = new HashMap<>();
        response.put("code", codeOptional.isPresent() ? codeOptional.get().getCode() : "");
        response.put("date", codeOptional.isPresent() ? String.valueOf(codeOptional.get().getDate()) : "");


        return ResponseEntity.ok(response);
    }

    @PostMapping("/code/new")
    public ResponseEntity<Map<String, String>> changeCode(@RequestBody Code input) {

        codeService.addNewCode(input);

        Map<String, String> response = new HashMap<>();
        response.put("id", input.getId().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/latest")
    public ResponseEntity<List<Code>> getLatestCodes() {

        return new ResponseEntity<>(
                codeService.getNLatestCodes(10),
                HttpStatus.OK
        );

    }
}
