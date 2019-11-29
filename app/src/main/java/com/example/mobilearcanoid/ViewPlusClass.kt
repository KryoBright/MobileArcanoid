package com.example.mobilearcanoid

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import kotlin.concurrent.thread
import kotlin.math.abs
import android.view.ViewManager



class ViewPlusClass(v:View,xspeedp:Int,yspeedp:Int,xi:Int,yi:Int,dest:Boolean) {
    var alive=true
    var x=xi.toFloat()
    var y=yi.toFloat()
    var vi=v
    var destructable=dest
    var maxx=400
    var maxy=600
    var width=v.layoutParams.width
    var height=v.layoutParams.height
    var xspeed=(xspeedp).toFloat()
    var yspeed=(yspeedp).toFloat()
    var stepLambda={x+=xspeed;y+=yspeed}
    var life = Thread(Runnable{ while (alive) {
        stepLambda();v.x = x;v.y = y;Thread.sleep(30);

        if (abs(x-maxx/2) >maxx/2)
        {
            xspeed=-xspeed
        }
        if (abs(y-maxy/2) >maxy/2)
        {
            yspeed=-yspeed
        }
    }

        destructor()
    })
    init {
        life.start()
    }


    fun stepChange(step2:()->Unit)
    {
        stepLambda={x+=xspeed;y+=yspeed;step2()}
    }

    fun destructor()
    {
       //life.interrupt()
        alive=false;
       // (vi.getParent() as ViewManager).removeView(vi)
    }

    fun isOverlaping(a:ViewPlusClass):Boolean
    {
        var rect1= Rect(x.toInt(),y.toInt(),(x+width).toInt(),(y+height).toInt())
        var rect2= Rect(a.x.toInt(),a.y.toInt(),(a.x+a.width).toInt(),(a.y+a.height).toInt())
        return Rect.intersects(rect1,rect2)
    }
}