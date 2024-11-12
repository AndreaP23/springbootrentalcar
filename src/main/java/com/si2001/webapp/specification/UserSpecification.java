package com.si2001.webapp.specification;

import com.si2001.webapp.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasEmail(String email) {
        return(root, query, criteriaBuilder) -> {
            if (email==null || email.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("email"), email);
        };
    }

    public static Specification<User> hasName(String name) {
        return (root,query, criteriaBuilder) -> {
            if( name == null || name.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("name"),name );
        };
    }
}


