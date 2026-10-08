import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: []
})
export class NavbarComponent {
  constructor(public apiService: ApiService) {}

  onLogout() {
    this.apiService.logout();
  }
}