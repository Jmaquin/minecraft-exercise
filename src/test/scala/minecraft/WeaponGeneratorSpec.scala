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
    val result = MinecraftToolsGenerator.craft(MinecraftToolsGenerator.shovelRecipe, aDiamondIngredient)

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
    val result = MinecraftToolsGenerator.craft(MinecraftToolsGenerator.shovelRecipe, aDiamondIngredient)

    //Then
    result.isLeft shouldBe true
    result.left.get shouldEqual "Dude, can't build this tool"
  }

  it should "give ingredient with quantity 2 for a sword weapon" in {
    //Given
    val aMaterial = Material.IRON
    val anIronSword = Weapon(ToolType.SWORD, aMaterial)

    //When
    val result = MinecraftToolsGenerator.uncraft(anIronSword)

    //Then
    result should not be null
    result.quantity shouldEqual 2
    result.material shouldEqual aMaterial
  }

  it should "give ingredient with quantity 3 for an axe weapon" in {
    //Given
    val aMaterial = Material.DIAMOND
    val aDiamondAxe = Weapon(ToolType.AXE, aMaterial)

    //When
    val result = MinecraftToolsGenerator.uncraft(aDiamondAxe)

    //Then
    result should not be null
    result.quantity shouldEqual 3
    result.material shouldEqual aMaterial
  }

  it should "give ingredient with quantity 1 for a shovel weapon" in {
    //Given
    val aMaterial = Material.WOOD
    val aWoodShovel = Weapon(ToolType.SHOVEL, aMaterial)

    //When
    val result = MinecraftToolsGenerator.uncraft(aWoodShovel)

    //Then
    result should not be null
    result.quantity shouldEqual 1
    result.material shouldEqual aMaterial
  }

  it should "build nothing when it has 0 Wood block" in {
    // Given
    val ingredients = Ingredient(0, Material.WOOD)
    val expected = Set()

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }

  it should "build 1 shovel when it has 1 Wood block" in {
    // Given
    val ingredients = Ingredient(1, Material.WOOD)
    val expected = Set(
      Weapon(ToolType.SHOVEL, Material.WOOD)
    )

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }

  it should "build 1 sword when it has 2 Wood block" in {
    // Given
    val ingredients = Ingredient(2, Material.WOOD)
    val expected = Set(
      Weapon(ToolType.SWORD, Material.WOOD)
    )

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }

  it should "build 1 axe when it has 3 Wood block" in {
    // Given
    val ingredients = Ingredient(3, Material.WOOD)
    val expected = Set(
      Weapon(ToolType.AXE, Material.WOOD)
    )

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }

  it should "build 1 axe and 1 shovel when it has 4 Wood block" in {
    // Given
    val ingredients = Ingredient(4, Material.WOOD)
    val expected = Set(
      Weapon(ToolType.AXE, Material.WOOD),
      Weapon(ToolType.SHOVEL, Material.WOOD)
    )

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }

  it should "build 1 Axe and 1 sword when it has 5 Wood block" in {
    // Given
    val ingredients = Ingredient(5, Material.WOOD)
    val expected = Set(
      Weapon(ToolType.SWORD, Material.WOOD),
      Weapon(ToolType.AXE, Material.WOOD)
    )

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }

  it should "build 1 Axe when it has 6 Wood block" in {
    // Given
    val ingredients = Ingredient(6, Material.WOOD)
    val expected = Set(
      Weapon(ToolType.AXE, Material.WOOD)
    )

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }

  it should "build 1 Axe and 1 shovel when it has 7 Wood block" in {
    // Given
    val ingredients = Ingredient(7, Material.WOOD)
    val expected = Set(
      Weapon(ToolType.AXE, Material.WOOD),
      Weapon(ToolType.SHOVEL, Material.WOOD)
    )

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }

  it should "build 1 Axe and 1 sword when it has 8 Wood block" in {
    // Given
    val ingredients = Ingredient(8, Material.WOOD)
    val expected = Set(
      Weapon(ToolType.AXE, Material.WOOD),
      Weapon(ToolType.SWORD, Material.WOOD)
    )

    //when
    val result = MinecraftToolsGenerator.buildBestPackageFrom(ingredients)

    //Then
    result should be(expected)
  }
}
