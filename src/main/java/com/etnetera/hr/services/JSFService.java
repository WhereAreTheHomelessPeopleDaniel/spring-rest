package com.etnetera.hr.services;

import com.etnetera.hr.data.JavaScriptFramework;

import java.util.List;

public interface JSFService {

    List<JavaScriptFramework> findByName(String name);
}
