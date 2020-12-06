#Rendu Projet 3 DAAR 2020

## Authors
- Sacha Memmi
- Karim Leffad

## Project Content
- `Docker-compose` : une configuration docker-compose pour lancer les instances
elasticsearch et l'instance h2 utilise dans le projet.

 Le fichier cmd-to-run contient un fix pour ES dans docker.

- `Requests` : Un fichier `Json` et un fichier `har` des requetes http pour
le logiciel <a href="https://insomnia.rest/download/#windows">insomnia</a>

- `src` : contient les sources de notre projet.

## Description de notre solution
- A l'initialisation du serveur, une tache plannifiee est lancee (1s au lancement puis a interval de 24h)
qui recupere une liste de competences fournies par une <a href="https://skills.emsidata.com/">API</a> utilisant le protocole `OAuth2` (les requestes sont dans le fichier insomnia).

- L'utilisateur envoie les CV (uniquement au format PDF) via une requete HTTP (fournie dans le fichier insomnia). Le CV est parse
et est indexe selon les competences trouvees dans le CV.

- L'utilisateur peut effectuer une recherche sur les CV en envoyant une liste de competences (pour une recherche stricte)
ou alors une liste de competences et un nombre minimum de competences a satisfaire (toutes les requetes sont fournies dans le fichier insomnia).
