package com.aas.firefragment.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aas.firefragment.R
import com.aas.firefragment.data.model.Employee
import com.aas.firefragment.ui.update.UpdateActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.list_employee.view.*
import java.text.DecimalFormat

class EmployeeAdapter(val context: Context, options: FirestoreRecyclerOptions<Employee>) :
    FirestoreRecyclerAdapter<Employee, EmployeeAdapter.EmployeeHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        return EmployeeHolder(
            LayoutInflater.from(context).inflate(R.layout.list_employee, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int, model: Employee) {
        val db = FirebaseFirestore.getInstance()
        val docId = this.snapshots.getSnapshot(position).id

        val df = DecimalFormat("#,###")
        holder.name.text = model.name
        holder.address.text = model.address
        holder.phone.text = model.phone
        holder.salary.text = "Rp${df.format(model.salary)}"
        holder.btnUpdate.setOnClickListener {
            val employee = Employee(model.name, model.address, model.phone, model.salary)
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", docId)
            intent.putExtra("employee", employee)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener {
            db.collection("employees")
                .document(docId)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Success Delete Data", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { err ->
                    Toast.makeText(context, "Error:${err}", Toast.LENGTH_LONG).show()
                }
        }
    }

    class EmployeeHolder(view : View) : RecyclerView.ViewHolder(view) {
        val name = view.emp_name
        val address = view.emp_address
        val phone = view.emp_phone
        val salary = view.emp_salary
        val btnUpdate = view.btn_to_update
        val btnDelete = view.btn_delete
    }
}