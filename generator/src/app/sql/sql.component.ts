import { Component, OnInit } from '@angular/core';
import { TerminalService } from 'primeng/components/terminal/terminalservice';

@Component({
  selector: 'app-sql',
  templateUrl: './sql.component.html',
  styleUrls: ['./sql.component.scss'],
  providers: [TerminalService]
})
export class SqlComponent implements OnInit {

  constructor(private terminalService: TerminalService) {
    this.terminalService.commandHandler.subscribe(command => {
      let response = (command === 'date') ? new Date().toDateString() : 'Unknown command: ' + command;
      this.terminalService.sendResponse(response);
    });
  }

  ngOnInit() {
  }

}
