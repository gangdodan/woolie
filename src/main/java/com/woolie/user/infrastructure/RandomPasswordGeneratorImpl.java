package com.woolie.user.infrastructure;


import com.woolie.user.domain.RandomPasswordGenerator;
import org.springframework.stereotype.Component;

import static com.woolie.security.constants.SecurityConstants.TEMPORARY_PASSWORD_LENGTH;

@Component
public class RandomPasswordGeneratorImpl implements RandomPasswordGenerator {
    @Override
    public String generateTemporaryPassword() {
        return generateRandomKey(TEMPORARY_PASSWORD_LENGTH);
    }
}
