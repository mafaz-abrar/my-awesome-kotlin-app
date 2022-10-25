package com.example.myawesomekotlinproject


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    // Add the ViewModel as a member variable using the viewModels delegate
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordApplication).repository)
    }

    // Activity Result Code from New Activity
    private val newWordActivityRequestCode = 1

    // onCreate, called when Application is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observing the allWords variable
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.submitList(it) }
        })

        // Create and Launch an intent when FAB clicked
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    // Called when Result is received from Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            // Get the word from the reply Intent
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(0, it)
                wordViewModel.insert(word)
            }
        } else {
            // Make a toast showing no word saved
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}