package com.nlorenzo.controller;

import com.nlorenzo.entity.Rol;
import com.nlorenzo.entity.Usuario;
import com.nlorenzo.enums.RolNombre;
import com.nlorenzo.service.RolService;
import com.nlorenzo.service.UsuarioService;
import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    //private final static Logger log = Logger.getLogger(UsuarioController .class.getName());
    private final static Logger log = Logger.getLogger(UsuarioController.class.getName());
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String registro(){
        log.info("Entró en Registro ----------------------");
        return "registro";
    }

    @PostMapping("/registrar")
    public ModelAndView registrar(String nombreUsuario, String password){
        ModelAndView mv = new ModelAndView();
        log.info("Entró REGISTRAR ----------------------");
        if(StringUtils.isBlank(nombreUsuario)){
            mv.setViewName("/registro");
            mv.addObject("error", "el nombre no puede estar vacía.");
            return mv;
        }
        if(StringUtils.isBlank(password)){
            mv.setViewName("/registro");
            mv.addObject("error", "la contraseña no puede estar vacía.");
            return mv;
        }
        if(usuarioService.existsByNombreUsuario(nombreUsuario)){
            mv.setViewName("/registro");
            mv.addObject("error", "ese nombre de usuario ya existe.");
            return mv;
        }
        // create a new user
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(passwordEncoder.encode(password)); // password cifrada.
        Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser);
        usuario.setRoles(roles);
        // por defecto NO va ser un user admin
        usuarioService.save(usuario);
        mv.setViewName("/login");
        mv.addObject("registroOK", "Cuenta creada, "+usuario.getNombreUsuario() + ", ya puedes iniciar sesión");
        return mv;
    }
}
