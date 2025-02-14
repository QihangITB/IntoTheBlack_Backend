package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.Collection;
import com.intotheblack.itb_api.repository.CollectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    } 
    
    public Collection findCollectionById(Long id){
        return this.collectionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Col·leccion no encontrado con id: " + id));
    }

    public Collection createCollection(Collection collection){
        return collectionRepository.save(collection);
    }

    public void deleteCollectionById(Long id) {
        if (!collectionRepository.existsById(id)) {
            throw new RuntimeException("Col·leccion no encontrado con ID: " + id);
        }
        collectionRepository.deleteById(id);
    }

    public Collection addNewFragmentById(Long collectionId, Integer fragmentId){
        Collection collection = findCollectionById(collectionId);

        List<Integer> fragmentList = collection.getFragmentList();
        if (fragmentList == null) {
            fragmentList = new ArrayList<>(); // Si es null, inicializamos la lista.
        }

        fragmentList.add(fragmentId);
        collection.setFragmentList(fragmentList);

        return collectionRepository.save(collection);
    }

    public Collection deleteOldFragmentById(Long collectionId, Integer fragmentId){
        Collection collection = findCollectionById(collectionId);

        List<Integer> fragmentList = collection.getFragmentList();

        // Verificamos si el fragmento existe en la lista
        if (fragmentList == null || !fragmentList.contains(fragmentId)) {
            throw new RuntimeException("Fragmento no encontrado en la colección");
        }

        fragmentList.remove(fragmentId);
        collection.setFragmentList(fragmentList);

        return collectionRepository.save(collection);
    }

    public Collection cleanFragmentsById(Long collectionId){
        Collection collection = findCollectionById(collectionId);
        collection.getFragmentList().clear(); // Reinicia la lista de fragmentos

        return collectionRepository.save(collection);
    }
}
