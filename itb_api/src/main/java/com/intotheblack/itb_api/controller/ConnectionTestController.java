package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.service.ConnectionTestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Connection-test")
public class ConnectionTestController {
    @Autowired
    private ConnectionTestService conectionTestService;

    @Operation(summary = "Comprobar la conexión a la base de datos")
    @GetMapping("/db-connection")
    public ResponseEntity<String> checkDatabaseConnection() {
        boolean isDbConnected = conectionTestService.isDatabaseConnected();

        if (isDbConnected) {
            return new ResponseEntity<>("Database connection is successful.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to connect to the database.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
