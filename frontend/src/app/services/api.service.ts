import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
}