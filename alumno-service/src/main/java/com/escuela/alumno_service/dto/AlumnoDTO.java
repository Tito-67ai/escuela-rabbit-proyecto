package com.escuela.alumno_service.dto;

public class AlumnoDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private Integer cursoId;

    public AlumnoDTO() {}

    public AlumnoDTO(Integer id, String nombre, String apellido, String dni, Integer cursoId) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.cursoId = cursoId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Integer getCursoId() { return cursoId; }
    public void setCursoId(Integer cursoId) { this.cursoId = cursoId; }
}