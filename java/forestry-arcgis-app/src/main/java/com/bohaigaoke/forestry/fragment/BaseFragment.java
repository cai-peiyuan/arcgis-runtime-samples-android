package com.bohaigaoke.forestry.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Message;

public class BaseFragment extends Fragment {

	public BaseMessage baseMessage;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		baseMessage = (BaseMessage) activity;
	}

	public interface BaseMessage {
		public void sendMessage(Message msg);
	}
}
