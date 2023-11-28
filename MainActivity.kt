package com.example.game

import android.R.attr.x
import android.R.attr.y
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

// тип для координат
data class Coord(val x: Int, val y: Int)

class MainActivity : AppCompatActivity() {

    lateinit var tiles: Array<Array<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiles = Array(4) { Array(4) { View(this) } }
        tiles = arrayOf(
            arrayOf(
                findViewById(R.id.t00),
                findViewById(R.id.t10),
                findViewById(R.id.t20),
                findViewById(R.id.t30)
            ),
            arrayOf(
                findViewById(R.id.t01),
                findViewById(R.id.t11),
                findViewById(R.id.t21),
                findViewById(R.id.t31)
            ),
            arrayOf(
                findViewById(R.id.t02),
                findViewById(R.id.t12),
                findViewById(R.id.t22),
                findViewById(R.id.t32)
            ),
            arrayOf(
                findViewById(R.id.t03),
                findViewById(R.id.t13),
                findViewById(R.id.t23),
                findViewById(R.id.t33)
            )
        )
        initField()
    }

    fun getCoordFromString(s: String): Coord {

        val numx = s[0].toString().toInt()
        val numy = s[1].toString().toInt()

        // Создание и вывод координат в лог
        val coord = Coord(numx, numy)
        Log.d("Coordinate", "Координаты: $coord")

        return coord
    }

    fun changeColor(view: View) {
        val brightColor = resources.getColor(R.color.bright)
        val darkColor = resources.getColor(R.color.dark)
        val drawable = view.background as ColorDrawable
        if (drawable.color ==brightColor ) {
            view.setBackgroundColor(darkColor)
        } else {
            view.setBackgroundColor(brightColor)
        }
    }


    fun onClick(v: View) {
            val Coord = getCoordFromString(v.tag.toString())
            changeColor(v)

            for (i in 0 until 4) {
                changeColor(tiles[Coord.y][i])  // Изменение цвета для всех клеток в столбце
                changeColor(tiles[i][Coord.x])  // Изменение цвета для всех клеток в строке
            }
        //changeColor(tiles[Coord.x][Coord.y])
            checkVictory()
        }


    fun checkVictory() {
        val firstColor = (tiles[0][0].background as ColorDrawable).color

        for (i in 0 until 4) {
            for (j in 0 until 4) {
                val currentColor = (tiles[i][j].background as ColorDrawable).color
                if (currentColor != firstColor) {
                    return // Не все плитки имеют одинаковый цвет
                }
            }
        }

        showToast("Поздравляю! Вы выиграли!")
    }

    fun initField() {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                val randomColor = if (Math.random() < 0.5) R.color.bright else R.color.dark
                tiles[i][j].setBackgroundColor(ContextCompat.getColor(this, randomColor))
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

