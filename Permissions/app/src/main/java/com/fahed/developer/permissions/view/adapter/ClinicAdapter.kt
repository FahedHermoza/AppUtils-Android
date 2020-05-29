package com.fahed.developer.permissions.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahed.developer.permissions.R
import com.fahed.developer.permissions.utils.inflate
import com.fahedhermoza.developer.examplenote01.Models.Clinic
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class ClinicAdapter(private var listClinics: MutableList<Clinic>, var handler: ClinicHandle) : RecyclerView.Adapter<ClinicAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(R.layout.recyclerview_item)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listClinics[position], handler)
    }

    override fun getItemCount() = listClinics.size

    fun setClinics(clinicList: List<Clinic>) {
        this.listClinics.clear()
        this.listClinics.addAll(clinicList)
        notifyDataSetChanged()
    }

    fun updateClinics(clinics: MutableList<Clinic>) {
        for(publication in clinics){
            this.listClinics.add(publication)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("MissingPermission")
        fun bind(clinic: Clinic, handler: ClinicHandle) {

            itemView.textViewTitle.text = clinic.title
            itemView.textViewLocation.text = clinic.location
            itemView.textViewPhone.text = clinic.phone

            itemView.imageCall.setOnClickListener{
                //Permission in Adapter
                handler.bodyImageCall(clinic.phone)
            }

            itemView.imageCamera.setOnClickListener{
                //Permission y startActivityForResult en Adapter
                handler.bodyImageCamera()
            }
        }

    }

    interface ClinicHandle{
        fun bodyImageCamera()
        fun bodyImageCall(phone: String)
    }
}