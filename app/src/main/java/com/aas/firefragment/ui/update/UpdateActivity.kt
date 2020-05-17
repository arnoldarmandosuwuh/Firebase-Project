package com.aas.firefragment.ui.update

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.aas.firefragment.MainActivity
import com.aas.firefragment.R
import com.aas.firefragment.data.model.Employee
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    lateinit var employee: Employee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        employee = intent.getSerializableExtra("employee") as Employee

        update_name.setText(employee.name)
        update_address.setText(employee.address)
        update_phone.setText(employee.phone)
        update_salary.setText(employee.salary.toString())

        btn_update.setOnClickListener {
            val employees = Employee(
                update_name.text.toString(),
                update_address.text.toString(),
                update_phone.text.toString(),
                Integer.parseInt(update_salary.text.toString())
            )

            updateData(employees)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun updateData(employee: Employee) {

        val db = FirebaseFirestore.getInstance()

        //Mendapatkan id dari intent
        val docId = intent.getStringExtra("id")

        //Tampilkan progress bar
        progress_update.visibility = View.VISIBLE

        //Hilangkan layout
        linear_update.visibility = View.GONE

        db.collection("employees")

            //Pilih document yang mau di update
            .document(docId)

            //Set data di document dengan data baru
            .set(employee)

            .addOnSuccessListener {
                Toast.makeText(this, "Success Update Data", Toast.LENGTH_LONG).show()

                val i = Intent(this, MainActivity::class.java)
                //Clear flag top agar tidak bisa di back
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(i)
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error: ${err}", Toast.LENGTH_LONG).show()
            }
    }

}
