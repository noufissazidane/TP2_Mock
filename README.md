
# TP Mockito : Tests Unitaires Avancés

## 1. Introduction aux Mocks

Dans le cadre du développement logiciel, un **Mock** (simulacre) est un objet "doublure" qui imite le comportement d'un objet réel de manière contrôlée.
Au lieu d'utiliser de vraies instances (qui pourraient nécessiter une base de données, une connexion réseau ou des calculs complexes), nous utilisons des Mocks pour :

* **Isoler l'unité de code** testée afin de s'assurer que les erreurs proviennent du code lui-même et non de ses dépendances.
* **Simuler des comportements spécifiques** (erreurs, exceptions, valeurs limites).
* **Vérifier les interactions** (s'assurer qu'une méthode a bien été appelée avec les bons paramètres).

## 2. But du TP

L'objectif de ce TP était de maîtriser le framework **Mockito** pour tester différents types de structures Java :

* **Interfaces :** Définir des comportements sur des méthodes non implémentées.
* **Spies (Espions) :** Mixer code réel et code simulé sur une classe concrète.
* **Méthodes Statiques et Finales :** Apprendre à mocker des éléments normalement "verrouillés" par le langage Java.
* **Application métier :** Appliquer ces concepts à la classe `SommeArgents` pour simuler des scénarios financiers artificiels.

## 3. Choix Techniques : Maven vs ZIP

Contrairement à la suggestion initiale d'utiliser un fichier ZIP (PowerMock), ce projet utilise **Maven** pour plusieurs raisons cruciales :

* **Gestion des dépendances :** Maven télécharge automatiquement les bibliothèques nécessaires (`mockito-core`, `junit-jupiter`) et gère leurs versions de manière cohérente.
* **Compatibilité Java 23 :** Les versions anciennes contenues dans les ZIP ne supportent pas les versions récentes du JDK. Maven nous permet d'utiliser `mockito-inline`, indispensable pour mocker les méthodes statiques et finales sur Java 23 sans l'instabilité de PowerMock.
* **Standardisation :** Maven est l'outil standard de l'industrie, permettant de compiler et tester le projet avec une seule commande (`mvn test`).

## 4. Interprétation des Résultats de Tests

Le dernier rapport d'exécution affiche les résultats suivants :

| Suite de Tests | Tests lancés | Succès | Interprétation |
| --- | --- | --- | --- |
| **MockitoTest** | 8 | 8 | Validation des matchers (gt, leq), des exceptions et des listes sur l'interface `I`. |
| **SommeArgentsMockTest** | 4 | 4 | La logique d'addition et de gestion des devises fonctionne correctement avec des mocks. |
| **SpyTest** | 2 | 2 | Le comportement réel est conservé tout en permettant le mock partiel. |
| **StaticFinalTest** | 2 | 2 | Les méthodes `static` et `final` de la classe `C` ont été mockées avec succès. |
| **TOTAL** | **16** | **16** | **BUILD SUCCESS : 100% de réussite.** |

### Analyse du Log Maven

* **Compiling 5 source files :** Le compilateur a bien pris en compte nos classes métier (`A`, `C`, `I`, `SommeArgents`).
* **Surefire Plugin :** C'est l'outil qui a orchestré l'exécution des 16 tests.
* **Exit code 0 :** Indique que le processus s'est terminé sans aucune erreur système ou logique.


Ce projet implémente une suite de tests unitaires avancés utilisant le framework **Mockito**. L'objectif est de maîtriser le doublage d'objets (Mocks et Spies) pour isoler les composants et simuler des comportements complexes.

## 🛠 Configuration Technique

* **Langage :** Java 23
* **Framework de Test :** JUnit Jupiter (JUnit 5.10.0)
* **Mocking :** Mockito 5.11.0 (Core & Inline)
* **Build Tool :** Maven

### Note sur la compatibilité Java 23

En raison de l'utilisation de **Java 23**, la configuration a été adaptée pour permettre à ByteBuddy (moteur de Mockito) de fonctionner en mode expérimental via l'option JVM suivante dans le `pom.xml` :

```xml
<argLine>-Dnet.bytebuddy.experimental=true</argLine>

```

---

## 🧪 Contenu du Projet

### 1. Tests sur l'Interface `I` (Mocking de base)

Utilisation de `Mockito.mock()` et `@Mock` pour :

* Vérifier les valeurs de retour par défaut.
* Configurer des retours successifs (`thenReturn(1, 2, 3)`).
* Simuler des exceptions sur des méthodes `int` et `void`.
* Utiliser des **Matchers** avancés (`gt`, `leq`) et des **ArgumentMatchers personnalisés** (via expressions Lambda) pour valider des listes.

### 2. Tests sur la Classe `A` (Spying)

Utilisation de `@Spy` pour effectuer du **Mocking partiel** :

* Conservation du comportement réel des méthodes par défaut.
* Modification sélective du comportement pour des paramètres spécifiques (ex: `m2(42)`).

### 3. Tests sur la Classe `C` (Static & Final)

Conformément aux consignes demandant de mocker des éléments statiques et finaux (initialement prévus pour PowerMock) :

* **Méthodes Finales :** Mockées nativement via `mockito-inline`.
* **Méthodes Statiques :** Utilisation de `MockedStatic<C>` avec le pattern *try-with-resources* pour isoler le comportement statique.

### 4. Application à `SommeArgents`

Refactorisation et tests de la logique métier :

* Simulation de **situations artificielles** (quantités négatives, devises incohérentes).
* Vérification des interactions entre objets (usage de `verify`).

---

## 🚀 Exécution des Tests

Pour lancer l'intégralité des tests via le terminal :

```bash
mvn test

```

## 📝 Conclusion

Ce TP a permis de démontrer l'efficacité des Mocks pour :

1. **Isoler** une classe de ses dépendances.
2. **Forcer** des chemins d'exécution difficiles à atteindre (erreurs, cas limites).
3. **Vérifier** que les contrats d'interface sont respectés sans exécuter le code réel des dépendances.

---

