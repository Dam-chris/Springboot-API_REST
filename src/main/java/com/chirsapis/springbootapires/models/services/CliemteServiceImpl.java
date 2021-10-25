package com.chirsapis.springbootapires.models.services;

import com.chirsapis.springbootapires.models.dao.IClientesDao;
import com.chirsapis.springbootapires.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CliemteServiceImpl implements IClientesService
{
    @Autowired
    private IClientesDao clientesDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll()
    {
        return (List<Cliente>) clientesDao.findAll();
    }

    @Override
    public Cliente findById(Long id)
    {
        return clientesDao.findById(id).orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente)
    {
        return clientesDao.save(cliente);
    }

    @Override
    public void delete(Long id)
    {
        clientesDao.deleteById(id);
    }
}
