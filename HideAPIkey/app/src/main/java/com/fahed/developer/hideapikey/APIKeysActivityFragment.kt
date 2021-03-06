package com.fahed.developer.hideapikey

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_apikeys.*

/**
 * Ocultando las keys en un archivo externo y recuperando con BuildConfigs
 * 1. Agregar API key="value" al archivo apikey.properties (creado en el root)
 *
 * 2. Agregar una entrada a build.gradle (module: ) (2 tipos: file and value)
 *
 *      * Al ofuscarlo Proguard: No es visible el archivo BuildConfig.class (key Facebook)
 *
 *      BuildConfig: Al ofuscarlo y decompilarlo(JD-GUI) es visible el valor en el codigo: setText("551745000360889 - 551745000360889")
 *
 *      Value: Al ofuscarlo y decompilarlo(JD-GUI) no es visible el valor en el codigo: stringBuilder.append(a(2131689472));
 *
 *      * Al convertir a apk a .zip no existe el archivo apikey.properties
 *
 * 3.Eliminar apikey.properties para subirlo a github
 *
 *      Add apikey.properties a .gitignore
 *
 * 3.1 Error al subirlo a Github, porque su archivo apikey.properties sigue siendo visible
 *      https://stackoverflow.com/a/11451731/6254339
 *          git rm -r --cached .
 *          git add .
 *          git commit -m "fixed untracked files"
 * 4. Commit a tu repo
 *
 */
class APIKeysActivityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apikeys, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //textViewFile.text = BuildConfig.API_KEY_FACEBOOK_APLICATION_ID +" - "+ BuildConfig.API_KEY_FACEBOOK_APLICATION_ID
        textViewValue.text = getString(R.string.API_KEY_FACEBOOK_APLICATION_ID) + " - "+ getString(R.string.API_KEY_FACEBOOK_APLICATION_ID)
    }
}
