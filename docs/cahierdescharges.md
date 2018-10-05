# Développement mobile - cahier des charges

## Description
On souhaite faire une application qui nous lance des alertes en fonctions de différentes conditions. Elle va devoir tourner en tâche de fonds et pouvoir notifier (on choisit le message que ça nous affiche), lancer des déclencheurs pour des applications tels que IFTTT ou lancer une alarme.

**Idées de conditions possibles :**

- Entrée/Sortie de zone géolocalisée
- Heure/Date
- Stockage de données
- Batterie
- Données mobiles

## Contraintes
- Application android
- Utilisation d’AndroidStudio
- Persistance des données
- Utilisation d’au moins deux capteurs

## Objectifs
**Minimum vital**

 	1. Lancer des notifications textuelles
 	2. Utiliser la géolocalisation et lancer des alertes
 	3. Utiliser l’heure pour lancer un réveil (répétition en fonction des jours de la semaine)
 	4. Utiliser une date comme alerte
 	5. Utiliser le niveau de batterie

 **Améliorations possibles optionnelles**

1. Mettre en place une API pour IFTTT et/ou d’autres applications
2. Utiliser un minuteur
3. Utiliser une date comme alerte
4. Utiliser plusieurs conditions
5. Ajouter des conditions comme :
   - Stockage de données
   - Données Mobiles
   - Etc.

