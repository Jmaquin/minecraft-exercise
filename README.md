Lunatech
========

Scala tutorial.

Audience : cet exercice s'adresse à des développeurs ayant quelques bases en Scala. Il permet de découvrir
la mise en place progressive d'un ensemble de fonctions et de types, pour résoudre un problème simple.

L'objectif de cet exercice est de réaliser un générateur d'équipement pour le jeu Minecraft.
Minecraft est un jeu d'exploration. L'aventurier doit trouver des ingrédients de base, puis les combiner afin de constuire
de l'équipement. Nous allons jouer une version simplifiée. 

A partir d'une certaine quantité d'ingrédients, par exemple 5 blocs de bois, votre
moteur doit trouver quelles sont les recettes les plus efficaces pour laisser le moins
d'ingrédient non utilisé. 
Une épée demandera 2 éléments. Une Hache demandera 3 éléments. Enfin une pioche ne demandera qu'un élément
Avec 5 blocs de bois, pour ne pas gaspiller d'éléments, nous pouvons donc fabriquer
une hache (3 blocs) et une épée (2 blocs). 

Votre mission est donc de créer un moteur simple capable d'exécuter des Recettes (Recipe) en
utilisant les matériaux.

Vous serez guidé pas à pas. Un ensemble de tests unitaires sera utilisé à partir de l'étape 5.

Si cet exercice vous a intéressé, et que vous souhaitez coder en Scala, sachez que Lunatech recrute. 
Rendez-vous sur notre site web http://www.lunatech.fr dans la rubrique Emploi.

Etape 1
-------

Dans un premier temps nous allons voir comment créer des Enumerations en Scala.

Créer un fichier WeaponGenerator.scala dans lequel vous devrez définir 2 Enumerations.

D'une part une Enumeration de Material avec DIAMOND, IRON et WOOD
D'autre part une Enumeration de ToolType avec AXE, SWORD et SHOVEL

Regardez dans la documentation de Scala comment créer des Enumerations.

Ensuite, ajoutez une case class Weapon composée d'un ToolType et d'un Material.
Par exemple une épée en bois sera créée de cette façon 

```
Wooden Sword is Weapon(ToolType.SWORD, Material.WOOD)
```

Enfin, créer une case class Ingredient, pour représenter une certaine quantité de Material.

A la fin de cette étape vous devez avoir :

  - deux Enumeration
  - une case class Weapon
  - une case class Ingredient avec un attribut quantity de type Int et un material
    
Etape 2
-------

Dans cette deuxième étape, nous commencerons par créer un object MinecraftToolsGenerator.
Ce singleton nous servira pour porter 3 types importants pour la suite.
Pour simplifier l'algorithme, notre constructeur d'outil fonctionnera à partir d'un Ingredient.
Par exemple 

```
Ingredient(6, WOOD) // 6 éléments en bois
```
 
Commençons par définir un type pour représenter une arme et éventuellement une liste d'ingrédient, qui seront les restes après
avoir fabriqué une arme :

```
  type WeaponAndIngredients = (Weapon, Option[Ingredient])
``` 

Nous continuons ensuite en créant une recette (Recipe). Ce type est un alias pour une fonction qui transforme un Ingredient en WeaponAndIngredients

```
  type Recipe = Ingredient => WeaponAndIngredients
```

Enfin, nous aurons nos formules de fabrication.
Celles-ci prennent un Ingredient (par exemple 3 éléments d'Acier) et retournerons soit une arme avec le reste
 des ingrédients non utilisés, soit un message d'erreur, représenté par une String.
 
 Regardez la documentation de la class Either. Celle-ci permet de représenter le fait qu'une fonction
 peut retourner soit un type de résultat (ici un String) soit un autre type (WeaponAndIngredients)
 Mais pas les deux. Un Either peut être soit un Left, soit un Right. 
    
```
  type RecipeBuilder = Ingredient => Either[String, WeaponAndIngredients]
```

Etape 3
-------

Nous allons maintenant commencer à construire la structure de notre logiciel.
Pour cela, dans un premier temps, nous vous proposons de créer une fonction coreRecipe avec la signature suivante :

```
   private def coreRecipe(ingredient: Ingredient, t: ToolType, requiredQuantity: Int): (Weapon, Option[Ingredient]) = { 
     ???
   }
```
  
A vous d'implémenter la fonction coreRecipe. Si vous n'arrivez pas à implémenter cette fonction, continuez l'exercice pour l'instant.

Etape 4
-------

Vous devez maintenant définir 3 recettes de fabrication. Une pour chaque type d'arme que nous pouvons fabriquer.
D'après le guide de Minecraft, une épée (sword) demande 2 Ingredient du même type pour être construite, une Hache (axe) demande 3 Ingredient du même type et
enfin une pioche (shovel) ne demande qu'une quantité unitaire. 
Avec Ingredient(2, WOOD) je peux donc construire une ,épée, et il ne me restera plus d'ingrédients.
Je pourrai aussi construire une pioche et il me restera une quantité de 1 de WOOD. Par contre, je ne pourrai
pas construire de hache. Et c'est justement là que notre RecipeBuilder nous indiquera
qu'il nous manque des ingrédients, sous la forme d'une String. 

A vous maintenant de coder les 3 Recipe, en faisant appel uniquement à coreRecipe => 

```
 def swordRecipe: Recipe = ???
 def axeRecipe: Recipe = ???
 def shovelRecipe: Recipe = ???
```

Nous avons maintenant nos 3 recettes basées sur coreRecipe. 
  
Etape 5
-------
  
Nous allons maintenant définir 3 fonctions qui nous permettrons de construire nos armes. 
D'après notre système de type, il faudra définir 3 fonctions qui retournent un RecipeBuilder

Ajouter les 3 fonctions suivantes et complétez pour chaque fonction ce qu'il manque.

!!! Utilisez les tests unitaires de la class TestMinecraftToolsGenerator afin de valider les contrats de vos fonctions 


 ``` 
   def buildSword: RecipeBuilder = {
    ???
  }
  
  def buildAxe: RecipeBuilder = {
   ???
  }
  
  def buildShovel: RecipeBuilder = {
   ???
  } 
  
```
Si vous écrivez les 3 fonctions, vous remarquerez que du code se répète dans les 3 fonctions.

Remplacez le code dupliqué par une fonction craft. 

Si vous êtes bloqué, la fonction craft doit avoir cette signature 

```
def craft(recipe: Recipe, ingredient: Ingredient): Either[String, WeaponAndIngredients] = 
```

Etape 6
-------

Avant-dernière étape. Nous vous demandons de définir une fonction uncraft, qui permettra 
de découvrir la quantité d'éléments nécessaires pour chaque type de Weapon.
 
 La signature de la fonction sera de ce type :
 
```
  def uncraft: Weapon => Ingredient = {
  }
``` 
 
Utilisez les tests unitaires pour valider votre implémentation  
  

Etape 7
-------

C'est maintenant que le plus important commence.
Votre dernière mission est d'écrire la fonction buildBestPackageFrom, et de faire passer tous les tests unitaires
de la class qui accompagne l'exercice 


```
def buildBestPackageFrom(ingredients: Ingredient): Set[Weapon] = {

}
```

Cette fonction prend en argument un Ingredient, et retourne la meilleur combinaison d'outils possible.
Par "meilleur combinaison" on entend celle qui est la plus écologique, et qui laisse le moins d'ingrédients à la fin

A titre d'exemple, si vous passez un Ingredient avec 5 WOOD alors nous aimerions que votre
code construise une Hache (3 blocs de bois) et une épée (2 blocs de bois)

```

    "it has  5 Wood block" should {
      "build 1 Axe and 1 axe" in {
        // GIVEN
        val ingredients = Ingredient(5, Material.IRON)

        val expected = Set(Weapon(ToolType.SWORD, Material.IRON),
          Weapon(ToolType.AXE, Material.IRON)
        )

        MinecraftToolsGenerator.buildBestPackageFrom(ingredients) should be(expected)
      }
    }

```


C'est terminé.
Vous venez de voir comment modéliser un petit algorithme simple, en utilisant le système de type de Scala.
On remarque que chaque traitement est décomposé et pensé en une suite de fonction.

En espérant que cela vous a aidé,
N.Martignole

Mai 2017
