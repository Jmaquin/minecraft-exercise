package minecraft

import org.scalatest._

class WeaponGeneratorSpec extends FlatSpec with Matchers {

  "Minecraft tools generator" should "build a sword when quantity is sufficient" in {
    //Given
    val aQuantity = 2
    val aMaterial = Material.IRON
    val anIronIngredient = Ingredient(aQuantity, aMaterial)

    //When
    val result = MinecraftToolsGenerator.craft(MinecraftToolsGenerator.swordRecipe, anIronIngredient)

    //Then
    result.isRight shouldBe true
    result.right.get._1 shouldBe Weapon(ToolType.SWORD, aMaterial)
    result.right.get._2.isEmpty shouldBe false
    result.right.get._2.get shouldBe Ingredient(0, aMaterial)
  }

  it should "not build a sword when quantity is not sufficient" in {
    //Given
    val aQuantity = 1
    val aMaterial = Material.IRON
    val anIronIngredient = Ingredient(aQuantity, aMaterial)

    //When
    val result = MinecraftToolsGenerator.craft(MinecraftToolsGenerator.swordRecipe, anIronIngredient)

    //Then
    result.isLeft shouldBe true
    result.left.get shouldEqual "Dude, can't build this tool"
  }

  it should "build an axe when quantity is sufficient" in {
    //Given
    val aQuantity = 3
    val aMaterial = Material.WOOD
    val aWoodIngredient = Ingredient(aQuantity, aMaterial)

    //When
    val result = MinecraftToolsGenerator.craft(MinecraftToolsGenerator.axeRecipe, aWoodIngredient)

    //Then
    result.isRight shouldBe true
    result.right.get._1 shouldBe Weapon(ToolType.AXE, aMaterial)
    result.right.get._2.isEmpty shouldBe false
    result.right.get._2.get shouldBe Ingredient(0, aMaterial)
  }

  it should "not build an axe when quantity is not sufficient" in {
    //Given
    val aQuantity = 1
    val aMaterial = Material.WOOD
    val aWoodIngredient = Ingredient(aQuantity, aMaterial)

    //When
    val result = MinecraftToolsGenerator.craft(MinecraftToolsGenerator.axeRecipe, aWoodIngredient)

    //Then
    result.isLeft shouldBe true
    result.left.get shouldEqual "Dude, can't build this tool"
  }

  it should "build a shovel when quantity is sufficient" in {
    //Given
    val aQuantity = 1
    val aMaterial = Material.DIAMOND
    val aDiamondIngredient = Ingredient(aQuantity, aMaterial)

    //When
    val result = MinecraftToolsGenerator.craft(MinecraftToolsGenerator.shovelRecipe,aDiamondIngredient)

    //Then
    result.isRight shouldBe true
    result.right.get._1 shouldBe Weapon(ToolType.SHOVEL, aMaterial)
    result.right.get._2.isEmpty shouldBe false
    result.right.get._2.get shouldBe Ingredient(0, aMaterial)
  }

  it should "not build a shovel when quantity is not sufficient" in {
    //Given
    val aQuantity = 0
    val aMaterial = Material.DIAMOND
    val aDiamondIngredient = Ingredient(aQuantity, aMaterial)

    //When
    val result = MinecraftToolsGenerator.craft(MinecraftToolsGenerator.shovelRecipe,aDiamondIngredient)

    //Then
    result.isLeft shouldBe true
    result.left.get shouldEqual "Dude, can't build this tool"
  }
}
