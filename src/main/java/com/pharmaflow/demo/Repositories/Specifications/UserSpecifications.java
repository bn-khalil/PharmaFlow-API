package com.pharmaflow.demo.Repositories.Specifications;

import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Enums.Role;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasFirstName(String firsName) {
        if (firsName == null || firsName.trim().isEmpty())
            return null;

        String SearchKeyWord = "%" + firsName.toLowerCase().trim() + "%";

        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), SearchKeyWord)
        );
    }

    public static Specification<User> hasLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty())
            return null;

        String SearchKeyWord = "%" + lastName.toLowerCase().trim() + "%";

        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), SearchKeyWord)
        );
    }

    public static Specification<User> hasRole(String search) {
        if (search == null)
            return null;

        Role SearchKeyWord = Role.valueOf(search.toUpperCase().trim());

        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("role"), SearchKeyWord)
        );
    }
}
