import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-personal',
  templateUrl: './personal.component.html'
})
export class PersonalComponent implements OnInit {

  personalList: any[] = [];

  idSeleccionado: number | null = null;
  nombre: string = '';
  apellido: string = '';
  dni: string = '';
  sueldo: number | null = null;
  cargo: string = '';
  tipo: string = 'DOCENTE';

  modoEdicion: boolean = false;
  cargando: boolean = false;

  private apiUrl = 'http://localhost:8080/admin/personal';

  constructor(
    private http: HttpClient,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.cargarPersonal();
  }

  get puedeGestionar(): boolean {
    const rol = this.apiService.obtenerRol();
    return rol === 'ROOT' || rol === 'ADMINISTRATIVO';
  }

  private getHeaders() {
    const token = this.apiService.obtenerToken();
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    if (token && token !== 'null' && token !== 'undefined') {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return headers;
  }

  cargarPersonal() {
    this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() }).subscribe({
      next: (data) => this.personalList = data,
      error: (err) => console.error('Error al cargar personal', err)
    });
  }

  guardarPersonal() {
    if (!this.nombre.trim() || !this.apellido.trim() || !this.dni.trim()) {
      alert('Por favor complete los campos obligatorios (Nombre, Apellido y DNI).');
      return;
    }

    if (this.cargando) return;

    let sueldoNumerico: number | null = null;
    if (this.sueldo !== null && this.sueldo !== undefined) {
      const sueldoLimpio = String(this.sueldo).replace(/\./g, '').replace(',', '.');
      sueldoNumerico = parseFloat(sueldoLimpio);
    }

    const docenteDTO = {
      nombre: this.nombre,
      apellido: this.apellido,
      dni: this.dni,
      sueldo: isNaN(sueldoNumerico!) ? 0 : sueldoNumerico,
      cargo: this.cargo,
      tipo: this.tipo 
    };

    this.cargando = true;

    if (this.modoEdicion && this.idSeleccionado !== null) {
      this.http.put(`${this.apiUrl}/${this.idSeleccionado}`, docenteDTO, { headers: this.getHeaders() }).subscribe({
        next: () => {
          this.cargarPersonal();
          this.limpiarFormulario();
          this.cargando = false;
        },
        error: (err) => {
          console.error('Error al actualizar personal', err);
          this.cargando = false;
        }
      });
    } else {
      this.http.post(this.apiUrl, docenteDTO, { headers: this.getHeaders() }).subscribe({
        next: () => {
          this.cargarPersonal();
          this.limpiarFormulario();
          this.cargando = false;
        },
        error: (err) => {
          console.error('Error al crear personal', err);
          this.cargando = false;
        }
      });
    }
  }

  seleccionarParaEditar(p: any) {
    this.modoEdicion = true;
    this.idSeleccionado = p.id;
    this.nombre = p.nombre;
    this.apellido = p.apellido;
    this.dni = p.dni;
    this.sueldo = p.sueldo;
    this.cargo = p.cargo;
    this.tipo = p.tipo;
  }

  eliminarPersonal(id: number) {
    if (confirm('¿Eliminar este registro de personal?')) {
      this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() }).subscribe({
        next: () => this.cargarPersonal(),
        error: (err) => console.error('Error al eliminar personal', err)
      });
    }
  }

  limpiarFormulario() {
    this.modoEdicion = false;
    this.idSeleccionado = null;
    this.nombre = '';
    this.apellido = '';
    this.dni = '';
    this.sueldo = null;
    this.cargo = '';
    this.tipo = 'DOCENTE';
  }
}
