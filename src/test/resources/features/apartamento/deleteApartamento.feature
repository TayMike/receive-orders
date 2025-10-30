#language: pt

Funcionalidade: Delete um apartamento

  Cenário: Delete um apartamento
    Dado que existe um apartamento cadastrado - Delete
    Quando o apartamento for deletado pelo ID - Delete
    Então o apartamento deletado não será retornado no sistema - Delete