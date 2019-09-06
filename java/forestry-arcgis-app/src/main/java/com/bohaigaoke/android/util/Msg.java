package com.bohaigaoke.android.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Msg {
	public static void showInfo(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static AlertDialog confirm(Context context, String msg,
			String title, MsgCallback callback) {
		return confirm(context, msg, title, callback, "aa", "aa");
	}

	public static AlertDialog confirm(Context context, String msg,
			String title, final MsgCallback callback, String ok, String cancel) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton(ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (callback != null)
					callback.callBack(true);
			}
		});
		builder.setNegativeButton(cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (callback != null)
					callback.callBack(false);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	public static AlertDialog showViewDialog(Context context, String title,
			View view, MsgCallback callback) {
		return showViewDialog(context, title, view, callback, "aa", "bvb");
	}

	public static AlertDialog showViewDialog(Context context, String title,
			View view, final MsgCallback callback, String ok, String cancel) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		if (!StringUtils.isNullOrEmpty(ok)) {
			builder.setPositiveButton(ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (callback != null) {
						callback.callBack(true);
					}
					dialog.dismiss();
				}
			});
		}
		if (!StringUtils.isNullOrEmpty(cancel)) {
			builder.setNegativeButton(cancel, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (callback != null) {
						callback.callBack(false);
					}
					dialog.dismiss();
				}
			});
		}
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	public static ProgressDialog showProgressDialog(Context context,
			String message, String title) {
		ProgressDialog dialog = ProgressDialog.show(context, title, message);
		dialog.setCancelable(true);
		return dialog;
	}

	public static AlertDialog showChoiceDialog(Context context, String title,
			CharSequence[] items, boolean multiChoice,
			final MsgCallback callback) {
		return showChoiceDialog(context, title, items, multiChoice, callback,
				"aa", "bbb");
	}

	public static AlertDialog showChoiceDialog(Context context, String title,
			final CharSequence[] items, boolean multiChoice,
			final MsgCallback callback, String ok, String cancel) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setNegativeButton(cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (callback != null)
					callback.callBack(false);
			}
		});
		if (multiChoice) {
			final ArrayList<Integer> selected = new ArrayList<Integer>();
			builder.setPositiveButton(ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (callback != null)
						callback.callBack(true, (Object[]) items,
								(Integer[]) selected.toArray());
				}
			});
			builder.setMultiChoiceItems(items, null,
					new OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1,
								boolean arg2) {
							if (!arg2 && selected.contains(arg1)) {
								selected.remove(arg1);
							} else if (arg2 && !selected.contains(arg1)) {
								selected.add(arg1);
							}
						}
					});
		} else {
			builder.setSingleChoiceItems(items, -1, new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if (callback != null) {
						callback.callBack(true, (Object[]) items, arg1);
					}
					arg0.dismiss();
				}
			});
		}
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	public static AlertDialog showChoiceDialog(Context context, String title,
			int itemsId, boolean multiChoice, final MsgCallback callback) {
		return showChoiceDialog(context, title, itemsId, multiChoice, callback,
				"aaa", "ddd");
	}

	public static AlertDialog showChoiceDialog(Context context, String title,
			int itemsId, boolean multiChoice, final MsgCallback callback,
			String ok, String cancel) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setNegativeButton(cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (callback != null)
					callback.callBack(false);
			}
		});
		if (multiChoice) {
			final ArrayList<Integer> selected = new ArrayList<Integer>();
			builder.setPositiveButton(ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (callback != null) {
						AlertDialog nowDialog = (AlertDialog) dialog;

						callback.callBack(true, nowDialog.getListView(),
								(Integer[]) selected.toArray());
					}
				}
			});
			builder.setMultiChoiceItems(itemsId, null,
					new OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1,
								boolean arg2) {
							if (!arg2 && selected.contains(arg1)) {
								selected.remove(arg1);
							} else if (arg2 && !selected.contains(arg1)) {
								selected.add(arg1);
							}
						}
					});
		} else {
			builder.setSingleChoiceItems(itemsId, -1, new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					AlertDialog dialog = (AlertDialog) arg0;
					if (callback != null) {
						callback.callBack(true, dialog.getListView(), arg1);
					}
					arg0.dismiss();
				}
			});
		}
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}
}
