package com.inhelp.view.main.main

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.inhelp.R
import com.inhelp.core.models.data.Menu
import com.inhelp.view.mvp.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.inhelp.core.LogConsoleService
import android.widget.Toast




class FragmentMain : BaseMvpFragment<ViewMain, PresenterMain>(), ViewMain {
    override fun backPress() {
    }

    private val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084

    override val presenter: PresenterMain by inject()

    private lateinit var mMenuRvAdapter: MenuRvAdapter
    private lateinit var mPhotosRvAdapter: PhotosRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initPhotoList()
        presenter.updateList()
    }

    private fun initList() {
        menuList.layoutManager = LinearLayoutManager(getCurrentContext(), RecyclerView.VERTICAL, false)
        mMenuRvAdapter = MenuRvAdapter(presenter.menuList,
                clickListener = { menuItem ->
                    when (menuItem) {
                        Menu.MENU_REPOST_PHOTO -> {
                            mPhotosRvAdapter.notifyDataSetChanged()
//                            presenter.pressRepost()
                        }
                        Menu.MENU_SAVE_PHOTO -> presenter.pressSave()
                        Menu.MENU_TAGS -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getCurrentContext())) {


                                //If the draw over permission is not available open the settings screen
                                //to grant the permission.
                                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                        Uri.parse("package:" + getCurrentContext().packageName))
                                startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
                            } else {
                                getCurrentContext().startService(Intent(getCurrentContext(), LogConsoleService::class.java))
                                activity?.finish()
                            }
//                            presenter.pressTags()
                        }
                    }
                })
        menuList?.adapter = mMenuRvAdapter
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                getCurrentContext().startService(Intent(getCurrentContext(), LogConsoleService::class.java))
                activity?.finish()
            } else { //Permission is not available
                Toast.makeText(getCurrentContext(),
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show()

                activity?.finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun initPhotoList() {
        photoList.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.HORIZONTAL)
        mPhotosRvAdapter = PhotosRvAdapter(presenter.photoList)
        photoList?.adapter = mPhotosRvAdapter
    }

    override fun updatePhotoList(){
        mPhotosRvAdapter.notifyDataSetChanged()
    }
}