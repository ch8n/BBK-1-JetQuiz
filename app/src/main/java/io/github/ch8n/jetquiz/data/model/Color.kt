package io.github.ch8n.jetquiz.data.model

data class Color(
    val name: String, // <- Name of Color
    val value: String, // <- Hex value of Color
)

/**
 * sample color for out application game
 */
val colors = listOf(
    Color(name = "Tiffany Blue",value ="#BCECE0"),
    Color(name = "Cyan",value ="#36EEE0"),
    Color(name = "Hot Pink",value ="#F652A0"),
    Color(name = "Cornflower",value ="#4C5270"),

    Color(name = "Purple",value ="#603F8B"),
    Color(name = "Aqua",value ="#B4FEE7"),
    Color(name = "Violet",value ="#A16AE8"),
    Color(name = "Fuchsia",value ="#FD49A0"),

    Color(name = "Cyan",value ="#FD49A0"),
    Color(name = "Coral",value ="#FF7077"),
    Color(name = "Rose Quartz",value ="#FFE9E4"),
    Color(name = "Orange",value ="#FFB067"),

    Color(name = "Turquoise",value ="#05E0E9"),
    Color(name = "Rose Red",value ="#FF2768"),
    Color(name = "Seafoam Green",value ="#CFEED1"),
    Color(name = "Carafe",value ="#4E1A3D"),

    Color(name = "Amber",value ="#FBBC54"),
    Color(name = "Peach",value ="#F8DCB0"),
    Color(name = "Coral",value ="#FC5E70"),
    Color(name = "Salmon",value ="#F2B190"),
)