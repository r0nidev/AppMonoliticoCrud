package com.nlorenzo.service;

import com.nlorenzo.entity.Producto;
import com.nlorenzo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public List<Producto> listar(){
        return productoRepository.findAll();
    }

    public Optional<Producto> getOne(Long id){
        return productoRepository.findById(id);
    }

    public Optional<Producto> getByNombre(String nombre){
        return  productoRepository.findByNombre(nombre);
    }

    public void save(Producto producto){
        productoRepository.save(producto);
    }

    public void delete(Long id){
        productoRepository.deleteById(id);
    }

    public boolean existsById(Long id){
        return productoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return productoRepository.existsByNombre(nombre);
    }
}
