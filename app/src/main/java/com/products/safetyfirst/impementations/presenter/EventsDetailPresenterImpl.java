package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.impementations.Interactor.EventsDetailInteractorImpl;
import com.products.safetyfirst.interfaces.interactor.EventsDetailInteractor;
import com.products.safetyfirst.interfaces.presenter.EventsDetailPresenter;
import com.products.safetyfirst.interfaces.view.EventsDetailView;
import com.products.safetyfirst.Pojos.EventModel;

/**
 * Created by vikas on 12/10/17.
 */

public class EventsDetailPresenterImpl implements EventsDetailPresenter, EventsDetailInteractor.OnUpdateFinishedListener {

    private EventsDetailView eventDetailView;
    private final EventsDetailInteractor eventDetailInteractor;
    private final String mEventKey;

    public EventsDetailPresenterImpl(EventsDetailView eventDetailView, String mEventKey) {
        this.eventDetailView = eventDetailView;
        this.mEventKey = mEventKey;
        this.eventDetailInteractor = new EventsDetailInteractorImpl(this);
    }
    @Override
    public void requestEvent() {
        eventDetailInteractor.requestEvent(mEventKey);
    }

    @Override
    public void getEvent(EventModel event) {
        if(eventDetailView!=null)
        eventDetailView.setEvent(event);
    }

    @Override
    public void onDestroy() {
        if(eventDetailView!=null)
         eventDetailView=null;
    }

    @Override
    public void setBookMark(String mEventKey) {
        eventDetailInteractor.setBookMark(mEventKey);
    }

    @Override
    public void setAction(int action) {
        eventDetailInteractor.setAction(mEventKey, action);
    }

    @Override
    public void onActionError(String message) {
        if(eventDetailView!=null)
            eventDetailView.onError(message);
    }

    @Override
    public void onActionSuccess() {
        if(eventDetailView!=null)
            eventDetailView.onSuccess();
    }

    @Override
    public void onError(String message) {
        if(eventDetailView!=null)
            eventDetailView.onError(message);
    }

    @Override
    public void onSuccess(String message) {
        if(eventDetailView!=null)
            eventDetailView.onSuccess();
    }
}
