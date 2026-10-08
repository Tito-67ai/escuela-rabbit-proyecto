import { Component } from '@angular/core';
import { ApiService } from './services/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent {
  constructor(public apiService: ApiService) {}

  get rol(): string {
    return this.apiService.obtenerRol() || '';
  }

  get verAlumnos(): boolean {
    return ['PRECEPTOR', 'DIRECTOR', 'ADMINISTRATIVO'].includes(this.rol);
  }

  get verCursos(): boolean {
    return this.rol !== '';
  }

  get verPersonal(): boolean {
    return ['ROOT', 'ADMINISTRATIVO'].includes(this.rol);
  }

  get tieneModulos(): boolean {
    return this.verAlumnos || this.verCursos || this.verPersonal;
  }

  onLoginExitoso() {
  }
}