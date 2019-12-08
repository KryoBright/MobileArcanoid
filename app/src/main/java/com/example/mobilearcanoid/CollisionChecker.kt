package com.example.mobilearcanoid

import kotlin.concurrent.thread

class CollisionChecker(obj:MutableList<ViewPlusClass>,shower:ColissionShowView)
{
    var life:Thread
    init {
        life= Thread(Runnable { while (ScoreKeeper.playing){
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
                                l.alive=0
                                l.onDestroy()
                                ScoreKeeper.score++
                                obj.removeAt(k)
                                shower.doOnCollisions()
                            }
                            if (i.destructable && l.destructable) {
                                i.yspeed += l.yspeed
                                i.xspeed += l.xspeed
                            }
                        }
                    }
                    k++
                }
                t++;
            }

            Thread.sleep(30)
        } })
        life.start()
    }
}