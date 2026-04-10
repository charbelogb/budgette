package com.budgette.backend;

import org.junit.jupiter.api.Test;

class BudgetteBackendApplicationTest {

    @Test
    void contextLoads() {
        // Validates that the domain classes instantiate correctly without Spring context
    }

    @Test
    void domainModelsAreFrameworkFree() throws Exception {
        // Verify domain classes have no Spring/JPA annotations
        Class<?> userClass = Class.forName("com.budgette.backend.domain.model.User");
        Class<?> accountClass = Class.forName("com.budgette.backend.domain.model.Account");
        Class<?> transactionClass = Class.forName("com.budgette.backend.domain.model.Transaction");

        for (Class<?> domainClass : new Class<?>[]{ userClass, accountClass, transactionClass }) {
            java.lang.annotation.Annotation[] annotations = domainClass.getAnnotations();
            for (java.lang.annotation.Annotation annotation : annotations) {
                String annotationName = annotation.annotationType().getName();
                assert !annotationName.startsWith("jakarta.persistence") :
                        "Domain class " + domainClass.getSimpleName() + " must not have JPA annotation: " + annotationName;
                assert !annotationName.startsWith("org.springframework") :
                        "Domain class " + domainClass.getSimpleName() + " must not have Spring annotation: " + annotationName;
            }
        }
    }
}
