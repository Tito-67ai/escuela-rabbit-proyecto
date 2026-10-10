import { Component, Output, EventEmitter } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent {
  credentials = { username: '', password: '' };
  errorLogin = false;
  mensajeError = '';

  @Output() loginExitoso = new EventEmitter<void>();

  constructor(private apiService: ApiService) {}

  onLogin() {
    this.errorLogin = false;
    this.mensajeError = '';
    this.apiService.login(this.credentials).subscribe({
      next: (res: any) => {
        this.apiService.guardarToken(res.token);
        this.apiService.guardarRol(res.rol);
        this.errorLogin = false;
        this.loginExitoso.emit();
      },
      error: (err) => {
        this.errorLogin = true;
        this.mensajeError = this.apiService.mensajeError(err);
      }
    });
  }
}
