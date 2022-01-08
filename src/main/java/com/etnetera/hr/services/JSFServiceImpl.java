package com.etnetera.hr.services;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JSFServiceImpl implements JSFService {

    private final JavaScriptFrameworkRepository repository;

    public JSFServiceImpl(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<JavaScriptFramework> findByName(String name) {
        Iterable<JavaScriptFramework> jsfs = repository.findAll();
        List<JavaScriptFramework> filtered = new ArrayList<>();
        for(JavaScriptFramework jsf : jsfs){
            if(name.toLowerCase().equals(jsf.getName().toLowerCase())){
                filtered.add(jsf);
            }
        }
        return filtered;
    }
}
