import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs/Observable';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import { catchError, retry } from 'rxjs/operators';

@Injectable()
export class UserService {
 
  private usersUrl: string;
 
  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/accounts';
  }
 
  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }
 
  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user).pipe(
      catchError(this.handleError)
    );
  }
  
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
	  // A client-side or network error occurred. Handle it accordingly.
	  console.error('An error occurred:', error.error.message);
	} else {
	  // The backend returned an unsuccessful response code.
	  // The response body may contain clues as to what went wrong.
	  console.error('Backend returned code ${error.status}');
	  console.error(error.error.message);
	}
	// Return an observable with a user-facing error message.
	return new ErrorObservable(error.error.message);
  }

}