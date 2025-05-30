import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { SecurityContextDiscoveryService, isRouteActive } from '@defendev/common-angular';



@Component({
  selector: 'ea-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: false,
})
export class AppComponent implements OnInit {

  public menuCollapsed: boolean = true;

  public constructor(
    private router: Router,
    public securityService: SecurityContextDiscoveryService,
  ) { }

  public ngOnInit(): void {

  }

  public toggleMenuCollapse() {
    this.menuCollapsed = !this.menuCollapsed;
  }

  public collapseMenu() {
    this.menuCollapsed = true;
  }

  public navigateHome() {
    this.router.navigate(['']);
    this.collapseMenu();
  }

  public navigateToDocuments() {
    this.router.navigate(['document']);
    this.collapseMenu();
  }

  public isRouteActiveDocuments() {
    return isRouteActive(this.router, 'document');
  }

}
