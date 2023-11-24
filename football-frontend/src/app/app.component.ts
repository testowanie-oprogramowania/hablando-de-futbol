import { Component, isDevMode, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {NavbarComponent} from "./navbar/navbar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  title = 'football-frontend';

  ngOnInit() {
    if (isDevMode()) {
      console.log('Development!');
    } else {
      console.log('Production!');
    }
  }
}
