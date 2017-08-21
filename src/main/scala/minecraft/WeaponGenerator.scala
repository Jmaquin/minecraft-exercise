package minecraft

import minecraft.Material.Material
import minecraft.ToolType.ToolType

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