package br.com.amplitude.admin.catalog.application.category.update;

import br.com.amplitude.admin.catalog.domain.category.Category;
import br.com.amplitude.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallUpdateCategory_thenShouldReturnCategoryId() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Ation", null, true);

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName, expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(aUpdatedCategory ->
                Objects.equals(expectedName, aUpdatedCategory.getName()) &&
                        Objects.equals(expectedId, aUpdatedCategory.getId()) &&
                        Objects.equals(expectedDescription, aUpdatedCategory.getDescription()) &&
                        Objects.equals(expectedIsActive, aUpdatedCategory.isActive()) &&
                        Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt()) &&
                        aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt()) &&
                        Objects.isNull(aCategory.getDeletedAt())
        ));
    }
}
