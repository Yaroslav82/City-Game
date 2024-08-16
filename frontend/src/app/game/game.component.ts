import { Component } from '@angular/core';
import { GameService } from './services/services.service';
import { GameSession } from './models/game-session.model';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss'
})
export class GameComponent {

  public session: GameSession | null = null;
  public playerCity: string = '';
  public systemCity: string = '';
  public usedCities: string[] = [];

  public errorMessage: string = '';
  public gameTitle: string = 'Гра "Міста"';

  constructor(private gameService: GameService) { }

  startGame() {
    this.gameService.startGame().subscribe({
      next: (session) => {
        this.playerCity = '';
        this.errorMessage = '';
        this.session = session;
        this.systemCity = session.currentCity;
        this.usedCities = [session.currentCity];
      },
      error: (error) => this.errorMessage = 'Не вдалося почати гру :('
    });
  }

  makeMove() {
    if (this.session && this.playerCity) {
      this.playerCity = this.playerCity.trim();
      this.gameService.nextMove(this.playerCity, this.session.id).subscribe({
        next: (session) => {
          this.errorMessage = '';
          if (!session.active) {
            this.session = null;
            this.gameTitle = 'Ви перемогли!';
          } else {
            this.systemCity = session.currentCity;
            this.usedCities.unshift(this.playerCity.charAt(0).toUpperCase() + this.playerCity.substring(1).toLowerCase());
            this.usedCities.unshift(session.currentCity);
            this.playerCity = '';
          }
        },
        error: (error) => this.errorMessage = error.error.message
      })
    }
  }

  endGame() {
    if (this.session) {
      this.gameService.endGame(this.session.id).subscribe({
        next: () => {
          this.session = null;
          this.errorMessage = '';
          this.gameTitle = 'Спасибі за гру!';
        }
      });
    }
  }
}
