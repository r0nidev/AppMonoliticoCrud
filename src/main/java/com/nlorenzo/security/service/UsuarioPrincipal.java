package com.nlorenzo.security.service;

import com.nlorenzo.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UsuarioPrincipal implements UserDetails {

    private Long id;
    private String nombreUsuario;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // ponemos un constructor con todos los campos


    public UsuarioPrincipal(Long id, String nombreUsuario, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.authorities = authorities;
    }

    // create a method
    public static UsuarioPrincipal build(Usuario usuario){
        List<GrantedAuthority> authorities = // manejo de lambda
            usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
        return new UsuarioPrincipal(usuario.getId(), usuario.getNombreUsuario(), usuario.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // getter 
    public Long getId() {
        return id;
    }
}
