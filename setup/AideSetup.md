[Git]: https://github.com/INTechTeachTheTeensy/INTechTeachTheTeensy/raw/master/setup/Git.png
[Gradle]: https://github.com/INTechTeachTheTeensy/INTechTeachTheTeensy/raw/master/setup/Gradle.png
[Open]: https://github.com/INTechTeachTheTeensy/INTechTeachTheTeensy/raw/master/setup/Open.png

Comment mettre en place le projet?
=====
Fermer le projet courant sur IDEA si nécessaire: File>Close Project

Cloner le projet depuis GitHub, je vous conseille de le faire depuis IDEA:
![Clonage][Git]

Après le clonage, IDEA vous demandera si vous voulez ouvrir le projet à partir du fichier `build.gradle`, dites oui:
![Ouverture][Open]

Enfin - **ETAPE SUPER IMPORTE** - changez la JVM utilisée par Gradle:
(vous pouvez aussi cocher les deux cases du haut comme sur le screen)
![JDK Gradle][Gradle]
Prenez celle qui se trouve dans /usr/lib/jvm/java-8-openjdk si IDEA ne vous propose pas "1.8" (faites attention à ne pas prendre la JVM d'IDEA, elle n'a pas ce qu'il faut pour JavaFX)