package com.production.ehayvanbackendapi.EntityTests;

import static org.assertj.core.api.Assertions.assertThat;

import com.production.ehayvanbackendapi.Entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserTypeTest {

    @Test
    void createEntity() {
        UserType test_user_type = new UserType();
        test_user_type.setType("ucakci");
        test_user_type.setUsers(List.of(new Customer(), new Customer()));
        test_user_type.setUserTypeID(10);

        assertThat(test_user_type.getType()).isEqualTo("ucakci");
        assertThat(test_user_type.getUsers().size()).isEqualTo(2);
        assertThat(test_user_type.getUserTypeID()).isEqualTo(10);
    }

}