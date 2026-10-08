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

  @Output() loginExitoso = new EventEmitter<void>();

  constructor(private apiService: ApiService) {}

  onLogin() {
    this.apiService.login(this.credentials).subscribe({
      next: (res: any) => {
        this.apiService.guardarToken(res.token);
        this.apiService.guardarRol(res.rol);
        this.errorLogin = false;
        this.loginExitoso.emit();
      },
      error: () => {
        this.errorLogin = true;
      }
    });
  }
}