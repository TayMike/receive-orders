#language: pt

Funcionalidade: Criar um apartamento

  Cenário: Criar um apartamento
    Dado que existe um apartamento cadastrado - Delete
    Quando o apartamento for deletado pelo ID - Delete
    Então o apartamento deletado não será retornado no sistema - Delete