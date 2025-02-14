package com.intotheblack.itb_api.controller;

import com.intotheblack.itb_api.model.Collection;
import com.intotheblack.itb_api.service.CollectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
@Tag(name = "Collections", description = "Endpoints para gestionar los col·leccionables de los jugadores")
public class CollectionController {
    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping()
    public ResponseEntity<Collection> createCollection(@RequestBody Collection collection) {
        Collection createdCollection = collectionService.createCollection(collection);
        return new ResponseEntity<>(createdCollection, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable Long id) {
        Collection collection = collectionService.findCollectionById(id);
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionById(@PathVariable Long id) {
        collectionService.deleteCollectionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/fragments/{fragmentId}")
    public ResponseEntity<Collection> addFragmentToCollection(@PathVariable Long id, @PathVariable Integer fragmentId) {
        Collection updatedCollection = collectionService.addNewFragment(id, fragmentId);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/fragments/{fragmentId}")
    public ResponseEntity<Collection> deleteFragmentOfCollection(@PathVariable Long id, @PathVariable Integer fragmentId) {
        Collection updatedCollection = collectionService.deleteOldFragment(id, fragmentId);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/fragments")
    public ResponseEntity<Collection> cleanCollectionFragments(@PathVariable Long id) {
        Collection updatedCollection = collectionService.cleanFragments(id);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }
}
