import { Injectable, Injector } from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { ApiService } from '../services/api.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private injector: Injector) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(this.conToken(req)).pipe(
      catchError((error: HttpErrorResponse) => {
        const esRutaAuth = req.url.includes('/auth/login') || req.url.includes('/auth/refresh');
        const api = this.injector.get(ApiService);
        if (error.status === 401 && !esRutaAuth && api.obtenerRefreshToken()) {
          return this.reintentarConTokenNuevo(req, next);
        }
        return throwError(() => error);
      })
    );
  }

  private conToken(req: HttpRequest<any>): HttpRequest<any> {
    const token = this.injector.get(ApiService).obtenerToken();
    return token
      ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
      : req;
  }

  private reintentarConTokenNuevo(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const api = this.injector.get(ApiService);
    return api.refrescarToken().pipe(
      switchMap((res: any) => {
        api.guardarToken(res.token);
        if (res.refreshToken) {
          api.guardarRefreshToken(res.refreshToken);
        }
        return next.handle(req.clone({ setHeaders: { Authorization: `Bearer ${res.token}` } }));
      }),
      catchError((error) => {
        api.logout();
        location.reload();
        return throwError(() => error);
      })
    );
  }
}
