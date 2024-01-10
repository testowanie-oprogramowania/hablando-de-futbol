import {before} from "mocha";

describe('Article', () => {
    before(() => {

    });

    it('Should create new article', () => {
        cy.fixture('article.json').then((article) => {
            cy.visit('articles/create');
            cy.wait(1000);

            cy.get('[data-testid="titleInput"]').type(article.title);
            cy.get('[data-testid="editorSelect"]')
                .click()
                .get('mat-option')
                .eq(1)
                .click();
            cy.get('[data-testid="articleContentTextArea"]').type(article.content);
            cy.get('[data-testid="photoUrlInput"]').type(article.photoUrl);
            cy.get('[data-testid="categorySelect"]')
                .click()
                .get('mat-option')
                .eq(1)
                .click();
            cy.wait(1000);

            cy.get('[data-testid="articleSaveButton"]').click();
            cy.wait(1000);

            cy.get('.article-list__cards').contains(article.title).should('exist');
        });
    });

    it('Should show article details after click on card', () => {
        cy.fixture('article.json').then((article) => {
            cy.visit('/articles');
            cy.get('.article-list__cards').contains(article.title).click();
            cy.wait(1000);

            cy.get('.show-page__main-image')
                .should('have.attr', 'src')
                .should('include', article.photoUrl);
            cy.contains(article.title).should('exist');
            cy.contains(article.content).should('exist');
        });
    });

    it('Should edit article content', () => {
        cy.fixture('article.json').then((article) => {
            cy.visit('/articles');
            cy.get('.article-list__cards').contains(article.title).click();
            cy.wait(1000);
            cy.get('button[aria-label="Edit article"]').click();
            cy.wait(1000);

            const newDescription = article.description + 'qwe';

            cy.get('[data-testid="articleContentTextArea"]').type(newDescription);
            cy.wait(1000);

            cy.get('[data-testid="articleSaveButton"]').click();
            cy.wait(1000);

            cy.get('.article-list__cards').contains(article.title).should('exist');
        });
    });

    it('Should edit article photo', () => {
        cy.fixture('article.json').then((article) => {
            cy.visit('/articles');
            cy.get('.article-list__cards').contains(article.title).click();
            cy.wait(1000);
            cy.get('button[aria-label="Edit article"]').click();
            cy.wait(1000);

            const newPhotoUrl =
                'https://images.newscientist.com/wp-content/uploads/2017/06/26190023/nationalgeographic_1558685.jpg';
            cy.get('[data-testid="photoUrlInput"]').clear();
            cy.get('[data-testid="photoUrlInput"]').type(newPhotoUrl);
            cy.wait(1000);

            cy.get('[data-testid="articleSaveButton"]').click();
            cy.wait(1000);

            cy.get('.article-list__cards').contains(article.title).click();
            cy.wait(1000);

            cy.get('.show-page__main-image')
                .should('have.attr', 'src')
                .should('include', newPhotoUrl);
        });
    });

    it('Delete article', () => {
        cy.fixture('article.json').then((article) => {
            cy.visit('/articles');
            cy.get('.article-list__cards').contains(article.title).click();
            cy.wait(1000);

            cy.get('button[aria-label="Delete article"]').click();
            cy.wait(1000);

            cy.get('.article-list__cards')
                .contains(article.title)
                .should('not.exist');
        });
    });
});


