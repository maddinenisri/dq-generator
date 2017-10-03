import { BaseEntity } from './../../shared';

export class TableDefinition implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public schemaName?: string,
        public columnDefinitions?: BaseEntity[],
    ) {
    }
}
