package br.com.amplitude.admin.catalog.application.category.create;

import br.com.amplitude.admin.catalog.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {
    public static CreateCategoryOutput from(final CategoryID anId) {
        return new CreateCategoryOutput(anId);
    }
}
