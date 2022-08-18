package platform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.models.Code;
import platform.repositories.CodeRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class APIController {

    private final CodeRepository codeRepository;

    public APIController(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @GetMapping("/code")
    public ResponseEntity<Map<String, String>> getCode() {

        Optional<Code> codeOptional = codeRepository.findById(1);

        Map<String, String> response = new HashMap<>();
        response.put("code", codeOptional.isPresent() ? codeOptional.get().getCode() : "");
        response.put("date", codeOptional.isPresent() ? String.valueOf(codeOptional.get().getDate()) : "");


        return ResponseEntity.ok(response);
    }

    @PostMapping("/code/new")
    public ResponseEntity<Map<String, String>> changeCode(@RequestBody Code input) {

        input.setId(1);
        input.setDate(LocalDate.now());

        codeRepository.save(input);

        Map<String, String> response = new HashMap<>();

        return ResponseEntity.ok(response);
    }
}
