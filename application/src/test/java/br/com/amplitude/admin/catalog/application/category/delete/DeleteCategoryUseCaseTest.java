package br.com.amplitude.admin.catalog.application.category.delete;

import br.com.amplitude.admin.catalog.domain.category.Category;
import br.com.amplitude.admin.catalog.domain.category.CategoryGateway;
import br.com.amplitude.admin.catalog.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset();
    }

    @Test
    public void givenAValidId_whenCallDeleteCategory_thenShouldBeOk() {
        final var aCategory = Category.newCategory(
                "Action",
                "Dive into a journey full of adrenaline and adventure with our Action Movies category",
                true);
        final var expectedId = aCategory.getId();

        Mockito.doNothing().when(categoryGateway).deleteById(expectedId);

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(Mockito.eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallDeleteCategory_thenShouldBeOk() {
        final var expectedId = CategoryID.from("123");

        Mockito.doNothing().when(categoryGateway).deleteById(expectedId);

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(Mockito.eq(expectedId));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnException() {
        final var aCategory = Category.newCategory(
                "Action",
                "Dive into a journey full of adrenaline and adventure with our Action Movies category",
                true);
        final var expectedId = aCategory.getId();

        Mockito.doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(Mockito.eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(Mockito.eq(expectedId));
    }
}
