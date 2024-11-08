package br.com.amplitude.admin.catalog.application.category.update;

import br.com.amplitude.admin.catalog.domain.category.Category;
import br.com.amplitude.admin.catalog.domain.category.CategoryGateway;
import br.com.amplitude.admin.catalog.domain.category.CategoryID;
import br.com.amplitude.admin.catalog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallUpdateCategory_thenShouldReturnCategoryId() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Ation", null, true);

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
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

    @Test
    public void givenAnInvalidName_whenCallUpdateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Ation", null, true);

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertNotNull(notification);
        Assertions.assertNotNull(notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallUpdateCategory_thenShouldReturnInactivatedCategoryId() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory("Action", null, true);

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
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
                        Objects.nonNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnAnException() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Ation", null, true);

        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "A error from gateway";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId().getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(1)).findById(Mockito.eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(aUpdatedCategory ->
                Objects.equals(expectedName, aUpdatedCategory.getName()) &&
                        Objects.equals(expectedId, aUpdatedCategory.getId()) &&
                        Objects.equals(expectedDescription, aUpdatedCategory.getDescription()) &&
                        Objects.equals(expectedIsActive, aUpdatedCategory.isActive()) &&
                        Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt()) &&
                        aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt()) &&
                        Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidId_whenCallsUpdateCategory_thenShouldReturnNotFoundException() {
        final var expectedName = "Action";
        final var expectedDescription = "Dive into a journey full of adrenaline and adventure with our Action Movies category";
        final var expectedIsActive = true;
        final var expectedId = "123";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Category with ID 123 was not found";

        final var aCommand = new UpdateCategoryCommand(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(CategoryID.from(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(Mockito.eq(CategoryID.from(expectedId)));
        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());
    }
}
