it('Home page should have correct title', () => {
cy.visit('/')
cy.contains('Hablando De Fútbol')
})
