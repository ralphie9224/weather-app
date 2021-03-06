package net.rmoreno.weatherapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*
import net.rmoreno.weatherapp.R
import net.rmoreno.weatherapp.SettingsInteractor
import net.rmoreno.weatherapp.presenters.SettingsPresenter
import net.rmoreno.weatherapp.repositories.WeatherRepository


class SettingsActivity : Activity(), SettingsView {
    private var SHARED_PREFERENCES = "MyPrefs"
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        presenter = SettingsPresenter(
                SettingsInteractor(WeatherRepository(preferences))
        )

        done.setOnClickListener {
            if (isValidInput(temperature.text.toString())) {
                presenter.updateSweather(Integer.parseInt(temperature.text.toString()))
                displayToast("Sweater Weather Updated!")
                val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                temperature.setText("")
                displayToast("Invalid input")
            }
        }
    }

    override fun displaySweather() {
        //TODO show the sweater weather
    }

    private fun isValidInput(input: String): Boolean {
        try {
            Integer.parseInt(input)
            if (input.isNotEmpty()) {
                return true
            }

        } catch (e: NumberFormatException) {
            Log.e("Invalid input", e.message)
            return false
        }

        return true
    }

    private fun displayToast(display: String) {
        Toast.makeText(this@SettingsActivity, display, Toast.LENGTH_SHORT).show()
    }

}
