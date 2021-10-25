package com.chirsapis.springbootapires.models.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        control de errores en la insercion a la bbdd.
        validaciones en los campos
        importar notempty de spring framework no de javax
    */
    @NotEmpty(message = "no puede estar vacio!!")
    @Size(min = 4, max = 15, message = "el tamaño debe estar entre 4 y 12 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "no puede estar vacio!!")
    private String apellido;

    @NotEmpty(message = "no puede estar vacio!!")
    @Email(message = "no es una direccion de correo bien formada")
    @Column(nullable = false, unique = true)
    private String email;

    /*
     en caso de que la variable se llame de forma de diferente a la columna de la BBDD
     O PARA ESPECIFICAR EL TIPO DE CAMPO QUE ES YA SEA NULLABLE UNIQUE ETC
     (no siempre es necesario pero si recomendable sobre todo en caso de las fechas)
  */
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date create_at;

    @PrePersist
    public void prePersist()
    {
        create_at = new Date();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido(String apellido)
    {
        this.apellido = apellido;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getCreateAt()
    {
        return create_at;
    }

    public void setCreateAt(Date createAt)
    {
        this.create_at = createAt;
    }
}
