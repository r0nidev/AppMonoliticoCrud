package com.nlorenzo.controller;


import org.apache.commons.lang3.StringUtils;
import com.nlorenzo.entity.Producto;
import com.nlorenzo.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // ya no
import org.springframework.http.ResponseEntity; // vamos a utilizarlo
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
//@RestController => API REST
import java.util.List;

@Controller // controller a secas
@RequestMapping("/producto") //@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    private final Logger log = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    ProductoService productoService;

    /**
     * GET : obtener todos los productos.
     *
     */
    @GetMapping("/lista")
    public ModelAndView lista(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName(("/producto/lista"));
        List<Producto> productos = productoService.listar();
        mv.addObject("productos", productos);
        return mv;
    }

    /**
     * CREATE : crear un nuevo producto.
     * New Product => is a view -> form
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("nuevo")
    public String nuevo(){
        return "producto/nuevo";
    }

    @PreAuthorize("hasRole('ADMIN')") // FOR security
    @PostMapping("/guardar")
    public ModelAndView crear(@RequestParam String nombre, @RequestParam float precio){
        ModelAndView mv = new ModelAndView();
        if(StringUtils.isBlank(nombre)){
            mv.setViewName("producto/nuevo");
            mv.addObject("error", "El nombre no puede estar vacío.");
            return mv;
        }
        if(precio < 1 ){
            mv.setViewName("producto/nuevo");
            mv.addObject("error", "El precio debe ser mayor que cero.");
            return mv;
        }
        if(productoService.existsByNombre(nombre)){
            mv.setViewName("producto/nuevo");
            mv.addObject("error", "Ese producto ya existe.");
            return mv;
        }
        Producto producto = new Producto(nombre, precio);
        productoService.save(producto);
        //mv.setViewName("/producto/lista");
        mv.setViewName("redirect:/producto/lista");
        return mv;
    }

    /**
     * UPDATE : get the id producto.
     *
     */
    @GetMapping("/detalle/{id}")
    public ModelAndView detalle(@PathVariable("id") Long id){
        if(!productoService.existsById(id))
            return new ModelAndView("redirect:/producto/lista");
        Producto producto = productoService.getOne(id).get();
        ModelAndView mv = new ModelAndView("/producto/detalle");
        mv.addObject("producto",producto);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        if(!productoService.existsById(id))
            return new ModelAndView("redirect:/producto/lista");
        Producto producto = productoService.getOne(id).get();
        ModelAndView mv = new ModelAndView("/producto/editar");
        mv.addObject("producto",producto);
        return mv;
    }

    /**
     * UPDATE
     *
     * Lo más correcto es @PutMapping
     *
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam Long id, @RequestParam String nombre, @RequestParam float precio){
        if(!productoService.existsById(id))
            return new ModelAndView("redirect:/producto/lista");
        ModelAndView mv = new ModelAndView();
        Producto producto = productoService.getOne(id).get();
        if(StringUtils.isBlank(nombre)){ // Validación -
            mv.setViewName("producto/editar");
            mv.addObject("producto", producto);
            mv.addObject("error", "El nombre no puede estar vacío.");
            return mv;
        }
        if(precio < 1 ){
            mv.setViewName("producto/editar");
            mv.addObject("error", "El precio debe ser mayor que cero.");
            mv.addObject("producto", producto);
            return mv;
        }
         if(productoService.existsByNombre(nombre) && productoService.getByNombre(nombre).get().getId() != id ){
             mv.setViewName("producto/editar");
             mv.addObject("error", "Ese producto ya existe.");
             mv.addObject("producto", producto);
             return mv;
         }

         producto.setNombre(nombre);
         producto.setPrecio(precio);
         productoService.save(producto);
        return new ModelAndView("redirect:/producto/lista");
    }


    // yet no funciona bien.
    /*@GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id") Long id){
        if(!productoService.existsById(id)){
            productoService.delete(id);
            return new ModelAndView("redirect:/producto/lista");
        }
        return null;
    }*/

    /**
     * DELETE
     * Lo más correcto es @Delete
     * */
    /*@DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        if(productoService.existsById(id)) {
            productoService.delete(id);
            log.info("Se ha eliminado un producto.");
            return ResponseEntity.ok(null);
            //return ResponseEntity.ok("redirect:/producto/lista");
          //  return "redirect:/producto/lista";
        }
        return null;
    }*/

    // Este si funciona! pero no es buena práctica hacerlo with @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable("id") Long id){
        if(productoService.existsById(id)) {
            log.info("ENTERED HERE ----");
            productoService.delete(id);
            return "redirect:/producto/lista";
        }
        return null;
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable Long id, Model model) {
        productoService.delete(id);
        return "redirect:/producto/lista";
        //return "redirect:/listar";
    }*/

    /*public ResponseEntity<List<Producto>> lista(){
        List<Producto> list = productoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }*/
}
