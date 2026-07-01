## Swagger

```
http://localhost:8080/swagger-ui/index.html

```
## Postman
```
http://localhost:8080/api/adresses/recherchePageAvecFiltre?codePostal=79000&rue=république&commune=niort&page=0&size=1000&sort=id,asc
```

# Exo
```
Un champ de recherche par code postal, un champ de recherche par rue et un champ par commune.
 
L'appel renvoie la liste des adresses correspondantes.
```

# Tracer les données

```
Pour tracer les données, savoir combien de ligne ont été lues.
On peut utiliser soit le contexte soit le métrique micrometer 
```




# Partie 2

````
Actuellement le flux fait :CSV -> AdresseDto -> AdresseProcessor -> AdresseEntity -> writer D


Il faut un rowValidator à la fin de l'étape 2

Si j'étais vous je regarderais certains cas particuliers 
comme par exemple : 79005_0162_00014


à la fin de l'étape 2  avec le fichier fourni vous devez 
 Sur les 215 163 lignes avoir 
 
Intégré 209 102 lignes uniques 
Rejeté 1 lignes 
Le reste en doublon
Avoir identifié des doublons "pur" exactement les mêmes
 données (le programme indique les lignes impliquées)
Identifié des doublons avec des différences de données 
et avoir établi une stratégie (idem tracé si plusieurs 
mises à jour du même identifiant)
Avoir le même résultat quelque soit l'ordre des 
données en entrée.
Temps d'insertion de tout le fichier < 20 secondes


Etape 3, vider la base, charger le fichier du 22 puis
 le fichier 
du 23 que j'ai envoyé. Et faire les mises à jours
 nécessaires (on doit savoir ce que chaque ligne devient.
 
On peut avoir des données non
 présentes dans le fichier du 23, elles sont
  donc... à supprimer !
````