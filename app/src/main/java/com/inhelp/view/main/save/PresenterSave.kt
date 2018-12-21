package com.inhelp.view.main.save

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import com.inhelp.core.models.InstagramUrlData
import com.inhelp.view.main.MainRouter
import com.inhelp.view.mvp.BaseMvpPresenterImpl
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.*


class PresenterSave constructor(private val mainRouter: MainRouter, private val instagramUrlData: InstagramUrlData) : BaseMvpPresenterImpl<ViewSave>() {

    private lateinit var mIntentSaveService: Intent
    private lateinit var mLoadedBitmap: Bitmap

    private lateinit var mPreviewPhotos: ArrayList<String>

    override fun attachView(view: ViewSave) {
        super.attachView(view)
//        mIntentSaveService = Intent(context, ServiceSavePhoto::class.java)
//        context.startService(mIntentSaveService)
    }

    override fun detachView() {
        super.detachView()
//        context.stopService(mIntentSaveService)
    }

    override fun onBackPress() {
        mainRouter.back()
    }

    fun pressPreviewItem(previewPosition: Int){
        view?.setPhoto(mPreviewPhotos[previewPosition])
    }


    fun getPhoto() = GlobalScope.launch(Dispatchers.Main) {
//        val image = instagramUrlData.getInstagramData().await()
        mPreviewPhotos = instagramUrlData.getPhotos().await()
//        mLoadedBitmap = photos
        if(mPreviewPhotos.count() > 1) view?.setPreviewPhotos(mPreviewPhotos)
        view?.setPhoto(mPreviewPhotos.first())
    }

    fun getPermissionAndSavePhoto() {
        Dexter.withActivity(view?.getCurrentActivity())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//                        context.saveBitmap(mLoadedBitmap)
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }).check()
    }


}