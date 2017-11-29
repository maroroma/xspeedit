# xspeedit

## jdk
Le projet est en jdk8

## Build

Via maven, avec :
```
mvn clean install
```


## Execution

Une fois le fichier jar généré, il peut être lancé avec la commande suivante :

```
java -jar vsct-packaging-0.0.1-SNAPSHOT.jar 93459834508233
```

## Sortie

Dans la console, le programme doit avoir ce genre de sortie :

```
Arguments en entrée :
1234566345
Résultat du packaging :
64/64/55/3321
4 paquets
```

## Dépendances
Le projet est assez simple, il y a juste une dépendance vers lombok et vers junit.
