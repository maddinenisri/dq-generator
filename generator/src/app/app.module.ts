import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SlideMenuModule, PanelMenuModule, TieredMenuModule, TerminalModule } from 'primeng/primeng';
import { DataTableModule, SharedModule, PanelModule, ButtonModule } from 'primeng/primeng';



// Import routes
import { routes } from './routes';
import { AppComponent } from './app.component';
import { SchemasComponent } from './schemas/schemas.component';
import { TablesComponent } from './tables/tables.component';
import { SqlComponent } from './sql/sql.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FileSystemComponent } from './file-system/file-system.component';
import { JdbcComponent } from './jdbc/jdbc.component';

@NgModule({
  declarations: [
    AppComponent,
    SchemasComponent,
    TablesComponent,
    SqlComponent,
    NavbarComponent,
    FileSystemComponent,
    JdbcComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes),
    SlideMenuModule,
    PanelMenuModule,
    TerminalModule,
    TieredMenuModule,
    FormsModule,
    DataTableModule,
    SharedModule,
    PanelModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
