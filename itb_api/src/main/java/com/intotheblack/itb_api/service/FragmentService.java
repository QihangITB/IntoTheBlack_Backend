package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.Fragment;
import com.intotheblack.itb_api.repository.FragmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FragmentService {
    private final FragmentRepository fragmentRepository;

    @Autowired
    public FragmentService(FragmentRepository fragmentRepository) {
        this.fragmentRepository = fragmentRepository;
    } 

    public Fragment findFragmentById(Long id){
        return this.fragmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fragmento no encontrado con id: " + id));
    }

    public List<Fragment> findAllFragmentsInOrder(){
        return this.fragmentRepository.findAllOrderByOrderNumber()
            .orElseThrow(() -> new RuntimeException("Lista de fragmentos no encontrado"));

    }

    public Fragment createFragment(Fragment fragment){
        return fragmentRepository.save(fragment);
    }

    public void deleteFragmentById(Long id) {
        if (!fragmentRepository.existsById(id)) {
            throw new RuntimeException("Fragmento no encontrado con ID: " + id);
        }
        fragmentRepository.deleteById(id);
    }
}
