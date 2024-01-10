import {before} from "mocha";

describe('Article', () => {
    let article = {
        "title": "",
        "editorId": -1,
        "content": "",
        "photoUrl": "",
        "categoryName": ""
    };
    before(() => {
        cy.fixture('article.json').then((articleLocal) => article = articleLocal);
    });

    it('Should not allow to create new article with empty title', () => {
        cy.visit('articles/create');
        cy.wait(1000);

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

        cy.get('[data-testid="articleSaveButton"]').should('be.disabled');
    });

    it('Should not allow to create new article with empty editor', () => {
        cy.visit('articles/create');
        cy.wait(1000);

        cy.get('[data-testid="titleInput"]').type(article.title);
        cy.get('[data-testid="editorSelect"]')
            .click()
            .get('mat-option')
            .eq(0)
            .click();
        cy.get('[data-testid="articleContentTextArea"]').type(article.content);
        cy.get('[data-testid="photoUrlInput"]').type(article.photoUrl);
        cy.get('[data-testid="categorySelect"]')
            .click()
            .get('mat-option')
            .eq(1)
            .click();
        cy.wait(1000);

        cy.get('[data-testid="articleSaveButton"]').should('be.disabled');
    });

    it('Should not allow to create new article with empty content', () => {
        cy.visit('articles/create');
        cy.wait(1000);

        cy.get('[data-testid="titleInput"]').type(article.title);
        cy.get('[data-testid="editorSelect"]')
            .click()
            .get('mat-option')
            .eq(1)
            .click();
        cy.get('[data-testid="photoUrlInput"]').type(article.photoUrl);
        cy.get('[data-testid="categorySelect"]')
            .click()
            .get('mat-option')
            .eq(1)
            .click();
        cy.wait(1000);

        cy.get('[data-testid="articleSaveButton"]').should('be.disabled');
    });

    it('Should not allow to create new article with empty photoUrl', () => {
        cy.visit('articles/create');
        cy.wait(1000);

        cy.get('[data-testid="titleInput"]').type(article.title);
        cy.get('[data-testid="editorSelect"]')
            .click()
            .get('mat-option')
            .eq(1)
            .click();
        cy.get('[data-testid="articleContentTextArea"]').type(article.content);
        cy.get('[data-testid="categorySelect"]')
            .click()
            .get('mat-option')
            .eq(1)
            .click();
        cy.wait(1000);

        cy.get('[data-testid="articleSaveButton"]').should('be.disabled');
    });

    it('Should not allow to create new article with empty category', () => {
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
            .eq(0)
            .click();
        cy.wait(1000);

        cy.get('[data-testid="articleSaveButton"]').should('be.disabled');
    });

    it('Should create new article', () => {
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

    it('Should show article details after click on card', () => {
        cy.visit('/articles');
        cy.get('.article-list__cards').contains(article.title).click();
        cy.wait(1000);

        cy.get('.show-page__main-image')
            .should('have.attr', 'src')
            .should('include', article.photoUrl);
        cy.contains(article.title).should('exist');
        cy.contains(article.content).should('exist');
    });

    it('Should edit article title', () => {
        cy.visit('/articles');
        cy.get('.article-list__cards').contains(article.title).click();
        cy.wait(1000);
        cy.get('button[aria-label="Edit article"]').click();
        cy.wait(1000);

        const newTitle = article.title + 'abcd123';
        cy.get('[data-testid="titleInput"]').type(newTitle);
        cy.wait(1000);

        cy.get('[data-testid="articleSaveButton"]').click();
        cy.wait(1000);

        cy.get('.article-list__cards').contains(article.title).should('exist');
    });

    it('Should edit article content', () => {
        cy.visit('/articles');
        cy.get('.article-list__cards').contains(article.title).click();
        cy.wait(1000);
        cy.get('button[aria-label="Edit article"]').click();
        cy.wait(1000);

        const newContent = article.content + 'qwe';
        cy.get('[data-testid="articleContentTextArea"]').type(newContent);
        cy.wait(1000);

        cy.get('[data-testid="articleSaveButton"]').click();
        cy.wait(1000);

        cy.get('.article-list__cards').contains(article.title).should('exist');
    });

    it('Should edit article photo', () => {
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

    it('Delete article', () => {
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


