package com.example.bourbon.activities.harish_activities.recycler_view_acts

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bourbon.R
import com.example.bourbon.activities.harish_activities.adapters.InfectedPeopleAdapter
import com.example.bourbon.activities.harish_activities.model.PersonLocModel
import com.example.bourbon.databinding.RvInfectedPeopleInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import print.Print

class InfectedPeopleInfo : AppCompatActivity() {

    private var binding: RvInfectedPeopleInfoBinding?=null
    private val p= Print(this)
    private var products= arrayListOf<PersonLocModel>()

    private val adapter=InfectedPeopleAdapter(products)
    private val database= FirebaseDatabase.getInstance()
    private var auth: FirebaseAuth?=null


    fun configRecyclerView() {
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.setLayoutManager(LinearLayoutManager(this))

        binding?.recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding?.recyclerView?.adapter = ScaleInAnimationAdapter(adapter).apply{
            setFirstOnly(false)
            setDuration(1000)
            setHasStableIds(false)
            setInterpolator(OvershootInterpolator(.100f))
        }
        //binding?.recyclerView?.itemAnimator= SlideInUpAnimator(OvershootInterpolator(1f))
    }
    fun init(){
        binding= DataBindingUtil.setContentView(this, R.layout.rv_infected_people_info)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        configRecyclerView()
        fetchProductsFromFirebase()
    }

    fun fetchProductsFromFirebase(){
        database.getReference("Infected")
                .addValueEventListener(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(usersList: DataSnapshot) {

                        if(usersList.exists()){
                            var i=0
                            for(obj in usersList.children){

                                for(innerObj in obj.children)
                                {
                                    val personLoc=innerObj?.getValue(PersonLocModel::class.java) ?: PersonLocModel("err","err")
                                    //p.sprintf("dateAndPERSON : ${personLoc.dateAndTime}")
                                    products.add(personLoc)
                                    println("Person Addr: ${personLoc.addr}")
                                    adapter.notifyItemInserted(i)
                                    i+=1
                                }



                            }
                        }

                    }

                })
    }
}
