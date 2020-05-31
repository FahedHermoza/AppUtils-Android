package com.fahed.developer.hideapikey

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_apikeys.*

/**
 * A placeholder fragment containing a simple view.
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

        textViewFile.text = BuildConfig.API_KEY_FACEBOOK_APLICATION_ID +" - "+ BuildConfig.API_KEY_FACEBOOK_APLICATION_ID
        textViewValue.text = getString(R.string.API_KEY_FACEBOOK_APLICATION_ID) + " - "+ getString(R.string.API_KEY_FACEBOOK_APLICATION_ID)
    }
}
