package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.dto.MessageRequestDTO;
import com.intotheblack.itb_api.dto.FragmentRequestDTO;
import com.intotheblack.itb_api.repository.FragmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FragmentService {
    
    private final FragmentRepository fragmentRepository;

    public FragmentService(FragmentRepository fragmentRepository) {
        this.fragmentRepository = fragmentRepository;
    } 

    // METHODS:
    public Fragment findFragmentById(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        return this.fragmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fragment not found with id: " + id));
    }

    public List<Fragment> findAllFragmentsInOrder(){
        return this.fragmentRepository.findAllOrderByOrderNumber()
            .orElseThrow(() -> new RuntimeException("Fragment list is empty"));
    }

    public Fragment createFragment(FragmentRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (request.getOrderNumber() == null) {
            throw new IllegalArgumentException("Order number is required");
        }
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Message is required");
        }
    
        Fragment fragment = new Fragment();
        fragment.setOrderNumber(request.getOrderNumber());
        fragment.setMessage(request.getMessage());
    
        return fragmentRepository.save(fragment);
    }

    public Fragment changeMessageById(Integer id, MessageRequestDTO request) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Message is required");
        }
    
        Optional<Fragment> fragmentOptional = fragmentRepository.findById(id);
    
        if (fragmentOptional.isPresent()) {
            Fragment fragment = fragmentOptional.get();
            fragment.setMessage(request.getMessage());
            return fragmentRepository.save(fragment);
        }
        throw new IllegalArgumentException("Fragment not found with id: " + id);
    }

    public boolean deleteFragmentById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
    
        Optional<Fragment> fragmentOptional = fragmentRepository.findById(id);
    
        if (fragmentOptional.isPresent()) {
            fragmentRepository.deleteById(id);
            return true; // Eliminado con éxito
        }
        return false; // Fragmento no encontrado
    }
}
