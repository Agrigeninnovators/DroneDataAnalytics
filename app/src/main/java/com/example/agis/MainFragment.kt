package com.example.agis
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment() {
    var cropStatus: Array<String> = arrayOf(
        "Healthy", "Diseased", "Unknown", "Healthy", "Diseased",
        "Unknown", "Healthy", "Diseased", "Unknown", "Healthy",
        "Diseased", "Unknown", "Healthy", "Diseased", "Unknown",
        "Healthy", "Diseased", "Unknown", "Healthy", "Diseased",
        "Healthy", "Unknown", "Healthy", "Diseased", "Healthy",
        "Diseased", "Unknown", "Healthy", "Diseased", "Healthy",
        "Unknown", "Healthy", "Diseased", "Unknown", "Healthy",
        "Diseased", "Unknown", "Healthy", "Diseased", "Unknown"
    )

    var colors: Array<String> = arrayOf(
        "#00FF00", "#FF0000", "#808080", "#00FF00", "#FF0000",
        "#808080", "#00FF00", "#FF0000", "#808080", "#00FF00",
        "#FF0000", "#808080", "#00FF00", "#FF0000", "#808080",
        "#00FF00", "#FF0000", "#808080", "#00FF00", "#FF0000",
        "#00FF00", "#808080", "#00FF00", "#FF0000", "#00FF00",
        "#FF0000", "#808080", "#00FF00", "#FF0000", "#00FF00",
        "#808080", "#00FF00", "#FF0000", "#808080", "#00FF00",
        "#FF0000", "#808080", "#00FF00", "#FF0000", "#808080"
    )

    private val actionTaken = HashMap<Int, Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        val gridView = rootView.findViewById<GridView>(R.id.farming_plot)
        val adapter = GridAdapter(requireContext(), cropStatus, colors)
        gridView.adapter = adapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            try {
                Log.d("MainFragment", "Item clicked at position: $position")
                if (position >= 0 && position < cropStatus.size) {
                    val status = cropStatus[position]
                    Log.d("MainFragment", "Crop Status: $status")
                    if ("Unknown" != status) {
                        showCropStatusPopup(position, rootView)
                    } else {
                        showErrorDialog("Unknown crop status. Please try again later.", rootView)
                    }
                } else {
                    Log.e("MainFragment", "Invalid position: $position")
                    showErrorDialog("Invalid position.", rootView)
                }
            } catch (e: Exception) {
                Log.e("MainFragment", "Error in onItemClick: ${e.message}")
            }
        }
        val chatButton: FloatingActionButton = rootView.findViewById(R.id.floatingActionButton)
        chatButton.setOnClickListener {
            val intent = Intent(requireContext(), ChatActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    private fun showCropStatusPopup(position: Int, rootView: View) {
        val customView = layoutInflater.inflate(R.layout.popup_crop_status, null)
        val popupWindow = PopupWindow(customView, 600, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable = true
        popupWindow.isTouchable = true
        popupWindow.isOutsideTouchable = true
        val cropStatusText = customView.findViewById<TextView>(R.id.cropStatus)
        val takeActionButton = customView.findViewById<Button>(R.id.takeActionButton)
        cropStatusText.text = cropStatus[position]
        if ("Diseased" == cropStatus[position]) {
            takeActionButton.visibility = View.VISIBLE
        } else {
            takeActionButton.visibility = View.GONE
        }
        popupWindow.showAtLocation(rootView.findViewById(R.id.farming_plot), Gravity.CENTER, 0, 0)
        takeActionButton.setOnClickListener {
            actionTaken[position] = true
            popupWindow.dismiss()
            Toast.makeText(requireContext(), "Action taken for crop at position $position", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showErrorDialog(message: String, rootView: View) {
        val customView = layoutInflater.inflate(R.layout.popup_error_message, null)
        val popupWindow = PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val errorMessageText = customView.findViewById<TextView>(R.id.errorMessage)
        val okButton = customView.findViewById<Button>(R.id.okButton)
        errorMessageText.text = message

        popupWindow.showAtLocation(rootView.findViewById(R.id.farming_plot), Gravity.CENTER, 0, 0)
        okButton.setOnClickListener { popupWindow.dismiss() }
    }
}