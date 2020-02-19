package com.nekobitlz.documentslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nekobitlz.documentslist.features.DocumentsFragment

class DocumentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documents)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, DocumentsFragment())
                .commit()
        }
    }
}
