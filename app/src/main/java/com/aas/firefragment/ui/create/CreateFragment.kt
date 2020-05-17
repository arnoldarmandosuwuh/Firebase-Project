package com.aas.firefragment.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.aas.firefragment.R
import com.aas.firefragment.data.model.Employee
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create.*

/**
 * A simple [Fragment] subclass.
 */
class CreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_create.setOnClickListener {
            val employee = Employee(
                edit_name.text.toString(),
                edit_address.text.toString(),
                edit_phone.text.toString(),
                Integer.parseInt(edit_salary.text.toString())
            )

            save(employee)
        }
    }

    fun save(employee: Employee) {
        val db = FirebaseFirestore.getInstance()

        //Menampilkan progressbar ketika method save dipanggil
        activity?.progress_circular?.visibility = View.VISIBLE

        //Menghilangkan form
        activity?.linear?.visibility = View.GONE

        //Menentukan collection
        db.collection("employees")

            //Method add untuk menambahkan data kedalam firebase
            .add(employee)
            .addOnSuccessListener { success ->
                Toast.makeText(activity, "Success Insert Employee", Toast.LENGTH_LONG).show()

                //Restart Activity
                val intent = activity?.intent
                activity?.finish()
                activity?.startActivity(intent)
            }
            .addOnFailureListener { err ->
                Toast.makeText(activity, "Error: ${err}", Toast.LENGTH_LONG).show()
            }
    }

}
