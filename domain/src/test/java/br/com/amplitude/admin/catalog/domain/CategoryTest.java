package br.com.amplitude.admin.catalog.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void testNewCategory() {
        var aCategory = new Category();
        Assertions.assertNotNull(aCategory);
    }
}
