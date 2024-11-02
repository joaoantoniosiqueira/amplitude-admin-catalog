package br.com.amplitude.admin.catalog.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCaseTest {

    @Test
    public void testCreateUseCase() {
        var aUseCase = new UseCase();
        Assertions.assertNotNull(aUseCase);
        Assertions.assertNotNull(aUseCase.execute());
    }
}
