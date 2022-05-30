#language: fr

Fonctionnalité: Utiliser le distributeur

Scénario: Demander un retour monnaie annule la transaction

  Étant donné qu'on insere un dollar
  Quand l'utilisateur demande un retour monnaie
  Alors le montant dans la transaction est 0


Scénario: Le montant inséré fait partie de la transaction

  Étant donné qu'on insere un dollar
  Alors le montant dans la transaction est 1
  Et le distributeur contient 1 dollar

Scénario: Acheter une friandise

  Étant donné que le distributeur est chargé à la position 1 avec 10 friandises
  Et qu'on insere un dollar
  Quand on achète une friandise à la position 1
  Alors le montant dans la transaction est 0
  Et le distributeur contient 1 dollar
  Et La quantité de friandises à la position 1 est 9