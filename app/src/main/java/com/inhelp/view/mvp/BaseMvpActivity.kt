package com.inhelp.view.mvp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseMvpActivity<in V : BaseMvpView, out T : BaseMvpPresenter<V>> : AppCompatActivity(), BaseMvpView {

    protected abstract val presenter: T



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(view = this as V)
    }

    override fun getCurrentContext(): Context = this

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show()
    }


    override fun showAlert(srtResId: Int) {
        //TODO alert
    }

    override fun showAlert(message: String) {
        //TODO alert
    }


    override fun getCurrentActivity(): androidx.fragment.app.FragmentActivity {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}