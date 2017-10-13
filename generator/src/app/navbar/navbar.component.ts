import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/primeng';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor() { }
  public items: MenuItem[];
  ngOnInit() {
    this.items = [
      {
        label: 'Schemas',
        icon: 'fa-database',
        items: [
          {
            label: 'File System',
            icon: 'fa-file',
            routerLink: 'schemas/fileSystem'
          },
          {
            label: 'JDBC',
            icon: 'fa-chain',
            routerLink: 'schemas/jdbc'
          }
        ]
      },
      {
        label: 'SQL',
        icon: 'fa-terminal',
        routerLink: 'sql'
      }
    ];
  }

}
