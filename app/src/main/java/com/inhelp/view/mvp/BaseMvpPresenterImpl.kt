package com.inhelp.view.mvp

open class BaseMvpPresenterImpl<V : BaseMvpView> : BaseMvpPresenter<V> {
    override fun onBackPress() {

    }

    protected var view: V? = null

    /**
     * Binds presenterMain with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenterMain
     */
    override fun attachView(view: V) {
        this.view = view
    }




    /**
     * Drops the reference to the view when destroyed
     */
    override fun detachView() {
        view = null
    }
}