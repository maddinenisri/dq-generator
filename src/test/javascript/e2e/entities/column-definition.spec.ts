import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('ColumnDefinition e2e test', () => {

    let navBarPage: NavBarPage;
    let columnDefinitionDialogPage: ColumnDefinitionDialogPage;
    let columnDefinitionComponentsPage: ColumnDefinitionComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ColumnDefinitions', () => {
        navBarPage.goToEntity('column-definition');
        columnDefinitionComponentsPage = new ColumnDefinitionComponentsPage();
        expect(columnDefinitionComponentsPage.getTitle()).toMatch(/Column Definitions/);

    });

    it('should load create ColumnDefinition dialog', () => {
        columnDefinitionComponentsPage.clickOnCreateButton();
        columnDefinitionDialogPage = new ColumnDefinitionDialogPage();
        expect(columnDefinitionDialogPage.getModalTitle()).toMatch(/Create or edit a Column Definition/);
        columnDefinitionDialogPage.close();
    });

    it('should create and save ColumnDefinitions', () => {
        columnDefinitionComponentsPage.clickOnCreateButton();
        columnDefinitionDialogPage.setColumnNameInput('columnName');
        expect(columnDefinitionDialogPage.getColumnNameInput()).toMatch('columnName');
        columnDefinitionDialogPage.setColumnTypeInput('columnType');
        expect(columnDefinitionDialogPage.getColumnTypeInput()).toMatch('columnType');
        columnDefinitionDialogPage.setPkColumnOrderInput('5');
        expect(columnDefinitionDialogPage.getPkColumnOrderInput()).toMatch('5');
        columnDefinitionDialogPage.tableDefinitionSelectLastOption();
        columnDefinitionDialogPage.save();
        expect(columnDefinitionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ColumnDefinitionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-column-definition div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ColumnDefinitionDialogPage {
    modalTitle = element(by.css('h4#myColumnDefinitionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    columnNameInput = element(by.css('input#field_columnName'));
    columnTypeInput = element(by.css('input#field_columnType'));
    pkColumnOrderInput = element(by.css('input#field_pkColumnOrder'));
    tableDefinitionSelect = element(by.css('select#field_tableDefinition'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setColumnNameInput = function (columnName) {
        this.columnNameInput.sendKeys(columnName);
    }

    getColumnNameInput = function () {
        return this.columnNameInput.getAttribute('value');
    }

    setColumnTypeInput = function (columnType) {
        this.columnTypeInput.sendKeys(columnType);
    }

    getColumnTypeInput = function () {
        return this.columnTypeInput.getAttribute('value');
    }

    setPkColumnOrderInput = function (pkColumnOrder) {
        this.pkColumnOrderInput.sendKeys(pkColumnOrder);
    }

    getPkColumnOrderInput = function () {
        return this.pkColumnOrderInput.getAttribute('value');
    }

    tableDefinitionSelectLastOption = function () {
        this.tableDefinitionSelect.all(by.tagName('option')).last().click();
    }

    tableDefinitionSelectOption = function (option) {
        this.tableDefinitionSelect.sendKeys(option);
    }

    getTableDefinitionSelect = function () {
        return this.tableDefinitionSelect;
    }

    getTableDefinitionSelectedOption = function () {
        return this.tableDefinitionSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
