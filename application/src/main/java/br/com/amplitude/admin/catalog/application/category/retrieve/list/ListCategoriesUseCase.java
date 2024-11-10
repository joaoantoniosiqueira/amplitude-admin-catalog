package br.com.amplitude.admin.catalog.application.category.retrieve.list;

import br.com.amplitude.admin.catalog.application.UseCase;
import br.com.amplitude.admin.catalog.domain.category.CategorySearchQuery;
import br.com.amplitude.admin.catalog.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
