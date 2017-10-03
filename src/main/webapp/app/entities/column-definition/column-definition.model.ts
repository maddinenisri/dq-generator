import { BaseEntity } from './../../shared';

export class ColumnDefinition implements BaseEntity {
    constructor(
        public id?: number,
        public columnName?: string,
        public columnType?: string,
        public pkColumnOrder?: number,
        public tableDefinition?: BaseEntity,
    ) {
    }
}
