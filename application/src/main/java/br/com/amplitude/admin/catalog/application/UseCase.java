package br.com.amplitude.admin.catalog.application;

import br.com.amplitude.admin.catalog.domain.category.Category;

public class UseCase {

    public Category execute() {
        final var name = "Action";
        final var description = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var isActive = true;

        return Category.newCategory(name, description, isActive);
    }
}