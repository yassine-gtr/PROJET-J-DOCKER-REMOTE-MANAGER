J-Docker Remote

Description

J-Docker Remote est une application Java permettant de gérer des conteneurs Docker à distance via un client léger. Le projet implémente un serveur qui communique avec Docker et un client CLI pour interagir avec le serveur.

Fonctionnalités principales :

Lister les images Docker disponibles (images).

Télécharger des images depuis Docker Hub (pull <image>).

(Évolutif) Gestion des conteneurs : démarrer, arrêter et supprimer des conteneurs.
Architecture
Client CLI  <--->  Serveur Java  <--->  Docker Engine


Client CLI : interface en ligne de commande pour envoyer des requêtes.

Serveur Java : gère la communication avec Docker via la bibliothèque docker-java.

Docker Engine : exécute les conteneurs et stocke les images.

Prérequis

Java 17

Maven

Docker Desktop avec exposition TCP activée (port 2375)

Connexion Internet pour le pull des images Docker

⚠️ Assurez-vous d’avoir activé l’option Expose daemon on tcp://localhost:2375 without TLS dans Docker Desktop.


Exemple de sortie :

Serveur Docker démarré sur le port 9090

Lancer le client
java -cp target/client-1.0.jar com.jdocker.client.DockerClientCLI


Exemple d’interaction :

Client connecté au serveur Docker
docker> images
Status: OK | Message: null | Payload: []
docker> pull busybox:latest
Status: OK | Message: Téléchargement démarré en arrière-plan | Payload: null



captures d'ecrant :

<img width="1920" height="1020" alt="Capture d&#39;écran 2026-01-08 222940" src="https://github.com/user-attachments/assets/db39f0c2-ed3b-4e63-a8e8-980b9dc59997" />
<img width="1920" height="1020" alt="Capture d&#39;écran 2026-01-08 222947" src="https://github.com/user-attachments/assets/a0f21a78-5f2c-44b7-872a-8c887ee3f282" />
<img width="1920" height="1020" alt="Capture d&#39;écran 2026-01-08 223007" src="https://github.com/user-attachments/assets/bfa7406c-3528-469a-9a15-3f7eb84f9623" />
<img width="1920" height="1020" alt="Capture d&#39;écran 2026-01-08 223028" src="https://github.com/user-attachments/assets/cceb2dfc-b402-436d-a296-447a01268bcb" />
<img width="1920" height="1020" alt="Capture d&#39;écran 2026-01-08 223041" src="https://github.com/user-attachments/assets/571eac92-b6aa-4b61-9208-2dfa48dc1ca9" />
<img width="1590" height="900" alt="Capture d&#39;écran 2026-01-08 223059" src="https://github.com/user-attachments/assets/7557d600-765c-41aa-a982-1ca5af49fe4f" />
<img width="1920" height="1020" alt="Capture d&#39;écran 2026-01-08 223126" src="https://github.com/user-attachments/assets/0b540dbf-1b8f-4522-98f4-2e2d3d4d7d93" />
<img width="1590" height="900" alt="Capture d&#39;écran 2026-01-08 223134" src="https://github.com/user-attachments/assets/8df45e8f-d707-4306-ab7c-f68da4b4625f" />


