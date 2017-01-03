package com.mchat.api.hx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ListView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.adapter.EaseConversationAdapter;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.mchat.api.hx.adapter.ConversationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CloudAnt on 2016/12/9.
 */

public class ConversationList extends ListView {
    protected int primaryColor;
    protected int secondaryColor;
    protected int timeColor;
    protected int primarySize;
    protected int secondarySize;
    protected float timeSize;
    protected final int MSG_REFRESH_ADAPTER_DATA = 0;

    protected Context context;
    protected ConversationAdapter adapter;
    protected List<EMConversation> conversations = new ArrayList<EMConversation>();
    protected List<EMConversation> passedListRef = null;


    public ConversationList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ConversationList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, com.hyphenate.easeui.R.styleable.EaseConversationList);
        primaryColor = ta.getColor(com.hyphenate.easeui.R.styleable.EaseConversationList_cvsListPrimaryTextColor, com.hyphenate.easeui.R.color.list_itease_primary_color);
        secondaryColor = ta.getColor(com.hyphenate.easeui.R.styleable.EaseConversationList_cvsListSecondaryTextColor, com.hyphenate.easeui.R.color.list_itease_secondary_color);
        timeColor = ta.getColor(com.hyphenate.easeui.R.styleable.EaseConversationList_cvsListTimeTextColor, com.hyphenate.easeui.R.color.list_itease_secondary_color);
        primarySize = ta.getDimensionPixelSize(com.hyphenate.easeui.R.styleable.EaseConversationList_cvsListPrimaryTextSize, 0);
        secondarySize = ta.getDimensionPixelSize(com.hyphenate.easeui.R.styleable.EaseConversationList_cvsListSecondaryTextSize, 0);
        timeSize = ta.getDimension(com.hyphenate.easeui.R.styleable.EaseConversationList_cvsListTimeTextSize, 0);

        ta.recycle();

    }

    public void init(Context context,List<EMConversation> conversationList){
        this.conversations = conversationList;
        adapter = new ConversationAdapter(context, conversationList);
        adapter.setCvsListHelper(conversationListHelper);
        adapter.setPrimaryColor(primaryColor);
        adapter.setPrimarySize(primarySize);
        adapter.setSecondaryColor(secondaryColor);
        adapter.setSecondarySize(secondarySize);
        adapter.setTimeColor(timeColor);
        adapter.setTimeSize(timeSize);
        setAdapter(adapter);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_REFRESH_ADAPTER_DATA:
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public EMConversation getItem(int position) {
        return adapter.getItem(position);
    }

    public void refresh() {
        if(!handler.hasMessages(MSG_REFRESH_ADAPTER_DATA)){
            handler.sendEmptyMessage(MSG_REFRESH_ADAPTER_DATA);
        }
    }


    private EaseConversationList.EaseConversationListHelper conversationListHelper;

    public interface EaseConversationListHelper{
        /**
         * set content of second line
         * @param lastMessage
         * @return
         */
        String onSetItemSecondaryText(EMMessage lastMessage);
    }
    public void setConversationListHelper(EaseConversationList.EaseConversationListHelper helper){
        conversationListHelper = helper;
    }
}
