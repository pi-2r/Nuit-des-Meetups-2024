<div align="center">  
    <h2>      
        Knowledge Base Agent      
    </h2>      
    <p>
    Ce projet est issue du projet Amazon Bedrock, il permet d'appeler un agent Bedrock depuis un serveur Flask.

source originale : https://github.com/build-on-aws/bedrock-agents-streamlit
    </p>      
</div>      


<!-- TABLE OF CONTENTS -->      
**Sommaire** :

- [Prérequis](#prérequis)
- [Authentification](#authentification)
- [Valeure à modifier](#valeure-à-modifier)
- [Lancer le serveur](#lancer-le-serveur)
- [Interroger l'endpoint](#interroger-lendpoint)


## Prérequis
Installer les dépendances python depuis le fichier **requirements.txt** avec la commande suivante:
    
    
    pip install -r requirements.txt
    

Pour la création d'un Agent Bedrock avec du RAG (Knowledge base), il faut suivre les instructions du README du projet 
officiel: https://github.com/build-on-aws/bedrock-agents-streamlit

## Authentification
Pour vous connecter à votre environnement AWS, vous devez avoir un compte AWS et un compte IAM avec les autorisations 
nécessaires pour exécuter les commandes AWS CLI.
awssec utilise les informations d'identification AWS stockées dans le fichier de configuration AWS CLI. Pour plus 
d'informations, consultez la documentation AWS CLI.

## Valeure à modifier

Dans le fichier **InvokeAgent.py** il faut modifier les valeurs suivantes :

* **CHANGE_AGENT_ID** qui correspond à l'identifiant de l'agent que vous avez créé dans Amazon Bedrock


* **CHANGE_ALIAS_ID** qui correspond à l'alias de l'agent que vous avez créé dans Amazon Bedrock


* **CHANGE_AWS_PROFILE** qui correspond au profile AWS que vous avez configuré dans votre machine


## Lancer le serveur
Pour lancer le serveur, il suffit de lancer la commande suivante :

```bash
python start.py
```


## Interroger l'endpoint

depuis un postman ou autre outils similaires, faire un post sur l'url suivante 
http://127.0.0.1:5000/agent avec le body suivant (par exemple) :

```json
{ "question": "quels sont les technologies utilisées dans l'entreprise ?"}
```

La réponse prend un peu de temps à arriver (compter 10 à 16 secondes).
