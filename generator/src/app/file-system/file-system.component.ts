import { Component, OnInit } from '@angular/core';
import { DataTable } from 'primeng/primeng';

@Component({
  selector: 'app-file-system',
  templateUrl: './file-system.component.html',
  styleUrls: ['./file-system.component.scss']
})
export class FileSystemComponent implements OnInit {

  constructor() { }

  public fileName: string;
  public columns: any = [];
  ngOnInit() {
    this.addColumn();
  }

  public addColumn = (dt?: DataTable) => {
    this.columns.push({
      columnName: null,
      dataType: null,
      constraint: null
    });
    if (dt) {
      dt.reset();
    }

  }


}
