package com.example.mobilearcanoid

import android.graphics.Rect
import android.view.View
import kotlin.math.abs


class ViewPlusClass(v:View,xspeedp:Int,yspeedp:Int,xi:Int,yi:Int,dest:Boolean) {
    var alive=255
    var x=xi.toFloat()
    var y=yi.toFloat()
    var vi=v
    var destructable=dest
    var exist=true
    var maxx=400
    var maxy=600
    var width=v.layoutParams.width
    var height=v.layoutParams.height
    var xspeed=(xspeedp).toFloat()
    var yspeed=(yspeedp).toFloat()
    var stepLambda={x+=xspeed;y+=yspeed}
    private var life = Thread(Runnable{ while (exist) {
        stepLambda();
        if (alive>=255) {
            v.x = x;
            v.y = y;

            if (abs(x - maxx / 2) > maxx / 2) {
                xspeed = -xspeed
            }
            if (abs(y - maxy / 2) > maxy / 2) {
                yspeed = -yspeed
            }
        }
        else
        {
            alive++
            if (alive>255)
            {
                alive=255
            }

        }
        Thread.sleep(30)

    }

        destructor()
    })
    init {
        v.background.alpha=255
        life.start()
    }


    fun stepChange(step2:()->Unit)
    {
        stepLambda={x+=xspeed;y+=yspeed;step2()}
    }

    fun destructor()
    {
       //life.interrupt()
        alive=0
        exist=false
       // (vi.getParent() as ViewManager).removeView(vi)
    }

    fun isOverlaping(a:ViewPlusClass):Boolean
    {
        var rect1= Rect(x.toInt(),y.toInt(),(x+width).toInt(),(y+height).toInt())
        var rect2= Rect(a.x.toInt(),a.y.toInt(),(a.x+a.width).toInt(),(a.y+a.height).toInt())
        return Rect.intersects(rect1,rect2)
    }
}