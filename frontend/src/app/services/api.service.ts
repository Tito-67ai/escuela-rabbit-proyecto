import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  login(credentials: {username: string, password: string}): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, credentials);
  }

  guardarToken(token: string) {
    localStorage.setItem('jwt_token', token);
  }

  obtenerToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  guardarRol(rol: string) {
    localStorage.setItem('user_rol', rol);
  }

  obtenerRol(): string | null {
    return localStorage.getItem('user_rol');
  }

  logout() {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('user_rol');
  }

  private getHeaders(): HttpHeaders {
    const token = this.obtenerToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getAlumnos(): Observable<any> {
    return this.http.get(`${this.baseUrl}/alumnos`, { headers: this.getHeaders() });
  }

  crearAlumno(alumno: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/alumnos`, alumno, { headers: this.getHeaders() });
  }

  getCursos(): Observable<any> {
    return this.http.get(`${this.baseUrl}/cursos`, { headers: this.getHeaders() });
  }

  getPersonal(): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/personal`, { headers: this.getHeaders() });
  }

  // Traduce la respuesta de error del backend (ErrorResponse) a un texto para mostrar en pantalla.
  mensajeError(err: unknown): string {
    const httpError = err as HttpErrorResponse;
    let cuerpo: any = httpError ? httpError.error : null;

    if (typeof cuerpo === 'string') {
      try {
        cuerpo = JSON.parse(cuerpo);
      } catch (e) {
        return cuerpo;
      }
    }

    if (cuerpo && typeof cuerpo === 'object') {
      const errores: any[] = Array.isArray(cuerpo.errors)
        ? cuerpo.errors.filter((e: any) => e !== null && e !== undefined && e !== '')
        : [];
      if (errores.length > 0) {
        return String(errores[0]);
      }
      if (cuerpo.message) {
        return String(cuerpo.message);
      }
      if (cuerpo.detail) {
        return String(cuerpo.detail);
      }
    }

    const status = httpError ? httpError.status : 0;
    if (status === 0) {
      return 'No se pudo conectar con el servidor.';
    }
    if (status === 401) {
      return 'Tu sesión expiró o las credenciales son inválidas.';
    }
    if (status === 403) {
      return 'No tenés permisos para realizar esta acción.';
    }
    if (status === 404) {
      return 'El recurso solicitado no existe.';
    }
    return 'Ocurrió un error inesperado.';
  }
}
