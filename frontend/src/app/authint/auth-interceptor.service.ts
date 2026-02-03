import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor{

  constructor() { }
   private excludedUrls: string[] = [
    '/userRegistration',
    '/hotelRegistration',
    '/adminRegistration',
    '/user/login',
    '/hotel/login',
    '/admin/login',
    // Add more URLs here if needed
  ];
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // 1️⃣ Check if request is in the excluded list
    const shouldExclude = this.excludedUrls.some(url => req.url.includes(url));
    if (shouldExclude) {
      return next.handle(req); // Skip token
    }

    // 2️⃣ Decide which token to use based on URL path
    let token: string | null = null;

    if (req.url.includes('/user/')) {
      token = localStorage.getItem('userToken'); // token for normal users
    } 
    else if (req.url.includes('/hotel/')) {
      token = localStorage.getItem('hotelToken'); // token for hotel role
    } 
    else if (req.url.includes('/admin/')) {
      token = localStorage.getItem('adminToken'); // token for admin role
    }

    // 3️⃣ Attach token if found
    if (token) {
      const clonedReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next.handle(clonedReq);
    }

    // 4️⃣ If no token matched, send request without it
    return next.handle(req);
  }
}
