import { before } from 'mocha';

describe('Editor', () => {
    let editor = {
        name: '',
        surname: '',
        photoUrl: '',
    };

    before(() => {
        cy.fixture('editor.json').then((editorLocal) => (editor = editorLocal));
    });

    it('Should create editor', () => {
        cy.visit('/editors');
        cy.wait(1000);

        cy.get('[data-testid="createEditorButton"]').click();
        cy.wait(1000);

        cy.get('[data-testid="editorNameInput"]').type(editor.name);
        cy.get('[data-testid="editorSurnameInput"]').type(editor.surname);
        cy.get('[data-testid="editorPhotoUrlInput"]').type(editor.photoUrl);

        cy.get('[data-testid="saveEditorButton"]').click();
        cy.wait(1000);

        cy.get('mat-card').children().contains(editor.name).should('exist');

        cy.get('mat-card').children().contains(editor.surname).should('exist');

        cy.get(`img[src="${editor.photoUrl}"`).should('exist');
    });

    it("Should edit editor\'s name", () => {
        cy.visit('/editors');
        cy.wait(1000);

        cy.get(
            `[data-testid="editEditorButton${editor.name}${editor.surname}"]`,
        ).click();
        cy.wait(1000);

        editor.name += 'gaterf 43';
        cy.get('[data-testid="editorNameInput"]').clear();
        cy.get('[data-testid="editorNameInput"]').type(editor.name);

        cy.get('[data-testid="saveEditorButton"]').click();
        cy.wait(1000);

        cy.get('mat-card').children().contains(editor.name).should('exist');
    });

    it("Should edit editor\'s surname", () => {
        cy.visit('/editors');
        cy.wait(1000);

        cy.get(
            `[data-testid="editEditorButton${editor.name}${editor.surname}"]`,
        ).click();
        cy.wait(1000);

        editor.surname += 'gpkhEg';
        cy.get('[data-testid="editorSurnameInput"]').clear();
        cy.get('[data-testid="editorSurnameInput"]').type(editor.surname);

        cy.get('[data-testid="saveEditorButton"]').click();
        cy.wait(1000);

        cy.get('mat-card').children().contains(editor.surname).should('exist');
    });

    it("Should edit editor\'s photo", () => {
        cy.visit('/editors');
        cy.wait(1000);

        cy.get(
            `[data-testid="editEditorButton${editor.name}${editor.surname}"]`,
        ).click();
        cy.wait(1000);

        editor.photoUrl = 'https://banner2.cleanpng.com/20180502/zwq/kisspng-person-silhouette-download-human-vector-5ae94b595e3c50.658892861525238617386.jpg';
        cy.get('[data-testid="editorPhotoUrlInput"]').clear();
        cy.get('[data-testid="editorPhotoUrlInput"]').type(editor.photoUrl);

        cy.get('[data-testid="saveEditorButton"]').click();
        cy.wait(1000);

        cy.get(`img[src="${editor.photoUrl}"`).should('exist');
    });

    it('Should delete editor', () => {
        cy.visit('/editors');
        cy.wait(1000);

        cy.get(
            `[data-testid="deleteEditorButton${editor.name}${editor.surname}"]`,
        ).click();
        cy.wait(1000);

        cy.get('mat-card').children().contains(editor.name).should('not.exist');

        cy.get('mat-card')
            .children()
            .contains(editor.surname)
            .should('not.exist');

        cy.get(`img[src="${editor.photoUrl}"`).should('not.exist');
    });
});
