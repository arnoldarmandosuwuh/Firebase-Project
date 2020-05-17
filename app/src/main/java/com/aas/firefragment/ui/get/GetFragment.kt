package com.aas.firefragment.ui.get

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.aas.firefragment.R
import com.aas.firefragment.data.model.Employee
import com.aas.firefragment.ui.adapter.EmployeeAdapter
import com.facebook.shimmer.Shimmer
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_get.*

/**
 * A simple [Fragment] subclass.
 */
class GetFragment : Fragment() {

    lateinit var adapter: EmployeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
        shimmer.startShimmer()
    }

    override fun onPause() {
        shimmer.stopShimmer()
        super.onPause()
        adapter.stopListening()
    }

    private fun loadData() {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("employees")

        //Membangun FirestoreRecyclerOptions untuk mendapatkan querynya
        val option = FirestoreRecyclerOptions.Builder<Employee>()
            .setQuery(query, Employee::class.java)
            .build()

        //Buat objek adapter
        adapter = EmployeeAdapter(activity!!.baseContext, option)

        //Cek apakah query akan jalan atau tidak
        //Kalau jalan akan kodingan di blok addOnSuccessListener akan dijalankan
        query.get().addOnSuccessListener {

            //Berhentikan animasi shimmer
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE

            //Membangun recyclerview adapter
            activity?.rv_emp?.setHasFixedSize(true)
            activity?.rv_emp?.adapter = adapter
            activity?.rv_emp?.layoutManager = LinearLayoutManager(activity)

            Toast.makeText(activity, "Success: ${it}", Toast.LENGTH_LONG).show()
        }.addOnFailureListener { err ->
            Toast.makeText(activity, "Error: ${err}", Toast.LENGTH_LONG).show()
        }

    }

}
