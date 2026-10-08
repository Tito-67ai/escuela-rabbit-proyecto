import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html'
})
export class CursosComponent implements OnInit {

  cursos: any[] = [];

  idSeleccionado: number | null = null; 
  nombre: string = '';
  docenteId: number | null = null; 
  materia: string = '';
  horario: string = '';
  cupo: number | null = null; 

  modoEdicion: boolean = false;

  private apiUrl = 'http://localhost:8080/cursos';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarCursos();
  }

  get puedeGestionar(): boolean {
    const rol = localStorage.getItem('user_rol');
    return rol === 'ROOT' || rol === 'PRECEPTOR';
  }

  private getHeaders() {
    const token = localStorage.getItem('jwt_token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  cargarCursos() {
    this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() }).subscribe({
      next: (data) => this.cursos = data,
      error: (err) => console.error('Error al cargar cursos', err)
    });
  }

  guardarCurso() {
    const cursoDTO = {
      nombre: this.nombre,
      docenteId: this.docenteId,
      materia: this.materia,
      horario: this.horario,
      cupo: this.cupo
    };

    if (this.modoEdicion && this.idSeleccionado !== null) {
      this.http.put(`${this.apiUrl}/${this.idSeleccionado}`, cursoDTO, { headers: this.getHeaders() }).subscribe({
        next: () => {
          this.cargarCursos();
          this.limpiarFormulario();
        },
        error: (err) => console.error('Error al actualizar curso', err)
      });
    } else {
      this.http.post(this.apiUrl, cursoDTO, { headers: this.getHeaders() }).subscribe({
        next: () => {
          this.cargarCursos();
          this.limpiarFormulario();
        },
        error: (err) => console.error('Error al crear curso', err)
      });
    }
  }

  seleccionarParaEditar(curso: any) {
    this.modoEdicion = true;
    this.idSeleccionado = curso.id;
    this.nombre = curso.nombre;
    this.docenteId = curso.docenteId;
    this.materia = curso.materia;
    this.horario = curso.horario;
    this.cupo = curso.cupo;
  }

  eliminarCurso(id: number) {
    if (confirm('¿Eliminar curso?')) {
      this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() }).subscribe({
        next: () => this.cargarCursos(),
        error: (err) => console.error('Error al eliminar curso', err)
      });
    }
  }

  limpiarFormulario() {
    this.modoEdicion = false;
    this.idSeleccionado = null;
    this.nombre = '';
    this.docenteId = null;
    this.materia = '';
    this.horario = '';
    this.cupo = null;
  }
}