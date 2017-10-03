import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('TableDefinition e2e test', () => {

    let navBarPage: NavBarPage;
    let tableDefinitionDialogPage: TableDefinitionDialogPage;
    let tableDefinitionComponentsPage: TableDefinitionComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TableDefinitions', () => {
        navBarPage.goToEntity('table-definition');
        tableDefinitionComponentsPage = new TableDefinitionComponentsPage();
        expect(tableDefinitionComponentsPage.getTitle()).toMatch(/Table Definitions/);

    });

    it('should load create TableDefinition dialog', () => {
        tableDefinitionComponentsPage.clickOnCreateButton();
        tableDefinitionDialogPage = new TableDefinitionDialogPage();
        expect(tableDefinitionDialogPage.getModalTitle()).toMatch(/Create or edit a Table Definition/);
        tableDefinitionDialogPage.close();
    });

    it('should create and save TableDefinitions', () => {
        tableDefinitionComponentsPage.clickOnCreateButton();
        tableDefinitionDialogPage.setNameInput('name');
        expect(tableDefinitionDialogPage.getNameInput()).toMatch('name');
        tableDefinitionDialogPage.setSchemaNameInput('schemaName');
        expect(tableDefinitionDialogPage.getSchemaNameInput()).toMatch('schemaName');
        tableDefinitionDialogPage.save();
        expect(tableDefinitionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TableDefinitionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-table-definition div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class TableDefinitionDialogPage {
    modalTitle = element(by.css('h4#myTableDefinitionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    schemaNameInput = element(by.css('input#field_schemaName'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setSchemaNameInput = function (schemaName) {
        this.schemaNameInput.sendKeys(schemaName);
    }

    getSchemaNameInput = function () {
        return this.schemaNameInput.getAttribute('value');
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
