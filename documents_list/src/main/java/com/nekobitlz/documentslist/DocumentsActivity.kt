package com.nekobitlz.documentslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nekobitlz.documentslist.features.auth.AuthFragment

class DocumentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documents)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, AuthFragment())
                .commit()
        }
    }
}
