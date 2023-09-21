package com.woolie.user.domain;


import com.woolie.common.RandomKeyGenerator;

public interface RandomPasswordGenerator extends RandomKeyGenerator {
    String generateTemporaryPassword();
}
