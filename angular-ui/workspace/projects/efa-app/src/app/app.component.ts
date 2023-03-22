import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { isRouteActive } from '@defendev/common-angular';


@Component({
  selector: 'ea-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  public menuCollapsed: boolean = true;

  public constructor(private router: Router) {
  }

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

  public navigateToSourceDocuments() {
    this.router.navigate(['srcdoc']);
    this.collapseMenu();
  }

  public isRouteActiveSourceDocuments() {
    return isRouteActive(this.router, 'srcdoc');
  }

}
