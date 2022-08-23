package platform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.models.Code;
import platform.repositories.CodeRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CodeService {

    private final CodeRepository codeRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }


    private void decreaseViews(Code code) {
        if(code.hasViewsRestriction()) {
            code.setViews(code.getViews() - 1);

            if(code.getViews() < 0) {
                codeRepository.delete(code);
            } else {
                codeRepository.save(code);
            }
        }


    }

    private void decreaseSeconds(Code code) {
        if(code.hasTimeRestriction()) {
            Duration durationSinceCreated = Duration.between(LocalDateTime.parse(code.getDate()), LocalDateTime.now());
            code.setTime((int) (code.getTime() - durationSinceCreated.getSeconds()));

            if(code.getTime() <= 0) {
                codeRepository.delete(code);
            } else {
                codeRepository.save(code);
            }
        }
    }

    public Optional<Code> findByUUIDIfUnrestricted(String id) {
        Optional<Code> codeOptional = codeRepository.findById(id);

        if(codeOptional.isEmpty()) {
            return codeOptional;
        }

        Code code = codeOptional.get();

        decreaseViews(code);
        decreaseSeconds(code);

        codeOptional = codeRepository.findById(id);

        return codeOptional;
    }

    public void addNewCode(Code code){
        if(code.getTime() != null && code.getTime() > 0) {
            code.setHasTimeRestriction(true);
        }

        if(code.getViews() != null && code.getViews() > 0) {
            code.setHasViewsRestriction(true);
        }

        codeRepository.save(code);
    }

    public List<Code> getNLatestUnrestrictedCodes(int n) {
        Iterable<Code> codes = codeRepository.findAll();

        List<Code> latestCodes = new ArrayList<>();

        codes.forEach(code -> {
            if(!code.hasTimeRestriction() && !code.hasViewsRestriction()) {
                latestCodes.add(code);
            }
        });

        latestCodes.sort(Collections.reverseOrder());

        int numLatestCodes = latestCodes.size();

        return latestCodes.subList(0, Math.min(n, numLatestCodes));
    }
}
