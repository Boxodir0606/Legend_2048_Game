package uz.boxodir.test2048game.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import uz.boxodir.test2048game.model.SideEnum
import kotlin.math.abs

class MyTouchListener(context:Context)  : View.OnTouchListener {

    private val myGestureDetector = GestureDetector(context, MyGestureListener())
    private var myMovementSideListener : ((SideEnum) -> Unit)?= null

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        myGestureDetector.onTouchEvent(event)
        return true
    }

    inner class MyGestureListener: GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {


            if (abs(e2.x - e1!!.x)> 100 || abs(e2.y - e1.y) > 100){
                if (abs(e2.x - e1.x) < abs(e2.y - e1.y)){
                    if (e2.y > e1.y){
                        myMovementSideListener?.invoke(SideEnum.DOWN)
                    }else{
                        myMovementSideListener?.invoke(SideEnum.UP)
                    }
                }
                else{
                    if (e2.x > e1.x){
                        myMovementSideListener?.invoke(SideEnum.RIGHT)
                    }else{
                        myMovementSideListener?.invoke(SideEnum.LEFT)
                    }
                }

            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }
    fun setMyMovementSideListener(block:(SideEnum) -> Unit){
        myMovementSideListener = block
    }
}