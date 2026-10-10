import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html'
})
export class AlumnosComponent implements OnInit {

  alumnos: any[] = [];
  cursos: any[] = [];

  idSeleccionado: number | null = null; 
  nombre: string = '';
  apellido: string = '';
  dni: string = '';
  cursoId: number | null = null; 

  modoEdicion: boolean = false;
  mensajeExito: string = '';
  mensajeError: string = '';

  private apiUrlAlumnos = 'http://localhost:8080/alumnos';
  private apiUrlCursos = 'http://localhost:8080/cursos';

  constructor(private http: HttpClient, private apiService: ApiService) {}

  ngOnInit(): void {
    this.cargarAlumnos();
    this.cargarCursos();
  }

  get puedeGestionar(): boolean {
    return localStorage.getItem('user_rol') === 'PRECEPTOR';
  }

  private getHeaders() {
    const token = localStorage.getItem('jwt_token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  cargarAlumnos() {
    this.http.get<any[]>(this.apiUrlAlumnos, { headers: this.getHeaders() }).subscribe({
      next: (data) => this.alumnos = data,
      error: (err) => {
        console.error('Error al cargar alumnos', err);
        this.mensajeError = this.apiService.mensajeError(err);
      }
    });
  }

  cargarCursos() {
    this.http.get<any[]>(this.apiUrlCursos, { headers: this.getHeaders() }).subscribe({
      next: (data) => this.cursos = data,
      error: (err) => {
        console.error('Error al cargar cursos', err);
        this.mensajeError = this.apiService.mensajeError(err);
      }
    });
  }

  nombreCurso(cursoId: number | null): string {
    const curso = this.cursos.find((c) => c.id === cursoId);
    return curso ? curso.nombre : 'Sin curso';
  }

  guardarAlumno() {
    const alumnoDTO = {
      nombre: this.nombre,
      apellido: this.apellido,
      dni: this.dni,
      cursoId: this.cursoId
    };

    this.mensajeExito = '';
    this.mensajeError = '';

    if (this.modoEdicion && this.idSeleccionado !== null) {
      this.http.put(`${this.apiUrlAlumnos}/${this.idSeleccionado}`, alumnoDTO, { headers: this.getHeaders() }).subscribe({
        next: () => {
          this.mensajeExito = 'Alumno actualizado correctamente.';
          this.cargarAlumnos();
          this.limpiarFormulario();
        },
        error: (err) => {
          console.error('Error al actualizar alumno', err);
          this.mensajeError = this.apiService.mensajeError(err);
        }
      });
    } else {
      this.http.post(this.apiUrlAlumnos, alumnoDTO, { headers: this.getHeaders() }).subscribe({
        next: () => {
          this.mensajeExito = 'Alumno registrado correctamente.';
          this.cargarAlumnos();
          this.limpiarFormulario();
        },
        error: (err) => {
          console.error('Error al crear alumno', err);
          this.mensajeError = this.apiService.mensajeError(err);
        }
      });
    }
  }

  seleccionarParaEditar(alumno: any) {
    this.modoEdicion = true;
    this.idSeleccionado = alumno.id;
    this.nombre = alumno.nombre;
    this.apellido = alumno.apellido;
    this.dni = alumno.dni;
    this.cursoId = alumno.cursoId;
  }

  eliminarAlumno(id: number) {
    if (confirm('¿Dar de baja al alumno?')) {
      this.mensajeExito = '';
      this.mensajeError = '';
      this.http.delete(`${this.apiUrlAlumnos}/${id}`, { headers: this.getHeaders() }).subscribe({
        next: () => {
          this.mensajeExito = 'Alumno dado de baja.';
          this.cargarAlumnos();
        },
        error: (err) => {
          console.error('Error al dar de baja alumno', err);
          this.mensajeError = this.apiService.mensajeError(err);
        }
      });
    }
  }

  activarAlumno(id: number) {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.http.put(`${this.apiUrlAlumnos}/${id}/activar`, {}, { headers: this.getHeaders() }).subscribe({
      next: () => {
        this.mensajeExito = 'Alumno activado correctamente.';
        this.cargarAlumnos();
      },
      error: (err) => {
        console.error('Error al activar alumno', err);
        this.mensajeError = this.apiService.mensajeError(err);
      }
    });
  }

  limpiarFormulario() {
    this.modoEdicion = false;
    this.idSeleccionado = null;
    this.nombre = '';
    this.apellido = '';
    this.dni = '';
    this.cursoId = null;
  }
}
