package com.example.mobilearcanoid

import android.view.View
import kotlin.math.abs

class ViewLogicCombiner(v: View,l:ViewPlusClass)
{
    init {
        l.xspeed = 1.toFloat()
        l.yspeed = 1.toFloat()
        var z = { v.x = l.x;
                            v.y = l.y;}
       l.stepChange { z }
    }
}