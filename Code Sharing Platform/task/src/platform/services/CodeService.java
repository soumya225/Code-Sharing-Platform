package platform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.models.Code;
import platform.repositories.CodeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    private final CodeRepository codeRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Optional<Code> findById(int id) {
        return codeRepository.findById(id);
    }

    public void addNewCode(Code code){
        code.setDate(LocalDateTime.now().toString());

        codeRepository.save(code);
    }

    public List<Code> getNLatestCodes(int n) {
        Iterable<Code> codes = codeRepository.findAll();

        List<Code> latestCodes = new ArrayList<>();

        codes.forEach(latestCodes::add);

        latestCodes.sort(Collections.reverseOrder());

        int numLatestCodes = latestCodes.size();

        return latestCodes.subList(0, Math.min(n, numLatestCodes));
    }
}
