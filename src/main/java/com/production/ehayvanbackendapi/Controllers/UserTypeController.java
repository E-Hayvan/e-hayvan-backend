package com.production.ehayvanbackendapi.Controllers;

import com.production.ehayvanbackendapi.DTO.UserTypeDTO;
import com.production.ehayvanbackendapi.Services.UserTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usertypes")
public class UserTypeController {

    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeDTO> getUserTypeById(@PathVariable Integer id) {
        UserTypeDTO userTypeDTO = userTypeService.getUserTypeById(id);

        if (userTypeDTO != null) {
            return new ResponseEntity<>(userTypeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserTypeDTO>> getAllUserTypes() {
        List<UserTypeDTO> response = userTypeService.getAllUserTypes();

        if (!response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //no other crud operation is necessary

}

