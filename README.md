# coltexpress

🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂🚂

![](https://i.imgur.com/v7gkJAk.png)

* **APERÇU DES RÈGLES DU JEU**
-Personnages / Elements : Train, Wagons, Bandits, Marshall, Butin

    -2 Phases :
    1. Planification : Chaque bandit décide des mouvements *(liste) qu'il va réaliser à l'avance*
    2. Action : Execution des mouvements planifiés *tour pour chaque indice de liste*



<ins> MODES DE JEU : <ins> 
- ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) `CLASSIC :` Le nombre de wagons est fixé à 4, le nombre d'actions à 2, le nombre de tours à 4 ainsi que le nombre de butins par wagon
- ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) `MARSHALL FOU :` On met la nervosité du marshall au maximum, on augmente le nombre d'actions des Bandits et du Marshall ![](https://i.imgur.com/z9G053k.png)


--------------------------------------------------------
## **PARTIES**
*toutes les parties ont été traitées*
## 1.3
On organise le jeu selon le modèle-vue-contrôleur, articulé autour du ``TRAIN``

-``TRAIN`` : On définit donc cette classe comme une extension de *Observable*. 
attributs :  taille,  liste de ``WAGON``, de ``BANDIT`` et de ``BUTIN``

-``WAGON`` : Classe qui permet de traiter les déplacements et l'organisation des éléments/personnages au sein du train. Elle est indépendante et définie par son ``train``, son ``rang``, et les ``BANDIT`` et ``BUTIN`` qu'elle contient

-``ELEMENT`` : Classe parent des objets/personnages (``BANDIT``, ``BUTIN``, ``MARSHALL``), elle permet de centraliser les attributs ``position`` et les méthodes d'affichage et de déplacement

-``BANDIT`` : Extension de ``ELEMENT``, il ajoute les champs ``name``, ``roof``(booléen qui indique s'il est sur le toit), et reprend l'entier ``NB_BALLES``


## 1.4
Ici, on traite l'affichage en reprenant le modèle de ``Conway.java`` : 
* On définit une classe ``CVue`` qui crée la fenêtre principale de l'application et contient les deux parties principales de notre vue :
    * Une zone d'affichage où on représente l'ensemble du train.
    * Une zone de commande avec un les boutons de contrôle du jeu

* On définit une classe ``vueTrain`` pour représenter la zone d'affichage du train avec ``JPanel``. Cette vue sera l'observateur du modèle mis à jour à chaque action

* On définit une classe ``vueBouttons`` pour représenter la zone de contrôle. Elle n'est donc pas un observateur. On utilise toujours la classe ``JPanel``

*On récupère les définitions de ``Observer``et ``Observable`` vues en cours*
 


## 2

On traite ensuite les deux nouvelles classes :
* ``BUTIN`` : Extension de ``ELEMENT``, il ajoute les champs ``name``, ``value``
* ``MARSHALL`` : également une Extension de ``ELEMENT``, il ajoute les champs ``position`` et ``TRAIN``



## 2.1
On ajoute le contrôleur, similaire à ``Conway.java`` :

La classe implémente l'interface ``ActionListener`` avec une méthode ``actionPerformed`` qui transmet la réponse du contrôleur à l'évènement. On peut ainsi gérer les deux états du jeu : planification et action en traitant chaque action/état dans cette méthode.
C'est également cette méthode qui affiche les actions du jeu dans le terminal.

## 2.2

On implémente l'action de tir dans la classe ``BANDIT`` avec une méthode ``shoot`` qui prend en paramètre une ``DIRECTION`` (type énuméré : ``HAUT``, ``BAS``, ``DROITE``, ``GAUCHE``, ``NULL``). Si le bandit a des munitions, le tir est lancé en direction d'un wagon. On crée une méthode ``shooted`` dans ``WAGON`` qui permet d'appliquer les conséquences : si le wagon n'est pas vide, choix d'un bandit au hasard , autrement le tir est dans le vide et on décrémente simplement le nombre de balles du bandit. Dans le 1er cas, on applique la méthode ``getshooted`` de ``BANDIT`` afin de retirer un butin au bandit touché (le butin est stocké dans le wagon). 

-------------------------------------------------

## 2.3 Expression Libre

Fonctionnalités supplémentaires ajoutées : ➕
* Récupération des [paramètres ⚒](#choix-d'architecture) en ligne de commande (sélection paramètres fenêtre de départ)
* Ajout d'un mode de jeu [Marshall fou](#aperçu-des-règles-du-jeu) avec des paramètres prédéfinis 
* Classement final des bandits 🤠  
* Vue narrateur dans la fenêtre du jeu 


## CHOIX D'ARCHITECTURE

Au coeur du programme, nous avons la classe principale :
* CLASSE ``PROJECT`` : 
*permet de définir les constantes du jeu*
    * VARIABLES GLOBALES  : 
        * ``DEPLACEMNT_MARSHALL`` : nombre de déplacements du marshall à la fin de chaque tour
        * ``NB_WAGONS `` : taille du train
        * ``NB_JOUEUR`` : entré par l'utilisateur
        * ``NB_ACTIONS`` : nombre d'actions par joueur à chaque tour (modifiable en paramètre)
        * ``NB_BALLES`` : nombre de balles des bandits au début du jeu
        * ``NB_BUTINS`` : nombre de butins maximal générés dans le wagon (modifiable en paramètre)
        * ``NB_TOURS`` :  (modifiable en paramètre)
    
    * contient le ``main`` qui lance la fenêtre d'accueil (cf : [interface](#-choix-d'architecture))
    
    

    


Niveau interface, le programme s'éxécute avec une première fenêtre de configuration : [choix de mode](#parties) et accès aux paramètres :



![](https://i.imgur.com/v7gkJAk.png)

Le choix du nombre de joueurs se fait dans le terminal
```
Super, combien de joueurs êtes vous ? Vous ne pouvez pas dépasser 12. 
3
```

La saisie de paramètres aussi : (après la saisie, on lance automatiquement le [mode CLASSIC](#PARTIES))
````
Entrez le nombre d'actions par joueur par tour.
2
Entrez le nombre de tours.
5
Entrez le nombre de butins max par wagons.
4
Entrez la nervosité du marshall, entre 0 et 1, sous le format "0,5" par exemple.
0,2
Entrez le nombre de déplacements du Marshall à la fin de chaque tour
3
````


Ensuite le jeu se lance dans une nouvelle fenêtre

![](https://i.imgur.com/cnZRTUi.png)


<ins> fichiers :<ins> 
```
├── Coltexpress
│   ├── README.md
│   ├── Pictures
│   │   ├── cowboyHat.jpeg
│   │   └── homeScreen.jpg
│   └── src
│       ├── Bandit.java
│       ├── Butin.java
│       ├── Element.java
│       ├── Marshall.java
│       ├── Observer.java
│       ├── Project.java
│       ├── StartFrame.java
│       ├── Train.java
│       └── Wagon.java
└── out
    ├── Bandit.class
    ├── Butin.class
    ├── CVue.class
    ├── Controleur.class
    ├── Direction.class
    ├── Element.class
    ├── ImagePanel.class
    ├── Marshall.class
    ├── Observable.class
    ├── Observer.class
    ├── Project.class
    ├── StartControleur.class
    ├── StartFrame.class
    ├── Train.class
    ├── Wagon.class
    ├── vueButtons.class
    ├── vueNarrateur.class
    └── vueTrain.class
```

<ins> classes : <ins>
![](https://i.imgur.com/M7TgQnD.png)

## Perspectives d'évolution

* Affichage graphique 2d avec images
* Affichage des actions planifiées
* Classement : gestion des égalités (*ici on classe les joueurs ex-aequo dans un ordre arbitraire*)
* Transition entre la fenêtre de départ et la fenêtre de jeu : tout traiter dans la même fenêtre 
