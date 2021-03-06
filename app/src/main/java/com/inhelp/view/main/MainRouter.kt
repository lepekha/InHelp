package com.inhelp.view.main

import android.content.Context
import android.view.View
import androidx.annotation.NonNull
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.inhelp.R
import com.inhelp.view.main.main.FragmentMain
import com.inhelp.view.main.save.FragmentSave
import com.inhelp.view.main.reply.FragmentReply
import com.inhelp.view.main.tags.FragmentTags
import com.inhelp.view.main.landimage.LandImageFragment


class MainRouter(val context: Context) {

    private val container: Int = R.id.container

    private val MOVE_DEFAULT_TIME: Long = 300

    lateinit var fragmentManager: FragmentManager

    fun goToMenu() {
        navigateTo(FragmentMain(), container)
    }

    fun goToMenu(view: View) {
        navigateTo(FragmentMain(), container, view = view)
    }

    fun goToLandImage() {
        navigateTo(LandImageFragment(), container)
    }

    fun goToSave() {
        navigateTo(FragmentSave(), container)
    }

    fun goToSave(view: View) {
        navigateTo(FragmentSave(), container, view = view)
    }

    fun goToRepost() {
        navigateTo(FragmentReply(), container)
    }

    fun goToRepost(view: View) {
        navigateTo(FragmentReply(), container, view = view)
    }

    fun goToTags(view: View) {
        navigateTo(FragmentTags(), container, view = view)
    }

    fun goToTags() {
        navigateTo(FragmentTags(), container)
    }

    fun navigateTo(@NonNull fragment: androidx.fragment.app.Fragment, container: Int, addToBackStack: Boolean = true, view: View? = null) {
        val transaction = fragmentManager.beginTransaction()

        view?.let {
            val enterTransitionSet = TransitionSet()
            enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
            enterTransitionSet.duration = MOVE_DEFAULT_TIME
            fragment.sharedElementEnterTransition = enterTransitionSet
            transaction.addSharedElement(it, ViewCompat.getTransitionName(it) ?: "sharedName")
        }

        transaction.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commitAllowingStateLoss()
    }


    fun clearBackStack() {
        val fragmentManager = fragmentManager
        while (fragmentManager.backStackEntryCount != 0) {
            fragmentManager.popBackStackImmediate()
        }
    }

    fun back(): Boolean {
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
            return true
        } else {
            return false
        }
    }
}