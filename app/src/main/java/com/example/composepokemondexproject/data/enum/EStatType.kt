package com.example.composepokemondexproject.data.enum

import androidx.compose.ui.graphics.Color

/**
 * Create by Nguyen Thanh Toan on 11/9/21
 *
 */
enum class EStatType(val color: Color) {
    HP(Color.Red),
    AT(Color.Yellow),
    DF(Color.Blue),
    SP_AT(Color.Magenta),
    SP_DF(Color.Cyan),
    Speed(Color.Green),
    EXP(Color.LightGray)
}