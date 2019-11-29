package com.example.mobilearcanoid

import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout.LayoutParams
import androidx.core.graphics.drawable.toDrawable
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Types.NULL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 /*       var newView=ViewPlusClass()
        newView.xspeed=1.toFloat()
        newView.yspeed=1.toFloat()
        var z={view.x=newView.x;view.y=newView.y; }
        newView.stepChange { z }*/
        var comList= mutableListOf<ViewPlusClass>()
        var i=0
        while(i<20)
        {
            var vTmp= View(this)
            var params : LayoutParams = LayoutParams(
                80, // This will define text view width
                40)
            vTmp.layoutParams=params
            vTmp.background=Color.CYAN.toDrawable()
            vTmp.setBackgroundColor(Color.CYAN)
            layoutView.addView(vTmp)
            comList.add(ViewPlusClass(vTmp,0,0,(i.rem(5)*100),(i.div(5)*50),true))
            i++;
        }
        var newCom=ViewPlusClass(view,10,10,200,500,false)
  //      var newCom2=ViewPlusClass(view2,0,0,50,200)
        comList.add(newCom)
        var newChkr=CollisionChecker(comList)
/*
        var newView=ViewPlusClass(this)
        newView.x=(100).toFloat()
        newView.y=(100.0).toFloat()
        var params : ActionBar.LayoutParams = ActionBar.LayoutParams(200,200)
        newView.background= Color.CYAN.toDrawable()
        newView.layoutParams=params*/
    //   var t= Thread(Runnable {  while (true){view.x=newView.x;view.y=newView.y;Thread.sleep(30)}  })
    //    t.start()
    }
}
