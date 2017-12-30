package minecraft

import minecraft.Material.Material
import minecraft.ToolType.ToolType

import scala.collection.immutable.Set

object Material extends Enumeration {
  type Material = Value
  val DIAMOND, IRON, WOOD = Value
}

object ToolType extends Enumeration {
  type ToolType = Value
  val AXE, SWORD, SHOVEL = Value
}

case class Weapon(toolType: ToolType, material: Material)

case class Ingredient(quantity: Int, material: Material)

object MinecraftToolsGenerator {
  type WeaponAndIngredients = (Weapon, Option[Ingredient])
  type Recipe = Ingredient => WeaponAndIngredients
  type RecipeBuilder = Ingredient => Either[String, WeaponAndIngredients]

  private def coreRecipe(ingredient: Ingredient, t: ToolType, requiredQuantity: Int): (Weapon, Option[Ingredient]) = {
    val remainingQuantity = ingredient.quantity - requiredQuantity
    remainingQuantity match {
      case x if x < 0 => (Weapon(t, ingredient.material), None)
      case _ => (Weapon(t, ingredient.material), Some(Ingredient(remainingQuantity, ingredient.material)))
    }
  }

  def swordRecipe: Recipe = Ingredient => coreRecipe(Ingredient, ToolType.SWORD, 2)

  def axeRecipe: Recipe = Ingredient => coreRecipe(Ingredient, ToolType.AXE, 3)

  def shovelRecipe: Recipe = Ingredient => coreRecipe(Ingredient, ToolType.SHOVEL, 1)

  def craft(recipe: Recipe, ingredient: Ingredient): Either[String, WeaponAndIngredients] =
    recipe(ingredient)._2 match {
      case Some(_) => Right(recipe(ingredient))
      case None => Left("Dude, can't build this tool")
    }

  def uncraft: Weapon => Ingredient = {
    Weapon =>
      Weapon.toolType match {
        case ToolType.SWORD => Ingredient(2, Weapon.material)
        case ToolType.AXE => Ingredient(3, Weapon.material)
        case ToolType.SHOVEL => Ingredient(1, Weapon.material)
      }
  }

  def buildBestPackageFrom(ingredients: Ingredient): Set[Weapon] = {
    val eitherWeaponAndIngredients = ingredients.quantity match {
      case x if (x / 3) == 1 => craft(axeRecipe, ingredients)
      case x if (x / 2) == 1 => craft(swordRecipe, ingredients)
      case x if x == 1 => craft(shovelRecipe, ingredients)
      case _ => craft(axeRecipe, ingredients)
    }
    eitherWeaponAndIngredients match {
      case Left(_) => Set()
      case Right((x, Some(y))) => Set() + x ++ buildBestPackageFrom(y)
    }
  }
}