package com.chirsapis.springbootapires.controllers;

import com.chirsapis.springbootapires.models.entity.Cliente;
import com.chirsapis.springbootapires.models.services.IClientesService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController
{
    @Autowired
    private IClientesService clientesService;

    @GetMapping("/clientes")
    public List<Cliente> index()
    {
        return clientesService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id)
    {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        //manejo de errores
        try
        {
            cliente = clientesService.findById(id);
        } catch (DataAccessException e)
        {
            response.put("mensaje", "error al realizar la consula en la BBDD");
            response.put("error", e.getMessage() + ": " + e.getCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cliente == null)
        {
            response.put("mensaje", "Cliente Inexistente");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    /*@Valid es un campo necesario para impletar la validacion de la clas entity
     * BindingResult result captura el resultado de la validacion
     * */
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result)
    {
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors())
        {
            /*
            forma anterior al JDK 8
                List<String> errors = new ArrayList<>();
                for (FieldError err: result.getFieldErrors())
                {
                    errors.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
                }
            */
            // JDK superior a 8
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try
        {
            clienteNew = clientesService.save(cliente);
        } catch (DataAccessException e)
        {
            response.put("mensaje", "Error al crear un nuevo cliente");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Cliente creado con exito!");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    /*
       importante!!!!!!! BindingResult result debe ir antes del path variable
       y despues del cliente en este caso
    */
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id)
    {
        Cliente clienteActual = clientesService.findById(id);

        Cliente clienteUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors())
        {
            // JDK superior a 8
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (clienteActual == null)
        {
            response.put("mensaje", "No se puede editar este cliente, no existe en la BBDD");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try
        {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());

            clienteUpdated = clientesService.save(clienteActual);

        } catch (DataAccessException e)
        {
            response.put("mensaje", "Error al actualizar el cliente en la BBDD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        response.put("mensaje", "Cliente actualizado con exito!");
        response.put("cliente", clienteUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        Map<String, Object> response = new HashMap<>();
        try
        {
            clientesService.delete(id);
        } catch (DataAccessException e)
        {
            response.put("mensaje", "Error al eliminar el cliente en la BBDD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Cliente eliminado con exito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
