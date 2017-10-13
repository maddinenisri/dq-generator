import { Routes } from '@angular/router';

import { SchemasComponent } from './schemas/schemas.component';
import { TablesComponent } from './tables/tables.component';
import { SqlComponent } from './sql/sql.component';
import { FileSystemComponent } from './file-system/file-system.component';
import { JdbcComponent } from './jdbc/jdbc.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/schemas/fileSystem',
        pathMatch: 'full'
    },
    {
        path: 'schemas',
        children: [
            {
                path: 'fileSystem',
                component: FileSystemComponent
            },
            {
                path: 'jdbc',
                component: JdbcComponent
            }
        ]
    },
    {
        path: 'tables',
        component: TablesComponent
    },
    {
        path: 'sql',
        component: SqlComponent
    }
];


