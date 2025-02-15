package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.dto.FragmentDTO;
import com.intotheblack.itb_api.dto.MessageRequestDTO;
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
        return this.fragmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fragmento no encontrado con id: " + id));
    }

    public List<Fragment> findAllFragmentsInOrder(){
        return this.fragmentRepository.findAllOrderByOrderNumber()
            .orElseThrow(() -> new RuntimeException("Lista de fragmentos no encontrado"));
    }

    public Fragment createFragment(FragmentDTO fragment){
        Fragment newFragment = new Fragment();
        newFragment.setOrderNumber(fragment.getOrderNumber());
        newFragment.setMessage(fragment.getMessage());

        return fragmentRepository.save(newFragment);
    }

    public boolean deleteFragmentById(Integer id){
        Optional<Fragment> fragmentOptional = fragmentRepository.findById(id);

        if(fragmentOptional.isPresent()){
            Fragment fragment = fragmentOptional.get();
            fragmentRepository.deleteById(fragment.getId());
            return true; // Eliminado con éxito
        }
        return false; // Fragmento no encontrado
    }

    public Fragment changeMessageById(Integer id, MessageRequestDTO request){
        Optional<Fragment> fragmentOptional = fragmentRepository.findById(id);

        if(fragmentOptional.isPresent()){
            Fragment fragment = fragmentOptional.get();
            fragment.setMessage(request.getMessage());
            return fragmentRepository.save(fragment);
        }
        throw new IllegalArgumentException("Fragmento no encontrado con id: " + id);
    }
}
