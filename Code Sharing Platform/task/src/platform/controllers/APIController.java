package platform.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.models.Code;
import platform.repositories.CodeRepository;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class APIController {

    private final CodeRepository codeRepository;

    public APIController(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @GetMapping("/code/{N}")
    public ResponseEntity<Map<String, String>> getCode(@PathVariable int N) {

        Optional<Code> codeOptional = codeRepository.findById(N);

        Map<String, String> response = new HashMap<>();
        response.put("code", codeOptional.isPresent() ? codeOptional.get().getCode() : "");
        response.put("date", codeOptional.isPresent() ? String.valueOf(codeOptional.get().getDate()) : "");


        return ResponseEntity.ok(response);
    }

    @PostMapping("/code/new")
    public ResponseEntity<Map<String, String>> changeCode(@RequestBody Code input) {

        input.setDate(LocalDateTime.now().toString());

        codeRepository.save(input);

        Map<String, String> response = new HashMap<>();
        response.put("id", input.getId().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/latest")
    public ResponseEntity<List<Code>> getLatestCodes() {
        Iterable<Code> codes = codeRepository.findAll();

        List<Code> latestCodes = new ArrayList<>();

        codes.forEach(latestCodes::add);

        latestCodes.sort(Collections.reverseOrder());

        int numLatestCodes = latestCodes.size();

        return new ResponseEntity<>(
                latestCodes.subList(0, Math.min(10, numLatestCodes)),
                HttpStatus.OK
        );

    }
}
