package com.example.mobilearcanoid

import kotlin.concurrent.thread

class CollisionChecker(obj:MutableList<ViewPlusClass>)
{
    var life:Thread
    init {
        life= Thread(Runnable { while (true){
            Thread.sleep(30)
            var t=0
            while (t<obj.size)
            {
                val i=obj[t]
                var k=0
                while (k<obj.size)
                {
                    if(t!=k) {
                        val l = obj[k]
                        if (i.isOverlaping(l)) {
                            i.x -= i.xspeed
                            i.y += i.yspeed
                            if (i.isOverlaping(l)) {
                                i.yspeed = -i.yspeed
                                i.y += 2 * i.yspeed
                                i.x = i.x + i.xspeed
                            } else {
                                i.xspeed = -i.xspeed
                                i.y -= i.yspeed
                            }
                            if (l.destructable)
                            {
                                l.alive=0.0
                                obj.removeAt(k)
                            }
                        }
                    }
                    k++
                }
                t++;
            }
        } })
        life.start()
    }
}