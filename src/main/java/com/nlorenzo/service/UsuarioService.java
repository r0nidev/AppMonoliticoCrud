package com.nlorenzo.service;

import com.nlorenzo.entity.Usuario;
import com.nlorenzo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> lista(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getById(Long id){
        return usuarioRepository.findById(id);
    }
// aperez@ramcestax.com.mx
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public void save(Usuario usuario){
         usuarioRepository.save(usuario);
    }

    public boolean existsById(Long id){
        return usuarioRepository.existsById(id);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
}
