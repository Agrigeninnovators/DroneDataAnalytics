package com.example.agis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PlotAdapter(private val context: Context, private val data: Array<Array<Array<Int>>>) : BaseAdapter() {

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = position.toLong() // Return position as ID

    override fun getCount(): Int {
        // Calculate total items in the 2D array
        return data.sumOf { it.size }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val (row, col) = getRowColFromPosition(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.grid_items, parent, false)
        val textView: TextView = view.findViewById(R.id.grid_text)

        // Access the 2D array using row and col
        textView.text = data[row][col].toString()
        return view
    }

    private fun getRowColFromPosition(position: Int): Pair<Int, Int> {
        val rows = data.size // Number of rows
        val cols = data[0].size // Number of columns

        // Calculate row and column from the position
        val row = position / cols // Determine the row
        val col = position % cols // Determine the column

        return Pair(row, col) // Return as a Pair
    }
}
