import { before } from 'mocha';

describe('Category', () => {
    let category = {
        name: '',
    };

    before(() => {
        cy.fixture('category.json').then(
            (categoryLocal) => (category = categoryLocal),
        );
    });

    it('Should create category', () => {
        cy.visit('/');
        cy.wait(1000);

        cy.get('[data-testid="categoryExpansionPanel"]').click();
        cy.get('[data-testid="addCategoryButton"]').click();

        cy.get('[data-testid="categoryNameInput"]').type(category.name);
        cy.get('[data-testid="categorySaveButton"]').click();
        cy.wait(1000);

        cy.get('[data-testid="categoryList"]')
            .contains(category.name)
            .should('exist');
    });

    it('Should edit category', () => {
        cy.visit('/');
        cy.wait(1000);

        cy.get('[data-testid="categoryExpansionPanel"]').click();
        cy.get(
            `[data-testid="categoryMoreButton${category.name}"]`,
        ).click();
        cy.get('button').contains('Edit').click();
        cy.wait(1000);

        category.name += 'ah4r3';
        cy.get('[data-testid="categoryNameInput"]').clear();
        cy.get('[data-testid="categoryNameInput"]').type(category.name);
        cy.get('[data-testid="categorySaveButton"]').click();
        cy.wait(1000);

        cy.get('[data-testid="categoryList"]')
            .contains(category.name)
            .should('exist');
    });

    it('Should delete category', () => {
        cy.visit('/');
        cy.wait(1000);

        cy.get('[data-testid="categoryExpansionPanel"]').click();
        cy.get(
            `[data-testid="categoryMoreButton${category.name}"]`,
        ).click();
        cy.get('button').contains('Delete').click();
        cy.wait(1000);

        cy.get('button').contains('Delete').click();
        cy.wait(1000);

        cy.get('[data-testid="categoryList"]')
            .contains(category.name)
            .should('not.exist');
    });
});
