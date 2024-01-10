it('Visits the initial project page', () => {
cy.visit('/')
cy.contains('Hablando De Fútbol')
})
