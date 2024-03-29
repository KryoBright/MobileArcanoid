package com.example.mobilearcanoid

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.graphics.drawable.toDrawable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.DisplayMetrics
import kotlin.math.roundToInt
import java.util.Random


class MainActivity : AppCompatActivity(),ColissionShowView {

    lateinit var player2:MediaPlayer
    lateinit var player1:MediaPlayer

    override fun doOnCollisions() {
        GlobalScope.launch {
            withContext(Dispatchers.Main)
            {
                if (player2.isPlaying)
                {
                    player2.stop()
                }
                player2.reset()
                player2.start()
            }
        }
}
    fun viewRec(v:ViewPlusClass,obj:MutableList<ViewPlusClass>){
        GlobalScope.launch {
            withContext(Dispatchers.Main)
            {
                v.vi.background.alpha=v.alive
                if (v.alive==254)
                {
                    obj.add(v)
                }
            }
        }
    }

    fun lose(con:Context)
    {
        GlobalScope.launch {
            withContext(Dispatchers.Main)
            {
                ScoreKeeper.playing = false
                var int = Intent(con, LeaderboardActivity::class.java)
                int.putExtra("score", ScoreKeeper.score)
                Thread.sleep(60)
                startActivity(int)
                finish()
            }
        }
    }

    fun displayMessage(str:String)
    {
        GlobalScope.launch {
                withContext(Dispatchers.Default)
                {
                    var flop=0
                    while (flop<3) {
                        withContext(Dispatchers.Main)
                        {
                            bonusTextView.text = str
                            if (flop == 0) bonusTextView.visibility = View.VISIBLE;
                            if (flop == 1) bonusTextView.visibility = View.INVISIBLE;
                            flop++;
                        }
                        Thread.sleep(500)
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ScoreKeeper.playing=true
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        val height = displayMetrics.heightPixels-50
        val width = displayMetrics.widthPixels
        ScoreKeeper.score=0
        var comList= mutableListOf<ViewPlusClass>()
        player2= MediaPlayer.create(this,R.raw.glassbreaksound)
        var r=Random()
        if (r.nextInt().rem(3)==0)
        {
            player1=MediaPlayer.create(this,R.raw.dreamenddischarger)
        }
        else if (r.nextBoolean())
        {
            player1=MediaPlayer.create(this,R.raw.goldensneer)
        }
        else player1=MediaPlayer.create(this,R.raw.happymariainstrumental)
        player1.isLooping=true
        var i=0
        while(i<20)
        {
            var vTmp= View(this)
            var params : LayoutParams = LayoutParams(
                60*width/400, // This will define text view width
                40*height/600)
            vTmp.layoutParams=params
            vTmp.background=Color.CYAN.toDrawable()
            vTmp.setBackgroundColor(Color.CYAN)
           // layoutView.addView(vTmp)
            var block=ViewPlusClass(vTmp,0,0,(i.rem(5)*80*width/400),(i.div(5)*50*height/600),true,width,height)
            var rand = Random()
            block.destLambda={if (rand.nextDouble()>0.75) ScoreKeeper.bonus=1
                else if (rand.nextDouble()<0.25) ScoreKeeper.bonus=2
            }
            comList.add(block)
            i++;
        }

        comList.map { layoutView.addView(it.vi) }
        comList.map {
            var spLam={viewRec(it,comList)};
                it.stepChange(spLam)}
        var view= View(this)
        var params : LayoutParams = LayoutParams(
            10*width/400, // This will define text view width
            10*height/600)
        view.layoutParams=params
        view.background=Color.RED.toDrawable()
        //view.setBackgroundColor(Color.RED)
        view.setBackgroundResource(R.drawable.ball)
        // layoutView.addView(vTmp)
        var ball=ViewPlusClass(view,10,10,width/2,height/2,false,width,height+50)
        var con:Context=this
        ball.stepChange { if (ScoreKeeper.bonus==1){
        ScoreKeeper.bonus--
        ball.xspeed*=1.1F
        ball.yspeed*=1.1F
            displayMessage("Faster ball!")
        }
        }
        ball.st2Lambda={ if (ball.y>height) lose(con) }
        comList.add(ball)
        layoutView.addView(view)
        CollisionChecker(comList,this)
        var mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        var vTmp= View(this)
        params  = LayoutParams(
            150, // This will define text view width
            40)
        vTmp.layoutParams=params
        vTmp.background=Color.GREEN.toDrawable()
        vTmp.setBackgroundResource(R.drawable.platform)
        //vTmp.setBackgroundColor(Color.GREEN)
        // layoutView.addView(vTmp)
        var platform=ViewPlusClass(vTmp,0,0,width/2,height-80,false,width,height)
        platform.stepChange2 {
            if (ScoreKeeper.bonus==2)
            {
                ScoreKeeper.bonus=0
                platform.width= (0.9*platform.width).roundToInt()
                displayMessage("Smaller Platform!")
                GlobalScope.launch {
                    withContext(Dispatchers.Main)
                    {
                        params  = LayoutParams(
                            (platform.width*0.9).roundToInt(), // This will define text view width
                            40)
                        platform.vi.layoutParams=params
                    }
                }
            }
        }
        comList.add(platform)
        PlatformController(platform,mSensorManager)
        layoutView.addView(vTmp)
        i++;

    }

    override fun onPause() {
        ScoreKeeper.playing=false
        Thread.sleep(30)
        player1.stop()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (!ScoreKeeper.playing)
        {
            lose(this)
        }
        else ScoreKeeper.playing=true
        player1.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        player1.release()
        player2.release()
    }
}
