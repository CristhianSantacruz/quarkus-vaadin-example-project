package org.acme;

import jakarta.enterprise.context.Dependent;

@Dependent
public class SaludoService {

    public String saludar(String name){
        return "Hola como estas " + name;
    }
}
