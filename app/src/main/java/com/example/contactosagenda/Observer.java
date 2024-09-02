package com.example.contactosagenda;

public interface Observer {
    void llamar(ModelContact modelContact);
    void ver_imagen(ModelContact modelContact);
    void editar_contacto(String id, String nombre, String telefono, String email, String dir, String notas, String foto);
}
