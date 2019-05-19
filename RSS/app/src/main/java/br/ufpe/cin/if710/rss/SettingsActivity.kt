package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceFragment
import android.content.SharedPreferences
import android.preference.Preference

class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    class RSSPreferenceFragment : PreferenceFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferencias)

            // Pega o valor atual de RSS_FEED
            val preference = preferenceManager.findPreference(RSS_FEED)

            // cria listener para atualizar summary ao modificar link do feed
            val mListener = SharedPreferences.OnSharedPreferenceChangeListener{ shared, _ ->
                setText(preference, shared)
            }

            // registra o listener no objeto SharedPreferences
            val shared = preferenceManager.sharedPreferences
            shared.registerOnSharedPreferenceChangeListener(mListener)
        }

        private fun setText(preference: Preference, shared: SharedPreferences) {
            val value = activity.resources.getString(R.string.rssfeed)
            preference.setSummary(shared.getString(RSS_FEED, value))
        }

        override fun onResume() {
            super.onResume()
            // Mostra o endereco atual salvo nas configuracoes
            val shared = preferenceManager.sharedPreferences
            val preference = preferenceManager.findPreference(RSS_FEED)
            setText(preference, shared)
        }

        companion object {
            val RSS_FEED = "rssfeed"
        }
    }
}
