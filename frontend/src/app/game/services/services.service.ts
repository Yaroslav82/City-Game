import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GameSession } from '../models/game-session.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private apiUrl = 'http://localhost:8080/api/game';

  constructor(private _httpClient: HttpClient) {}


  startGame(): Observable<GameSession> {
    return this._httpClient.get<GameSession>(`${this.apiUrl}/begin`);
  }

  nextMove(word: string, sessionId: number): Observable<GameSession> {
    const headers = new HttpHeaders({ 'Session-Id': sessionId });
    return this._httpClient.get<GameSession>(`${this.apiUrl}/next?word=${word}`, { headers });
  }

  endGame(sessionId: number): Observable<void> {
    const headers = new HttpHeaders({ 'Session-Id': sessionId });
    return this._httpClient.post<void>(`${this.apiUrl}/end`, {}, { headers });
  }

}