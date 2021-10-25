package com.chirsapis.springbootapires.models.services;

import com.chirsapis.springbootapires.models.entity.Cliente;

import java.util.List;

public interface IClientesService
{

    public List<Cliente>findAll();

    public Cliente findById(Long id);

    public Cliente save(Cliente cliente);

    public void delete(Long id);
}
