package com.si2001.webapp.controller;

import com.si2001.webapp.entities.User;
import com.si2001.webapp.service.UserService;
import com.si2001.webapp.specification.UserSpecification;
import org.openapitools.api.SuperUserApi;
import org.openapitools.model.GetUsersWithPagination200Response;
import org.openapitools.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SuperUserController implements SuperUserApi {

    private final UserService userService;

    @Autowired
    public SuperUserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> authSuperuserDeleteIdDelete(@PathVariable("id") Integer id) {
        userService.deleteUser(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }


    /*@Override
    public ResponseEntity<List<UserDTO>> authSuperuserListuserGet() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }*/

    @Override
    public ResponseEntity<GetUsersWithPagination200Response> getUsersWithPagination(Integer page, Integer size){
        if(page == null)  page = 0;
        if(size == null)  size = 10;

        Pageable pageable = PageRequest.of(page, size);

        Page<UserDTO> userPage = userService.getAllUsers(pageable);

        GetUsersWithPagination200Response response = new GetUsersWithPagination200Response();
        response.setContent(userPage.getContent());
        response.setTotalElements(userPage.getNumberOfElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setSize(userPage.getSize());
        response.setNumber(response.getTotalElements());
        return ResponseEntity.ok(response);
    }

}
