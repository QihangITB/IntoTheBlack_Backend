package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.dto.MessageRequestDTO;
import com.intotheblack.itb_api.dto.FragmentRequestDTO;
import com.intotheblack.itb_api.repository.FragmentRepository;
import com.intotheblack.itb_api.util.GlobalMessage;
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
            throw new IllegalArgumentException(GlobalMessage.ID_REQUIRED);
        }
        if (id < 0) {
            throw new IllegalArgumentException(GlobalMessage.ID_NEGATIVE);
        }
        return this.fragmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(GlobalMessage.FRAGMENT_NOT_FOUND + id));
    }

    public List<Fragment> findAllFragmentsInOrder(){
        return this.fragmentRepository.findAllOrderByOrderNumber()
            .orElseThrow(() -> new RuntimeException(GlobalMessage.FRAGMENT_LIST_EMPTY));
    }

    public Fragment createFragment(FragmentRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException(GlobalMessage.REQUEST_NULL);
        }
        if (request.getOrderNumber() == null) {
            throw new IllegalArgumentException(GlobalMessage.ORDER_NUMBER_REQUIRED);
        }
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            throw new IllegalArgumentException(GlobalMessage.MESSAGE_REQUIRED);
        }
    
        Fragment fragment = new Fragment();
        fragment.setOrderNumber(request.getOrderNumber());
        fragment.setMessage(request.getMessage());
    
        return fragmentRepository.save(fragment);
    }

    public Fragment changeMessageById(Integer id, MessageRequestDTO request) {
        if (id == null) {
            throw new IllegalArgumentException(GlobalMessage.ID_REQUIRED);
        }
        if (id < 0) {
            throw new IllegalArgumentException(GlobalMessage.ID_NEGATIVE);
        }
        if (request == null) {
            throw new IllegalArgumentException(GlobalMessage.REQUEST_NULL);
        }
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            throw new IllegalArgumentException(GlobalMessage.MESSAGE_REQUIRED);
        }
    
        Optional<Fragment> fragmentOptional = fragmentRepository.findById(id);
    
        if (fragmentOptional.isPresent()) {
            Fragment fragment = fragmentOptional.get();
            fragment.setMessage(request.getMessage());
            return fragmentRepository.save(fragment);
        }
        throw new IllegalArgumentException(GlobalMessage.FRAGMENT_NOT_FOUND + id);
    }

    public boolean deleteFragmentById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException(GlobalMessage.ID_REQUIRED);
        }
        if (id < 0) {
            throw new IllegalArgumentException(GlobalMessage.ID_NEGATIVE);
        }
    
        Optional<Fragment> fragmentOptional = fragmentRepository.findById(id);
    
        if (fragmentOptional.isPresent()) {
            fragmentRepository.deleteById(id);
            return true; // Eliminado con éxito
        }
        return false; // Fragmento no encontrado
    }
}