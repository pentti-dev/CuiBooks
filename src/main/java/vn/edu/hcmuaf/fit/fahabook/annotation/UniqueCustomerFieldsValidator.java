package vn.edu.hcmuaf.fit.fahabook.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CustomerRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class UniqueCustomerFieldsValidator implements ConstraintValidator<UniqueCustomerField, CustomerRequestDTO> {
    private final CustomerRepository customerRepository;

    @Override
    public void initialize(UniqueCustomerField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CustomerRequestDTO dto, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (customerRepository.existsByUsername(dto.getUsername())) {
            isValid = false;
            addViolation(context, "username", "USERNAME_EXISTED");
        }
        if (customerRepository.existsByEmail(dto.getEmail())) {
            isValid = false;
            addViolation(context, "email", "EMAIL_EXISTED");
        }

        if (customerRepository.existsByPhone(dto.getPhone())) {
            isValid = false;
            addViolation(context, "phone", "PHONE_EXISTED");
        }

        return isValid;
    }

    private void addViolation(ConstraintValidatorContext context, String field, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}
