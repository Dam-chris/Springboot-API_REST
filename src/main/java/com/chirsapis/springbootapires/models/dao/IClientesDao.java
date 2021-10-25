package com.chirsapis.springbootapires.models.dao;

import com.chirsapis.springbootapires.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClientesDao extends CrudRepository<Cliente, Long>
{

}
